package database

class SignUpRepository(private val dao: SignUpDatabaseDao) {

    val users = dao.getAllUsers()

    suspend fun insert(user: SignUpEntity) {
        return dao.insert(user)
    }

    suspend fun getUserName(userName: String): SignUpEntity?{
        return dao.getUser(userName)
    }

    suspend fun getLoggedUser(): SignUpEntity?{
        return dao.getLoggedUser()
    }

    suspend fun logOutUser(): Int{
        return dao.logOut()
    }

    suspend fun logIn(email: String): Int{
        return dao.logIn(email)
    }
}
