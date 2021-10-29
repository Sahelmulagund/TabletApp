package com.sahel.hotc.presentation.home

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sahel.hotc.R
import com.sahel.hotc.common.Constants
import com.sahel.hotc.presentation.home.adapter.ImageAdapter
import com.sahel.hotc.presentation.home.adapter.VideoListAdapter
import com.sahel.hotc.presentation.home.data.ThumbnailModel
import com.sahel.hotc.presentation.home.viewmodels.ImageViewModel
import com.sahel.hotc.presentation.home.viewmodels.VideoListViewModel
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.fragment_list_videos.*
import kotlinx.android.synthetic.main.recycler_video_list.*


class ListVideosFragment : Fragment() {



    private var thumbNailViewModel: VideoListViewModel?=null
   

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_videos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        thumbNailViewModel = ViewModelProvider(this)[VideoListViewModel::class.java]
        loadData()
    }

    private fun initView() {
        tvName.text = Constants.videoFolderSelected?.name
        ivArrowBack.setOnClickListener {
            (activity as HomeActivity).onBackPressed()
        }

        rvVideos.adapter = VideoListAdapter(::videoClicked)
        rvVideos.setHasFixedSize(true)


    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun videoClicked(videoModel: ThumbnailModel) {
        Constants.videoSelected = videoModel
        (activity as HomeActivity).addReplaceFragment(SingleVideoFragment(),2,Constants.folderName!!)
    }

    private fun loadData() {
        try {
            //For background
            val listBg = (activity as HomeActivity).getFilesFromPath(Constants.folderName!!).filter {
                it.name == Constants.VIDEO
            }.mapNotNull {f1->
                f1.listFiles()!!.filter {
                        f2->f2.name == Constants.BACKGROUND
                }.mapNotNull {
                        f3->
                     f3.listFiles()!!.forEach {
                         if (it.name.trim() == Constants.videoFolderSelected!!.name.trim()){
                             Glide.with(requireContext()).load(it.listFiles()!!.get(0).absoluteFile).into(bgImg)
                         }
                     }
                }
            }

            //Video list thumbnails
            val fileName = (activity as HomeActivity).getFilesFromPath(Constants.folderName!!).filter {
                it.name == Constants.VIDEO
            }.mapNotNull {f1->
                f1.listFiles()!!.filter {
                        f2->f2.name == Constants.videoFolderSelected!!.name
                }.mapNotNull {
                        f3->
                    thumbNailViewModel?.getVideoList(f3.listFiles()!!.toList().filter{ it.name != Constants.BACKGROUND })
                }
            }



            thumbNailViewModel?.videoList?.observe(viewLifecycleOwner,{
                (rvVideos.adapter as VideoListAdapter).videoList = it
            })



        }catch (e:Exception){
            Toast.makeText(context,e.message, Toast.LENGTH_SHORT).show()
        }
    }

}