package dev.sakura.news.main

<<<<<<< HEAD
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
=======
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
>>>>>>> 54693bd4d5d17ebca58fa09ea294feeafb636e92
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
<<<<<<< HEAD
import androidx.compose.material3.CircularProgressIndicator
=======
>>>>>>> 54693bd4d5d17ebca58fa09ea294feeafb636e92
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
<<<<<<< HEAD
import androidx.compose.ui.Alignment
=======
>>>>>>> 54693bd4d5d17ebca58fa09ea294feeafb636e92
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
<<<<<<< HEAD
=======
import dev.sakura.news.data.model.Article
>>>>>>> 54693bd4d5d17ebca58fa09ea294feeafb636e92

@Composable
public fun NewsMainScreen() {
    NewsMainScreen(viewModel = viewModel())
}

@Composable
internal fun NewsMainScreen(viewModel: NewsMainViewModel) {
    val state by viewModel.state.collectAsState()
    when (val currentState = state) {
        is State.Success -> Articles(currentState.articles)
        is State.Error -> ArticlesWithError(currentState.articles)
        is State.Loading -> ArticlesDuringUpdate(currentState.articles)
        State.None -> NewsEmpty()
    }
}

@Composable
internal fun ArticlesWithError(articles: List<ArticleUI>?) {
<<<<<<< HEAD
    Column {
        Box(
            Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.error),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Error during update", color = MaterialTheme.colorScheme.onError)
        }
        if (articles != null) {
            Articles(articles = articles)
        }
=======
    if (articles != null) {
        Articles(articles = articles)
    } else {
        NewsEmpty()
>>>>>>> 54693bd4d5d17ebca58fa09ea294feeafb636e92
    }
}

@Composable
<<<<<<< HEAD
@Preview
internal fun ArticlesDuringUpdate(
    @PreviewParameter(ArticlesPreviewProvider::class, limit = 1) articles: List<ArticleUI>?,
) {
    Column {
        Box(
            Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        if (articles != null) {
            Articles(articles = articles)
        }
=======
internal fun ArticlesDuringUpdate(
    @PreviewParameter(ArticlesPreviewProvider::class, limit = 1) articles: List<ArticleUI>?,
) {
    if (articles != null) {
        Articles(articles = articles)
    } else {
        NewsEmpty()
>>>>>>> 54693bd4d5d17ebca58fa09ea294feeafb636e92
    }
}

@Composable
internal fun NewsEmpty() {
<<<<<<< HEAD
    Box(contentAlignment = Alignment.Center) {
        Text("No news")
    }
=======
>>>>>>> 54693bd4d5d17ebca58fa09ea294feeafb636e92
}

@Preview
@Composable
private fun Articles(
    @PreviewParameter(ArticlesPreviewProvider::class, limit = 1) articles: List<ArticleUI>,
) {
    LazyColumn {
        items(articles) { article ->
            key(article.id) {
                Article(article)
            }
        }
    }
}

@Preview
@Composable
internal fun Article(
    @PreviewParameter(ArticlePreviewProvider::class, limit = 1) article: ArticleUI,
) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = article.title, style = MaterialTheme.typography.headlineMedium, maxLines = 1)
        Spacer(modifier = Modifier.size(4.dp))
        Text(text = article.description, style = MaterialTheme.typography.bodyMedium, maxLines = 3)
    }

}

private class ArticlePreviewProvider : PreviewParameterProvider<ArticleUI> {
    override val values = sequenceOf(
        ArticleUI(
            1,
            "Android Studio Ladybug",
            "News stable version of Android IDE has been release",
            imageUrl = null,
            url = "",
        ),
        ArticleUI(
            2,
            "Gemini 1.5 Release",
            "Update version of Google AI is available",
            imageUrl = null,
            url = "",
        ),
        ArticleUI(
            3,
            "Shape animations (10 min)",
            "How to use shape transform animations in Compose",
            imageUrl = null,
            url = "",
        ),
    )

}

private class ArticlesPreviewProvider : PreviewParameterProvider<List<ArticleUI>> {

    private val articleProvider = ArticlePreviewProvider()

    override val values = sequenceOf(
        articleProvider.values.toList()
    )
}