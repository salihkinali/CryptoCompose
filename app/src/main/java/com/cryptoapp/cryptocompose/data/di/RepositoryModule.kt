package com.cryptoapp.cryptocompose.data.di

import com.cryptoapp.cryptocompose.data.repository.CoinRepository
import com.cryptoapp.cryptocompose.data.repository.CoinRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    @ViewModelScoped
    abstract fun bindRepository(storeRepositoryImpl: CoinRepositoryImpl): CoinRepository

}