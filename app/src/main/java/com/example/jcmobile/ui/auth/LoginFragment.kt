package com.example.jcmobile.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.jcmobile.R
import com.example.jcmobile.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        initClick()
    }


    private fun initClick(){

        binding.btnLogin.setOnClickListener{validateData()

        }
        binding.btnRecover.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_recoverFragment)
        }

    }
    private fun validateData(){
        val email= binding.editEmail.text.toString().trim()
        val password= binding.editPassword.text.toString().trim()
        if (email.isNotEmpty()){
            if (password.isNotEmpty()){
                loginUser(email, password)
            }else{
                Toast.makeText(requireContext(), "Informe sua senha.", Toast.LENGTH_SHORT).show()}

        }else{
            Toast.makeText(requireContext(), "Informe seu email.", Toast.LENGTH_SHORT).show()
        }
    }
    private fun loginUser(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                } else {
                    Toast.makeText(requireContext(),"Credenciais incorretas, tente novamente.", Toast.LENGTH_SHORT).show()

                }
            }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}