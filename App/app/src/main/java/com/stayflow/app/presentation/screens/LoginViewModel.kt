package com.stayflow.app.presentation.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stayflow.app.domain.libs.GraphqlError
import com.stayflow.app.domain.repository.SessionRepository
import com.stayflow.app.domain.repository.TokenStorageRepository
import com.stayflow.app.ui.navigation.NavController
import com.stayflow.app.ui.routes.HomeRoute
import com.stayflow.app.util.FetchState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val sessionRepository: SessionRepository,
    private val tokenStorage: TokenStorageRepository,
    private val navController: NavController,
    @ApplicationContext private val context: Context,
) : ViewModel() {
    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var state by mutableStateOf(FetchState.AWAITING)
        private set

    fun onEmailChange(newEmail: String) {
        email = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
    }

    fun login() {
        state = FetchState.LOADING
        viewModelScope.launch {
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                state = FetchState.ERROR
                delay(2000)
                state = FetchState.AWAITING
                return@launch
            }

            try {
                val token = sessionRepository.login(email, password)
                tokenStorage.saveToken(token.token, token.expiresIn * 1000 + token.issuedAt)
                state = FetchState.AWAITING
                navController.navigate(HomeRoute::class.java)
            } catch (e: GraphqlError) {
                Toast.makeText(context, e.errors.first().message, Toast.LENGTH_SHORT).show()
                delay(2000)
            } catch (e: Exception) {
                Log.e(LoginViewModel::class.java.canonicalName, "login: ", e)
                delay(2000)
            }
            state = FetchState.AWAITING
        }
    }
}