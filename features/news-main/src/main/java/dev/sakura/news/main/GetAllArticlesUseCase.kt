package dev.sakura.news.main

import dev.sakura.news.data.ArticlesRepository
import dev.sakura.news.data.RequestResult
import dev.sakura.news.data.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import dev.sakura.news.data.model.Article as DataArticle

internal class GetAllArticlesUseCase @Inject constructor(
    private val repository: ArticlesRepository,
) {

    operator fun invoke(query: String): Flow<RequestResult<List<ArticleUI>>> {
        return repository.getAll(query)
            .map { requestResult ->
                requestResult.map { articles ->
                    articles.map { it.toUiArticles() }
                }
            }

    }
}

private fun DataArticle.toUiArticles(): ArticleUI {
<<<<<<< HEAD
    return ArticleUI(
        id = cacheId,
        title = title,
        description = description,
        imageUrl = urlToImage,
        url = url
    )
=======
    TODO("Not yet implemented")
>>>>>>> 54693bd4d5d17ebca58fa09ea294feeafb636e92
}