package com.sahel.hotc.common

import java.io.File

object Constants {


    const val FOLDER_NAME:  String = "folderName"
    const val PHOTO: String = "Photo"
    const val VIDEO:String="Video"

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