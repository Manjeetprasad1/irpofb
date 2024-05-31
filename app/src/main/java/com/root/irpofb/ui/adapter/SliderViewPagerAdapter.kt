package com.root.irpofb.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.NonNull
import androidx.viewpager.widget.PagerAdapter
import com.root.irpofb.R
import com.root.irpofb.extension.loadURL


class SliderViewPagerAdapter(private val context: Context, private val imagesUrlList: List<String>) : PagerAdapter() {

    private val mLayoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return imagesUrlList.size
    }

    override fun isViewFromObject(@NonNull view: View, @NonNull `object`: Any): Boolean {
        return view == `object` as LinearLayout
    }

    @SuppressLint("MissingInflatedId")
    @NonNull
    override fun instantiateItem(@NonNull container: ViewGroup, position: Int): Any {
        // inflating the item.xml
        val itemView = mLayoutInflater.inflate(R.layout.item_slider_image, container, false)

        // referencing the image view from the item.xml file
        val imageView = itemView.findViewById<ImageView>(R.id.imageViewMain)

        // setting the image in the imageView
        val imageUrl = imagesUrlList[position]
        //Glide.with(context).load(imageUrl).into(imageView)
        imageView.loadURL(imageUrl,true)

        // Adding the View
        container.addView(itemView)

        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }
}