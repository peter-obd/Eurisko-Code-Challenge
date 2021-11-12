package login

import android.app.Application
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import database.SignUpRepository
import database.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LogInViewModel(private val repository: SignUpRepository, application: Application) :
    AndroidViewModel(application),  Observable {


    lateinit var userInfo : User

    @Bindable
    val inputEmail = MutableLiveData<String?>()

    @Bindable
    val inputPassword = MutableLiveData<String?>()


    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _navigatetoUserDetails = MutableLiveData<Boolean>()

    val navigatetoUserDetails: LiveData<Boolean>
        get() = _navigatetoUserDetails

    private val _errorToast = MutableLiveData<Boolean>()

    val errotoast: LiveData<Boolean>
        get() = _errorToast

    private val _errorToastUsername = MutableLiveData<Boolean>()

    val errotoastUsername: LiveData<Boolean>
        get() = _errorToastUsername

    private val _errorToastInvalidPassword = MutableLiveData<Boolean>()

    val errorToastInvalidPassword: LiveData<Boolean>
        get() = _errorToastInvalidPassword

    private val _errorToastEamil = MutableLiveData<Boolean>()

    val errortoastEmail: LiveData<Boolean>
        get() = _errorToastEamil

    private val _userIsLogged = MutableLiveData<Boolean>()
    val userIsLogged: LiveData<Boolean>
        get() = _userIsLogged


    fun isUserLogged(){

        uiScope.launch {
            val loggedUser = repository.getLoggedUser()

            if (loggedUser != null) {
                userInfo = User(
                    loggedUser.firstName,
                    loggedUser.lastName,
                    loggedUser.email,
                    loggedUser.passwrd
                )
                _userIsLogged.value = true
            } else {
                _userIsLogged.value = false
            }
        }

    }



    fun loginButton() {
        if (inputEmail.value == null || inputPassword.value == null) {
            _errorToast.value = true
        }
        else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(inputEmail.value!!).matches()){
            _errorToastEamil.value = true
        }
        else {
            uiScope.launch {
                val usersName = repository.getUserName(inputEmail.value!!)

                if (usersName != null) {
                    userInfo = User(usersName.firstName, usersName.lastName, usersName.email, usersName.passwrd)

                    if(usersName.passwrd == inputPassword.value){
                        repository.logIn(usersName.email)

                        inputEmail.value = null
                        inputPassword.value = null
                        _navigatetoUserDetails.value = true

                    }else{
                        _errorToastInvalidPassword.value = true
                    }
                } else {
                    _errorToastUsername.value = true
                }
            }
        }
    }


    fun doneNavigatingUserDetails() {
        _navigatetoUserDetails.value = false
    }


    fun donetoast() {
        _errorToast.value = false
    }


    fun donetoastErrorUsername() {
        _errorToastUsername .value = false
    }

    fun donetoastInvalidPassword() {
        _errorToastInvalidPassword.value = false
    }

    fun donetoastEmail(){
        _errorToastEamil.value = false
    }


    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }


}