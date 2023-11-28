package com.example.shopmarket.view

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.shopmarket.R
import com.example.shopmarket.databinding.ActivityMainBinding
import com.example.shopmarket.databinding.FragmentAddShopItemBinding
import com.example.shopmarket.model.ShopItem
import com.example.shopmarket.viewmodel.ShopViewModel
import com.example.shopmarket.viewmodel.ViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.UUID


class AddShopItemFragment(
    val mode: String,
    val id: String
) : BottomSheetDialogFragment() {


    private var _binding: FragmentAddShopItemBinding? = null
    private val binding: FragmentAddShopItemBinding
        get() = _binding ?: throw RuntimeException("binding == null")

    private val viewModelFactory by lazy {
        ViewModelFactory(requireActivity().application)
    }
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[ShopViewModel::class.java]
    }

    private var imageUri = EMPTY_STRING
    private var buttonMode: (() -> Unit)? = null
    val adapter = ShopItemsAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAddShopItemBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
        initViews()
        Toast.makeText(requireContext(),mode,Toast.LENGTH_LONG).show()

    }


    private fun initListeners() {
        binding.apply {
            addImageButton.setOnClickListener {
                val i = Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                )

                startActivityForResult(i, RESULT_LOAD_IMAGE)
            }

            addProductButton.setOnClickListener {
                buttonMode?.invoke()
            }

        }
    }

    private fun initViews() {
        binding.apply {
            when (mode) {
                MODE_ADD -> {
                    binding.addProductButton.text = "Добавить"
                    buttonMode = ::addProduct
                }

                MODE_EDIT -> {
                    binding.addProductButton.text = "Изменить"
                    buttonMode = ::editProduct
                }
            }
        }
    }

    private fun addProduct() {
        val id = UUID.randomUUID().toString()
        binding.apply {
            val name = productNameEditText.text.toString()
            val count = quantityEditText.text.toString()
            val price = quantityEditText.text.toString()

            viewModel.addShopItem(ShopItem(id, name, count, price, imageUri))
        }

    }

    private fun editProduct() {
        binding.apply {
            val name = productNameEditText.text.toString()
            val count = quantityEditText.text.toString()
            val price = quantityEditText.text.toString()

            viewModel.editShopItem(ShopItem(id, name, count, price, imageUri))
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            imageUri = data.data.toString() ?: ""
        }
    }

    companion object {
        private val RESULT_LOAD_IMAGE = 1
        private val EMPTY_STRING = ""
        const val MODE_ADD = "add"
        const val MODE_EDIT = "edit"



        fun newInstanceAdd(mode: String): AddShopItemFragment {
            return AddShopItemFragment(mode, EMPTY_STRING)
        }

        fun newInstanceEditOrRemove(mode: String, id: String): AddShopItemFragment {
            return AddShopItemFragment(mode, id)
        }
    }

}