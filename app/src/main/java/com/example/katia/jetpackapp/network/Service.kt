package com.example.katia.jetpackapp.network

import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

class Service {
    interface PostMethods {
        @POST("Controller/controllerAuth.php")
        suspend fun login(@Body body: RequestBody): NetworkResponse
    }

    interface GetMethods {
        @GET("Controller/controllerBook.php")
        suspend fun getBookList(@Query("opc") opc : Int): NetworkBooksContainer
    }


    object RetrofitSingleton {

        private lateinit var INSTANCE: Retrofit
        private lateinit var okHttpClient: OkHttpClient
        const val BASE_URL = "http://192.168.0.12/AppPrueba/"
        const val TIMEOUT_REQUEST: Long = 15
        const val APP_JSON = "application/json"
        val postMethods by lazy { INSTANCE.create(PostMethods::class.java) }
        val getMethods by lazy { INSTANCE.create(GetMethods::class.java) }

        init {
            getInstance()
        }

        fun initClient(): OkHttpClient {
            val builder = OkHttpClient.Builder()
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            builder.connectTimeout(TIMEOUT_REQUEST, TimeUnit.SECONDS)
            builder.readTimeout(TIMEOUT_REQUEST, TimeUnit.SECONDS)
            builder.writeTimeout(TIMEOUT_REQUEST, TimeUnit.SECONDS)
            builder.addInterceptor(interceptor)
            return builder.build()
        }

        fun getInstance(): Retrofit {
            synchronized(Service::class.java) {
                if (!::INSTANCE.isInitialized) {
                    okHttpClient = initClient()
                    INSTANCE =
                        Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .client(okHttpClient)
                            .addConverterFactory(MoshiConverterFactory.create())
                            .build()
                }
            }
            return INSTANCE
        }

        fun createBody(params: Map<String, Any>): RequestBody {
            val paramsObject = JSONObject(params).toString()
            return RequestBody.create(MediaType.parse(APP_JSON), paramsObject)
        }
    }

}

