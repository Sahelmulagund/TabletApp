package com.sahel.hotc.presentation.home

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.media.AudioManager
import android.media.AudioMetadata
import android.media.VolumeProvider
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Layout
import android.view.*
import android.widget.MediaController
import android.widget.SeekBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.sahel.hotc.R
import com.sahel.hotc.common.Constants
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.fragment_list_videos.*
import kotlinx.android.synthetic.main.fragment_single_video.*
import kotlinx.android.synthetic.main.vod_controller.*
import java.io.File


class SingleVideoFragment : Fragment() {

    private var player: SimpleExoPlayer? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_single_video, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState != null) {
            onSaveInstanceState(savedInstanceState)
        }
        tvName.text = null
        ivArrowBack.setOnClickListener {
            (activity as HomeActivity).onBackPressed()
        }
        ivHome.setOnClickListener {
            (activity as HomeActivity).addReplaceFragment(HomeFragment(),1,Constants.FOLDER_NAME)
        }

        loadData()


    }


    @RequiresApi(Build.VERSION_CODES.P)
    private fun loadData() {
        try {


            //Video list thumbnails
            val fileName = (activity as HomeActivity).getFilesFromPath(Constants.folderName!!).filter {
                it.name == Constants.VIDEO
            }.mapNotNull { f1 ->
                f1.listFiles()!!.filter { f2 ->
                    f2.name == Constants.videoFolderSelected!!.name
                }.mapNotNull { f3 ->
                    f3.listFiles()!!.filter {
                       it.nameWithoutExtension == Constants.videoSelected!!.name!!.removeRange(
                           Constants.videoSelected!!.name.indexOf('.'),
                           Constants.videoSelected!!.name.length)
                    }
                        .mapNotNull {

//                           videoPlayer.setVideoURI(Uri.parse(it.path))
                            player = SimpleExoPlayer.Builder(requireContext())
                                .build()
                                .also { exoPlayer ->
                                    videoPlayer.player = exoPlayer
//                                   videoPlayer.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL)

                                    val mediaItem = MediaItem.fromUri(it.absolutePath)
                                    exoPlayer.setMediaItem(mediaItem)
                                    exoPlayer.prepare()

                                    if (!exoPlayer.isPlaying){
                                        exoPlayer.play()
                                    }
                                     exo_ffwd.setOnClickListener {
                                         exoPlayer.seekTo(exoPlayer.currentPosition+10000)

                                     }

                                    exo_rew.setOnClickListener {
                                        exoPlayer.seekTo(exoPlayer.currentPosition-10000)
                                    }
//                                   exoPlayer.setVideoScalingMode(C.VIDEO_SCALING_MODE_SCALE_TO_FIT)
                                    val audioManager = (activity as HomeActivity).getSystemService(
                                        Context.AUDIO_SERVICE) as AudioManager

                                    volumeControl.max = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
                                    volumeControl.min = audioManager.getStreamMinVolume(AudioManager.STREAM_MUSIC)
                                    volumeControl.progress = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
                                    var isVolumeClicked:Boolean=true

                                    ivVolume.setOnClickListener {

                                        if (isVolumeClicked){
                                            ivVolume.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_baseline_volume_off_24))
                                            isVolumeClicked = false
                                            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,0,0)
                                            volumeControl.setProgress(0)

                                        }else if (!isVolumeClicked){
                                            ivVolume.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_baseline_volume_up_24))
                                            isVolumeClicked = true
                                            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC),0)
                                            volumeControl.setProgress(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC))
                                        }
                                    }


                                    volumeControl.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
                                        override fun onProgressChanged(
                                            seekBar: SeekBar?,
                                            progress: Int,
                                            fromUser: Boolean
                                        ) {
                                            audioManager.setStreamVolume(
                                                AudioManager.STREAM_MUSIC,
                                                progress,0
                                            )
                                            if (progress > 0){
                                                ivVolume.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_baseline_volume_up_24))
                                            }else{
                                                ivVolume.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_baseline_volume_off_24))

                                            }
                                        }


                                        override fun onStartTrackingTouch(seekBar: SeekBar?) {

                                        }

                                        override fun onStopTrackingTouch(seekBar: SeekBar?) {
                                             if (seekBar!!.progress == 0){
                                                 audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                                                 0,0)
                                                 ivVolume.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_baseline_volume_off_24))
                                             }
                                        }

                                    })


                                }
//
//
//
//
////                           val videoMedia = MediaController(context)
////                           videoMedia.setAnchorView(videoPlayer)
////                           videoPlayer.setMediaController(videoMedia)
////
////                           videoMedia.setMediaPlayer(videoPlayer)
////                           videoPlayer.requestFocus()
////                           videoPlayer.setZOrderMediaOverlay(true)
////
////                           videoPlayer.start()
//                       }

                        }


                }
            }

        }catch (e:Exception){
            Toast.makeText(context,e.message, Toast.LENGTH_SHORT).show()
        }
    }



    @RequiresApi(Build.VERSION_CODES.P)

//    override fun onConfigurationChanged(newConfig: Configuration) {
//        super.onConfigurationChanged(newConfig)
//
//        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
//           newConfig.orientation = Configuration.ORIENTATION_PORTRAIT
//            this.onConfigurationChanged(newConfig)
//        }else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
//            newConfig.orientation = Configuration.ORIENTATION_LANDSCAPE
//            this.onConfigurationChanged(newConfig)
//        }
//    }



    override fun onDestroy() {
        super.onDestroy()
        player?.release()
    }
}