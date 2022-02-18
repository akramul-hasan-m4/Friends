package org.karigor.friends.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.karigor.friends.dto.User
import org.karigor.friends.repo.FriendsRepo

class FriendsViewModel : ViewModel(){

    fun getFriends() : MutableLiveData<User>{
        return FriendsRepo.getUsers()
    }

}