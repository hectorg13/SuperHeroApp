package com.smartsolution.heroeslibrary.data.repository.network

import com.smartsolution.heroeslibrary.data.repository.core.SuperHeroDataResponse
import com.smartsolution.heroeslibrary.data.repository.core.SuperHeroDetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WebService {
    @GET("/api/3582365648665498/search/{name}")
    suspend fun getSuperheroes(@Path("name") superheroName: String): Response<SuperHeroDataResponse>

    @GET("/api/3582365648665498/{id}")
    suspend fun getSuperHeroDetail(@Path("id") superheroId: String): Response<SuperHeroDetailResponse>


}