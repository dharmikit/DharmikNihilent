package com.app.dharmiknihilent.database

import androidx.room.*
import com.app.dharmiknihilent.models.ProductList
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Query("SELECT * FROM productList")
    fun getProduct(): Flow<List<ProductList>>

    @Insert
    fun insertAllProduct(products: List<ProductList>)

    @Update
    suspend fun updateProduct(productList: ProductList)

}