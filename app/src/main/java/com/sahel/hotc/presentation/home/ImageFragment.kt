package com.sahel.hotc.presentation.home

import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.sahel.hotc.R
import com.sahel.hotc.common.Constants
import com.sahel.hotc.presentation.home.adapter.ImageAdapter
import com.sahel.hotc.presentation.home.adapter.PhotoAdapter
import com.sahel.hotc.presentation.home.data.ImageModel
import com.sahel.hotc.presentation.home.viewmodels.ImageViewModel
import kotlinx.android.synthetic.main.app_bar.*

import kotlinx.android.synthetic.main.fragment_image.*

import java.io.File


class ImageFragment : Fragment() {

//    companion object {
//        private const val ARG_PATH: String = "HOTC"
//        fun build(block: Builder.() -> Unit) = Builder().apply(block).build()
//    }
//
//    class Builder {
//        var path: String = ""
//
//        fun build(): HomeFragment {
//            val fragment = HomeFragment()
//            val args = Bundle()
//            args.putString(ARG_PATH, path)
//            fragment.arguments = args;
//            return fragment
//        }
//    }
    private var imageViewModel:ImageViewModel?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       initView()
        imageViewModel = ViewModelProvider(this)[ImageViewModel::class.java]
        loadData()
    }

    private fun initView() {
        tvName.text = Constants.photoFolderSelected?.name
        ivArrowBack.setOnClickListener {
            (activity as HomeActivity).onBackPressed()
        }

        rvImage.adapter = ImageAdapter(::imageClicked)
        rvImage.setHasFixedSize(true)
        rvImage.layoutManager = GridLayoutManager(requireContext(),4,RecyclerView.VERTICAL,false)


    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun imageClicked(imageModel: ImageModel,pos:Int) {
        Constants.imageSelected = imageModel
        Constants.slidePosition = pos
        (activity as HomeActivity).addReplaceFragment(SlideShowFragment(),2,Constants.folderName!!)
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
                (rvImage.adapter as ImageAdapter).imageList = it
            })



        }catch (e:Exception){
            Toast.makeText(context,e.message, Toast.LENGTH_SHORT).show()
        }
    }

}