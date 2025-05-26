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
import com.stayflow.app.domain.model.UserRegister
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
class RegisterViewModel @Inject constructor(
    private val repository: SessionRepository,
    private val tokenStorage: TokenStorageRepository,
    private val navController: NavController,
    @ApplicationContext private val context: Context,
) : ViewModel() {
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var firstName by mutableStateOf("")
    var lastName by mutableStateOf("")
    var acceptedTerms by mutableStateOf(false)

    var state by mutableStateOf(FetchState.AWAITING)
        private set

    fun register() {
        state = FetchState.LOADING
        viewModelScope.launch {
            if (password.length < 8) {
                state = FetchState.ERROR
                Toast.makeText(
                    context,
                    "Password must be at least 8 characters long",
                    Toast.LENGTH_SHORT
                ).show()
                delay(2000)
                state = FetchState.AWAITING
                return@launch
            }
            if (!acceptedTerms) {
                state = FetchState.ERROR
                Toast.makeText(
                    context,
                    "You must accept the terms and conditions",
                    Toast.LENGTH_SHORT
                ).show()
                delay(2000)
                state = FetchState.AWAITING
                return@launch
            }
            if (email.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
                state = FetchState.ERROR
                Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                delay(2000)
                state = FetchState.AWAITING
                return@launch
            }

            val user = UserRegister(email, password, firstName, lastName)

            try {
                val token = repository.register(user)
                tokenStorage.saveToken(token.token, token.expiresIn * 1000 + token.issuedAt)
                navController.navigate(HomeRoute::class.java)
            } catch (e: GraphqlError) {
                state = FetchState.ERROR
                Toast.makeText(context, e.errors.first().message, Toast.LENGTH_SHORT).show()
                delay(2000)
            } catch (e: Exception) {
                Log.e(RegisterViewModel::class.java.canonicalName, "register: ", e)
                delay(2000)
            }
            state = FetchState.AWAITING
        }

    }
}