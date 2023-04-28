package com.app.dharmiknihilent.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.dharmiknihilent.R
import com.app.dharmiknihilent.databinding.ItemProductBinding
import com.app.dharmiknihilent.models.ProductList
import com.bumptech.glide.Glide

class ProductListAdapter(private val productList :List<ProductList>,
private val productListListener:ProductListListener) :
    RecyclerView.Adapter<ProductListAdapter.ProductListHolder>(){

    class ProductListHolder(val view: ItemProductBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListHolder {
        return ProductListHolder(
            ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: ProductListHolder, position: Int) {

        val productData = productList[position]
        Glide.with(holder.view.root.context)
            .load(productData.imageURL)
            .placeholder(R.drawable.ic_photo_thumb)
            .error(R.drawable.ic_photo_thumb)
            .centerCrop()
            .into(holder.view.imgProductImage)

        holder.view.tvProductName.text = productData.title
        holder.view.tvProductPrice.text = productData.price[0].value.toString()

        if(productData.isFavourite){
            holder.view.imgAddToFavourite.setImageResource(R.drawable.ic_favourite)
        }else{
            holder.view.imgAddToFavourite.setImageResource(R.drawable.ic_un_favorite)
        }

        holder.view.imgAddToFavourite.setOnClickListener {
            if(!productData.isFavourite) {
                productListListener.onProductAddToFavourite(position,productData)
            }
        }

        holder.view.root.setOnClickListener {
            productListListener.onProductDetail(productData)
        }
    }

    fun setAsFavourite(position: Int){
        productList[position].isFavourite = true
        notifyItemChanged(position)
    }

    interface ProductListListener{
        fun onProductAddToFavourite(position: Int,productList:ProductList)
        fun onProductDetail(productList: ProductList)
    }
}