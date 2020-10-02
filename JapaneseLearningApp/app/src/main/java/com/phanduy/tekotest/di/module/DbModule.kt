package com.phanduy.tekotest.di.module

import android.app.Application
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import com.phanduy.tekotest.api.core.TekoApi
import com.phanduy.tekotest.data.db.AppDatabase
import com.phanduy.tekotest.data.db.ProductCartDAO
import com.phanduy.tekotest.data.repository.ProductApiRepository
import com.phanduy.tekotest.data.repository.ProductCartRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DbModule {
    /*
     * The method returns the Database object
     * */
    @Provides
    @Singleton
    internal fun provideDatabase(application: Application): AppDatabase {
        return databaseBuilder(
                application, AppDatabase::class.java, "Entertainment.db")
                .allowMainThreadQueries().build()
    }


    /*
     * We need the MovieDao module.
     * For this, We need the AppDatabase object
     * So we will define the providers for this here in this module.
     * */
    @Provides
    @Singleton
    internal fun provideProductCartDAO(appDatabase: AppDatabase): ProductCartDAO {
        return appDatabase.productCartDao()
    }

    @Provides
    @Singleton
    internal fun provideProductCartRepo(productCartDAO: ProductCartDAO): ProductCartRepository {
        return ProductCartRepository(productCartDAO)
    }
}