package ru.prodcontest.crazypeppers.common.di.util

import retrofit2.Retrofit

inline fun <reified T> Retrofit.bind(): T = create(T::class.java)
