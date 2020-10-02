package com.phanduy.tekotest.di.module

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.savedstate.SavedStateRegistryOwner
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.phanduy.tekotest.api.core.TekoApi
import com.phanduy.tekotest.data.repository.ProductApiRepository
import com.phanduy.tekotest.data.service.interceptor.RequestInterceptor
import com.phanduy.tekotest.factory.ProductListViewModelFactory
import com.phanduy.tekotest.utilities.SERVICE_BASE_URL
import com.squareup.picasso.BuildConfig
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    internal fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.create()
    }



    /*
     * The method returns the Cache object
     * */
    @Provides
    @Singleton
    internal fun provideCache(application: Application): Cache {
        val cacheSize = (10 * 1024 * 1024).toLong() // 10 MB
        val httpCacheDirectory = File(application.cacheDir, "http-cache")
        return Cache(httpCacheDirectory, cacheSize)
    }


    /*
     * The method returns the Okhttp object
     * */
    @Provides
    @Singleton
    internal fun provideOkhttpClient(cache: Cache): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

//        val httpClient = OkHttpClient.Builder()
//        httpClient.cache(cache)
//        httpClient.addInterceptor(logging)
//        httpClient.addNetworkInterceptor(RequestInterceptor())
//        httpClient.connectTimeout(30, TimeUnit.SECONDS)
//        httpClient.readTimeout(30, TimeUnit.SECONDS)


        return if(BuildConfig.DEBUG){
            OkHttpClient().newBuilder()
                    .cache(cache)
                    .addInterceptor(RequestInterceptor())
                    .addInterceptor(logging)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build()
        }else{
            OkHttpClient().newBuilder()
                    .cache(cache)
                    .addInterceptor(logging)
                    .addInterceptor(RequestInterceptor())
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build()
        }
//        return client.build()
    }


    /*
     * The method returns the Retrofit object
     * */
    @Provides
    @Singleton
    internal fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .baseUrl(SERVICE_BASE_URL)
                .client(okHttpClient)
                .build()
    }

    @Provides
    @Singleton
    internal fun provideTekoApiService(retrofit: Retrofit): TekoApi {
        return retrofit.create(TekoApi::class.java)
    }

    @Provides
    internal fun provideApiProductRepo(tekoApi: TekoApi): ProductApiRepository {
        return ProductApiRepository(tekoApi)
    }

}