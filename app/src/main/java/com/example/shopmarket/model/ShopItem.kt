package com.example.shopmarket.model

import com.google.gson.annotations.SerializedName


data class ShopItem(
    @SerializedName("id") val id: String,
    @SerializedName("name") var name: String,
    @SerializedName("count") var count: String,
    @SerializedName("sum") var sum: String,
    @SerializedName("imagePath") var imagePath: String
)