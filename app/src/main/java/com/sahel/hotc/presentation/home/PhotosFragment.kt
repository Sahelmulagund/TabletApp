package com.sahel.hotc.presentation.home

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.sahel.hotc.R
import com.sahel.hotc.common.Constants
import com.sahel.hotc.presentation.home.adapter.PhotoAdapter
import com.sahel.hotc.presentation.home.data.FileModel
import com.sahel.hotc.presentation.home.data.PhotoModel
import com.sahel.hotc.presentation.home.data.VideoModel
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.fragment_photosragment.*
import java.io.File
import java.util.*
import kotlin.random.Random


class PhotosFragment : Fragment() {
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

        return inflater.inflate(R.layout.fragment_photosragment, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ivArrowBack.setOnClickListener {
            (activity as HomeActivity).onBackPressed()
        }
        ivHome.setOnClickListener {
            (activity as HomeActivity).addReplaceFragment(HomeFragment(),1,Constants.FOLDER_NAME)
        }
        rvPhotos.adapter = PhotoAdapter(::itemClicked)
        rvPhotos.setHasFixedSize(true)
        tvName.setText(Constants.PHOTO)
        loadData()

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun itemClicked(photoModel: PhotoModel) {
        (activity as HomeActivity).addReplaceFragment(ImageFragment(),2, Constants.folderName!!)
        Constants.photoFolderSelected = photoModel
    }

    private fun loadData() {
        try {
            val nameFolder = arguments?.getString(Constants.FOLDER_NAME, "")

            val fileName = nameFolder?.let { (activity as HomeActivity).getFilesFromPath(it) }!!
            //Getting Main Folder Under HOTC
            val file = fileName?.get(0)
            file.listFiles()?.filter {
                it.name == Constants.PHOTO
            }?.mapNotNull {
                it.listFiles()!!.forEach {
                    if (it.name.trim() == Constants.BACKGROUND) {
                        Glide.with(requireContext()).load(it.listFiles()!!.get(0).absoluteFile)
                            .into(bgImg)
                        val bundle = Bundle()
                        bundle.putString(Constants.PHOTOBG, it.listFiles()!![0].absolutePath)
                    }
                }
            }
            Constants.folderName = file?.path
            val mainFile = file?.listFiles()?.filter {
                it.name.trim() == Constants.PHOTO
            }?.mapNotNull {

                (rvPhotos.adapter as PhotoAdapter).photoList =
                    getPhotoModelsFromFiles(it.listFiles()!!.toList().sortedBy { it.name }.filter { it.name != Constants.BACKGROUND })

            }


        }catch (e:Exception){
            Toast.makeText(context,e.message,Toast.LENGTH_SHORT).show()
        }

    }
    fun getPhotoModelsFromFiles(files: List<File>): List<PhotoModel> {
        return files.map {
            var fileList:MutableList<File> = ArrayList<File>()
            it.listFiles()!!.forEach {
                if (it.absolutePath.contains(".jpg") || it.absolutePath.contains(".png")){
                    fileList.add(it)
                }
            }

            var myBitmap = ""
            if (fileList.isNotEmpty()){
                myBitmap = fileList.get(0).absolutePath
            }

            PhotoModel(it.name,myBitmap)



        }
        }
    }

