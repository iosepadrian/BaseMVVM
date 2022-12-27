package com.zynksoftware.base.di

import com.zynksoftware.base.utils.StringResource
import com.zynksoftware.base.utils.StringResourceProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class AppModuleAbstract {
    @Binds
    abstract fun bindStringResource(stringResourceProvider: StringResourceProvider): StringResource
}