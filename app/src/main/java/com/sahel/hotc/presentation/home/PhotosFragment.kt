package com.sahel.hotc.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.sahel.hotc.R
import com.sahel.hotc.common.Constants
import com.sahel.hotc.presentation.home.adapter.PhotoAdapter
import com.sahel.hotc.presentation.home.data.FileModel
import com.sahel.hotc.presentation.home.data.PhotoModel
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_photosragment.*
import java.io.File


class PhotosFragment : Fragment() {
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

        return inflater.inflate(R.layout.fragment_photosragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as HomeActivity).relAppBar.visibility = View.VISIBLE
        rvPhotos.adapter = PhotoAdapter()
        rvPhotos.setHasFixedSize(true)
        loadData()
    }

    private fun loadData() {
        try {
            val nameFolder = arguments?.getString(Constants.FOLDER_NAME,"")
            val fileName = nameFolder?.let { (activity as HomeActivity).getFilesFromPath(it) }
            val file = fileName?.get(0)
            val mainFile = file?.listFiles()?.filter {
                it.name == Constants.PHOTO
            }?.map {
                (rvPhotos.adapter as PhotoAdapter).photoList= getPhotoModelsFromFiles(it.listFiles()!!.toList())
            }

        }catch (e:Exception){
            Toast.makeText(context,e.message,Toast.LENGTH_SHORT).show()
        }

    }
    fun getPhotoModelsFromFiles(files: List<File>): List<PhotoModel> {
        return files.map {
            PhotoModel(it.name,R.drawable.img1)
        }
    }

}