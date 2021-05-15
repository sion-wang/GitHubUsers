package com.sion.githubusers.di

import com.google.gson.Gson
import com.sion.githubusers.BuildConfig
import com.sion.githubusers.model.api.user.UserApiRepository
import com.sion.githubusers.model.api.user.UserApiService
import com.sion.githubusers.model.api.GithubInterceptor
import com.sion.githubusers.model.api.user.IUserApiRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val apiModule = module {
    single { provideGithubInterceptor() }
    single { provideHttpLoggingInterceptor() }
    single { provideOkHttpClient(get(), get()) }
    single { provideUserApiService(get()) }
    single { provideUserApiRepository(get()) }
}

fun provideGithubInterceptor(): GithubInterceptor {
    return GithubInterceptor()
}

fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor().also {
        it.level = HttpLoggingInterceptor.Level.BODY
    }
}

fun provideOkHttpClient(
    githubInterceptor: GithubInterceptor,
    httpLoggingInterceptor: HttpLoggingInterceptor
): OkHttpClient {
    val builder = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .addInterceptor(githubInterceptor)
        .addInterceptor(httpLoggingInterceptor)
    return builder.build()
}

fun provideUserApiService(okHttpClient: OkHttpClient): UserApiService {
    return Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .client(okHttpClient)
        .baseUrl(BuildConfig.GITHUB_API_HOST)
        .build()
        .create(UserApiService::class.java)
}

fun provideUserApiRepository(userApiService: UserApiService): IUserApiRepository {
    return UserApiRepository(userApiService)
}