package com.example.demoapplication.ui.gallerydetail

import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.transition.*
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import androidx.constraintlayout.motion.widget.MotionInterpolator
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.navigation.fragment.navArgs
import androidx.swiperefreshlayout.widget.CircularProgressDrawable

import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.example.demoapplication.R
import com.example.demoapplication.data.model.GalleryImage
import com.google.android.material.transition.MaterialContainerTransform
import kotlinx.android.synthetic.main.motion_gallery_detail.*
import kotlinx.coroutines.test.withTestContext


class GalleryDetailFragment : Fragment() {

    private val args: GalleryDetailFragmentArgs by navArgs()
    private lateinit var galleryImage: GalleryImage


    companion object {
        fun newInstance() = GalleryDetailFragment()
    }

    private lateinit var viewModel: GalleryDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prepareTransitions()

        //sharedElementReturnTransition =
           // TransitionInflater.from(context).inflateTransition(android.R.transition.move)
//        sharedElementEnterTransition = ChangeBounds().apply {
//            duration = 750
//        }
     //   sharedElementReturnTransition= TransitionInflater.from(context).inflateTransition(android.R.transition.fade)

    }

    private fun prepareTransitions() {
        postponeEnterTransition()

        sharedElementEnterTransition = MaterialContainerTransform().apply {
            // Scope the transition to a view in the hierarchy so we know it will be added under
            // the bottom app bar but over the Hold transition from the exiting HomeFragment.
            drawingViewId = R.id.nav_host_fragment
            duration = resources.getInteger(R.integer.reply_motion_default_large).toLong()
            interpolator =  FastOutSlowInInterpolator()
        }
        sharedElementReturnTransition = MaterialContainerTransform().apply {
            // Again, scope the return transition so it is added below the bottom app bar.
            drawingViewId = R.id.gallery_list_rv
            duration = resources.getInteger(R.integer.reply_motion_default_large).toLong()
            interpolator = FastOutSlowInInterpolator()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        galleryImage = args.galleryItem

        return inflater.inflate(R.layout.motion_gallery_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Add these two lines below
        //setSharedElementTransitionOnEnter()
        //postponeEnterTransition()


        gallery_detail_iv?.run {
            transitionName = galleryImage.id.toString()
            startEnterTransitionAfterLoadingImage(galleryImage.downloadUrl,this)
        }

        infoButton.setOnClickListener {
            if (motionLayout.progress > 0.5f) {
                motionLayout.transitionToStart()
            } else {
                motionLayout.transitionToEnd()
            }
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(GalleryDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun setSharedElementTransitionOnEnter() {
        sharedElementEnterTransition = TransitionInflater.from(context)
            .inflateTransition(R.transition.shared_element_transition)
    }

    private fun startEnterTransitionAfterLoadingImage(
        imageAddress: String,
        imageView: ImageView
    ) {
        Glide.with(this)
            .load(imageAddress)
            .dontAnimate() // 1
            .listener(object : RequestListener<Drawable> { // 2
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: com.bumptech.glide.request.target.Target<Drawable>,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }
            })
            .into(imageView)
    }


}
