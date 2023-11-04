import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.cc.codingcamp.R
import com.cc.codingcamp.modal.Event

class EventAdapter(private val context: Context, var eventList: List<Event>) :
    RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    private lateinit var mListener: OnItemClickListener
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = eventList[position]

        Picasso.get().load(event.gambar).into(holder.gambarImageView)

        holder.judulEventTextView.text = event.judul_event
        holder.pelaksanaanTextView.text = event.pelaksanaan
        holder.lokasiTextView.text = event.lokasi
        holder.tanggalTextView.text = event.tanggal
        holder.idEventTextView.text = event.id_event

        holder.itemView.setOnClickListener {
            mListener.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val gambarImageView: ImageView = itemView.findViewById(R.id.gambar)
        val judulEventTextView: TextView = itemView.findViewById(R.id.judul_event)
        val pelaksanaanTextView: TextView = itemView.findViewById(R.id.pelaksanaan)
        val lokasiTextView: TextView = itemView.findViewById(R.id.lokasi)
        val tanggalTextView: TextView = itemView.findViewById(R.id.tanggal)
        val idEventTextView: TextView = itemView.findViewById(R.id.id_event)
    }
}