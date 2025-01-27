package dev.sakura.news.data

import dev.sakura.common.Logger
import dev.sakura.news.data.model.Article
import dev.sakura.news.database.NewsDatabase
import dev.sakura.news.database.models.ArticleDBO
import dev.sakura.newsapi.NewsApi
import dev.sakura.newsapi.models.ArticleDTO
import dev.sakura.newsapi.models.ResponseDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ArticlesRepository @Inject constructor(
    private val database: NewsDatabase,
    private val api: NewsApi,
    private val logger: Logger,
) {

    fun getAll(
        query: String,
        mergeStrategy: MergeStrategy<RequestResult<List<Article>>> = RequestResponseMergeStrategy(),
    ): Flow<RequestResult<List<Article>>> {
        val cachedAllArticles: Flow<RequestResult<List<Article>>> = gelAllFromDatabase()
        val remoteArticles: Flow<RequestResult<List<Article>>> = getAllFromServer(query)

        return cachedAllArticles.combine(remoteArticles, mergeStrategy::merge)
            .flatMapLatest { result ->
                if (result is RequestResult.Success) {
                    database.articleDAO.observeAll()
                        .map { DBOS -> DBOS.map { it.toArticle() } }
                        .map { RequestResult.Success(it) }
                } else {
                    flowOf(result)
                }
            }
    }

    private fun getAllFromServer(query: String): Flow<RequestResult<List<Article>>> {
        val apiRequest =
            flow { emit(api.everything(query = query)) }
                .onEach { result ->
                    if (result.isSuccess) saveArticlesToCache(result.getOrThrow().articles)
                }
                .onEach { result ->
                    if (result.isFailure) {
                        logger.e(
                            LOG_TAG,
                            "Error getting data from server. Cause = ${result.exceptionOrNull()}"
                        )
                    }
                }
                .map { it.toRequestResult() }

        val start = flowOf<RequestResult<ResponseDTO<ArticleDTO>>>(RequestResult.InProgress())
        return merge(apiRequest, start)
            .map { result: RequestResult<ResponseDTO<ArticleDTO>> ->
                result.map { response -> response.articles.map { it.toArticle() } }
            }
    }

    private suspend fun saveArticlesToCache(data: List<ArticleDTO>) {
        val DBOS = data.map { articleDTO -> articleDTO.toArticleDBO() }
        database.articleDAO.insert(DBOS)
    }

    private fun gelAllFromDatabase(): Flow<RequestResult<List<Article>>> {
        val databaseRequest =
            database.articleDAO::getAll.asFlow()
                .map<List<ArticleDBO>, RequestResult<List<ArticleDBO>>> { RequestResult.Success(it) }
                .catch {
                    logger.e(LOG_TAG, "Error getting from database. Cause = $it")
                    emit(RequestResult.Error(error = it))
                }

        val start = flowOf<RequestResult<List<ArticleDBO>>>(RequestResult.InProgress())

        return merge(start, databaseRequest).map { result ->
            result.map { DBOS ->
                DBOS.map { it.toArticle() }
            }
        }
    }

    private companion object {
        const val LOG_TAG = "ArticlesRepository"
    }
}
