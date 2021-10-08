package com.fmaldonado.tallerandroid

import com.google.gson.annotations.SerializedName

data class CharacterModel(
    @SerializedName("title")
    val name: String,
    @SerializedName("mal_id")
    val id: Int,
    @SerializedName("image_url")
    val imageUrl: String
)
