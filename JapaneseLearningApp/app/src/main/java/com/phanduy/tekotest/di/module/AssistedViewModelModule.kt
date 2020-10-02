package com.phanduy.tekotest.di.module

import androidx.lifecycle.ViewModel
import com.phanduy.tekotest.di.AssistedSavedStateViewModelFactory
import com.phanduy.tekotest.di.ViewModelKey
import com.phanduy.tekotest.viewmodel.ListProductViewModel
import com.phanduy.tekotest.viewmodel.ProductDetailViewModel
import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@AssistedModule
@Module(includes = [AssistedInject_AssistedViewModelModule::class])
abstract class AssistedViewModelModule {
//    @Binds
//    @IntoMap
//    @ViewModelKey(ProductDetailViewModel::class)
//    abstract fun bindsProductDetailViewModel(vm: ProductDetailViewModel): ViewModel
//
    @Binds
    @IntoMap
    @ViewModelKey(ListProductViewModel::class)
    abstract fun bindsListProductViewModel(f: ListProductViewModel.Factory): AssistedSavedStateViewModelFactory<out ViewModel>

//    @Binds
//    @IntoMap
//    @ViewModelKey(ProductDetailViewModel::class)
//    abstract fun bindsProductDetailViewModel(f: ProductDetailViewModel.Factory): AssistedSavedStateViewModelFactory<out ViewModel>
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(SomeViewModel::class)
//    abstract fun bindsSomeViewModel(f: SomeViewModel.Factory): AssistedSavedStateViewModelFactory<out ViewModel>
}