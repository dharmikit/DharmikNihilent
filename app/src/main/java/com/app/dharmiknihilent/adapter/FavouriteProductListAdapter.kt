package com.app.dharmiknihilent.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.dharmiknihilent.R
import com.app.dharmiknihilent.databinding.ItemFavouriteProductBinding
import com.app.dharmiknihilent.models.ProductList
import com.bumptech.glide.Glide

class FavouriteProductListAdapter(private val productList :MutableList<ProductList>,
                                  private val favouriteProductListener: FavouriteProductListener) :
    RecyclerView.Adapter<FavouriteProductListAdapter.FavouriteProductListHolder>(){

    class FavouriteProductListHolder(val view: ItemFavouriteProductBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteProductListHolder {
        return FavouriteProductListHolder(
            ItemFavouriteProductBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: FavouriteProductListHolder, position: Int) {

        val productData = productList[position]
        Glide.with(holder.view.root.context)
            .load(productData.imageURL)
            .placeholder(R.drawable.ic_photo_thumb)
            .error(R.drawable.ic_photo_thumb)
            .centerCrop()
            .into(holder.view.imgProductImage)

        holder.view.tvProductName.text = productData.title
        holder.view.tvProductPrice.text = productData.price[0].value.toString()

        holder.view.imgRemove.setOnClickListener {
            favouriteProductListener.onRemoveFavouriteProduct(position,productData)
        }

        holder.view.root.setOnClickListener {
            favouriteProductListener.onProductDetail(productData)
        }
    }

    fun setAsUnFavourite(position: Int){
        if(position==0){
            productList.removeAt(0)
            notifyDataSetChanged()
        }else{
            productList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    interface FavouriteProductListener{
        fun onRemoveFavouriteProduct(position: Int,productList: ProductList)
        fun onProductDetail(productList: ProductList)
    }
}