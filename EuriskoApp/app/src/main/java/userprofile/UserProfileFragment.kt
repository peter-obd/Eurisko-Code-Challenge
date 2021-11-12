package userprofile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import database.SignUpDatabase
import database.SignUpRepository
import database.User
import paulobd.example.euriskoapp.R
import paulobd.example.euriskoapp.databinding.FragmentUserProfileBinding
import signup.SignUpViewModel
import signup.SignUpViewModelFactory

class UserProfileFragment : Fragment() {

    private lateinit var userProfileViewModel: UserProfileViewModel
    private lateinit var binding: FragmentUserProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_user_profile, container, false
        )

        val application = requireNotNull(this.activity).application

        val dao = SignUpDatabase.getInstance(application).registerDatabaseDao

        val repository = SignUpRepository(dao)

        val factory =UserViewModelFactory(repository, application)


        userProfileViewModel = ViewModelProvider(this, factory).get(UserProfileViewModel::class.java)

        binding.myUserProfileViewModel = userProfileViewModel

        binding.lifecycleOwner = this

    userProfileViewModel.logOut.observe(viewLifecycleOwner, Observer { haslogout ->
        if (haslogout == true){
            logout()
            userProfileViewModel.logoutDone()
        }
    })




    return binding.apply {
        val arguments = arguments
        if(arguments != null){
            val args = UserProfileFragmentArgs.fromBundle(arguments)
            val user: User = args.userInfo
            binding.nameTv.text = "Hello ${user}"
        }

    }.root

    }

    fun logout(){
       // userProfileViewModel.logOutUser()
        val action =  UserProfileFragmentDirections.actionUserProfileFragmentToLogInFragment()
        NavHostFragment.findNavController(this).navigate(action)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        }
    }

