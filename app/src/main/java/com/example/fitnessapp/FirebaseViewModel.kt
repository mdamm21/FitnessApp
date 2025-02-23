package com.example.fitnessapp

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.fitnessapp.data.UserProfile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
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

    fun loadTodayCalories(callback: (CalorieEntry?) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        val todayDocId = getTodayDocumentId()
        db.collection("calories")
            .document(todayDocId)
            .get()
            .addOnSuccessListener { doc ->
                if (doc.exists()){
                    val entry = doc.toObject(CalorieEntry::class.java)
                    callback(entry)
                } else {
                    callback(null)
                }
            }
            .addOnFailureListener {
                callback(null)
            }
    }

    fun saveCalories(breakfast: String, lunch: String, dinner: String, snack: String) {
        val db = FirebaseFirestore.getInstance()

        val breakfastInt = breakfast.toIntOrNull() ?: 0
        val lunchInt = lunch.toIntOrNull() ?: 0
        val dinnerInt = dinner.toIntOrNull() ?: 0
        val snackInt = snack.toIntOrNull() ?: 0

        val data = hashMapOf(
            "breakfast" to breakfastInt,
            "lunch" to lunchInt,
            "dinner" to dinnerInt,
            "snack" to snackInt,
            "timestamp" to FieldValue.serverTimestamp()
        )
        val todayDocId = getTodayDocumentId()

        db.collection("calories")
            .document(todayDocId)
            .set(data)
            .addOnSuccessListener {
                popUpNotification.value = Event("Kalorien erfolgreich gespeichert!")
            }
            .addOnFailureListener { e ->
                popUpNotification.value = Event("Fehler beim Speichern: ${e.localizedMessage}")
            }
    }
    fun getTodayDocumentId(): String {
        val sdf = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        return sdf.format(Date())
    }

    fun saveUserProfile(profile: UserProfile, onSuccess: () -> Unit) {
        val db = FirebaseFirestore.getInstance()
        val uid = auth.currentUser?.uid ?: return
        db.collection("userProfiles")
            .document(uid)
            .set(profile)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e ->
                popUpNotification.value = Event("Profil konnte nicht gespeichert werden: ${e.localizedMessage}")
            }
    }

    fun loadUserProfile(callback: (UserProfile?) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        val uid = auth.currentUser?.uid ?: return callback(null)
        db.collection("userProfiles")
            .document(uid)
            .get()
            .addOnSuccessListener { doc ->
                if(doc.exists()) {
                    val profile = doc.toObject(UserProfile::class.java)
                    callback(profile)
                } else {
                    callback(null)
                }
            }
            .addOnFailureListener {
                callback(null)
            }
    }
    fun savePlanProgress(progress: Map<String, Boolean>) {
        val db = FirebaseFirestore.getInstance()
        val uid = auth.currentUser?.uid ?: return
        db.collection("planProgress")
            .document(uid)
            .set(progress)
            .addOnSuccessListener {
                // Optional: Erfolgsmeldung, falls gewünscht
            }
            .addOnFailureListener { e ->
                popUpNotification.value = Event("Plan Fortschritt konnte nicht gespeichert werden: ${e.localizedMessage}")
            }
    }

    fun loadPlanProgress(callback: (Map<String, Boolean>) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        val uid = auth.currentUser?.uid ?: return callback(emptyMap())
        db.collection("planProgress")
            .document(uid)
            .get()
            .addOnSuccessListener { doc ->
                if (doc.exists() && doc.data != null) {
                    // Konvertiere das zurückgegebene Map<String, Any> in Map<String, Boolean>
                    val progress = doc.data!!.mapValues { entry ->
                        when (val value = entry.value) {
                            is Boolean -> value
                            is Long -> value != 0L // Falls als Zahl gespeichert
                            else -> false
                        }
                    }
                    callback(progress)
                } else {
                    callback(emptyMap())
                }
            }
            .addOnFailureListener {
                callback(emptyMap())
            }
    }
}