package com.example.pic.view.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pic.R
import com.example.pic.databinding.FragmentPhotographerDetailsBinding
import com.example.pic.util.loadImage
import com.example.pic.view.adapter.FeedPagerDataAdapter
import com.example.pic.view.custom.gone
import com.example.pic.view.custom.visible
import com.example.pic.viewModel.FeedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
@OptIn(ExperimentalPagingApi::class)

class PhotographerDetailsFragment : Fragment() {

    private lateinit var binding: FragmentPhotographerDetailsBinding
    private val viewModel: FeedViewModel by viewModels()
    private lateinit var itemsAdapter: FeedPagerDataAdapter
    private val bundle = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPhotographerDetailsBinding.inflate(
            LayoutInflater.from(context),
            container,
            false
        )

        init()

        viewModel.getUserByUsername(requireArguments().getString("username")!!)
            .observe(viewLifecycleOwner) {
                binding.details = it
                loadImage(binding.profileImage, it!!.profile_image.large)

            }

        viewModel.getUsersPhotos(requireArguments().getString("username")!!)
            .observe(viewLifecycleOwner) {
                lifecycleScope.launch(Dispatchers.IO) {
                    itemsAdapter.submitData(it)
                }

            }

        lifecycleScope.launch(viewLifecycleOwner.lifecycleScope.coroutineContext) {
            itemsAdapter.loadStateFlow.collectLatest { loadStates ->
                if (loadStates.refresh == LoadState.Loading){
                    binding.shimmerDetailsPhotographer.startShimmerAnimation()
                    binding.shimmerDetailsPhotographer.visible()
                }else{
                    binding.shimmerDetailsPhotographer.stopShimmerAnimation()
                    binding.shimmerDetailsPhotographer.gone()
                }
            }
        }


        binding.toolbarPhotographDetail.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        return binding.root
    }

    private fun init() {

        setUpList()

//        ViewCompat.setElevation(
//            binding.appBarLayoutPhotographDetails,
//            8f
//        )

    }


    private fun setUpList() {
        itemsAdapter = FeedPagerDataAdapter() { feed, onLong ->
            if (onLong) {
                imgPreview(feed?.urls?.regular)
            } else {
                bundle.putString("imageID", feed?.id)
                findNavController().navigate(R.id.detailsFeedFragment, bundle)
            }
        }

        binding.recvUserItem.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = itemsAdapter
        }
    }

    private fun imgPreview(imgLink: String?) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_image_preview)
        val imgPreview: ImageView = dialog.findViewById(R.id.img_preview_feed)
        loadImage(imgPreview, imgLink!!)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.show()
    }


}