package com.soneralci.contactsmanagerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.soneralci.contactsmanagerapp.viewModel.UserViewModel
import com.soneralci.contactsmanagerapp.viewModel.UserViewModelFactory
import com.soneralci.contactsmanagerapp.databinding.ActivityMainBinding
import com.soneralci.contactsmanagerapp.room.User
import com.soneralci.contactsmanagerapp.room.UserDatabase
import com.soneralci.contactsmanagerapp.room.UserRepo
import com.soneralci.contactsmanagerapp.viewUI.MyRecyclerViewAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        //Room
        val dao = UserDatabase.getInstance(application).userDAO
        val repository = UserRepo(dao)
        val factory = UserViewModelFactory(repository)

        userViewModel = ViewModelProvider(this,factory).get(UserViewModel::class.java)

        binding.userViewModel = userViewModel

        binding.lifecycleOwner = this

        initRecyclerView()

    }

    private fun initRecyclerView(){
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        DisplayUserList()
    }

    private fun DisplayUserList(){
        userViewModel.users.observe(this, Observer {
            binding.recyclerView.adapter = MyRecyclerViewAdapter(
                it,{selectedItem: User -> listItemClicked(selectedItem)}
            )
        })
    }
    private fun listItemClicked(selectedItem : User){
        Toast.makeText(this,"Selected name is ${selectedItem.name}",Toast.LENGTH_LONG).show()

        userViewModel.initUpdateAndDelete(selectedItem)
    }


}