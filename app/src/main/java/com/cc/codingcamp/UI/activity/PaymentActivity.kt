package com.cc.codingcamp.UI.activity

import android.app.Activity
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import com.cc.codingcamp.API.ApiService
import com.cc.codingcamp.API.Service
import com.cc.codingcamp.R
import com.cc.codingcamp.UI.fragment.dialog.SuccessnotificationDialog
import com.cc.codingcamp.modal.Payment
import com.squareup.picasso.Picasso
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class PaymentActivity : AppCompatActivity() {

    private lateinit var fileUploadService: ApiService
    private lateinit var imageView: ImageView
    private lateinit var totalPembayaran: TextView
    private lateinit var namaPembayaran: TextView
    private lateinit var gambarPembayaran: ImageView
    private lateinit var noRek: TextView
    private lateinit var idPayment: TextView
    private lateinit var successNotificationDialog: SuccessnotificationDialog
    private var successNotificationDialogDismissed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        // Inisialisasi Retrofit Service
        fileUploadService = Service.apiService
        successNotificationDialog = SuccessnotificationDialog()

        imageView = findViewById(R.id.img_buktipembayaran)
        totalPembayaran = findViewById(R.id.txt_totalpayment)
        namaPembayaran = findViewById(R.id.txt_namapayment)
        gambarPembayaran = findViewById(R.id.img_gambarpayment)
        noRek = findViewById(R.id.txt_norek)
        idPayment = findViewById(R.id.txt_idpayment)

        val uploadButton: Button = findViewById(R.id.btnUpload)
        uploadButton.setOnClickListener {
            uploadImage()
        }

        val ChoosePhoto: ImageView = findViewById(R.id.img_buktipembayaran)
        ChoosePhoto.setOnClickListener{
            choosePhotoFromGallery()
        }

        val txtNorek: TextView = findViewById(R.id.txt_norek)
        val btnCopy: Button = findViewById(R.id.btn_copy)

        btnCopy.setOnClickListener {
            // Mendapatkan teks dari TextView
            val norekText = txtNorek.text.toString()

            // Membuat ClipboardManager
            val clipboardManager =
                getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

            // Menyalin teks ke clipboard
            clipboardManager.setPrimaryClip(
                android.content.ClipData.newPlainText("Norek", norekText)
            )

            // Menampilkan pesan sukses
            Toast.makeText(this, "berhasil disalin", Toast.LENGTH_SHORT).show()
        }

        val idTransaksi = intent.getStringExtra("idTransaksi")
        Toast.makeText(this, idTransaksi, Toast.LENGTH_SHORT).show()
        if (idTransaksi != null) {
            getDataPayment(idTransaksi)
        }
    }

    private fun getDataPayment(idTransaksi: String) {
        val apiService = Service.apiService

        apiService.getDataPayment(idTransaksi).enqueue(object : Callback<List<Payment>> {
            override fun onResponse(call: Call<List<Payment>>, response: Response<List<Payment>>) {
                if (response.isSuccessful) {
                    // Handle response
                    val paymentList = response.body()
                    if (paymentList != null && paymentList.isNotEmpty()) {
                        val payment = paymentList[0]
                        idPayment.text = payment.id_transaksi
                        totalPembayaran.text = payment.total
                        namaPembayaran.text = payment.nama_pembayaran
                        Picasso.get().load(payment.icon).into(gambarPembayaran)
                        noRek.text = payment.no_rekening
                    }
                } else {
                    // Handle failure
                    Toast.makeText(this@PaymentActivity, "Failed to get payment data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Payment>>, t: Throwable) {
                // Handle failure (e.g., network error)
                Toast.makeText(this@PaymentActivity, "Failed to connect to the server", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun choosePhotoFromGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, REQUEST_IMAGE_PICK)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQUEST_IMAGE_PICK -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val selectedImageUri: Uri? = data.data
                    imageView.setImageURI(selectedImageUri)
                }
            }
        }
    }

    private fun uploadImage() {
        // Convert ImageView to File
        val file = convertImageViewToFile()

        // Menentukan jenis media berdasarkan ekstensi file
        val mediaType: MediaType? = when {
            file.name.endsWith(".jpeg") || file.name.endsWith(".jpg") -> "image/jpeg".toMediaTypeOrNull()
            file.name.endsWith(".png") -> "image/png".toMediaTypeOrNull()
            else -> null
        }

        if (mediaType != null) {
            val buktiPembayaran = RequestBody.create(mediaType, file)
            val buktiPembayaranPart =
                MultipartBody.Part.createFormData("bukti_pembayaran", file.name, buktiPembayaran)

            // Mendapatkan id transaksi
            val idTransaksi =
                RequestBody.create("text/plain".toMediaTypeOrNull(), "${idPayment.text}")

            // Melakukan request untuk upload gambar
            val call = fileUploadService.uploadImage(buktiPembayaranPart, idTransaksi)

            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    // Handle response
                    if (response.isSuccessful) {
                        Toast.makeText(this@PaymentActivity, "Berhasil mengunggah gambar", Toast.LENGTH_SHORT).show()
                        successNotificationDialogDismissed = false // Set ulang flag
                        successNotificationDialog.show(supportFragmentManager, "SuccessnotificationDialog")
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    // Handle failure
                    Toast.makeText(
                        this@PaymentActivity,
                        "Gagal mengunggah gambar",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        } else {
            // Handle unsupported file type
            Toast.makeText(this, "Jenis file tidak didukung", Toast.LENGTH_SHORT).show()
        }
    }

    private fun convertImageViewToFile(): File {
        val bitmap = (imageView.drawable).toBitmap()
        val filesDir = applicationContext.filesDir
        val imageFile = File(filesDir, "temp_image.jpg")

        try {
            FileOutputStream(imageFile).use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return imageFile
    }

    fun onSuccessNotificationDialogDismissed() {
        finish()
    }

    companion object {
        private const val REQUEST_IMAGE_PICK = 1
    }
}
