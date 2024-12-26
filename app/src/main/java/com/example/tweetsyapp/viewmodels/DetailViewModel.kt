package com.example.tweetsyapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tweetsyapp.models.TweetListItem
import com.example.tweetsyapp.repository.TweetRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class DetailViewModel @Inject constructor(private val repository: TweetRepository) : ViewModel() {

    val tweets: StateFlow<List<TweetListItem>> = repository.tweets

    init {
        viewModelScope.launch {
            repository.getTweets("android")
        }
    }
}