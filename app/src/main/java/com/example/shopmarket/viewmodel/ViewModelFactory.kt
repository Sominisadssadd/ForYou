package com.example.shopmarket.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.RuntimeException

class ViewModelFactory(
    val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShopViewModel::class.java)){
            return ShopViewModel(application) as T
        }
        throw RuntimeException("Unknown viewModel type")
    }

}