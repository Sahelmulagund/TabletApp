package com.sahel.hotc.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.sahel.hotc.R
import com.sahel.hotc.common.Constants

import com.sahel.hotc.presentation.home.adapter.VideoAdapter

import com.sahel.hotc.presentation.home.data.VideoModel
import kotlinx.android.synthetic.main.app_bar.*



import kotlinx.android.synthetic.main.fragment_videos.*
import java.io.File


class VideosFragment : Fragment() {
    private lateinit var PATH: String
    companion object {
        private const val ARG_PATH: String = "HOTC"
        fun build(block: Builder.() -> Unit) = Builder().apply(block).build()
    }

    class Builder {
        var path: String = ""

        fun build(): HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle()
            args.putString(ARG_PATH, path)
            fragment.arguments = args;
            return fragment
        }
    }

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

    private fun itemClicked(videoModel: VideoModel) {

    }

    private fun loadData(){
        try {
            val nameFolder = arguments?.getString(Constants.FOLDER_NAME,"")

            val fileName = nameFolder?.let { (activity as HomeActivity).getFilesFromPath(it) }
            val file = fileName?.get(0)
            file?.listFiles()?.filter {
                it.name == Constants.BACKGROUND
            }?.mapNotNull {
                it.listFiles()!!.forEach {
                    if (it.name.trim() == Constants.VIDEO){
                        Glide.with(requireContext()).load(it.listFiles()!!.get(0).absoluteFile).into(bgImg)
                    }
                }
            }
           Constants.folderName = file?.path


            val mainFile = file!!.listFiles()!!.filter {
                it.name.trim() == Constants.BACKGROUND.trim()


            }?.mapNotNull {
                it.listFiles()!!.filter {
                    it.name.trim() == Constants.VIDEOBG
                }.mapNotNull {
                    (rvVideos.adapter as VideoAdapter).videoList= getVideoModelsFromFiles(it.listFiles()!!.toList().sortedBy { it.name })

                }



            }

        }catch (e:Exception){
            Toast.makeText(context,e.message, Toast.LENGTH_SHORT).show()
        }

    }
    fun getVideoModelsFromFiles(files: List<File>): List<VideoModel> {
        return files.map {

            val myBitmap = it.listFiles()?.get(0)?.absolutePath!!
            VideoModel(it.name,myBitmap)
        }
    }
}