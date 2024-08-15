package com.example.newsfeed.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsfeed.databinding.NewsFeedsFragmentBinding
import com.example.newsfeed.presentation.viewModel.NewsFeedsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class NewsFeedsFragment : Fragment() {

    private lateinit var binding: NewsFeedsFragmentBinding
    private lateinit var newsFeedsViewModel: NewsFeedsViewModel

    private lateinit var layoutManager: LinearLayoutManager
    private val newsFeedAdapter: NewsFeedAdapter by lazy {
        NewsFeedAdapter(
            requireContext(),
            newsFeedsViewModel._newsFeeds.value.feeds?.data ?: mutableListOf()
        )
    }
    var offsetValue = 0

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
        newsFeedsViewModel.fetchFeeds(offsetValue)
        CoroutineScope(Dispatchers.Main).launch {
            newsFeedsViewModel._newsFeeds.collect { value ->
                when {
                    value.isLoading -> binding.progressCircular.visibility = View.VISIBLE
                    value.feeds?.data?.isEmpty()?.not() == true -> {
                        binding.progressCircular.visibility = View.GONE
                        initiateAdapter()
                    }

                    value.error.isNotBlank() -> {
                        binding.progressCircular.visibility = View.GONE
                        offsetValue = 0
                    }
                }
            }
        }
    }

    private fun initiateAdapter() {
        layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding.newsFeedRv.apply {
            layoutManager = layoutManager
            itemAnimator = DefaultItemAnimator()
            adapter = newsFeedAdapter

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                }
            })
        }
    }
}