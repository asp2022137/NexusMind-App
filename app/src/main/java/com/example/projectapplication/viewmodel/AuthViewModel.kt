package com.example.projectapplication.viewmodel

import androidx.lifecycle.ViewModel
import com.example.projectapplication.data.AuthState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    // User දැනටමත් Login වී ඇත්දැයි පරීක්ෂා කිරීම (Auto-Login)
    init {
        if (auth.currentUser != null) {
            _authState.value = AuthState.Success
        }
    }

    // Login Logic
    fun loginUser(email: String, pass: String) {
        if (email.isBlank() || pass.isBlank()) {
            _authState.value = AuthState.Error("Email සහ Password හිස්ව තැබිය නොහැක!")
            return
        }

        _authState.value = AuthState.Loading

        auth.signInWithEmailAndPassword(email.trim(), pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Success
                } else {
                    _authState.value = AuthState.Error(task.exception?.localizedMessage ?: "Login වීම අසාර්ථකයි!")
                }
            }
    }

    // Sign Up / Register Logic
    fun registerUser(email: String, pass: String, confirmPass: String) {
        if (email.isBlank() || pass.isBlank() || confirmPass.isBlank()) {
            _authState.value = AuthState.Error("සියලුම විස්තර ඇතුළත් කරන්න!")
            return
        }

        if (pass != confirmPass) {
            _authState.value = AuthState.Error("Passwords දෙක එකිනෙකට ගැලපෙන්නේ නැත!")
            return
        }

        if (pass.length < 6) {
            _authState.value = AuthState.Error("Password එකට අවම වශයෙන් අකුරු/ඉලක්කම් 6ක් තිබිය යුතුය!")
            return
        }

        _authState.value = AuthState.Loading

        auth.createUserWithEmailAndPassword(email.trim(), pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Success
                } else {
                    _authState.value = AuthState.Error(task.exception?.localizedMessage ?: "Sign Up වීම අසාර්ථකයි!")
                }
            }
    }

    // Logout Logic
    fun logout() {
        auth.signOut()
        _authState.value = AuthState.Idle
    }

    // Password Reset Logic
    fun resetPassword(email: String) {
        if (email.isBlank()) {
            _authState.value = AuthState.Error("Email ලිපිනය ඇතුළත් කරන්න!")
            return
        }

        _authState.value = AuthState.Loading

        auth.sendPasswordResetEmail(email.trim())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Success
                } else {
                    _authState.value = AuthState.Error(task.exception?.localizedMessage ?: "Email එක යැවීමට නොහැකි වුණා.")
                }
            }
    }

    // AuthState reset කිරීමට (Error Messages අයින් කිරීමට)
    fun resetAuthState() {
        _authState.value = AuthState.Idle
    }
}
