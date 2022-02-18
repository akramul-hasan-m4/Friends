package org.karigor.friends.network

import org.karigor.friends.dto.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("api")
    fun getUsers(@Query("results") results : Int = 12) : Call<User>
}