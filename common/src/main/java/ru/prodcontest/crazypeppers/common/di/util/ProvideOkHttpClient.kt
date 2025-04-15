package ru.prodcontest.crazypeppers.common.di.util

import okhttp3.Interceptor
import okhttp3.OkHttpClient

fun provideOkHttpClient(interceptor: Interceptor?): OkHttpClient {
    return OkHttpClient.Builder()
        .apply {
            interceptor?.let { addInterceptor(it) }
        }
        .build()
}
