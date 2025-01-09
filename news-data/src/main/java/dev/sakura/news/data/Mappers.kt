package dev.sakura.news.data

import dev.sakura.news.data.model.Article
import dev.sakura.news.data.model.Source
import dev.sakura.news.database.models.ArticleDBO
import dev.sakura.newsapi.models.ArticleDTO
import dev.sakura.newsapi.models.SourceDTO
import dev.sakura.news.database.models.Source as SourceDBO

internal fun ArticleDBO.toArticle(): Article {
    return Article(
        cacheId = id,
        source = source.toSource(),
        author = author,
        title = title,
        description = description,
        url = url,
        urlToImage = urlToImage,
        publishedAt = publishedAt,
        content = content
    )
}

internal fun SourceDBO.toSource(): Source {
    return Source(id = id, name = name)
}

internal fun SourceDTO.toSource(): Source {
    return Source(id = id ?: name, name = name)
}

internal fun SourceDTO.toSourceDBO(): SourceDBO {
    return SourceDBO(id = id ?: name, name = name)
}

internal fun ArticleDTO.toArticle(): Article {
    return Article(
        source = source.toSource(),
        author = author,
        title = title,
        description = description,
        url = url,
        urlToImage = urlToImage,
        publishedAt = publishedAt,
        content = content
    )
}

internal fun ArticleDTO.toArticleDBO(): ArticleDBO {
    return ArticleDBO(
        source = source.toSourceDBO(),
        author = author,
        title = title,
        description = description,
        url = url,
        urlToImage = urlToImage,
        publishedAt = publishedAt,
        content = content
    )
}