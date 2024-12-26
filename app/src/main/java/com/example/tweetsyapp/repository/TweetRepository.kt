package com.example.tweetsyapp.repository

import android.util.Log
import com.example.tweetsyapp.api.TweetsyApi
import com.example.tweetsyapp.models.TweetListItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class TweetRepository @Inject constructor(private val tweetsyApi : TweetsyApi) {


    private val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories: StateFlow<List<String>> = _categories.asStateFlow()


    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error


    private val _tweets = MutableStateFlow<List<TweetListItem>>(emptyList())
    val tweets: StateFlow<List<TweetListItem>> = _tweets.asStateFlow()


    suspend fun getCategories(){
        try {
            val response = tweetsyApi.getCategories()
            if (response.isSuccessful && response.body() != null) {
                _categories.emit(response.body()!!)
            }
            else {
                Log.d("API Error","The response is empty")
            }
        }
        catch(e:Exception) {
            Log.d("Network Error", "This is a network error")
        }
    }


    //Retry function with automatic retries delay between attempts
    suspend fun getTweets(category: String){
        var attempt = 1
        val maxAttempts = 3
        val delayDuration = 1000L // 1 second between retries
        while(attempt<=maxAttempts)
        {
            try{
                val response = tweetsyApi.getTweets("tweets[?(@.category==\"$category\")]")
                if(response.isSuccessful && response.body()!=null)
                {
                    _tweets.emit(response.body()!!)
                    return
                }
                else{
                    Log.d("API Error","Api error is coming after ${attempt}")
                }
            }
            catch (e:Exception)
            {
                Log.d("Network Error","Network error is coming in $attempt")
            }
            attempt++
            if(attempt<=maxAttempts){
                kotlinx.coroutines.delay(delayDuration*attempt) // Exponential backoff (increasing delay)

            }

            //After all retry fails,show an error
            _error.emit("failed to fetch tweets after max attempts $maxAttempts")
        }

    }

}