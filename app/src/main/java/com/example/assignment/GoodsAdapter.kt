package com.example.assignment

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class GoodsAdapter (val goodsList: ArrayList<GoodsForm>,
                    val onClickImage: (list: GoodsForm) -> Unit):
    RecyclerView.Adapter<GoodsAdapter.CustomViewHolder>()
{
    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int): GoodsAdapter.CustomViewHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_layout,parent, false)
        return CustomViewHolder(view)
    }

    class CustomViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val image = itemView.findViewById<ImageView>(R.id.ImageView_card)
        val title = itemView.findViewById<TextView>(R.id.TextView_title)
        }

    override fun onBindViewHolder(holder: GoodsAdapter.CustomViewHolder, position: Int){
        val listPosition = goodsList[position]
        holder.image.setImageDrawable(goodsList.get(position).image )
        holder.title.text = goodsList.get(position).title.toString()
        holder.image.setOnClickListener{
            onClickImage(listPosition)
        }
    }

    override fun getItemCount(): Int {
        return goodsList.size
    }
}
