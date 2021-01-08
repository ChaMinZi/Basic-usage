package com.example.maskinfo

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.maskinfo.databinding.ItemStoreBinding
import com.example.maskinfo.model.Store
import java.lang.String


class StoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val binding = ItemStoreBinding.bind(itemView)
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
        holder.binding.store = mItems[position]
    }

    override fun getItemCount() = mItems.size
}

@BindingAdapter("remainStat")
fun setRemainStat(textView: TextView, store: Store) = when (store.remain_stat) {
    "plenty" -> textView.text = "충분"
    "safe" -> textView.text = "여유"
    "few" -> textView.text = "매진 임박"
    else -> textView.text = "재고 없음"
}

@BindingAdapter("count")
fun setCount(textView: TextView, store: Store) = when (store.remain_stat) {
    "plenty" -> textView.text = "100개 이상"
    "safe" -> textView.text = "30개 이상"
    "few" -> textView.text = "2개 이상"
    else -> textView.text = "1개 이하"
}

@BindingAdapter("color")
fun setColor(textView: TextView, store: Store) = when (store.remain_stat) {
    "plenty" -> textView.setTextColor(Color.GREEN)
    "safe" -> textView.setTextColor(Color.YELLOW)
    "few" -> textView.setTextColor(Color.RED)
    else -> textView.setTextColor(Color.GRAY)
}