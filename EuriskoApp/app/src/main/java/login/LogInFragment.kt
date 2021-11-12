package login

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import database.SignUpDatabase
import database.SignUpRepository
import helpers.ClickableTv
import paulobd.example.euriskoapp.R
import paulobd.example.euriskoapp.databinding.FragmentLogInBinding

class LogInFragment : Fragment(), ClickableTv.ClickableTvCallBack {
    private lateinit var loginViewModel: LogInViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentLogInBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_log_in, container, false
        )

        val application = requireNotNull(this.activity).application

        val dao = SignUpDatabase.getInstance(application).registerDatabaseDao

        val repository = SignUpRepository(dao)

        val factory = LogInViewModelFactory(repository, application)

        loginViewModel = ViewModelProvider(this, factory).get(LogInViewModel::class.java)

        binding.myLoginViewModel = loginViewModel

        binding.lifecycleOwner = this

        val signUpHyperLinkTv = SpannableString("Sign Up")
        val clickableTvObject = ClickableTv(this)
        signUpHyperLinkTv.setSpan(clickableTvObject, 0, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE )

        loginViewModel.isUserLogged()

        loginViewModel.userIsLogged.observe(viewLifecycleOwner, Observer { islogged ->
            if (islogged == true){
                navigatetoUserProfile()
            }
        })


        loginViewModel.errotoast.observe(viewLifecycleOwner, Observer { hasError->
            if(hasError==true){
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
                loginViewModel.donetoast()
            }
        })

        loginViewModel.errotoastUsername .observe(viewLifecycleOwner, Observer { hasError->
            if(hasError==true){
                Toast.makeText(requireContext(), "User doesnt exist,please Register!", Toast.LENGTH_SHORT).show()
                loginViewModel.donetoastErrorUsername()
            }
        })

        loginViewModel.errorToastInvalidPassword.observe(viewLifecycleOwner, Observer { hasError->
            if(hasError==true){
                Toast.makeText(requireContext(), "Please check your Password", Toast.LENGTH_SHORT).show()
                loginViewModel.donetoastInvalidPassword()
            }
        })


        loginViewModel.navigatetoUserDetails.observe(viewLifecycleOwner, Observer { hasFinished->
            if (hasFinished == true){
                navigatetoUserProfile()
                loginViewModel.doneNavigatingUserDetails()
            }
        })

        loginViewModel.errortoastEmail.observe(viewLifecycleOwner, Observer { hasError->
            if(hasError==true){
                Toast.makeText(requireContext(), "Please enter a valid Email", Toast.LENGTH_SHORT).show()
                loginViewModel.donetoastEmail()
            }
        })



        return binding.apply {
            binding.LiSignUpTV.text = signUpHyperLinkTv
            binding.LiSignUpTV.movementMethod = LinkMovementMethod.getInstance()
        }.root
    }

    fun navigatetoUserProfile(){
        val userinfo = loginViewModel.userInfo

        if (userinfo != null){
            val action = LogInFragmentDirections.actionLogInFragmentToUserProfileFragment(userinfo)
            NavHostFragment.findNavController(this).navigate(action)
        }

    }

    override fun onTvClick(widget: View) {
        val action = LogInFragmentDirections.actionLogInFragmentToSignUpFragment()
        NavHostFragment.findNavController(this).navigate(action)

    }

    override fun onUpdateDrawState(ds: TextPaint) {
        ds.setColor(Color.BLUE)
        ds.isUnderlineText = true
    }


}
