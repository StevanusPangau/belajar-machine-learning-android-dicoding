package com.dicoding.mygenerativeaibertqa.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.mygenerativeaibertqa.DataSetClient
import com.dicoding.mygenerativeaibertqa.R
import com.dicoding.mygenerativeaibertqa.adapters.TopicsAdapter
import com.dicoding.mygenerativeaibertqa.databinding.FragmentTopicsBinding

class TopicsFragment : Fragment() {

    private lateinit var binding: FragmentTopicsBinding

    private var topicsAdapter: TopicsAdapter? = null

    private var topicsTitle = emptyList<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTopicsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataSetClient = DataSetClient(requireActivity())
        dataSetClient.loadJsonData()?.let {
            topicsTitle = it.getTitles()
        }

        val topicsAdapter = TopicsAdapter(topicsTitle, object : TopicsAdapter.OnItemSelected {
            override fun onItemClicked(itemID: Int, itemTitle: String) {
                startQaScreen(itemID, itemTitle)
            }
        })

        val linearLayoutManager = LinearLayoutManager(requireContext())
        val decoration = DividerItemDecoration(
            binding.rvTopics.context,
            linearLayoutManager.orientation
        )

        with(binding.rvTopics) {
            layoutManager = linearLayoutManager
            adapter = topicsAdapter
            addItemDecoration(decoration)
        }

    }

    private fun startQaScreen(itemID: Int, itemTitle: String) {
        val action = TopicsFragmentDirections.actionTopicsFragmentToQAFragment(
            itemID,
            itemTitle
        )
        Navigation.findNavController(requireActivity(), R.id.fragment_container)
            .navigate(action)
    }

}