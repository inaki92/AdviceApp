package com.inaki.dailyAdvice.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inaki.dailyAdvice.Model.AdviceModel
import com.inaki.dailyAdvice.R
import kotlinx.android.synthetic.main.advice_item.view.*

class AdviceSearchedAdapter(private val context: Context, private val advice: AdviceModel):
    RecyclerView.Adapter<AdviceSearchedAdapter.AdviceViewHolder>() {

    class AdviceViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var advice = itemView.advice!!
        var adviceId = itemView.category!!
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdviceViewHolder {
        val searchItemView = LayoutInflater.from(context).inflate(R.layout.advice_item,parent,false)
        return AdviceViewHolder(searchItemView)
    }

    override fun getItemCount(): Int {
        return advice.slips.size
    }

    override fun onBindViewHolder(holder: AdviceViewHolder, position: Int) {
        holder.advice.text = advice.slips[position].advice
        holder.adviceId.text = advice.slips[position].slipId
    }
}