package com.example.pic.view.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pic.R
import com.example.pic.view.adapter.FeedPagerDataAdapter
import com.example.pic.model.res.Feed
import com.example.pic.viewModel.SearchViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ImageSearchFragment : Fragment() {

    private lateinit var imageSearchView: View
    private lateinit var searchImagesAdapter: FeedPagerDataAdapter
    lateinit var lists: List<Feed>
    private val bundle= Bundle()
    private lateinit var rcv: RecyclerView
    val viewModel: SearchViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        imageSearchView = inflater.inflate(R.layout.fragment_image_search, container, false)

        viewModel.getSomeImages().observe(viewLifecycleOwner){
            lifecycleScope.launch(Dispatchers.IO) {
                searchImagesAdapter.submitData(it)
            }
        }



        viewModel.getQuery().observe(viewLifecycleOwner){
            println("qqqqqqqqqqqqqqqqqqqqqqqqqqqq $it")
            viewModel.getListOfImagesSearched().observe(viewLifecycleOwner){
                lifecycleScope.launch {
                    searchImagesAdapter.submitData(it)
                }
            }
        }

        init()


        return imageSearchView
    }

    private fun init(){
        rcv = imageSearchView.findViewById(R.id.searchImg_rcv)
        setUpImagesList()
    }

    private fun setUpImagesList(){

        searchImagesAdapter = FeedPagerDataAdapter(){ feed, onLong ->
            if (onLong){
                imgPreview(feed?.urls?.regular)
            }else{
                bundle.putString("imageID", feed?.id)
                findNavController().navigate(R.id.detailsFeedFragment, bundle)
            }
        }

        rcv.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = searchImagesAdapter
        }

    }


    private fun imgPreview(imgLink: String?) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_image_preview)
        var imgPreview: ImageView = dialog.findViewById(R.id.img_preview_feed)
        loadImage(imgPreview, imgLink)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.show()
    }

    @BindingAdapter("imageUrl")
    fun loadImage(view: ImageView, url: String?) {
        Picasso.get().load(url).into(view)
    }


}