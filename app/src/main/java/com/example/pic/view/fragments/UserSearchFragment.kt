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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pic.R
import com.example.pic.view.adapter.UserListAdapter
import com.example.pic.viewModel.SearchViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UserSearchFragment : Fragment() {


    private lateinit var userSearchFragmentView: View
    private val viewModel: SearchViewModel by activityViewModels()
    private lateinit var rcv: RecyclerView
    private lateinit var userLstAdapter: UserListAdapter
    private val bundle = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        userSearchFragmentView = inflater.inflate(R.layout.fragment_user_search, container, false)

        init()

        viewModel.getListOfUsersSearched().observe(viewLifecycleOwner){
            userLstAdapter.submitList(it)
        }


        return userSearchFragmentView
    }

    private fun init(){
        rcv = userSearchFragmentView.findViewById(R.id.searchUser_rcv)

        setUpList()
    }

    private fun setUpList(){
        userLstAdapter = UserListAdapter(){
            bundle.putString("username", it.username)
            loadUser(bundle)

        }

        rcv.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = userLstAdapter
        }
    }

    private fun imgPreview(imgLink: String) {
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
    fun loadImage(view: ImageView, url: String) {
        Picasso.get().load(url).into(view)
    }

    private fun loadUser(userBundle: Bundle){
        findNavController().navigate(R.id.photographerDetailsFragment, userBundle)
    }

}