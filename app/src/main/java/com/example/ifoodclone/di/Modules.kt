@file:JvmName("ModulesKt")

package com.example.ifoodclone.di



import com.example.ifoodclone.base.BaseHomeRepository
import com.example.ifoodclone.data.HomeRepository
import com.example.ifoodclone.helper.ManagementCart
import com.example.ifoodclone.helper.TinyDB
import com.example.ifoodclone.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module



val modules= module {

    single <BaseHomeRepository>{ HomeRepository() }
    single { ManagementCart(get()) }
    single { TinyDB(get()) }
    viewModel{HomeViewModel(repository = get())}
}