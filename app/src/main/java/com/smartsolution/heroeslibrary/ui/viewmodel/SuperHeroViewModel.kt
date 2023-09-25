package com.smartsolution.heroeslibrary.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smartsolution.heroeslibrary.data.repository.network.RetrofitClient
import com.smartsolution.heroeslibrary.data.repository.core.SuperHeroDetailResponse
import com.smartsolution.heroeslibrary.data.repository.core.SuperHeroItemResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SuperHeroViewModel : ViewModel() {

    private val _superHeroesList = MutableLiveData<List<SuperHeroItemResponse>>()
    val superHeroesList: LiveData<List<SuperHeroItemResponse>> = _superHeroesList

    private val _superHeroItem = MutableLiveData<SuperHeroDetailResponse>()
    val superHeroitem: LiveData<SuperHeroDetailResponse> = _superHeroItem

    private val _waitDownload = MutableLiveData<Boolean>()
    val waitDownload : LiveData<Boolean> = _waitDownload

    fun getSuperHeroFind(query: String) {
        _waitDownload.value= true
        viewModelScope.launch(Dispatchers.IO) {
            val myResponse = RetrofitClient.webService.getSuperheroes(query)
            if (myResponse.isSuccessful) {
                if (myResponse.body() != null) {
                    withContext(Dispatchers.Main) {
                        _superHeroesList.value = myResponse.body()!!.superheroes
                        _waitDownload.value = false
                    }
                }
            }
        }
    }

    fun getSuperHeroItem(id: String) {
        _waitDownload.value= true
        viewModelScope.launch(Dispatchers.IO) {
            val myResponse = RetrofitClient.webService.getSuperHeroDetail(id)
            if (myResponse.body() != null) {
                withContext(Dispatchers.Main) {
                    _superHeroItem.value = myResponse.body()
                    _waitDownload.value= false
                }
            }
        }
    }

    init {
        getSuperHeroFind("Super")
    }

}