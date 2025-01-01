package dev.sakura.newsapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
<<<<<<< HEAD
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
=======
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
>>>>>>> 54693bd4d5d17ebca58fa09ea294feeafb636e92
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import dev.sakura.news.main.NewsMainScreen
import dev.sakura.newsapplication.ui.theme.NewsApplicationTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            NewsApplicationTheme {
                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NewsMainScreen()
                }
            }
        }
    }
}
