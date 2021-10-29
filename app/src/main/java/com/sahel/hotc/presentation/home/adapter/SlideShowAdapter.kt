package com.sahel.hotc.presentation.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sahel.hotc.R
import com.sahel.hotc.presentation.home.data.ImageModel
import com.sahel.hotc.presentation.home.data.PhotoModel
import kotlinx.android.synthetic.main.recycler_slider.view.*
import java.io.File

class SlideShowAdapter () : RecyclerView.Adapter<SlideShowAdapter.SlideSHowVH>() {

     var mFiles:List<ImageModel> = ArrayList<ImageModel>()

    fun setItems(files: List<ImageModel>) {
        mFiles = files
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = mFiles.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlideSHowVH {
        return SlideSHowVH(
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_slider,parent,false))
    }
    override fun onBindViewHolder(holder: SlideSHowVH, position: Int) {
        holder.bind(mFiles[position])
    }

    class SlideSHowVH(itemView: View) :
        RecyclerView.ViewHolder(itemView) {


        fun bind(file: ImageModel) {
            with(itemView){
                Glide.with(context).load(file.image).into(ivImg)
            }
        }
    }
}