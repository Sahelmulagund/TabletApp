package com.sahel.hotc.common

import com.sahel.hotc.presentation.home.data.ImageModel
import com.sahel.hotc.presentation.home.data.PhotoModel
import com.sahel.hotc.presentation.home.data.ThumbnailModel
import com.sahel.hotc.presentation.home.data.VideoModel
import java.io.File

object Constants {


    var slidePosition: Int?=null
    var videoSelected: ThumbnailModel?=null
    var videoFolderSelected: VideoModel?=null
    var imageSelected: ImageModel?=null
    var folderName: String?=null
    var photoFolderSelected: PhotoModel?=null
    const val FOLDER_NAME:  String = "/emulated/0/HOTC/"
    const val PHOTO: String = "Photos"
    const val VIDEO:String="Videos"
    const val PHOTOBG:String="PhotoBg"
    const val VIDEOBG:String="VideoBg"
    const val BACKGROUND = "Backgrounds"
    const val THUMBNAIL = "Thumbnails"

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