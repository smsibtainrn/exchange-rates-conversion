package com.smsrn.exchangerate.network

object ProtocolException {

    const val SOMETHING_WENT_WRONG = "Something went wrong. Please try again later."
    const val ERROR_CONNECTION_TIME_OUT = "Request timed out"
    const val ERROR_CONNECTION_FAILED = "Failed to connect. Please check internet connection."
    const val ERROR_UNKNOWN_HOST = "Please check internet connection."

    val exceptions = hashMapOf<Int, String>().apply {
        put(401, "Session time out. Please login again.")
        put(400, "Bad Request.")
        put(404, "Not found")
        put(500, SOMETHING_WENT_WRONG)
    }
}