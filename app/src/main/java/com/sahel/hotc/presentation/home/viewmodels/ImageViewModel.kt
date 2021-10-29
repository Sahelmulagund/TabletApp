package com.sahel.hotc.presentation.home.viewmodels

import android.graphics.BitmapFactory
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sahel.hotc.presentation.home.data.ImageModel
import java.io.File

class ImageViewModel:ViewModel() {

     var imageList = MutableLiveData<List<ImageModel>>()

    fun getImageList(files: List<File>){
        imageList.value = getImageModelsFromFiles(files)
    }

    fun getImageModelsFromFiles(files: List<File>): List<ImageModel> {
        return files.filter { it.absolutePath.contains(".jpg") || it.absolutePath.contains(".png")}.map {

            val myBitmap = it.getAbsolutePath()

            ImageModel(myBitmap)
        }
    }
}