package kg.study.roomstudy.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kg.study.roomstudy.model.User
import kg.study.roomstudy.data.UserDatabase
import kg.study.roomstudy.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG = "UserViewModel"

    val readAllData: LiveData<List<User>>
    private val repository: UserRepository

    init {
        Log.v(TAG, "init")
        val userDao = UserDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
        readAllData = repository.readAllData
    }

    fun addUser(user: User) { // in background thread
        Log.v(TAG, "addUser")
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }
    }

    fun updateUser(user : User) { // in background thread
        Log.v(TAG, "updateUser")
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateUser(user)
        }
    }

    fun deleteUser(user: User) {
        Log.v(TAG, "deleteUser")
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUser(user)
        }
    }

    fun deleteAllUsers() {
        Log.v(TAG, "deleteAllUsers")
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllUsers()
        }
    }
}
