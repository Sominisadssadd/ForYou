package com.example.shopmarket.view

import androidx.recyclerview.widget.DiffUtil
import com.example.shopmarket.model.ShopItem

class ShopItemCallBack : DiffUtil.ItemCallback<ShopItem>() {

    override fun areItemsTheSame(oldItem: ShopItem, newItem: ShopItem) = oldItem == newItem


    override fun areContentsTheSame(oldItem: ShopItem, newItem: ShopItem) = oldItem.id == newItem.id
}