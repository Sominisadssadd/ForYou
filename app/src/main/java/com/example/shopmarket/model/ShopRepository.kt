package com.example.shopmarket.model

interface ShopRepository {

    fun addShopItem(shopItem: ShopItem)

    fun editShopItem(shopItem: ShopItem)

    fun removeShopItem(id: String)

    fun getAllShopItem(): List<ShopItem>

    fun findShopItem(text: String): List<ShopItem>


}