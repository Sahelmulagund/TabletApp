package com.sahel.hotc.presentation.home.data

import com.sahel.hotc.common.Constants


data class FileModel(
    val path: String,
    val fileType: Constants.FileType,
    val name: String,
    val sizeInMB: Double,
    val extension: String = "",
    val subFiles: Int = 0
)
