package com.soneralci.contactsmanagerapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.soneralci.contactsmanagerapp.room.UserRepo
import java.lang.IllegalArgumentException

class UserViewModelFactory(private val reposiyory : UserRepo): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)){
            return UserViewModel(reposiyory) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}