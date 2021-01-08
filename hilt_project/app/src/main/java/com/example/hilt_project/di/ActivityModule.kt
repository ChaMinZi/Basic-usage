package com.example.hilt_project.di

import com.example.hilt_project.di.qualifier.ActivityHash
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
object ActivityModule {

    @ActivityHash
    @Provides
    fun provideHash(): String = hashCode().toString()
}