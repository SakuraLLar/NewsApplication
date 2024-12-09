package dev.sakura.news.main

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow

internal class NewsMainViewModel : ViewModel() {

    val articles: StateFlow<Article>
}