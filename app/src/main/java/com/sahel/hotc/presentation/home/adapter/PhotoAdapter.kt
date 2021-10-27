package com.sahel.hotc.presentation.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sahel.hotc.R
import com.sahel.hotc.presentation.home.data.PhotoModel
import kotlinx.android.synthetic.main.recycler_photos.view.*

class PhotoAdapter:RecyclerView.Adapter<PhotoAdapter.VH>() {

    var photoList:List<PhotoModel> = ArrayList<PhotoModel>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }


    inner class VH(itemView:View):RecyclerView.ViewHolder(itemView){
        init {

        }
        fun bind(data:PhotoModel){
            with(itemView){
                ivImg.setImageResource(data.image)
                tvName.text = data.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(parent.context).inflate(R.layout.recycler_photos,parent,false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(photoList.get(position))
    }

    override fun getItemCount(): Int {
        return photoList.size
    }
}