package com.example.maskinfo.di.module

import com.example.maskinfo.repository.MaskService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    // ApplicationComponent 이기 때문에 Singleton annotation을 안 붙여도 singleton으로 생성된다.
    @Provides
    @Singleton
    fun provideMaskService(): MaskService {
        val retrofit = Retrofit.Builder()
            .baseUrl(MaskService.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        return retrofit.create(MaskService::class.java)
    }
}