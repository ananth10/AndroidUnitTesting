package com.ananth.artbooktesting.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.ananth.artbooktesting.adapter.ArtRecyclerviewAdapter
import com.ananth.artbooktesting.adapter.ImageRecyclerAdapter
import com.bumptech.glide.RequestManager
import javax.inject.Inject

class ArtFragmentFactory @Inject constructor(
    private val glide: RequestManager,
    private val artRecyclerviewAdapter: ArtRecyclerviewAdapter,
    private val imageRecyclerAdapter: ImageRecyclerAdapter
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {

        return when (className) {
            ArtFragment::class.java.name -> ArtFragment(artRecyclerviewAdapter)
            ImageApiFragment::class.java.name -> ImageApiFragment(imageRecyclerAdapter)
            ArtDetailFragment::class.java.name -> ArtDetailFragment(glide)
            else -> {
                super.instantiate(classLoader, className)
            }
        }
    }
}