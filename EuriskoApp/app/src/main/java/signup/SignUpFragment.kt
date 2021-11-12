package signup

import android.os.Bundle
import android.text.Layout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import database.SignUpDatabase
import database.SignUpRepository
import database.User
import paulobd.example.euriskoapp.R
import paulobd.example.euriskoapp.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {
    private lateinit var signUpViewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentSignUpBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_sign_up, container, false
        )

        val application = requireNotNull(this.activity).application

        val dao = SignUpDatabase.getInstance(application).registerDatabaseDao

        val repository = SignUpRepository(dao)

        val factory = SignUpViewModelFactory(repository, application)

        signUpViewModel = ViewModelProvider(this, factory).get(SignUpViewModel::class.java)

        binding.mySignUpViewModel = signUpViewModel

        binding.lifecycleOwner = this

        signUpViewModel.errotoast.observe( viewLifecycleOwner, Observer { hasError->
            if(hasError==true){
                Toast.makeText(requireContext(), "All fields should be filled", Toast.LENGTH_SHORT).show()
                signUpViewModel.donetoast()
            }

        })
        signUpViewModel.passverification.observe( viewLifecycleOwner, Observer { hasError->
            if(hasError==true){
                Toast.makeText(requireContext(), "Verify your password correctly", Toast.LENGTH_SHORT).show()
                signUpViewModel.donetoastPassVerif()
            }
        })
        signUpViewModel.errorToastEmail.observe( viewLifecycleOwner, Observer { hasError->
            if(hasError==true){
                Toast.makeText(requireContext(), "Invalid email", Toast.LENGTH_SHORT).show()
                signUpViewModel.donetoastEmail()
            }
        })

        signUpViewModel.navigateto.observe(viewLifecycleOwner, Observer { hasFinished->
            if (hasFinished == true){
                displayUsersList()
                signUpViewModel.doneNavigating()
            }
        })
        signUpViewModel.errortoastUsername.observe(viewLifecycleOwner, Observer { hasError ->
            if(hasError==true){
                Toast.makeText(requireContext(), "You are already a member", Toast.LENGTH_SHORT).show()
                signUpViewModel.donetoastUserName()
            }
        })




        return binding.root
    }

    private fun displayUsersList() {
        val user: User = signUpViewModel.user
        if (user != null){
        val action = SignUpFragmentDirections.actionSignUpFragmentToUserProfileFragment(user)
        NavHostFragment.findNavController(this).navigate(action)
        }

    }

}

