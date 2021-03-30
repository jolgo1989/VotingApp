package com.example.voting.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addVoter(voters: Voters)

    @Query("SELECT * FROM user_table WHERE userName = :username AND password =:password")
    fun getLoginDetails(username: String, password: String): LiveData<User>

    @Query("SELECT * FROM voters_table ORDER BY votersId ASC")
    fun readAllData(): LiveData<List<Voters>>


}
