package com.phanduy.tekotest.di.module

import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import com.phanduy.tekotest.factory.ViewModelFactory
import com.phanduy.tekotest.ui.main.LearningFragment
import com.phanduy.tekotest.ui.main.ListProductPreviewFragment
import dagger.Binds
import dagger.Module

@Module
internal abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    internal abstract fun bindSavedStateRegistryOwner(fragment : ListProductPreviewFragment): SavedStateRegistryOwner


    /*
     * This method basically says
     * inject this object into a Map using the @IntoMap annotation,
     * with the  MovieListViewModel.class as key,
     * and a Provider that will build a MovieListViewModel
     * object.
     *
     * */

//    @Binds
//    @IntoMap
//    @ViewModelKey(ListProductViewModel::class)
//    protected abstract fun listProductViewModel(listProductViewModel: ListProductViewModel): ViewModel

//    @Binds
//    @IntoMap
//    @ViewModelKey(ProductDetailViewModel::class)
//    protected abstract fun productDetailViewModel(productDetailViewModel: ProductDetailViewModel): ViewModel



}