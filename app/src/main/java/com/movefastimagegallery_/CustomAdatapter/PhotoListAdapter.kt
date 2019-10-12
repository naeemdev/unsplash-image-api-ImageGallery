package com.movefastimagegallery_.CustomAdatapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.movefastimagegallery_.Model.ImagesUrl_Model
import com.movefastimagegallery_.R
import com.movefastimagegallery_.listeners.ListItemClickListener


class PhotoListAdapter(
    private val context: Context,
    private val list: List<ImagesUrl_Model>,
    internal var listener: ListItemClickListener
) : RecyclerView.Adapter<PhotoListAdapter.ViewHolder>() {
    private val price: String? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoListAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.image_singleitem, parent, false)
        return ViewHolder(view)
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: PhotoListAdapter.ViewHolder, position: Int) {


        //set image from url using  Glide
        Glide.with(context).load(list[position].small)
            .thumbnail(0.5f)
            .placeholder(R.drawable.ic_placeholder)
            .error(R.drawable.ic_placeholder)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.image)

        /*
        set  interface  ListItemClickListener funcation  item click  Listener for detail of photo
         */
        holder.lytParent.setOnClickListener {
            listener.onListItemClick(
                holder.adapterPosition,
                "imageclick"
            )
        }


    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        internal var image: ImageView

        //  CardView ;
        internal var lytParent: CardView


        init {
            image = itemView.findViewById(R.id.image)
            lytParent = itemView.findViewById(R.id.card_view)


        }
    }


}

