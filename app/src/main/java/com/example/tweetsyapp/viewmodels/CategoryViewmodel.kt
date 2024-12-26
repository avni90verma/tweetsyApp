package com.example.tweetsyapp.viewmodels

import com.example.tweetsyapp.repository.TweetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

@HiltViewModel
class CategoryViewmodel @Inject constructor(private val repository: TweetRepository) :ViewModel() {

    val categories: StateFlow<List<String>> = repository.categories

    init {
       viewModelScope.launch {
           repository.getCategories()
       }
    }

}