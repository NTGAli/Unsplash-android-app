package com.example.pic.view.fragments

import android.Manifest
import android.app.Activity.RESULT_OK
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.pic.R
import com.example.pic.databinding.FragmentProfileBinding
import com.example.pic.model.User
import com.example.pic.view.LoginActivity
import com.example.pic.viewModel.ProfileViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var binding: FragmentProfileBinding
    lateinit var imageView: ImageView
    lateinit var button: Button
    private val pickImage = 100
    private var imageUri: Uri? = null
    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (!isGranted){
                Toast.makeText(requireContext(), "We need your permission :)", Toast.LENGTH_SHORT).show()
            }else{
                pickupImage()
            }
        }

    companion object{
        private var mUser: User? = null
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(LayoutInflater.from(context), container, false)

            viewModel.getUser(getUserEmail()!!).observe(viewLifecycleOwner){
                mUser = it
                binding.user = it
                setImageUri(binding.userProfile, it?.profile?.toUri())
            }


        binding.firstNameItem.setOnClickListener {
            showBottomSheet(true,"First Name", mUser?.firstName)
        }

        binding.lastNaemItem.setOnClickListener {
            showBottomSheet(true, "Last Name", mUser?.lastName )
        }

        binding.emailItem.setOnClickListener {
            showBottomSheet(true, "Email", mUser?.email)

        }

        binding.passwordItem.setOnClickListener {
            showBottomSheet(false, "Password", mUser?.password)
        }

        binding.logoutItem.setOnClickListener {
            showBottomSheet(false, "-", null, true)
        }

        binding.editBtn.setOnClickListener {

            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // Pass any permission you want while launching
                requestPermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)

            }else{
                pickupImage()
            }




        }


        return binding.root
    }

    private fun pickupImage(){
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery, pickImage)
    }

    private fun getUserEmail(): String? {
        val sharedPreference =  activity?.getSharedPreferences("UserData", Context.MODE_PRIVATE)
        return sharedPreference?.getString("email",null)
    }

    private fun updateEmailInShared(email: String){

        val sharedPreference =  activity?.getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val editor = sharedPreference?.edit()
        editor!!.putString("email",email)
        editor.apply()
    }


    private fun showBottomSheet(isSingleItem: Boolean, txtHint: String, txtData:String?, isLogout: Boolean = false){
        val dialog = BottomSheetDialog(requireContext())

        dialog.setContentView(R.layout.bottom_sheet_profile)

        val singleItem = dialog.findViewById<LinearLayout>(R.id.single_item_in_layout)
        val logoutLayout = dialog.findViewById<LinearLayout>(R.id.logout_layout)
        val twoItem = dialog.findViewById<LinearLayout>(R.id.two_item_in_layout)
        val singleTIL = dialog.findViewById<TextInputLayout>(R.id.til_bottom_sheet)
        val passTIL = dialog.findViewById<TextInputLayout>(R.id.pass_profile_til)
        val confirmPassTil = dialog.findViewById<TextInputLayout>(R.id.confirm_pass_profile_til)
        val button = dialog.findViewById<Button>(R.id.btn_submit_bottom_sheet)
        val buttonYse = dialog.findViewById<Button>(R.id.btn_logout_yes)
        val buttonNo = dialog.findViewById<Button>(R.id.byn_logout_no)



        if (isLogout){

            logoutLayout!!.visibility = View.VISIBLE
            twoItem!!.visibility = View.GONE
            singleItem!!.visibility = View.GONE
            button!!.visibility = View.GONE

        }else if (isSingleItem){
            twoItem!!.visibility = View.GONE
            singleItem!!.visibility = View.VISIBLE
            logoutLayout!!.visibility = View.GONE
            singleTIL!!.editText!!.setText(txtData)
            singleTIL.hint = txtHint

            if (txtHint == "email")
                singleTIL.editText!!.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS

        }
        else{
            twoItem!!.visibility = View.VISIBLE
            singleItem!!.visibility = View.GONE
            logoutLayout!!.visibility = View.GONE
//            singleTIL.hint = "Last Name"
        }

        button!!.setOnClickListener {
            if (isSingleItem){

                if (singleTIL!!.editText!!.text.isEmpty()){
                    singleTIL.error = "$txtHint Cant be empty!"
                }else{
                    singleTIL.isErrorEnabled = false
                }

                when(txtHint){
                    "First Name" ->{
                        viewModel.updateFirstName(singleTIL.editText!!.text.toString(), mUser!!.id)

                    }
                    "Last Name" ->{
                        viewModel.updateLastName(singleTIL.editText!!.text.toString(), mUser!!.id)
                    }

                    "Email" ->{
                        viewModel.updateEmail(singleTIL.editText!!.text.toString(), mUser!!.id)
                        updateEmailInShared(singleTIL.editText!!.text.toString())
                    }
                }

                dialog.dismiss()
            }else if (passTIL!!.editText?.text.toString() == confirmPassTil!!.editText?.text.toString()){
                viewModel.updatePassword(passTIL.editText?.text.toString(), mUser!!.id)
                dialog.dismiss()
            }
        }



        buttonNo!!.setOnClickListener {
            dialog.dismiss()
        }

        buttonYse!!.setOnClickListener {
            submitLogout()
        }




        dialog.show()
    }

    private fun submitLogout(){
        val sharedPreference =  activity?.getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val editor = sharedPreference?.edit()
        editor!!.remove("email")
        editor.apply()
        startActivity(Intent(requireContext(), LoginActivity::class.java))
        activity?.finish()
    }


    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            viewModel.updateProfile(imageUri.toString(), mUser?.id)
            binding.userProfile.setImageURI(imageUri)
        }
    }

    @BindingAdapter("android:src")
    fun setImageUri(view: ImageView, imageUri: Uri?) {
        view.setImageURI(imageUri)
    }




//    @Deprecated("Deprecated in Java")
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
//                                            grantResults: IntArray) {
//        when (requestCode) {
//            1 -> {
//                if (grantResults.isNotEmpty() && grantResults[0] ==
//                    PackageManager.PERMISSION_GRANTED) {
//                    if ((ContextCompat.checkSelfPermission(requireContext(),
//                            Manifest.permission.ACCESS_FINE_LOCATION) ===
//                                PackageManager.PERMISSION_GRANTED)) {
//                        Toast.makeText(requireContext(), "Permission Granted", Toast.LENGTH_SHORT).show()
//                    }
//                } else {
//                    Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_SHORT).show()
//                }
//                return
//            }
//        }
//    }


}