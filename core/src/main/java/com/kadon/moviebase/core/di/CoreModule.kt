package com.kadon.moviebase.core.di

import androidx.paging.ExperimentalPagingApi
import androidx.room.Room
import com.kadon.moviebase.core.data.FavoriteRepository
import com.kadon.moviebase.core.data.MoviePagingRepository
import com.kadon.moviebase.core.data.MovieRemoteMediator
import com.kadon.moviebase.core.data.MovieRepository
import com.kadon.moviebase.core.data.source.local.LocalDataSource
import com.kadon.moviebase.core.data.source.local.room.MoviesDatabase
import com.kadon.moviebase.core.data.source.remote.RemoteDataSource
import com.kadon.moviebase.core.data.source.remote.api.ApiService
import com.kadon.moviebase.core.domain.repository.IFavoriteRespository
import com.kadon.moviebase.core.domain.repository.IMoviePagingRepository
import com.kadon.moviebase.core.domain.repository.IMovieRepository
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<MoviesDatabase>().movieDao() }
    factory { get<MoviesDatabase>().remoteKeysDao() }
    factory { get<MoviesDatabase>().favoriteDao() }
    single {
        //val passphrase: ByteArray = SQLiteDatabase.getBytes("moviebase_passphrase".toCharArray())
        //val supportFactory = SupportFactory(passphrase)

        Room.databaseBuilder(
            androidContext(),
            MoviesDatabase::class.java, "Moviebase.db"
        )
            //.openHelperFactory(supportFactory)
            .fallbackToDestructiveMigration()
            .build()
    }
}

val networkModule = module {
    single {
        val hostname = "api.themoviedb.org"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/+vqZVAzTqUP8BGkfl88yU7SQ3C8J2uNEa55B7RZjEg0=")
            .add(hostname, "sha256/JSMzqOOrtyOT1kmau6zKhgT676hGgczD5VMdRMyJZFA=")
            .add(hostname, "sha256/++MBgDH5WGvL9Bcn5Be30cRcL0f5O+NyoXuWtQdX1aI=")
            .add(hostname, "sha256/KwccWaCgrnaw6tsrrSO61FgLacNgG2MMLq8GE6+oP5I=")
            .build()

        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

@ExperimentalPagingApi
val repositoryModule = module {
    single { LocalDataSource(get(), get(), get()) }
    single { RemoteDataSource(get()) }
    single { MovieRemoteMediator(get(),get()) }

    single<IMovieRepository> { MovieRepository(get(), get()) }
    single<IMoviePagingRepository> { MoviePagingRepository(get(), get()) }
    single<IFavoriteRespository> { FavoriteRepository(get()) }
}