package login

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import database.SignUpRepository

class LogInViewModelFactory (
    private  val repository: SignUpRepository,
    private val application: Application
): ViewModelProvider.Factory{
    @Suppress("Unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LogInViewModel::class.java)) {
            return LogInViewModel(repository, application) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }

}