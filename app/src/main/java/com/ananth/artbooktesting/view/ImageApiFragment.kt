package com.ananth.artbooktesting.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.ananth.artbooktesting.R
import com.ananth.artbooktesting.databinding.FragmentImageApiBinding
import com.ananth.artbooktesting.adapter.ImageRecyclerAdapter
import com.ananth.artbooktesting.util.Status
import com.ananth.artbooktesting.viewmodel.ArtViewModel
import com.google.gson.Gson
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class ImageApiFragment @Inject constructor(
     val imageRecyclerAdapter: ImageRecyclerAdapter
) : Fragment(R.layout.fragment_image_api) {

    lateinit var viewModel: ArtViewModel

    private var fragmentBinding: FragmentImageApiBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)

        val binding = FragmentImageApiBinding.bind(view)
        fragmentBinding = binding
        subscribeToObservers()

        binding.imageRecyclerview.adapter = imageRecyclerAdapter
        binding.imageRecyclerview.layoutManager = GridLayoutManager(requireContext(), 3)
        imageRecyclerAdapter.setOnItemClickListener { url ->
            findNavController().popBackStack()
            viewModel.setSelectedImage(url)
        }

        var job: Job? = null
        binding.editTextTextPersonName.addTextChangedListener {
            job?.cancel()
            job = lifecycleScope.launch {
                delay(1000)
                it?.let {
                    if (it.toString().isNotEmpty()) {
                        viewModel.searchForImage(it.toString())
                    }
                }
            }
        }
    }

    private fun subscribeToObservers() {
        viewModel.imageList.observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.SUCCESS -> {

                    val urls = resource.data?.hits?.map { imageResult ->
                        imageResult.previewUrl
                    }
                    imageRecyclerAdapter.imageList = urls ?: listOf()
                    fragmentBinding?.progressView?.visibility = View.GONE

                }
                Status.ERROR -> {
                    Toast.makeText(
                        requireContext(),
                        resource.message ?: "Error",
                        Toast.LENGTH_SHORT
                    ).show()
                    fragmentBinding?.progressView?.visibility = View.GONE
                }
                Status.LOADING -> {
                    fragmentBinding?.progressView?.visibility = View.VISIBLE
                }
            }
        }
    }
}