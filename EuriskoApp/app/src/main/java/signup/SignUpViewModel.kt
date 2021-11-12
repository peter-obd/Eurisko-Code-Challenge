package signup

import android.app.Application

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import database.SignUpEntity
import database.SignUpRepository
import database.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class SignUpViewModel(private val repository: SignUpRepository, application: Application) :
    AndroidViewModel(application), Observable {

    val users = repository.users

    @Bindable
    val inputEmail = MutableLiveData<String?>()

    @Bindable
    val inputPassword = MutableLiveData<String?>()

    @Bindable
    val inputVerifyPassword = MutableLiveData<String?>()

    @Bindable
    val inputFirstName = MutableLiveData<String?>()

    @Bindable
    val inputLastName = MutableLiveData<String?>()

    lateinit var user: User

   private val _errorToast = MutableLiveData<Boolean>()
    val errotoast: LiveData<Boolean>
        get() = _errorToast

    private val _errorToastUsername = MutableLiveData<Boolean>()
    val errortoastUsername: LiveData<Boolean>
        get() = _errorToastUsername

    private val _navigateto = MutableLiveData<Boolean>()
    val navigateto : LiveData<Boolean>
        get() = _navigateto


    private val _passverifiction = MutableLiveData<Boolean>()
    val passverification: LiveData<Boolean>
        get() = _passverifiction


    private val _errorToastEmail = MutableLiveData<Boolean>()
    val errorToastEmail: LiveData<Boolean>
        get() = _errorToastEmail


    private val uiScope = CoroutineScope(Dispatchers.Main + Job())

    fun submitButton() {
        if (inputFirstName.value == null || inputLastName.value == null || inputEmail.value == null  || inputPassword.value == null) {
            _errorToast.value = true
        }
        else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(inputEmail.value!!).matches()){
            _errorToastEmail.value = true
        }
        else if(inputPassword.value != inputVerifyPassword.value){
            _passverifiction.value = true
        }
        else {
            uiScope.launch {
                val usersNames = repository.getUserName(inputEmail.value!!)
                if (usersNames != null) {
                    _errorToastUsername.value = true
                } else {
                    val firstName = inputFirstName.value!!
                    val lastName = inputLastName.value!!
                    val email = inputEmail.value!!
                    val password = inputPassword.value!!
                    user = User(firstName, lastName, email, password)
                    insert(SignUpEntity(0, firstName, lastName, email, password, true))
                    inputFirstName.value = null
                    inputLastName.value = null
                    inputEmail.value = null
                    inputPassword.value = null
                    inputVerifyPassword.value = null
                    _navigateto.value = true
                }
            }
        }
    }

    private fun insert(user: SignUpEntity): Job = viewModelScope.launch {
        repository.insert(user)
    }

    fun doneNavigating() {
        _navigateto.value = false
    }

    fun donetoast() {
        _errorToast.value = false
    }

    fun donetoastUserName() {
        _errorToast.value = false
    }

    fun donetoastPassVerif(){
        _passverifiction.value = false
    }
    fun donetoastEmail(){
        _errorToastEmail.value = false
    }


    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }


}