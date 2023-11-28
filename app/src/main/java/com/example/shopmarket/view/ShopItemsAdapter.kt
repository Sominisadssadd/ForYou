package com.example.shopmarket.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewbinding.ViewBinding
import com.example.shopmarket.R
import com.example.shopmarket.databinding.ActivityMainBinding
import com.example.shopmarket.databinding.ShopitemBinding
import com.example.shopmarket.model.ShopItem
import com.squareup.picasso.Picasso


class ShopItemsAdapter : ListAdapter<ShopItem, ShopItemsAdapter.MyViewHolder>(ShopItemCallBack()) {


    var onItemClick: ((shopItem: ShopItem) -> Unit)? = null
    var onLongListener: ((shopItem: ShopItem) -> Unit)? = null




    inner class MyViewHolder(val binding: ShopitemBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ShopitemBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = currentList[position]
        holder.binding.apply {

            var imagePath = currentItem.imagePath
            if (imagePath.isBlank()) {
                itemImage.setImageDrawable(ContextCompat.getDrawable(holder.itemView.context,R.drawable.baseline_do_disturb_24))
            }else{
                Picasso.get().load(imagePath).into(itemImage)
            }

            itemName.text = currentItem.name
            itemQuantity.text = currentItem.count
            itemPrice.text = currentItem.sum
        }

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(currentItem)
        }

    }

    fun removeShopItemElements(shopItem: ShopItem) {
        onLongListener?.invoke(shopItem)
    }
}