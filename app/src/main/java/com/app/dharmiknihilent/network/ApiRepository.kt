package com.app.dharmiknihilent.network

import com.app.dharmiknihilent.database.AppDatabase
import com.app.dharmiknihilent.models.ProductList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ApiRepository @Inject constructor(private val apiService: ApiService,
                                        private val appDatabase: AppDatabase){

    suspend fun getProductList() = flow { emit(apiService.getProductList()) }

    fun getProduct (): Flow<List<ProductList>> = appDatabase.productDao().getProduct()

    fun insertAllProduct(products: List<ProductList>): Flow<Unit> = flow {
        appDatabase.productDao().insertAllProduct(products)
        emit(Unit)
    }

    suspend fun updateProduct(productList:ProductList) = withContext(Dispatchers.IO){
        appDatabase.productDao().updateProduct(productList)
    }

}