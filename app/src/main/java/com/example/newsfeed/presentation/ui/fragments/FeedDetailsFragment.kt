package com.example.newsfeed.presentation.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.newsfeed.R
import com.example.newsfeed.databinding.FeedDetailsFragmentBinding

class FeedDetailsFragment : Fragment() {

    private lateinit var binding: FeedDetailsFragmentBinding
    private val args: FeedDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FeedDetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        binding.title.text = args.itemData.title ?: ""
        binding.desc.text = args.itemData.description ?: ""
        binding.author.text = if (args.itemData.author.isNullOrBlank()
                .not()
        ) "Author: ${args.itemData.author ?: ""}" else ""
        binding.country.text = if (args.itemData.country.isNullOrBlank()
                .not()
        ) "Country: ${args.itemData.country ?: ""}" else ""
        Glide.with(requireContext())
            .load(args.itemData.image)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.news_placeholder)
                    .error(R.drawable.news_placeholder)
            )
            .into(binding.detailsImageVw)
    }
}