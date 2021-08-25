package com.kadon.moviebase

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import com.kadon.moviebase.core.di.databaseModule
import com.kadon.moviebase.core.di.networkModule
import com.kadon.moviebase.core.di.repositoryModule
import com.kadon.moviebase.di.useCaseModule
import com.kadon.moviebase.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

@Suppress("unused")
class MoviebaseApplication : Application() {
    @ExperimentalPagingApi
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MoviebaseApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}