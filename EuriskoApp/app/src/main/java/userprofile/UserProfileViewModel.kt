package userprofile

import android.app.Application
import androidx.databinding.Observable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import database.SignUpRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class UserProfileViewModel(private val repository: SignUpRepository, application: Application) :
    AndroidViewModel(application), Observable {



    private val _logOut = MutableLiveData<Boolean>()
    val logOut: LiveData<Boolean>
        get() = _logOut

    private val uiScope = CoroutineScope(Dispatchers.Main + Job())

    fun logOut(){
        uiScope.launch {
            repository.logOutUser()
            _logOut.value = true
        }

    }

    fun logoutDone(){
        _logOut.value = false
    }





    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}