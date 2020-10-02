package com.phanduy.tekotest.di.component

import android.app.Application
import com.phanduy.tekotest.AppController
import com.phanduy.tekotest.MainActivity
import com.phanduy.tekotest.api.core.TekoApi
import com.phanduy.tekotest.di.module.*
import com.phanduy.tekotest.di.module.ViewModelModule
import com.phanduy.tekotest.ui.main.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(
        modules = [
//            AppCommonModule::class,
            CommonUiModule::class,
            AssistedViewModelModule::class,
            DbModule::class,
            ActivityModule::class,
            FragmentModule::class,
//            ViewModelModule::class,
            ApiModule::class,
            AndroidSupportInjectionModule::class]
)
@Singleton
interface AppComponent {

//    @Component.Factory
//    interface Factory {
//        fun create(
//                @BindsInstance
//                application: Application
//        ): AppComponent
//    }
    /*
     * We will call this builder interface from our custom Application class.
     * This will set our application object to the AppComponent.
     * So inside the AppComponent the application instance is available.
     * So this application instance can be accessed by our modules
     * such as ApiModule when needed
     *
     * */
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent

    }

    /*
     * This is our custom Application class
     * */
    fun inject(appController: AppController)
    fun inject(mainActivity: MainActivity)
    fun inject(listProductPreviewFragment: ListProductPreviewFragment)
    fun inject(productDetailFragment: ProductDetailFragment)
    fun inject(learningFragment: LearningFragment)
    fun inject(landingPageFragment: LandingPageFragment)
    fun inject(gamingFragment: GamingFragment)

    fun getTekoApi() : TekoApi
}