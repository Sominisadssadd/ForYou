package com.example.shopmarket.model

import android.app.Application
import android.content.Context
import android.widget.Toast
import com.example.shopmarket.utils.removeFirstAndLastSymbols
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.io.IOException
import java.io.InputStream


private const val FILE_NAME = "shopItems.json"

class ShopRepositoryImpl(val context: Application) : ShopRepository {

    override fun addShopItem(shopItem: ShopItem) {
        val currentList = getAllShopItem().toMutableList()
        currentList.add(shopItem)
        writeChanges(currentList)
    }


    override fun editShopItem(shopItem: ShopItem) {

        val currentList = getAllShopItem().toMutableList()
        val item = currentList.find { it.id == shopItem.id }
        with(item) {
            this!!.name = shopItem.name
            count = shopItem.count
            sum = shopItem.sum
            imagePath = shopItem.imagePath

        }
        writeChanges(currentList)
    }

    override fun removeShopItem(id: String) {

        val currentList = getAllShopItem().toMutableList()
        currentList.removeIf { it.id == id }
        writeChanges(currentList)
    }

    private fun writeChanges(list: List<ShopItem>) {
        val mapper = jacksonObjectMapper()
        val file = File(context.filesDir, FILE_NAME)
        val result = mapper.writeValueAsString(list)
        file.writeText(result.toString())
    }

    override fun getAllShopItem(): List<ShopItem> {
        val file = File(context.filesDir, FILE_NAME)
        val datas = file.readLines().toString()

        val listPersonType = object : TypeToken<List<ShopItem>>() {}.type
        val gson: List<ShopItem> =
            Gson().fromJson(datas.removeFirstAndLastSymbols(), listPersonType)
        return gson

    }

    override fun findShopItem(text: String): List<ShopItem> {
        val data = getAllShopItem().toMutableList()
        if (text.isBlank()) return data

        return data.filter { it.name.startsWith(text) }

    }


}