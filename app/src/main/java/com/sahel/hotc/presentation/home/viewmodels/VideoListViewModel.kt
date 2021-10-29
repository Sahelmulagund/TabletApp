package com.sahel.hotc.presentation.home.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sahel.hotc.presentation.home.data.ImageModel
import com.sahel.hotc.presentation.home.data.ThumbnailModel
import com.sahel.hotc.presentation.home.data.VideoModel
import java.io.File

class VideoListViewModel:ViewModel() {
    var videoList = MutableLiveData<List<ThumbnailModel>>()

    fun getVideoList(files: List<File>){
        videoList.value = getVideoModelsFromFiles(files)
    }

    fun getVideoModelsFromFiles(files: List<File>): List<ThumbnailModel> {
        return files.filter { it.absolutePath.contains(".mp4") }.map {

            val myBitmap = it.absolutePath

            ThumbnailModel(myBitmap,it.name)
        }
    }
}