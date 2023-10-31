package com.cc.codingcamp.fragment

import ProductAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cc.codingcamp.R
import com.cc.codingcamp.modal.ProductItem

class HomeFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var productAdapter: ProductAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val productList = ArrayList<ProductItem>()
        productList.add(ProductItem(R.mipmap.ic_launcher, "Belajar Konsep OOP Dalam Bahasa Java", "Rp. 120.000"))
        productList.add(ProductItem(R.mipmap.ic_launcher, "Membuat ppp dengan Java", "Rp. 120.000"))
        productList.add(ProductItem(R.mipmap.ic_launcher, "Membuat ppp dengan Java", "Rp. 120.000"))

        recyclerView = view?.findViewById(R.id.recyclerView) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        productAdapter = ProductAdapter(requireContext(), productList)
        recyclerView.adapter = productAdapter

        return view
    }
}