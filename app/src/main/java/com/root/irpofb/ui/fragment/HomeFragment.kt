package com.root.irpofb.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.root.irpofb.databinding.FragmentHomeBinding
import com.root.irpofb.interfac.RecyclerItemPosition
import com.root.irpofb.model.NewsTypeModel
import com.root.irpofb.ui.adapter.NewsTypeAdapter
import com.root.irpofb.ui.adapter.SliderViewPagerAdapter

class HomeFragment : BaseFragment() , RecyclerItemPosition{

    private lateinit var binding : FragmentHomeBinding
    val imageList = ArrayList<String>()
    private var newsTypeAdapter : NewsTypeAdapter? = null
    val newsTypeString = ArrayList<NewsTypeModel>()
    private val handler = Handler(Looper.getMainLooper())
    private val slideRunnable = object : Runnable {
        override fun run() {
            val nextItem = (binding.viewPagerMain.currentItem + 1) % imageList.size
            binding.viewPagerMain.setCurrentItem(nextItem, true)
            handler.postDelayed(this, 2000) // 3-second delay
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvHandIne.isSelected = true

        showSlider()

        showNewsType()


    }

    private fun showNewsType() {
        newsTypeString.add(NewsTypeModel("Who we are: IRPOF",true))
        newsTypeString.add(NewsTypeModel("Mission & Vision"))
        newsTypeString.add(NewsTypeModel("Recent Events"))
        newsTypeString.add(NewsTypeModel("Images"))
        newsTypeString.add(NewsTypeModel("Videos"))
        newsTypeAdapter = NewsTypeAdapter(requireContext(),newsTypeString, this@HomeFragment)
        binding.rvPostCategories.apply {
            adapter = newsTypeAdapter
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        }


    }

    private fun showSlider() {
        imageList.add("https://irpof.vercel.app/homeimage2.jpeg")
        imageList.add("https://irpof.vercel.app/homeimage1.jpeg")
        imageList.add("https://irpof.vercel.app/homeimage3.jpeg")
        binding.viewPagerMain.apply {
            adapter = SliderViewPagerAdapter(requireContext(),imageList)
        }
        handler.postDelayed(slideRunnable,2000)

    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(slideRunnable)
    }

    override fun getItemPosition(position: Int) {
        for (i in newsTypeString){
            i.isSelected = false
        }
        newsTypeString.get(position).isSelected = !newsTypeString.get(position).isSelected
        newsTypeAdapter?.notifyDataSetChanged()
        if (position==0){
            binding.layoutOne.visibility = View.VISIBLE
            binding.layoutTwo.visibility = View.GONE
        }else if (position==1){
            binding.layoutOne.visibility = View.GONE
            binding.layoutTwo.visibility = View.VISIBLE
        }

    }
}