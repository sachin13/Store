package com.example.store.data.util

class ResponseNullException(override val message: String? = "Response null"): Exception()

class RequestNotSuccessException(override val message: String? = "Request not ok"): Exception()