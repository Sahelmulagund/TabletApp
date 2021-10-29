package com.sahel.hotc.presentation.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.doOnAttach
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.sahel.hotc.R
import com.sahel.hotc.common.Constants
import com.sahel.hotc.presentation.home.adapter.ImageAdapter
import com.sahel.hotc.presentation.home.adapter.SlideShowAdapter
import com.sahel.hotc.presentation.home.data.PhotoModel
import com.sahel.hotc.presentation.home.viewmodels.ImageViewModel
import kotlinx.android.synthetic.main.app_bar.*

import kotlinx.android.synthetic.main.fragment_slide_show.*


import java.io.File
import java.util.ArrayList

class SlideShowFragment : Fragment() {
    private lateinit var sliderHandler: Handler
    private var imageViewModel: ImageViewModel? = null
    private lateinit var sliderRunnable: Runnable
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_slide_show, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        imageViewModel = ViewModelProvider(this)[ImageViewModel::class.java]
        loadData()

    }

    private fun initView() {
        ivArrowBack.setOnClickListener {
            (activity as HomeActivity).onBackPressed()
        }
        viewPagerSlideShow.adapter = SlideShowAdapter()


         viewPagerSlideShow.post {
             viewPagerSlideShow.setCurrentItem(Constants.slidePosition!!,false)
         }

    }



    private fun loadData() {
        try {
            val fileName = (activity as HomeActivity).getFilesFromPath(Constants.folderName!!).filter {
                it.name == Constants.PHOTO
            }.map {f1->
                f1.listFiles()!!.filter {
                        f2->f2.name == Constants.photoFolderSelected!!.name
                }.map {
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