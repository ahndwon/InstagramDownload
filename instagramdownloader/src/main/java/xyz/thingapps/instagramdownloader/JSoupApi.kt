package xyz.thingapps.instagramdownloader

import android.util.Log
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.jsoup.nodes.Document
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.http.GET
import retrofit2.http.Url
import java.util.concurrent.TimeUnit

interface JSoupApi {
    @GET
    fun getDocument(@Url url: String): Single<Document>

    companion object {
        private const val DUMMY_URL = "https://www.google.com"
        fun create(): JSoupApi {
            val logger = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
                Log.d("API_LOGGER", it)
            })
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .callTimeout(5000, TimeUnit.MILLISECONDS)
                .build()
            return Retrofit.Builder()
                .baseUrl(DUMMY_URL)
                .client(client)
                .addConverterFactory(JSoupConverterFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .build()
                .create(JSoupApi::class.java)
        }
    }
}