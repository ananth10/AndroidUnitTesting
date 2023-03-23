package com.ananth.artbooktesting.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ananth.artbooktesting.R
import com.ananth.artbooktesting.adapter.ArtRecyclerviewAdapter
import com.ananth.artbooktesting.databinding.FragmentArtsBinding
import com.ananth.artbooktesting.viewmodel.ArtViewModel
import com.google.gson.Gson
import javax.inject.Inject

class ArtFragment @Inject constructor(
    val artRecyclerviewAdapter: ArtRecyclerviewAdapter
) : Fragment(R.layout.fragment_arts) {

    private var fragmentBinding: FragmentArtsBinding? = null;

    lateinit var viewModel: ArtViewModel

    private val swipeCallBack =
        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val layoutPosition = viewHolder.layoutPosition
                val selectedArt = artRecyclerviewAdapter.arts[layoutPosition]
                viewModel.deleteArt(selectedArt)
            }

        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)
        val binding = FragmentArtsBinding.bind(view)
        fragmentBinding = binding

        subscribeToObservers()

        binding.recyclerView.adapter = artRecyclerviewAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        ItemTouchHelper(swipeCallBack).attachToRecyclerView(binding.recyclerView)

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(ArtFragmentDirections.actionArtFragmentToArtDetailFragment())
        }

    }

    private fun subscribeToObservers() {
        viewModel.artList.observe(viewLifecycleOwner) { artList ->
            println("LIST::${Gson().toJson(artList)}")
            artRecyclerviewAdapter.arts = artList
        }
    }

    override fun onDestroyView() {
//        fragmentBinding=null
        super.onDestroyView()
    }
}