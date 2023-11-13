package com.cc.codingcamp.modal

data class MainPreview (
    val id_bab : String,
    val nama_bab : String,
    var subItemModels : List<SubPreview>
)