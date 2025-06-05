package com.example.latarea.ui.activities.login.views

import android.content.Context
import android.util.Patterns
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.latarea.data.helper.TokenManager
import com.example.latarea.data.model.LoginRequest
import com.example.latarea.data.network.AuthApi
import com.example.latarea.data.network.RetrofitClient
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginViewModel(private val context: Context) : ViewModel(){

    private val api: AuthApi = RetrofitClient.authApi

    val state = mutableStateOf("")

    fun firebaseLogin(auth: FirebaseAuth, email: String, password: String, onResult: (Boolean) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val firebaseUser = auth.currentUser
                    firebaseUser?.getIdToken(true)
                        ?.addOnCompleteListener { tokenTask ->
                            if (tokenTask.isSuccessful) {
                                val token = tokenTask.result?.token ?: ""
                                loginUser(token) // Llamar a backend
                                viewModelScope.launch {
                                    try {
                                        val result = FirebaseAuth.getInstance()
                                            .signInWithEmailAndPassword(email, password)
                                            .await()

                                        val token = result.user?.getIdToken(true)?.await()?.token

                                        if (token != null) {
                                            TokenManager.saveToken(context, token)
                                            onResult(true)
                                        } else {
                                            onResult(false)
                                        }
                                    } catch (e: Exception) {
                                        onResult(false)
                                    }
                                }
                                state.value = "Inicio de sesión exitoso"
                            } else {
                                state.value = "Error al obtener token"
                            }
                        }
                } else {
                    state.value = "Error al iniciar sesión: ${task.exception?.message}"
                }
            }
    }

    private fun loginUser(token: String) {
        viewModelScope.launch {
            try {
                val request = LoginRequest(token = token)
                val response = api.login(request)
                if (response.isSuccessful) {
                    // Puedes guardar más info si la API te la da
                } else {
                    state.value = "Error en backend (login)"
                }
            } catch (e: Exception) {
                state.value = "Excepción: ${e.message}"
            }
        }
    }

    // Validad datos
    private val _email = MutableLiveData<String>()
    val email : LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password : LiveData<String> = _password

    private val _loginEnable = MutableLiveData<Boolean>()
    val loginEnable : LiveData<Boolean> = _loginEnable

    fun  onLoginChange(email : String, password : String){
        _email.value = email
        _password.value = password
        _loginEnable.value = isValidEmail(email) && isValidPassword(password)
    }

    private fun isValidEmail(email: String) : Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun isValidPassword(password: String) : Boolean = password.length > 8

    fun onLoginSelected(){

    }

}