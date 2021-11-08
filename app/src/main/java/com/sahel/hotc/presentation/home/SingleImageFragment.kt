package com.sahel.hotc.presentation.home

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.sahel.hotc.R
import com.sahel.hotc.common.Constants
import com.sahel.hotc.presentation.home.adapter.ImageAdapter
import com.sahel.hotc.presentation.home.adapter.SlideShowAdapter
import com.sahel.hotc.presentation.home.viewmodels.ImageViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.fragment_image.*
import kotlinx.android.synthetic.main.fragment_single_image.*



class SingleImageFragment : Fragment() {

    private lateinit var sliderHandler: Handler
    private var imageViewModel: ImageViewModel? = null
    private lateinit var sliderRunnable: Runnable

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_single_image, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

         initView()
        imageViewModel = ViewModelProvider(this)[ImageViewModel::class.java]
         loadData()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun initView() {
        try {
            ivArrowBack.setOnClickListener {
                (activity as HomeActivity).onBackPressed()

            }
            ivHome.setOnClickListener {
                (activity as HomeActivity).addReplaceFragment(HomeFragment(),1,Constants.FOLDER_NAME)
            }
            tvName.text = null
            viewPagerSlideShow.adapter = SlideShowAdapter()
            sliderHandler = Handler(Looper.getMainLooper())
            sliderRunnable = Runnable {
                if (viewPagerSlideShow.currentItem == (viewPagerSlideShow.adapter as SlideShowAdapter).itemCount) {
                    viewPagerSlideShow.currentItem = 0
                } else {
                    viewPagerSlideShow.currentItem = viewPagerSlideShow.currentItem + 1
                }
            }

            viewPagerSlideShow.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                private var currentPage: Int = 0
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    currentPage = position
                    sliderHandler.removeCallbacks(sliderRunnable)
                    sliderHandler.postDelayed(sliderRunnable, 3000)

                }
            })
        }catch (e:Exception){
            Log.v("ErrorImageFragment",e.message!!)
        }

    }

    private fun loadData() {
        try {

            val fileName = (activity as HomeActivity).getFilesFromPath(Constants.folderName!!).filter {
                it.name == Constants.PHOTO
            }.mapNotNull {f1->
                f1.listFiles()!!.filter {
                        f2->f2.name == Constants.photoFolderSelected!!.name
                }
                    .mapNotNull {
                            f3->
                        imageViewModel?.getImageList(f3.listFiles()!!.toList().filter { it.name != Constants.BACKGROUND })
                    }
            }


            imageViewModel?.imageList?.observe(viewLifecycleOwner,{
                (viewPagerSlideShow.adapter as SlideShowAdapter).mFiles = it
            })







        }catch (e:Exception){
            Toast.makeText(context,e.message, Toast.LENGTH_SHORT).show()
        }

    }
}