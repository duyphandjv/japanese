package com.phanduy.tekotest.data.service.factory

import com.phanduy.tekotest.api.core.TekoApi
import com.phanduy.tekotest.utilities.SERVICE_BASE_URL

object ApiFactory{
    val tekoApi = RetrofitFactory.retrofit(SERVICE_BASE_URL)
            .create(TekoApi::class.java)
}