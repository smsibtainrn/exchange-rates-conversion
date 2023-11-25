package com.smsrn.exchangerate.domain.models


/**
 * Created by Sibtain Raza on 5/4/2023.
 * smsibtainrn@gmail.com
 */
open class BaseResponse {
    var statusCode: String? = null
    var code: Int? = null
    var status: String? = null
    var message: String? = null
}