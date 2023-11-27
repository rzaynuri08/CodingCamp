package com.cc.codingcamp.UI.fragment.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.airbnb.lottie.LottieAnimationView
import com.cc.codingcamp.R
import com.cc.codingcamp.UI.activity.PaymentActivity

class SuccessnotificationDialog : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_successnotification, container, false)


        return view
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        handleDialogDismissed()
    }

    private fun handleDialogDismissed() {
        // Lakukan tindakan yang diinginkan setelah dialog ditutup
        (activity as? PaymentActivity)?.onSuccessNotificationDialogDismissed()
    }
}
