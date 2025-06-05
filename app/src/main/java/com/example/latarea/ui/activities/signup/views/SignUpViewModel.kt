package com.example.latarea.ui.activities.signup.views

import android.content.Context
import android.util.Patterns
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.latarea.data.helper.TokenManager
import com.example.latarea.data.model.RegisterRequest
import com.example.latarea.data.network.RetrofitClient
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.latarea.data.network.AuthApi
import kotlinx.coroutines.tasks.await

class SignUpViewModel(private val context: Context) : ViewModel() {

    private val api: AuthApi = RetrofitClient.authApi
    val isLoginSuccessful = mutableStateOf(false)
    val state = mutableStateOf("")


    fun firebaseRegister(
        auth: FirebaseAuth,
        email: String,
        password: String,
        name: String,
        onResult: (Boolean) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val firebaseUser = auth.currentUser
                    firebaseUser?.getIdToken(true)
                        ?.addOnCompleteListener { tokenTask ->
                            if (tokenTask.isSuccessful) {
                                val token = tokenTask.result?.token ?: ""
                                // Llamar al backend con el token y name
                                viewModelScope.launch {
                                    val success = sendTokenAndNameToBackend(token, name)
                                    if (success) {
                                        TokenManager.saveToken(context, token)
                                        isLoginSuccessful.value = true
                                        state.value = "Registro exitoso"
                                        onResult(true)
                                    } else {
                                        state.value = "Error al registrar en backend"
                                        onResult(false)
                                    }
                                }
                            } else {
                                state.value = "Error al obtener token"
                                onResult(false)
                            }
                        }
                } else {
                    isLoginSuccessful.value = false
                    state.value = "Error al registrar en Firebase: ${task.exception?.message}"
                    onResult(false)
                }
            }
    }


    private suspend fun sendTokenAndNameToBackend(token: String, name: String): Boolean {
        return try {
            val response = RetrofitClient.authApi.register(RegisterRequest(token, name))
            response.isSuccessful
        } catch (e: Exception) {
            false
        }
    }

    private fun registerUser(token: String, name: String) {
        viewModelScope.launch {
            try {
                val request = RegisterRequest(token = token, name = name)
                val response = api.register(request)
                if (response.isSuccessful) {
                    // Usuario registrado correctamente en tu backend
                } else {
                    // Error del backend
                }
            } catch (e: Exception) {
                // Error de red o excepción
            }
        }
    }


    // Validar datos
    private val _name = MutableLiveData<String>()
    val name : LiveData<String> = _name

    private val _email = MutableLiveData<String>()
    val email : LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password : LiveData<String> = _password

    private val _registerEnable = MutableLiveData<Boolean>()
    val registerEnable : LiveData<Boolean> = _registerEnable

    fun  onRegisterChange(name: String, email : String, password : String){
        _name.value = name
        _email.value = email
        _password.value = password
        _registerEnable.value = isValidEmail(email) && isValidPassword(password)
    }

    private fun isValidEmail(email: String) : Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun isValidPassword(password: String) : Boolean = password.length > 8

    fun onRegisterSelected(){

    }
}