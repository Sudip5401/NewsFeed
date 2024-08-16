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
    private var offsetValue = 0
    private var valueRepeat = 3

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
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
        newsFeedsViewModel._newsFeeds.value.feeds?.data?.isEmpty() ?: newsFeedsViewModel.fetchFeeds(
            offsetValue
        )
        observerApi()
    }

    private fun observerApi() {
        CoroutineScope(Dispatchers.Main).launch {
            repeat(valueRepeat) {
                newsFeedsViewModel._newsFeeds.collect { value ->
                    when {
                        value.isLoading -> binding.progressCircular.visibility = View.VISIBLE
                        value.feeds?.data?.isEmpty()?.not() == true -> {
                            valueRepeat = 0
                            binding.progressCircular.visibility = View.GONE
                            newsFeedAdapter.setData(
                                value.feeds.data.toMutableList()
                            )
                        }

                        value.error.isNotBlank() -> {
                            valueRepeat = 0
                            binding.progressCircular.visibility = View.GONE
                            offsetValue = 0
                            Toast.makeText(requireContext(), value.error, Toast.LENGTH_LONG).show()
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
                    offsetValue += 20
                    newsFeedsViewModel.fetchFeeds(offsetValue)
                    observerApi()
                }
            }
        })
    }
}