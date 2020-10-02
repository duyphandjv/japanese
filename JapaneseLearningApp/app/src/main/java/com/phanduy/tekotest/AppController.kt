package com.phanduy.tekotest

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import com.phanduy.tekotest.api.core.TekoApi
import com.phanduy.tekotest.di.component.AppComponent
import com.phanduy.tekotest.di.component.DaggerAppComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

/*
 * we use our AppComponent (now prefixed with Dagger)
 * to inject our Application class.
 * This way a DispatchingAndroidInjector is injected which is
 * then returned when an injector for an activity is requested.
 * */
class AppController : Application(), HasActivityInjector {

    @Inject
    lateinit var tekoApi: TekoApi

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): DispatchingAndroidInjector<Activity>? {
        return dispatchingAndroidInjector
    }

//    companion object {
//        fun getTekoApi() : TekoApi {
//            return tekoApi
//        }
//    }

//    fun getTekoApiInstance() : TekoApi? {
//        return tekoApi
//    }

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .application(this)
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
    }

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: AppController? = null

        fun getInstance() =
            instance
                ?: synchronized(this) {
                    instance
                        ?: AppController().also { instance = it }
                }
    }
}

val Activity.appComponent get() = (application as AppController).appComponent
val Fragment.appComponent get() = (requireActivity().application as AppController).appComponent