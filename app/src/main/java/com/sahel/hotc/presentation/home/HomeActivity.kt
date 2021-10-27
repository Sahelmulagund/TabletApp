package com.sahel.hotc.presentation.home

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.Environment.getStorageDirectory
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import com.sahel.hotc.R
import com.sahel.hotc.common.Constants
import com.sahel.hotc.presentation.home.data.FileModel
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileOutputStream

class HomeActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private val filepaths = "HOTC"
    internal var myExternalFile: File? = null
    private val isExternalStorageReadOnly: Boolean
        get() {
            val extStorageState = Environment.getExternalStorageState()
            return Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)
        }
    private val isExternalStorageAvailable: Boolean
        get() {
            val extStorageState = Environment.getExternalStorageState()
            return Environment.MEDIA_MOUNTED.equals(extStorageState)
        }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        WindowInsetsControllerCompat(window, activitySplash).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            controller.isAppearanceLightStatusBars = true
        }
        myExternalFile = File(getExternalFilesDir(filepaths), "")


        if (savedInstanceState == null) {
            val filesListFragment = HomeFragment.build {
                path = File(getExternalFilesDir(filepaths), "").absolutePath
            }
            val bundle = Bundle()
            supportFragmentManager.beginTransaction().replace(R.id.nav_fragment, filesListFragment)
                .addToBackStack(File(getExternalFilesDir(filepaths), "").absolutePath)
                .commit()


        }

    }
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun addReplaceFragment(fragment: Fragment?, addOrReplace: Int, backStackValue: String) {
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        when (addOrReplace) {
            1 -> {

                transaction.replace(nav_fragment.id, fragment!!)
                manager.popBackStack()
                transaction.commit()

            }
            2 -> {

                val bundle = Bundle()
                bundle.putString(Constants.FOLDER_NAME,backStackValue)
                fragment?.arguments = bundle
                transaction.replace(nav_fragment.id, fragment!!)
                transaction.addToBackStack(File(getExternalFilesDir(filepaths), "").absolutePath)
                transaction.commit()

            }
            else -> {

                transaction.replace(nav_fragment.id, fragment!!)
                manager.popBackStack()
                transaction.commit()

            }
        }
    }
    fun getFilesFromPath(path: String, showHiddenFiles: Boolean = true, onlyFolders: Boolean = true): List<File> {
        val file = File(path)
        return file.listFiles()
            .filter { onlyFolders }
            .toList()
    }
    fun getFileModelsFromFiles(files: List<File>): List<FileModel> {
        return files.map {
            FileModel(it.path, Constants.FileType.getFileType(it), it.name, convertFileSizeToMB(it.length()), it.extension, it.listFiles()?.size
                ?: 0)
        }
    }

    fun convertFileSizeToMB(sizeInBytes: Long): Double {
        return (sizeInBytes.toDouble()) / (1024 * 1024)
    }

}