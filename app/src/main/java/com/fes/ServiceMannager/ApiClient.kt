package com.fes.hoori.controller

import com.fes.ServiceMannager.URLConstants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiClient {




    companion object Factory {
        private var retrofit: Retrofit? = null

        fun getRetrofit(): Retrofit? {
            if (retrofit == null) {

                retrofit = Retrofit.Builder()
                    .baseUrl(URLConstants.BASE_URL_API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            }
            return retrofit
        }

    }


}