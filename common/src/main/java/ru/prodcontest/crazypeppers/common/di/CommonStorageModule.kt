package ru.prodcontest.crazypeppers.common.di

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val commonStorageModule = module {
    single {
        androidContext().getSharedPreferences("prefs", Context.MODE_PRIVATE)
    }
}
