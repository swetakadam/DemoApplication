package com.example.demoapplication.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.transition.TransitionInflater
import com.example.demoapplication.R
import kotlinx.android.synthetic.main.fragment_gallery.*
import kotlinx.android.synthetic.main.fragment_gallery.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class GalleryFragment : Fragment() {

    private val galleryViewModel: GalleryViewModel by viewModel()
    private lateinit var galleryAdapter: GalleryAdapter



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_gallery, container, false)
        //setRecyclerView(view)
        return view
    }

    private fun setRecyclerView(view:View){
        galleryAdapter = GalleryAdapter()
        view.gallery_list_rv.apply {
            layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
            adapter = galleryAdapter

            //postponeEnterTransition()
//2
//            this.viewTreeObserver.addOnPreDrawListener {
//                //3
//                startPostponedEnterTransition()
//                true
//            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        galleryViewModel.getGalleryImages()

        galleryViewModel.getGalleryStatusLiveData().observe(this, Observer {

            if (it) {
                //(context as MainActivity).hideProgressBar()
                empty_view.visibility = View.VISIBLE
                gallery_list_rv.visibility = View.GONE
            }

        })
        //Observe the viewmodel
        galleryViewModel.imagesLiveData.observe(this, Observer { pagedList ->
            pagedList?.let {
                Timber.d("within gallery list  live data page list size is " + pagedList.size)
                galleryAdapter.submitList(pagedList)
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //postponeEnterTransition()
        galleryAdapter = GalleryAdapter()
        gallery_list_rv.apply {
            layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
            adapter = galleryAdapter

//            postponeEnterTransition()
////2
//            this.viewTreeObserver.addOnPreDrawListener {
//                //3
//                startPostponedEnterTransition()
//                true
//            }
        }

        postponeEnterTransition()
        gallery_list_rv.doOnPreDraw { startPostponedEnterTransition() }
        //gallery_list_rv.doOnPreDraw { startPostponedEnterTransition() }
        //initialize the paged recycler view
        //(activity as MainActivity).showProgressBar()


    }
}