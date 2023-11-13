package com.cc.codingcamp.UI.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cc.codingcamp.R
import com.cc.codingcamp.adapter.ExpandableRecyclerViewAdapter
import com.cc.codingcamp.modal.MainPreview
import com.cc.codingcamp.modal.SubPreview
import com.cc.codingcamp.API.Service
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class ReviewproductFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ExpandableRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_reviewproduct, container, false)
        recyclerView = view.findViewById(R.id.ReviewRecycleView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = ExpandableRecyclerViewAdapter(emptyList())
        recyclerView.adapter = adapter

        val idModul = arguments?.getString("idJenisModul")
        if (idModul != null) {
            CoroutineScope(Dispatchers.Main).launch {
                fetchData(idModul)
            }
        }

        return view
    }

    private suspend fun fetchMainPreviews(idModul: String): List<MainPreview> {
        return try {
            suspendCoroutine { continuation ->
                Service.apiService.getParentPreview(idModul)
                    .enqueue(object : Callback<List<MainPreview>> {
                        override fun onResponse(
                            call: Call<List<MainPreview>>,
                            response: Response<List<MainPreview>>
                        ) {
                            if (response.isSuccessful) {
                                val mainPreviews = response.body()
                                if (mainPreviews != null) {
                                    continuation.resume(mainPreviews)
                                } else {
                                    continuation.resumeWithException(NullPointerException("MainPreviews is null"))
                                }
                            } else {
                                continuation.resumeWithException(HttpException(response))
                            }
                        }

                        override fun onFailure(call: Call<List<MainPreview>>, t: Throwable) {
                            continuation.resumeWithException(t)
                        }
                    })
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    private suspend fun fetchSubPreviews(idBab: String): List<SubPreview> {
        return try {
            suspendCoroutine { continuation ->
                Service.apiService.getSubPreview(idBab)
                    .enqueue(object : Callback<List<SubPreview>> {
                        override fun onResponse(
                            call: Call<List<SubPreview>>,
                            response: Response<List<SubPreview>>
                        ) {
                            if (response.isSuccessful) {
                                val subPreviews = response.body()
                                if (subPreviews != null) {
                                    continuation.resume(subPreviews)
                                } else {
                                    continuation.resumeWithException(NullPointerException("SubPreviews is null"))
                                }
                            } else {
                                continuation.resumeWithException(HttpException(response))
                            }
                        }

                        override fun onFailure(call: Call<List<SubPreview>>, t: Throwable) {
                            continuation.resumeWithException(t)
                        }
                    })
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    private suspend fun fetchData(idModul: String) {
        try {
            val mainPreviews = fetchMainPreviews(idModul)
            val subPreviews = mainPreviews.map { mainPreview ->
                fetchSubPreviews(mainPreview.id_bab)
            }

            updateAdapter(mainPreviews, subPreviews)
        } catch (e: Exception) {
            // Handle exceptions
        }
    }

    private suspend fun updateAdapter(mainPreviews: List<MainPreview>, subPreviews: List<List<SubPreview>>) {
        withContext(Dispatchers.Main) {
            for (i in mainPreviews.indices) {
                mainPreviews[i].subItemModels = subPreviews[i]
            }
            adapter.updateData(mainPreviews)
        }
    }
}
