package com.inaki.dailyAdvice.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inaki.dailyAdvice.Model.AdviceModel
import com.inaki.dailyAdvice.Model.GameAdviceModel
import com.inaki.dailyAdvice.Network.AdviceService
import kotlinx.coroutines.*


class AdviceViewModel(private val adviceService: AdviceService): ViewModel() {


    fun getRandomAdvice(): MutableLiveData<AdviceModel> {
        val result = MutableLiveData<AdviceModel>()

        viewModelScope.launch {
                val newAdvice: AdviceModel = withContext(Dispatchers.IO) {
                    adviceService.getRandomAdvice().body()!!
                }
                result.postValue(newAdvice)
        }
        return result
    }

    fun getSearchedAdvices(topic: String): MutableLiveData<AdviceModel> {
        val advices = MutableLiveData<AdviceModel>()

        viewModelScope.launch {
            val newAdviceSearched = withContext(Dispatchers.IO){
                adviceService.getAdvices(topic).body()
            }
            advices.postValue(newAdviceSearched)
        }
        return advices
    }

    fun getGameAdvice(id: String): MutableLiveData<GameAdviceModel> {
        val gameAdvice = MutableLiveData<GameAdviceModel>()

        viewModelScope.launch {
            val newGameAdvice = withContext(Dispatchers.IO){
                adviceService.getGameAdvice(id).body()
            }
            gameAdvice.postValue(newGameAdvice)
        }
        return gameAdvice
    }
}