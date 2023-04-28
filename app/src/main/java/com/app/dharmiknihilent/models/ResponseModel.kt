package com.app.dharmiknihilent.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class ProductData(
    val products: List<ProductList>)

@Entity(tableName = "productList")
data class ProductList(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String="",

    @ColumnInfo(name = "addToCartButtonText")
    val addToCartButtonText: String="",

    @ColumnInfo(name = "brand")
    val brand: String,

    @ColumnInfo(name = "imageURL")
    val imageURL: String="",

    @ColumnInfo(name = "isAddToCartEnable")
    val isAddToCartEnable: Boolean,

    @ColumnInfo(name = "isDeliveryOnly")
    val isDeliveryOnly: Boolean,

    @ColumnInfo(name = "isDirectFromSupplier")
    val isDirectFromSupplier: Boolean,

    @ColumnInfo(name = "isFindMeEnable")
    val isFindMeEnable: Boolean,

    @ColumnInfo(name = "isInTrolley")
    val isInTrolley: Boolean,

    @ColumnInfo(name = "isInWishlist")
    val isInWishlist: Boolean,
    @ColumnInfo(name = "price")
    val price: List<Price> = listOf(),
    @ColumnInfo(name = "ratingCount")
    val ratingCount: Double,

    @ColumnInfo(name = "saleUnitPrice")
    val saleUnitPrice: Double,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "totalReviewCount")
    val totalReviewCount: Int,

    @ColumnInfo(name = "isFavourite")
    var isFavourite:Boolean=false)

data class Price(
    val isOfferPrice: Boolean=false,
    val message: String="",
    val value: Double=0.0)

