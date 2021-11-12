package signup

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import database.SignUpRepository

class SignUpViewModelFactory(
    private  val repository: SignUpRepository,
    private val application: Application
): ViewModelProvider.Factory{
    @Suppress("Unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
            return SignUpViewModel(repository, application) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}
