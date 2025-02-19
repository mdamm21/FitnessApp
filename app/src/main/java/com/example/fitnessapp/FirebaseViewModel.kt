package com.example.fitnessapp

import androidx.compose.runtime.mutableStateOf
import androidx.core.app.NotificationCompat.MessagingStyle.Message
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class FirebaseViewModel @Inject constructor(val auth: FirebaseAuth): ViewModel(){

    val signedIn = mutableStateOf(false)
    val inProgress = mutableStateOf(false )
    val popUpNotification = mutableStateOf<Event<String>?>(null)

    fun onSignUp(email: String, password: String){
        inProgress.value = true

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                if(it.isSuccessful){
                    signedIn.value= true
                    handleException(it.exception, "Sign Up erfolgreich")
                }
                else{
                    handleException(it.exception, "Sign Up fehlgeschlagen")
                }
                inProgress.value = false
            }
    }
    fun onLogIn(email: String, password: String){
        inProgress.value = true

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                if(it.isSuccessful){
                    signedIn.value = true
                    handleException(it.exception, "Log In erfolgreich")
                }
                else{
                    handleException(it.exception, "Log In fehlgeschlagen")
                }
                inProgress.value = false
            }
    }

    fun handleException(exception: Exception? = null, customMessage: String = ""){
        exception?.printStackTrace()
        val errorMess = exception?.localizedMessage ?: ""
        val message = if(customMessage.isEmpty()) errorMess else "$customMessage: $errorMess"
        popUpNotification.value = Event(message)
    }
}