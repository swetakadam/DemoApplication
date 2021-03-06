package com.example.demoapplication.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.transition.TransitionInflater
import com.example.demoapplication.R
import com.google.android.material.transition.Hold
import kotlinx.android.synthetic.main.fragment_gallery.*
import kotlinx.android.synthetic.main.fragment_gallery.view.*
import org.koin.androidx.viewmodel.ext.android.stateViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

import timber.log.Timber

class GalleryFragment : Fragment() {

    private val galleryViewModel: GalleryViewModel by stateViewModel()
    private lateinit var galleryAdapter: GalleryAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_gallery, container, false)
        //setRecyclerView(view)
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        exitTransition = Hold().apply {
//            duration = resources.getInteger(R.integer.reply_motion_default_large).toLong()
//        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        galleryViewModel.getGalleryImages()

        galleryViewModel.getGalleryStatusLiveData().observe(viewLifecycleOwner, Observer {

            if (it) {
                //(context as MainActivity).hideProgressBar()
                empty_view.visibility = View.VISIBLE
                gallery_list_rv.visibility = View.GONE
            }

        })
        //Observe the viewmodel
        galleryViewModel.imagesLiveData.observe(viewLifecycleOwner, Observer { pagedList ->
            pagedList?.let {
                Timber.d("within gallery list  live data page list size is " + pagedList.size)
                galleryAdapter.submitList(pagedList)
                if (galleryViewModel.listState != null) {
                    Timber.d("list state is not null and scroll potions are "+galleryViewModel.listPositions?.get(0))
                    (gallery_list_rv.layoutManager as StaggeredGridLayoutManager).onRestoreInstanceState(galleryViewModel.listState)
                    galleryViewModel.listState = null
                }
            }
        })
    }

    override fun onPause() {
        super.onPause()
        galleryViewModel.listState = (gallery_list_rv.layoutManager  as StaggeredGridLayoutManager).onSaveInstanceState()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        //galleryViewModel.listState = (gallery_list_rv.layoutManager  as StaggeredGridLayoutManager).onSaveInstanceState()
        Timber.d("on destroy view  called")


    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("on destroy called")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Postpone enter transitions to allow shared element transitions to run.
        // https://github.com/googlesamples/android-architecture-components/issues/495
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }

        galleryAdapter = GalleryAdapter()
        gallery_list_rv.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = galleryAdapter

            postponeEnterTransition()

            this.viewTreeObserver.addOnPreDrawListener {

                startPostponedEnterTransition()
                true
            }
        }

    }


}