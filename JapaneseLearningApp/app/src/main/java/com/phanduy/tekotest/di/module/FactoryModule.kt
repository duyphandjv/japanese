package com.phanduy.tekotest.di.module

import androidx.savedstate.SavedStateRegistryOwner
import com.phanduy.tekotest.data.repository.ProductApiRepository
import com.phanduy.tekotest.factory.ProductListViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

//@Module
//class FactoryModule {
//
//    @Provides
//    @Singleton
//    internal fun provideProductListFactory(productApiRepository: ProductApiRepository, listProductPreviewFragment: FragmentModule_ContributeListProductPreviewFragment): ProductListViewModelFactory {
//        return ProductListViewModelFactory(productApiRepository, listProductPreviewFragment as SavedStateRegistryOwner)
//    }
//}