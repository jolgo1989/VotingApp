package com.example.voting.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.voting.data.entities.User
import com.example.voting.data.entities.Voters

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addVoter(voters: Voters)

    @Update
    suspend fun updateVoter(voters: Voters)

    @Delete
    suspend fun deleteCandidate (voters: Voters)

    @Query ("DELETE FROM  voters_table")
    suspend fun deleteAllUsers()

    @Query("SELECT * FROM user_table WHERE userName = :username AND password =:password")
    fun getLoginDetails(username: String, password: String): LiveData<User>

    @Query("SELECT * FROM voters_table ORDER BY votersId ASC")
    fun readAllData(): LiveData<List<Voters>>


}
