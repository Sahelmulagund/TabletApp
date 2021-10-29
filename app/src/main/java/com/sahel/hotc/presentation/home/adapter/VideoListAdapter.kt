package com.sahel.hotc.presentation.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sahel.hotc.R
import com.sahel.hotc.presentation.home.data.ImageModel
import com.sahel.hotc.presentation.home.data.ThumbnailModel
import com.sahel.hotc.presentation.home.data.VideoModel
import kotlinx.android.synthetic.main.recycler_video_list.view.*


class VideoListAdapter(val callable:(ThumbnailModel)->Unit): RecyclerView.Adapter<VideoListAdapter.VH>() {

    var videoList:List<ThumbnailModel> = ArrayList<ThumbnailModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    inner class VH(itemView: View): RecyclerView.ViewHolder(itemView){
        init {
            with(itemView){
                setOnClickListener {
                    callable.invoke(videoList[adapterPosition])
                }
            }
        }
        fun bind(data: ThumbnailModel?){
            with(itemView){
                try {
                    Glide.with(context).load(data?.thumbNail).into(ivImg)
                }catch (e:Exception){
                    Toast.makeText(context,e.message, Toast.LENGTH_SHORT).show()
                }



            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(parent.context).inflate(R.layout.recycler_video_list,parent,false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(videoList.get(position))
    }

    override fun getItemCount(): Int {
        return videoList.size
    }
}