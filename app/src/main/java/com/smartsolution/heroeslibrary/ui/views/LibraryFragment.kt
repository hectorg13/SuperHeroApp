package com.smartsolution.heroeslibrary.ui.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.smartsolution.heroeslibrary.R
import com.smartsolution.heroeslibrary.databinding.FragmentLibraryBinding
import com.smartsolution.heroeslibrary.ui.adapter.SuperheroAdapter
import com.smartsolution.heroeslibrary.ui.viewmodel.SuperHeroViewModel

class LibraryFragment : Fragment() {

    private var _binding: FragmentLibraryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SuperHeroViewModel by activityViewModels()
    private lateinit var adapter: SuperheroAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLibraryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        binding.svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.getSuperHeroFind(query.orEmpty())
                binding.progressBar.isVisible = true
                return false
            }

            override fun onQueryTextChange(newText: String?) = false
        })

        viewModel.superHeroesList.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = false
            if (it != null) {
                adapter.updateList(it)
            }
        }
    }

    private fun setupRecyclerView() {
        binding.apply {
            adapter = SuperheroAdapter { superHeroID -> onItemSelected(superHeroID) }
            rvSuperheroList.setHasFixedSize(true)
            rvSuperheroList.layoutManager = LinearLayoutManager(requireContext())
            rvSuperheroList.adapter = adapter
        }
    }

    private fun onItemSelected(id: String) {
        viewModel.getSuperHeroItem(id)
        viewModel.waitDownload.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}