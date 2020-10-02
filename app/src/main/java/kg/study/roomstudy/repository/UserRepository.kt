package kg.study.roomstudy.repository

import android.util.Log
import androidx.lifecycle.LiveData
import kg.study.roomstudy.data.UserDao
import kg.study.roomstudy.model.User

class UserRepository(private val userDao: UserDao) {

    private val TAG = "UserRepository"

    val readAllData: LiveData<List<User>> = userDao.readAllData()

    suspend fun addUser(user: User) {
        Log.d(TAG, "addUser")
        userDao.addUser(user)
    }

    suspend fun updateUser(user: User) {
        Log.d(TAG, "updateUser")
        userDao.updateUser(user)
    }

    suspend fun deleteUser(user: User) {
        Log.d(TAG, "deleteUser")
        userDao.deleteUser(user)
    }

    suspend fun deleteAllUsers() {
        Log.d(TAG, "deleteAllUsers")
        userDao.deleteAllUsers()
    }
}