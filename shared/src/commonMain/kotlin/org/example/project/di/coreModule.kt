package org.example.project.di

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import org.koin.dsl.module

val coreModule = module {
    single<StoreFactory> { DefaultStoreFactory() }
}