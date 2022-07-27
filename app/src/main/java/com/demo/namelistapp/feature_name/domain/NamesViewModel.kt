package com.demo.namelistapp.feature_name.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.namelistapp.db.table.NameItem
import com.demo.namelistapp.network.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NamesViewModel @Inject constructor(
    private val namesUseCase: GetNamesUseCase
) : ViewModel() {

    private val _noteLiveData: MutableLiveData<NetworkResult<List<NameItem>>> = MutableLiveData(NetworkResult.Loading())
    val noteLiveData: LiveData<NetworkResult<List<NameItem>>> = _noteLiveData

    private var getNotesJob: Job? = null

    init {
        getNotes()
    }

    private fun getNotes() {
        getNotesJob?.cancel()
        getNotesJob = namesUseCase.invoke()
            .onEach { notes ->
                _noteLiveData.value = notes
            }
            .launchIn(viewModelScope)
    }
}