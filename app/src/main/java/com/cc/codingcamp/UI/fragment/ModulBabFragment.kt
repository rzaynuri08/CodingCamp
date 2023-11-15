package com.cc.codingcamp.UI.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cc.codingcamp.R
import com.cc.codingcamp.adapter.ExpandableModulAdapter
import com.cc.codingcamp.modal.MainPreview
import com.cc.codingcamp.modal.SubPreview
import com.cc.codingcamp.API.Service
import com.cc.codingcamp.UI.activity.MateriActivity
import com.cc.codingcamp.adapter.SubModulAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.await

class ModulBabFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ExpandableModulAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_modul_bab, container, false)
        recyclerView = view.findViewById(R.id.Recyclemodulbab)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = ExpandableModulAdapter(emptyList())
        recyclerView.adapter = adapter

        adapter.setOnSubItemClickListener { subPreview ->
            val intent = Intent(requireContext(), MateriActivity::class.java)
            intent.putExtra("id_subbab", subPreview.id_subbab)
            startActivity(intent)
        }

        val idModul = arguments?.getString("idJenisModul")
        if (idModul != null) {
            MainScope().launch { // Use MainScope for simplicity, create your own CoroutineScope if needed
                fetchData(idModul)
            }
        }

        return view
    }

    private suspend fun fetchMainPreviews(idModul: String): List<MainPreview> {
        return try {
            Service.apiService.getParentPreview(idModul).await()
        } catch (e: Exception) {
            emptyList()
        }
    }

    private suspend fun fetchSubPreviews(idBab: String): List<SubPreview> {
        return try {
            Service.apiService.getSubPreview(idBab).await()
        } catch (e: Exception) {
            emptyList()
        }
    }

    private suspend fun fetchData(idModul: String) {
        try {
            val mainPreviews = fetchMainPreviews(idModul)
            val subPreviews = mainPreviews.flatMap { mainPreview ->
                fetchSubPreviews(mainPreview.id_bab)
            }

            updateAdapter(mainPreviews, subPreviews)
        } catch (e: Exception) {
            // Handle exceptions
        }
    }

    private suspend fun updateAdapter(mainPreviews: List<MainPreview>, subPreviews: List<SubPreview>) {
        withContext(Dispatchers.Main) {
            mainPreviews.forEachIndexed { index, mainPreview ->
                mainPreview.subItemModels = subPreviews.filter { it.id_bab == mainPreview.id_bab }
            }
            adapter.updateData(mainPreviews)
        }
    }
}
