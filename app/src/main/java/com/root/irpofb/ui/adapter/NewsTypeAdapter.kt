package com.root.irpofb.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.root.irpofb.R
import com.root.irpofb.databinding.ItemNewsTypeBinding
import com.root.irpofb.extension.changeButtonState
import com.root.irpofb.interfac.RecyclerItemPosition
import com.root.irpofb.model.NewsTypeModel

class NewsTypeAdapter(
    val context: Context,
    val newsTypeList: List<NewsTypeModel>,
    val recyclerItemPosition: RecyclerItemPosition
) :
    RecyclerView.Adapter<NewsTypeAdapter.MyViewHolder>(){


    inner class MyViewHolder(val binding: ItemNewsTypeBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(newsTypeModel: NewsTypeModel) {
            binding.tvNewsType.text = newsTypeModel.typeString


            changeButtonState(binding.layout,newsTypeModel.isSelected,context)
            if (newsTypeModel.isSelected){
                binding.tvNewsType.setTextColor(context.getColor(R.color.white))
            }else{
                binding.tvNewsType.setTextColor(context.getColor(R.color.black_text_color))
            }

            binding.layout.setOnClickListener {
                recyclerItemPosition.getItemPosition(layoutPosition)
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsTypeAdapter.MyViewHolder {
        val itemMessageBinding = ItemNewsTypeBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyViewHolder(itemMessageBinding)
    }

    override fun onBindViewHolder(holder: NewsTypeAdapter.MyViewHolder, position: Int) {
        holder.bind(newsTypeList[position])
    }

    override fun getItemCount(): Int {
        return newsTypeList.size
    }
}