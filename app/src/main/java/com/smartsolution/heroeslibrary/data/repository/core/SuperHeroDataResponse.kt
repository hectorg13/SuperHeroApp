package com.smartsolution.heroeslibrary.data.repository.core

import com.google.gson.annotations.SerializedName

data class SuperHeroDataResponse(
    @SerializedName("response") val response: String,
    @SerializedName("results") val superheroes: List<SuperHeroItemResponse>
)

data class SuperHeroItemResponse(
    @SerializedName("id") val superHeroesId: String,
    @SerializedName("name") val name: String,
    @SerializedName("image") val superHeroImage: SuperHeroImageResponse
)

data class SuperHeroImageResponse(@SerializedName("url") val url: String)