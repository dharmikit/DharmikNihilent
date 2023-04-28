package com.app.dharmiknihilent.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dharmiknihilent.activities.ProductDetailActivity
import com.app.dharmiknihilent.adapter.FavouriteProductListAdapter
import com.app.dharmiknihilent.databinding.FragmentProductListBinding
import com.app.dharmiknihilent.models.ProductList
import com.app.dharmiknihilent.utils.AppConstant
import com.app.dharmiknihilent.utils.DataState
import com.app.dharmiknihilent.viewmodels.ProductViewModel
import com.google.gson.Gson
import kotlinx.coroutines.launch

class FavoriteFragment : Fragment(), FavouriteProductListAdapter.FavouriteProductListener {

    private lateinit var productListBindingBinding: FragmentProductListBinding
    private val productViewModel: ProductViewModel by activityViewModels()
    private var favouriteProductAdapter: FavouriteProductListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        productListBindingBinding = FragmentProductListBinding.inflate(inflater, container, false)
        return productListBindingBinding.root
    }

    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)
        if (menuVisible && favouriteProductAdapter == null) {
            productViewModel.fetchFavouriteList()
            getFavouriteList()
        }
    }

    private fun getFavouriteList() {
        lifecycleScope.launch {
            productViewModel.favouriteProductListState.collect {
                when (it) {
                    is DataState.Data -> {
                        productListBindingBinding.progressBar.visibility = View.VISIBLE
                    }
                    is DataState.Success -> {
                        displayFavouriteProductList(it.data)
                    }
                    is DataState.Error -> {
                        noDataDisplay()
                    }
                }
            }
        }
    }

    private fun displayFavouriteProductList(productList: List<ProductList>) {
        productListBindingBinding.progressBar.visibility = View.GONE
        if (productList.isEmpty()) {
            productListBindingBinding.rvProductList.visibility = View.GONE
            productListBindingBinding.tvNoDataFound.visibility = View.VISIBLE
        } else {
            productListBindingBinding.tvNoDataFound.visibility = View.GONE
            productListBindingBinding.rvProductList.visibility = View.VISIBLE
        }

        val layoutManager = LinearLayoutManager(requireContext())
        val dividerItemDecoration =
            DividerItemDecoration(
                productListBindingBinding.rvProductList.context,
                layoutManager.orientation
            )
        favouriteProductAdapter =
            FavouriteProductListAdapter(productList as MutableList<ProductList>, this)
        productListBindingBinding.rvProductList.layoutManager = layoutManager
        productListBindingBinding.rvProductList.addItemDecoration(dividerItemDecoration)
        productListBindingBinding.rvProductList.adapter = favouriteProductAdapter
    }

    private fun noDataDisplay() {
        productListBindingBinding.progressBar.visibility = View.GONE
        productListBindingBinding.rvProductList.visibility = View.GONE
        productListBindingBinding.tvNoDataFound.visibility = View.VISIBLE
    }

    override fun onRemoveFavouriteProduct(position: Int, productList: ProductList) {
        productList.isFavourite = false
        productViewModel.addRemoveFavourite(productList)
        favouriteProductAdapter!!.setAsUnFavourite(position)
        if (favouriteProductAdapter!!.itemCount == 0) {
            noDataDisplay()
        }
    }

    override fun onProductDetail(productList: ProductList) {
        startActivity(
            Intent(requireActivity(), ProductDetailActivity::class.java)
                .putExtra(AppConstant.productData, Gson().toJson(productList)))
    }
}