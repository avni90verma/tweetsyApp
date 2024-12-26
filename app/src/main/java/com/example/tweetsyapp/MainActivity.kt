package com.example.tweetsyapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tweetsyapp.api.TweetsyApi
import com.example.tweetsyapp.screens.CategoryScreen
import com.example.tweetsyapp.screens.DetailsScreen
import com.example.tweetsyapp.ui.theme.TweetsyAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    @Inject
    lateinit var tweetsyApi: TweetsyApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GlobalScope.launch {
            var response = tweetsyApi.getCategories()
            Log.d("AVNI",response.body().toString())
        }
        enableEdgeToEdge()
        setContent {
          DetailsScreen()
        }
    }
}