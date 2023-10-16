import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.test.learnactivity.R
import com.test.learnactivity.modal.ProductItem

class ProductAdapter(private val context: Context, private val productList: List<ProductItem>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.data_recourse, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val productItem = productList[position]

        holder.imageView = holder.itemView.findViewById(R.id.gambar)
        holder.titleTextView = holder.itemView.findViewById(R.id.judul)
        holder.priceTextView = holder.itemView.findViewById(R.id.harga)

        holder.imageView.setImageResource(productItem.imageResource)
        holder.titleTextView.text = productItem.title
        holder.priceTextView.text = productItem.price
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var imageView: ImageView
        lateinit var titleTextView: TextView
        lateinit var priceTextView: TextView
    }
}

