package dev.sakura.news.data

interface MergeStrategy<E> {

    fun merge(right: E, left: E): E
}

internal class RequestResponseMergeStrategy<T : Any> : MergeStrategy<RequestResult<T>> {

    override fun merge(right: RequestResult<T>, left: RequestResult<T>): RequestResult<T> {
        return when {
            right is RequestResult.InProgress && left is RequestResult.InProgress -> merge(right, left)

            right is RequestResult.Success && left is RequestResult.InProgress -> merge(right, left)

            right is RequestResult.InProgress && left is RequestResult.Success -> merge(right, left)

            right is RequestResult.Success && left is RequestResult.Error -> merge(right, left)

            else -> error("Unimplemented branch")
        }
    }

    private fun merge(
        right: RequestResult.InProgress<T>,
        left: RequestResult.InProgress<T>
    ): RequestResult<T> {
        return when {
            left.data != null -> RequestResult.InProgress(left.data)
            else -> RequestResult.InProgress(right.data)
        }
    }

    private fun merge( 
        right: RequestResult.Success<T>,
        left: RequestResult.InProgress<T>
    ): RequestResult<T> {
        return RequestResult.InProgress(right.data)
    }

    private fun merge(
        right: RequestResult.InProgress<T>,
        left: RequestResult.Success<T>
    ): RequestResult<T> {
        return RequestResult.InProgress(left.data)
    }

    private fun merge(
        right: RequestResult.Success<T>,
        left: RequestResult.Error<T>
    ): RequestResult<T> {
        return RequestResult.Error(data = right.data, error = left.error)
    }
}