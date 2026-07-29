package org.example.project.di

import org.example.project.data.local.SettingsDataSource
import org.example.project.data.local.createSettings
import org.example.project.data.repository.NoteRepositoryImpl
import org.example.project.domain.repository.NoteRepository
import org.example.project.domain.usecase.DeleteItemUseCase
import org.example.project.domain.usecase.GetAllItemUseCase
import org.example.project.domain.usecase.GetItemByIdUseCase
import org.example.project.domain.usecase.InsertItemUseCase
import org.example.project.domain.usecase.UpdateItemUseCase
import org.example.project.presentation.mvi.main.MainViewModel
import org.example.project.presentation.mvi.notedetail.NoteDetailViewModel
import org.example.project.presentation.mvi.noteedit.NoteEditViewModel
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single {
        createSettings()
    }
    single {
        SettingsDataSource(
            get()
        )
    }

    single<NoteRepository> { NoteRepositoryImpl(get()) }

    single {
        DeleteItemUseCase(get())
    }
    single {
        UpdateItemUseCase(get())
    }
    single {
        GetAllItemUseCase(get())
    }
    single {
        GetItemByIdUseCase(get())
    }
    single {
        InsertItemUseCase(get())
    }
    viewModel {
        MainViewModel(get())
    }
    viewModel {
        NoteDetailViewModel(get(), get())
    }
    viewModel {
        NoteEditViewModel(get(), get(), get())
    }
}