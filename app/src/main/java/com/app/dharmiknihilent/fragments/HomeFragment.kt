package com.app.dharmiknihilent.fragments

import android.annotation.SuppressLint
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
import com.app.dharmiknihilent.adapter.ProductListAdapter
import com.app.dharmiknihilent.databinding.FragmentProductListBinding
import com.app.dharmiknihilent.models.ProductList
import com.app.dharmiknihilent.utils.AppConstant
import com.app.dharmiknihilent.utils.DataState
import com.app.dharmiknihilent.viewmodels.ProductViewModel
import com.google.gson.Gson
import kotlinx.coroutines.launch

class HomeFragment : Fragment(), ProductListAdapter.ProductListListener {

    private lateinit var productListBindingBinding: FragmentProductListBinding
    private val productViewModel: ProductViewModel by activityViewModels()
    private var productListAdapter: ProductListAdapter? = null
    private var productList = mutableListOf<ProductList>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        productListBindingBinding = FragmentProductListBinding.inflate(inflater, container, false)
        return productListBindingBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObservers()
    }

    private fun setUpObservers() {
        lifecycleScope.launch {
            productViewModel.productListState.collect {
                when (it) {
                    is DataState.Data -> {
                        productListBindingBinding.progressBar.visibility = View.VISIBLE
                    }
                    is DataState.Success -> {
                        productList.clear()
                        productList.addAll(it.data)
                        displayProductList()
                    }
                    is DataState.Error -> {
                        noDataDisplay()
                    }
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun displayProductList() {
        productListBindingBinding.progressBar.visibility = View.GONE
        if (productList.isEmpty()) {
            productListBindingBinding.rvProductList.visibility = View.GONE
            productListBindingBinding.tvNoDataFound.visibility = View.VISIBLE
        } else {
            productListBindingBinding.tvNoDataFound.visibility = View.GONE
            productListBindingBinding.rvProductList.visibility = View.VISIBLE
        }

        if(productListAdapter==null) {
            val layoutManager = LinearLayoutManager(requireContext())
            val dividerItemDecoration =
                DividerItemDecoration(productListBindingBinding.rvProductList.context, layoutManager.orientation)
            productListAdapter = ProductListAdapter(productList, this)
            productListBindingBinding.rvProductList.layoutManager = layoutManager
            productListBindingBinding.rvProductList.addItemDecoration(dividerItemDecoration)
            productListBindingBinding.rvProductList.hasFixedSize()
            productListBindingBinding.rvProductList.adapter = productListAdapter
        }else{
            productListAdapter!!.notifyDataSetChanged()
        }
    }

    private fun noDataDisplay() {
        productListBindingBinding.progressBar.visibility = View.GONE
        productListBindingBinding.rvProductList.visibility = View.GONE
        productListBindingBinding.tvNoDataFound.visibility = View.VISIBLE
    }

    override fun onProductAddToFavourite(position: Int, productList: ProductList) {
        productList.isFavourite = true
        productViewModel.addRemoveFavourite(productList)
        productListAdapter!!.setAsFavourite(position)
    }

    override fun onProductDetail(productList: ProductList) {
        startActivity(
            Intent(requireActivity(), ProductDetailActivity::class.java)
                .putExtra(AppConstant.productData, Gson().toJson(productList)))
    }
}