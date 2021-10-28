package com.sahel.hotc.common

import com.sahel.hotc.presentation.home.data.ImageModel
import com.sahel.hotc.presentation.home.data.PhotoModel
import java.io.File

object Constants {


    var imageSelected: ImageModel?=null
    var folderName: String?=null
    var photoFolderSelected: PhotoModel?=null
    const val FOLDER_NAME:  String = "folderName"
    const val PHOTO: String = "Photo"
    const val VIDEO:String="Video"
    const val PHOTOBG:String="PhotoBg"
    const val VIDEOBG:String="VideoBg"
    const val BACKGROUND = "Background"

    enum class FileType {
        FILE,
        FOLDER;

        companion object {
            fun getFileType(file: File) = when (file.isDirectory) {
                true -> FOLDER
                false -> FILE
            }
        }
    }
}