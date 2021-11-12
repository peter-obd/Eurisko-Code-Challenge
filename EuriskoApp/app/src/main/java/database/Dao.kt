package database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SignUpDatabaseDao {

    @Insert
    suspend fun insert(register: SignUpEntity)

    @Query("SELECT * FROM Signup_users_table ORDER BY userId DESC")
    fun getAllUsers(): LiveData<List<SignUpEntity>>

    @Query("SELECT * FROM Signup_users_table WHERE email LIKE :email")
    suspend fun getUser(email: String): SignUpEntity?

    @Query("SELECT * FROM Signup_users_table WHERE logged = 1")
    suspend fun getLoggedUser(): SignUpEntity?

    @Query("UPDATE Signup_users_table SET logged = 0 WHERE logged = 1")
    suspend fun logOut(): Int

    @Query("UPDATE Signup_users_table SET logged = 1 WHERE email LIKE :email")
    suspend fun logIn(email: String): Int

}