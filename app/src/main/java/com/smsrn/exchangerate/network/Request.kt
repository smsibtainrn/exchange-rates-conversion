package com.smsrn.exchangerate.network

interface Request<E> {
    suspend fun execute(): Response<E?>
    suspend operator fun invoke(): Response<E?>
    fun suppressUnAuthorizedPropagation(): Request<E>
    fun cancel()
}