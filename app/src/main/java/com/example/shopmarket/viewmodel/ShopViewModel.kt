package com.example.shopmarket.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopmarket.model.ShopItem
import com.example.shopmarket.model.ShopRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ShopViewModel(application: Application) : AndroidViewModel(application) {

    private var repositoryImpl = ShopRepositoryImpl(application)

    private val shopItemsMLV = MutableLiveData<List<ShopItem>>()
    val shopItemLV: LiveData<List<ShopItem>>
        get() = shopItemsMLV

    init {
        loadDataFromFile()
    }

    fun loadDataFromFile() {
        shopItemsMLV.value = (repositoryImpl.getAllShopItem())
    }

    fun addShopItem(shopItem: ShopItem) {
        repositoryImpl.addShopItem(shopItem)
    }

    fun removeShopItem(shopItemId: String) {
        repositoryImpl.removeShopItem(shopItemId)
    }

    fun editShopItem(shopItem: ShopItem) {
        repositoryImpl.editShopItem(shopItem)
    }

    fun searchShopItem(prefix: String) {
        shopItemsMLV.value = repositoryImpl.findShopItem(prefix)
    }
}