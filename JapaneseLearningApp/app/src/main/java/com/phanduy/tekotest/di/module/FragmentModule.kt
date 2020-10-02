package com.phanduy.tekotest.di.module

import androidx.lifecycle.SavedStateHandle
import com.phanduy.tekotest.api.core.TekoApi
import com.phanduy.tekotest.ui.main.LandingPageFragment
import com.phanduy.tekotest.ui.main.ListProductPreviewFragment
import com.phanduy.tekotest.ui.main.ProductDetailFragment
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeListProductPreviewFragment(): ListProductPreviewFragment

    @ContributesAndroidInjector
    abstract fun contributeProductDetailFragment(): ProductDetailFragment

    @ContributesAndroidInjector
    abstract fun contributeLandingPageFragment(): LandingPageFragment
}