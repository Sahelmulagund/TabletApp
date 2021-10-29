package com.sahel.hotc.presentation.home

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.sahel.hotc.R
import com.sahel.hotc.common.Constants

import com.sahel.hotc.presentation.home.adapter.VideoAdapter

import com.sahel.hotc.presentation.home.data.VideoModel
import kotlinx.android.synthetic.main.app_bar.*



import kotlinx.android.synthetic.main.fragment_videos.*
import java.io.File


class VideosFragment : Fragment() {
//    private lateinit var PATH: String
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_videos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ivArrowBack.setOnClickListener {
            (activity as HomeActivity).onBackPressed()
        }
        rvVideos.adapter = VideoAdapter(::itemClicked)
        rvVideos.setHasFixedSize(true)
        tvName.setText(R.string.video)
        loadData()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun itemClicked(videoModel: VideoModel) {

            (activity as HomeActivity).addReplaceFragment(ListVideosFragment(),2, Constants.folderName!!)

        Constants.videoFolderSelected = videoModel

    }

    private fun loadData(){
        try {
            val nameFolder = arguments?.getString(Constants.FOLDER_NAME,"")

            val fileName = nameFolder?.let { (activity as HomeActivity).getFilesFromPath(it) }
            val file = fileName?.get(0)

            file?.listFiles()?.filter {
                it.name == Constants.VIDEO
            }?.mapNotNull {
                it.listFiles()!!.forEach {
                    if (it.name.trim() == Constants.BACKGROUND){
                        Glide.with(requireContext()).load(it.listFiles()!!.get(0).absoluteFile).into(bgImg)
                    }
                }
            }
           Constants.folderName = file?.path


            val mainFile = file!!.listFiles()!!.filter {
                it.name.trim() == Constants.VIDEO.trim()


            }?.mapNotNull {
                    (rvVideos.adapter as VideoAdapter).videoList= getVideoModelsFromFiles(it.listFiles()!!.toList()
                        .sortedBy { it.name }.filter { it.name != Constants.BACKGROUND })


                }





        }catch (e:Exception){
            Toast.makeText(context,e.message, Toast.LENGTH_SHORT).show()
        }

    }
    fun getVideoModelsFromFiles(files: List<File>): List<VideoModel> {
        return files.map {

            var fileList:MutableList<File> = ArrayList<File>()
            it.listFiles()!!.forEach {
                if (it.absolutePath.contains(".mp4")){
                    fileList.add(it)
                }
            }

               var myBitmap = ""
              if (fileList.isNotEmpty()){
                  myBitmap = fileList.get(0).absolutePath
              }

               VideoModel(it.name,myBitmap)



        }
    }
}