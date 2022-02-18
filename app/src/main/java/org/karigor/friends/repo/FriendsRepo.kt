package org.karigor.friends.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import org.karigor.friends.dto.User
import org.karigor.friends.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object FriendsRepo {

    private const val TAG = "FriendsRepo"

    fun getUsers(): MutableLiveData<User> {
        val liveData = MutableLiveData<User>()
        val call = RetrofitClient.apiInterface.getUsers()

        call.enqueue(object: Callback<User> {
            override fun onResponse(
                call: Call<User>,
                response: Response<User>
            ) {
                val body = response.body()
                liveData.postValue(response.body())
            }
            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e(TAG, "getUsers onFailure: ", t)
            }
        })

        return liveData
    }

}