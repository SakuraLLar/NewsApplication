package dev.sakura.news.data

import dev.sakura.news.data.RequestResult.InProgress
import dev.sakura.news.data.RequestResult.Success
import dev.sakura.news.data.RequestResult.Error

interface MergeStrategy<E> {

    fun merge(right: E, left: E): E
}

internal class RequestResponseMergeStrategy<T : Any> : MergeStrategy<RequestResult<T>> {

    override fun merge(
        right: RequestResult<T>,
        left: RequestResult<T>
    ): RequestResult<T> {
        return when {
            right is InProgress && left is InProgress -> merge(right, left)
            right is Success && left is InProgress -> merge(right, left)
            right is InProgress && left is Success -> merge(right, left)
            right is Success && left is Success -> merge(right, left)
            right is InProgress && left is Error -> merge(right, left)
            right is Success && left is Error -> merge(right, left)
            else -> error("Unimplemented branch right=$right & left=$left ")
        }
    }

    private fun merge(
        right: InProgress<T>,
        left: InProgress<T>
    ): RequestResult<T> {
        return when {
            left.data != null -> InProgress(left.data)
            else -> InProgress(right.data)
        }
    }

    @Suppress("UNUSED_PARAMETER")
    private fun merge(
        right: Success<T>,
        left: InProgress<T>
    ): RequestResult<T> {
        return InProgress(right.data)
    }

    @Suppress("UNUSED_PARAMETER")
    private fun merge(
        right: InProgress<T>,
        left: Success<T>
    ): RequestResult<T> {
        return InProgress(left.data)
    }

    @Suppress("UNUSED_PARAMETER")
    private fun merge(
        right: Success<T>,
        left: Success<T>
    ): RequestResult<T> {
        return Success(data = left.data)
    }

    private fun merge(
        right: InProgress<T>,
        left: Error<T>
    ): RequestResult<T> {
        return Error(data = left.data?: right.data, error = left.error)
    }

    private fun merge(
        right: Success<T>,
        left: Error<T>
    ): RequestResult<T> {
        return Error(data = right.data, error = left.error)
    }
}