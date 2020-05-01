package com.example.demoapplication.ui.gallery

import androidx.recyclerview.widget.RecyclerView


class GalleryListAdapterDataObserver(
    private val mRecyclerView: RecyclerView,
    private val mChangeListener: ChangeListener
) : RecyclerView.AdapterDataObserver() {

    val size: Int
        get() = if (mRecyclerView.adapter != null) {
            mRecyclerView.adapter!!.itemCount
        } else 0

    override fun onChanged() {
        super.onChanged()
        sendItemCount()
    }

    private fun sendItemCount() {
        if (mRecyclerView.adapter != null) {
            mChangeListener.onChanged(size)
        }
    }

    override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
        super.onItemRangeInserted(positionStart, itemCount)
        sendItemCount()
    }

    override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
        super.onItemRangeChanged(positionStart, itemCount)
        sendItemCount()
    }

    override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
        super.onItemRangeRemoved(positionStart, itemCount)
        sendItemCount()
    }

    interface ChangeListener {
        fun onChanged(size: Int)
    }
}