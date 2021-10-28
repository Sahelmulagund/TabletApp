package com.sahel.hotc.presentation.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sahel.hotc.R

import com.sahel.hotc.presentation.home.data.VideoModel
import kotlinx.android.synthetic.main.recycler_videos.view.*


class VideoAdapter(val callable:(VideoModel)->Unit): RecyclerView.Adapter<VideoAdapter.VH>() {

    var videoList:List<VideoModel> = ArrayList<VideoModel>()
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
        fun bind(data: VideoModel){
            with(itemView){
                Glide.with(context).load(data.thumbNail).into(ivImg)
                tvName.text = data.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(parent.context).inflate(R.layout.recycler_videos,parent,false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(videoList.get(position))
    }

    override fun getItemCount(): Int {
        return videoList.size
    }
}