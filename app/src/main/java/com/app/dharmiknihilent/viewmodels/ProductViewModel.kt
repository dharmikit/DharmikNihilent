package com.app.dharmiknihilent.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.dharmiknihilent.models.ProductList
import com.app.dharmiknihilent.network.ApiRepository
import com.app.dharmiknihilent.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val apiRepository: ApiRepository): ViewModel(){

    val productListState = MutableStateFlow<DataState<List<ProductList>>>(DataState.Data)
    val favouriteProductListState = MutableStateFlow<DataState<List<ProductList>>>(DataState.Data)

    init {
        fetchProductList()
    }

    fun addRemoveFavourite(productList:ProductList) {
        viewModelScope.launch {
            apiRepository.updateProduct(productList)
        }
    }

    fun fetchFavouriteList() {
        viewModelScope.launch(Dispatchers.Main) {
            favouriteProductListState.value = DataState.Data
            apiRepository.getProduct()
                .map {productList->
                    val favouriteProduct = mutableListOf<ProductList>()
                    for (product in productList) {
                        if(product.isFavourite){
                            favouriteProduct.add(product)
                        }
                    }
                    favouriteProduct
                }
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    favouriteProductListState.value = DataState.Error(e.toString())
                }
                .collect {
                    favouriteProductListState.value = DataState.Success(it)
                }
        }
    }

    private fun fetchProductList() {
        viewModelScope.launch(Dispatchers.Main) {
            productListState.value = DataState.Data
            apiRepository.getProduct()
                .flatMapConcat { productFromDb ->
                    if (productFromDb.isEmpty()) {
                        return@flatMapConcat apiRepository.getProductList()
                            .map { apiProductList ->
                                val productList = mutableListOf<ProductList>()
                                productList.addAll(apiProductList.products)
                                productList
                            }
                            .flatMapConcat { usersToInsertInDB ->
                                apiRepository.insertAllProduct(usersToInsertInDB)
                                    .flatMapConcat {
                                        flow {
                                            emit(usersToInsertInDB)
                                        }
                                    }
                            }
                    } else {
                        return@flatMapConcat flow {
                            emit(productFromDb)
                        }
                    }
                }
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    productListState.value = DataState.Error(e.toString())
                }
                .collect {
                    productListState.value = DataState.Success(it)
                }
        }
    }
}