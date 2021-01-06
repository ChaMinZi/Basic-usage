package com.example.maskinfo

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.maskinfo.model.Store
import java.lang.String


class StoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var nameTextView : TextView = itemView.findViewById(R.id.name_text_view)
    var addressTextView : TextView = itemView.findViewById(R.id.addr_text_view)
    var distanceTextView : TextView = itemView.findViewById(R.id.distance_text_view)
    var remainTextView : TextView = itemView.findViewById(R.id.remain_text_view)
    var countTextView : TextView = itemView.findViewById(R.id.count_text_view)
}

class StoreAdapter : RecyclerView.Adapter<StoreViewHolder>() {
    private var mItems: List<Store> = ArrayList()

    fun updateItems(items: List<Store>) {
        mItems = items
        notifyDataSetChanged() // UI 갱신
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_store, parent, false)
        return StoreViewHolder(view)
    }

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        val store = mItems[position]
        holder.nameTextView.text = store.name
        holder.addressTextView.text = store.addr

        var remainStat = "충분"
        var count = "100개 이상"
        var color: Int = Color.GREEN

        when (store.remain_stat) {
            "plenty" -> {
                count = "100개 이상"
                remainStat = "충분"
                color = Color.GREEN
            }
            "safe" -> {
                count = "30개 이상"
                remainStat = "여유"
                color = Color.YELLOW
            }
            "few" -> {
                count = "2개 이상"
                remainStat = "매진 임박"
                color = Color.RED
            }
            "empty" -> {
                count = "1개 이하"
                remainStat = "재고 없음"
                color = Color.GRAY
            }
            else -> {
            }
        }
        holder.countTextView.text = count
        holder.countTextView.setTextColor(color)
        holder.remainTextView.text = remainStat
        holder.remainTextView.setTextColor(color)
    }


    override fun getItemCount() = mItems.size

}