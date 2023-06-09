package uz.univer.kattabozortask.di

import android.util.Log
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import uz.univer.kattabozortask.BuildConfig
import uz.univer.kattabozortask.data.remote.OffersApi
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @[Provides Singleton]
    fun provideRetrofit(
        gsonConverterFactory: GsonConverterFactory,
        client: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .addConverterFactory(gsonConverterFactory)
        .client(client)
        .baseUrl(BuildConfig.BASE_URL)
        .build()

    @[Provides Singleton]
    fun provideCountryApi(retrofit: Retrofit): OffersApi = retrofit.create()
    @[Provides Singleton]
    fun provideClient(
        loggingInterceptor: HttpLoggingInterceptor,
    ) = OkHttpClient.Builder()
        .readTimeout(1000L, TimeUnit.SECONDS)
        .writeTimeout(1000L, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor)
        .build()
    @[Provides Singleton]
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor {
            Log.d("TTT", it)
        }
        interceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
        else HttpLoggingInterceptor.Level.NONE
        return interceptor
    }

    @[Provides Singleton]
    fun provideGsonConverter(gson: Gson): GsonConverterFactory = GsonConverterFactory.create(gson)

    @[Provides Singleton]
    fun provideGson(): Gson = Gson()

}