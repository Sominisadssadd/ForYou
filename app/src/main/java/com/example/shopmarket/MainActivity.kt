package com.example.shopmarket

import android.os.Bundle
import android.widget.SearchView.OnQueryTextListener

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopmarket.databinding.ActivityMainBinding
import com.example.shopmarket.view.AddShopItemFragment
import com.example.shopmarket.view.AddShopItemFragment.Companion.MODE_ADD
import com.example.shopmarket.view.AddShopItemFragment.Companion.MODE_EDIT

import com.example.shopmarket.view.ShopItemsAdapter
import com.example.shopmarket.view.SwipeToDeleteCallBack
import com.example.shopmarket.viewmodel.ShopViewModel
import com.example.shopmarket.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    val adapter = ShopItemsAdapter()

    private val viewModelFactory by lazy {
        ViewModelFactory(application)
    }
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[ShopViewModel::class.java]
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initRecyclerView()
        initViews()
    }


    private fun initViews() {
        binding.buttonAddProduct.setOnClickListener {
            var dialog = AddShopItemFragment.newInstanceAdd(MODE_ADD)
            dialog.show(supportFragmentManager, "addFragment")
        }

        binding.buttonRefresh.setOnClickListener {
            viewModel.loadDataFromFile()
        }

        binding.search.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchShopItem(newText!!)
                return true
            }

        })
    }


    private fun initRecyclerView() {


        viewModel.shopItemLV.observe(this) {
            adapter.submitList(it)
        }
        binding.recyclerViewShopItem.layoutManager = LinearLayoutManager(this@MainActivity)
        binding.recyclerViewShopItem.adapter = adapter

        adapter.onItemClick = {
            launchScreenEditRemove(MODE_EDIT, it.id)
        }
        val swipeToDeleteCallBack = SwipeToDeleteCallBack(adapter)
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallBack)
        itemTouchHelper.attachToRecyclerView(binding.recyclerViewShopItem)

        adapter.onLongListener = {
            viewModel.removeShopItem(it.id)
            viewModel.loadDataFromFile()
        }
    }

    private fun launchScreenEditRemove(id: String, mode: String) {
        var dialog = AddShopItemFragment.newInstanceEditOrRemove(id, mode)
        dialog.show(supportFragmentManager, "addFragment")
    }


}