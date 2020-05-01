package com.example.demoapplication.ui.gallery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.demoapplication.R
import com.example.demoapplication.data.model.GalleryImage
import com.example.demoapplication.ui.gallerydetail.GalleryDetailFragmentArgs
import com.rishabhharit.roundedimageview.RoundedImageView
import kotlinx.android.synthetic.main.item_gallery.view.*
import kotlinx.android.synthetic.main.nav_header_main.view.*

/**
 * Created by swetashinde on 30,April,2020
 * USA
 */

class GalleryAdapter : PagedListAdapter<GalleryImage, RecyclerView.ViewHolder>(diffCallback) {


    inner class GalleryViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val image: RoundedImageView = view.gallery_iv

        fun bind(galleryImage: GalleryImage) {

            val circularProgressDrawable = CircularProgressDrawable(view.context)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.setColorSchemeColors(
                ContextCompat.getColor(
                    view.context,
                    R.color.secondaryDarkColor
                )
            )
            circularProgressDrawable.start()



            Glide.with(view.context)
                .load(galleryImage.downloadUrl)
                //.centerCrop()
                .placeholder(circularProgressDrawable)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(image)


            ViewCompat.setTransitionName(image, galleryImage.id.toString())

            image.setOnClickListener {
                //preventTwoClick(it)
                val extras = FragmentNavigatorExtras(
                    it to galleryImage.id.toString()
                )
                val action = GalleryFragmentDirections.actionNavGalleryToGalleryDetail(galleryImage)
                view.findNavController().navigate(action,extras)

            }

        }
    }


    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<GalleryImage>() {
            override fun areItemsTheSame(oldItem: GalleryImage, newItem: GalleryImage): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: GalleryImage, newItem: GalleryImage): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        GalleryViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_gallery, parent, false)
        )


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)

        item?.run {
            (holder as GalleryViewHolder).bind(item)
            ///** Hide progress bar after first item of recycler view is drawn **/
            //            (title.context as MainActivity).hideProgressBar()
        }
    }


}