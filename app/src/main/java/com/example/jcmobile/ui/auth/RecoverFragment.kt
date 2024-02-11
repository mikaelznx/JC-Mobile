package com.example.jcmobile.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.jcmobile.R
import com.example.jcmobile.databinding.FragmentRecoverBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RecoverFragment : Fragment() {
    private var _binding: FragmentRecoverBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecoverBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        initClick()

    }
    private fun initClick(){
        binding.btnSend.setOnClickListener{validateData()}

    }
    private fun validateData(){
        val email= binding.editEmail.text.toString().trim()
        if (email.isNotEmpty()){
            recoverAccountUser(email)
        }else{
            Toast.makeText(requireContext(), "Informe seu email.", Toast.LENGTH_SHORT).show()
        }
    }
    private fun recoverAccountUser(email: String){
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireContext(),"Um link para redefiniçao de senha foi enviado para o e-mail informado", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_recoverFragment_to_loginFragment)
                }
            }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}