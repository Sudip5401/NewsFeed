package com.example.newsfeed.presentation.ui.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsfeed.R
import com.example.newsfeed.core.State
import com.example.newsfeed.databinding.NewsFeedsFragmentBinding
import com.example.newsfeed.presentation.ui.common.NewsFeedAdapter
import com.example.newsfeed.presentation.viewModel.NewsFeedsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsFeedsFragment : Fragment() {

    private lateinit var binding: NewsFeedsFragmentBinding
    private lateinit var newsFeedsViewModel: NewsFeedsViewModel

    private lateinit var layoutManager: LinearLayoutManager
    private val newsFeedAdapter: NewsFeedAdapter by lazy {
        NewsFeedAdapter(
            requireContext(), mutableListOf()
        ) {
            findNavController().navigate(
                NewsFeedsFragmentDirections.actionNewsFeedsFragmentToFeedDetailsFragment(
                    it
                )
            )
        }
    }

    private var valueRepeat = 3

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        newsFeedsViewModel = ViewModelProvider(this)[NewsFeedsViewModel::class.java]
        binding = NewsFeedsFragmentBinding.inflate(inflater, container, false).apply {
            viewModel = newsFeedsViewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        initiateAdapter()
        observerApi()
    }

    private fun observerApi() {
        CoroutineScope(Dispatchers.Main).launch {
            repeat(valueRepeat) {
                newsFeedsViewModel.newsFeeds.collect {
                    when (it) {
                        is State.Loading -> binding.progressCircular.visibility = View.VISIBLE

                        is State.Success -> {
                            valueRepeat = 0
                            binding.progressCircular.visibility = View.GONE
                            it.data?.let { feed ->
                                feed.data?.toMutableList()?.let { currentListItems ->
                                    newsFeedAdapter.setData(
                                        currentListItems
                                    )
                                }
                            } ?: Toast.makeText(
                                context,
                                getString(R.string.something_went_wrong),
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }

                        is State.Error -> {
                            valueRepeat = 0
                            binding.progressCircular.visibility = View.GONE
                            newsFeedsViewModel.offsetValue = 0
                            Toast.makeText(
                                requireContext(),
                                it.message?.asString(requireContext()),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }

        }
    }

    private fun initiateAdapter() {
        layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding.newsFeedRv.layoutManager = layoutManager
        binding.newsFeedRv.itemAnimator = DefaultItemAnimator()
        binding.newsFeedRv.adapter = newsFeedAdapter
        binding.newsFeedRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (layoutManager.findLastVisibleItemPosition() == layoutManager.itemCount - 1) {
                    newsFeedsViewModel.offsetValue += 20
                    newsFeedsViewModel.fetchFeeds(newsFeedsViewModel.offsetValue)
                    observerApi()
                }
            }
        })
    }
}