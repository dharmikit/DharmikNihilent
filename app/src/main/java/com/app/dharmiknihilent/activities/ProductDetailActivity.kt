package com.app.dharmiknihilent.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.app.dharmiknihilent.R
import com.app.dharmiknihilent.databinding.ActivityProductDetailBinding
import com.app.dharmiknihilent.models.ProductList
import com.app.dharmiknihilent.utils.AppConstant
import com.app.dharmiknihilent.viewmodels.ProductViewModel
import com.bumptech.glide.Glide
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailActivity : AppCompatActivity(){

    private lateinit var productDetailBinding: ActivityProductDetailBinding
    private val productViewModel: ProductViewModel by viewModels()
    private var productList:ProductList?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productDetailBinding = ActivityProductDetailBinding.inflate(layoutInflater)
        val view = productDetailBinding.root
        setContentView(view)
        init()
    }

    private fun init(){

        productList = Gson().fromJson(intent.getStringExtra(AppConstant.productData),ProductList::class.java)
        productDetailBinding.tbProductDetail.title = productList!!.brand
        productDetailBinding.tbProductDetail.setNavigationOnClickListener {
            onBackPressed()
        }

        Glide.with(this@ProductDetailActivity)
            .load(productList!!.imageURL)
            .placeholder(R.drawable.ic_photo_thumb)
            .error(R.drawable.ic_photo_thumb)
            .centerCrop()
            .into(productDetailBinding.imgProductDetail)

        productDetailBinding.tvProducDetailName.text = productList!!.title
        productDetailBinding.tvProductDetailPrice.text = productList!!.price[0].value.toString()
        productDetailBinding.rbProductDetail.rating = productList!!.ratingCount.toFloat()
        if(productList!!.isFavourite){
            productDetailBinding.imgProductDetailFavourite.setImageResource(R.drawable.ic_favourite)
        }else{
            productDetailBinding.imgProductDetailFavourite.setImageResource(R.drawable.ic_un_favorite)
        }

        productDetailBinding.imgProductDetailFavourite.setOnClickListener {
            if(!productList!!.isFavourite){
                productList!!.isFavourite = true
                productViewModel.addRemoveFavourite(productList!!)
                productDetailBinding.imgProductDetailFavourite.setImageResource(R.drawable.ic_favourite)
            }
        }
    }
}