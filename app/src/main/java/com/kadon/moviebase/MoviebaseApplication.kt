package com.kadon.moviebase

import android.app.Application
import android.util.Log
import com.kadon.moviebase.core.di.databaseModule
import com.kadon.moviebase.core.di.networkModule
import com.kadon.moviebase.core.di.repositoryModule
import com.kadon.moviebase.di.useCaseModule
import com.kadon.moviebase.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MoviebaseApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("MoviebaseApplication","onCreate")
        startKoin{
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