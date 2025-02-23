package com.example.fitnessapp.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fitnessapp.DestinationScreen
import com.example.fitnessapp.FirebaseViewModel
import com.example.fitnessapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(navController: NavController, vm: FirebaseViewModel) {
    val empty by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var cpassword by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    var cpasswordVisibility by remember { mutableStateOf(false) }
    var errorE by remember { mutableStateOf(false) }
    var errorP by remember { mutableStateOf(false) }
    var errorCP by remember { mutableStateOf(false) }
    var errorC by remember { mutableStateOf(false) }
    var pLength by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (vm.inProgress.value) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 150.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Sign Up",
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp,
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(50.dp))
        if (errorE) {
            Text(
                text = "Bitte geben sie eine E-Mail ein",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(end = 100.dp)
            )
        }
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "E-Mail", color = MaterialTheme.colorScheme.onSurface) },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_person_24),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            },
            trailingIcon = {
                if (email.isNotEmpty()) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_close_24),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.clickable { email = empty }
                    )
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            ),
            shape = RoundedCornerShape(50.dp),
            modifier = Modifier
                .width(300.dp)
                .height(60.dp),
            colors = TextFieldDefaults.textFieldColors(
                unfocusedIndicatorColor = MaterialTheme.colorScheme.surface,
                focusedIndicatorColor = MaterialTheme.colorScheme.surface,
                cursorColor = MaterialTheme.colorScheme.primary,
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                focusedLeadingIconColor = MaterialTheme.colorScheme.onSurface,
                unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurface,
                focusedLabelColor = MaterialTheme.colorScheme.onSurface,
                unfocusedLabelColor = MaterialTheme.colorScheme.onSurface,
                focusedTrailingIconColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTrailingIconColor = MaterialTheme.colorScheme.onSurface,
            )
        )
        Spacer(modifier = Modifier.height(30.dp))
        if (errorP) {
            Text(
                text = "Passwort eingeben",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(end = 100.dp)
            )
        }
        if (pLength) {
            Text(
                text = "Passwort muss 6 Zeichen haben",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(end = 100.dp)
            )
        }
        TextField(
            value = password,
            onValueChange = {
                password = it
                pLength = it.length < 6
            },
            label = { Text(text = "Passwort hier", color = MaterialTheme.colorScheme.onSurface) },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_lock_24),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            },
            trailingIcon = {
                if (password.isNotEmpty()) {
                    val visualIcon = if (passwordVisibility)
                        painterResource(id = R.drawable.baseline_visibility_24)
                    else
                        painterResource(id = R.drawable.baseline_visibility_off_24)
                    Icon(
                        painter = visualIcon,
                        contentDescription = if (passwordVisibility) "Passwort verbergen" else "Passwort anzeigen",
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.clickable { passwordVisibility = !passwordVisibility }
                    )
                }
            },
            visualTransformation = if (passwordVisibility)
                VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Password
            ),
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            ),
            shape = RoundedCornerShape(50.dp),
            modifier = Modifier
                .width(300.dp)
                .height(60.dp),
            colors = TextFieldDefaults.textFieldColors(
                unfocusedIndicatorColor = MaterialTheme.colorScheme.surface,
                focusedIndicatorColor = MaterialTheme.colorScheme.surface,
                cursorColor = MaterialTheme.colorScheme.primary,
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                focusedLeadingIconColor = MaterialTheme.colorScheme.onSurface,
                unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurface,
                focusedLabelColor = MaterialTheme.colorScheme.onSurface,
                unfocusedLabelColor = MaterialTheme.colorScheme.onSurface,
                focusedTrailingIconColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTrailingIconColor = MaterialTheme.colorScheme.onSurface,
            )
        )
        Spacer(modifier = Modifier.height(30.dp))
        if (errorC) {
            Text(
                text = "Passwort hier wiederholen",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(end = 100.dp)
            )
        }
        if (errorCP) {
            Text(
                text = "Passwörter müssen übereinstimmen",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(end = 100.dp)
            )
        }
        TextField(
            value = cpassword,
            onValueChange = { cpassword = it },
            label = { Text(text = "Passwort hier wiederholen", color = MaterialTheme.colorScheme.onSurface) },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_lock_24),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            },
            trailingIcon = {
                if (cpassword.isNotEmpty()) {
                    val visualIcon = if (cpasswordVisibility)
                        painterResource(id = R.drawable.baseline_visibility_24)
                    else
                        painterResource(id = R.drawable.baseline_visibility_off_24)
                    Icon(
                        painter = visualIcon,
                        contentDescription = if (cpasswordVisibility) "Passwort verbergen" else "Passwort anzeigen",
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.clickable { cpasswordVisibility = !cpasswordVisibility }
                    )
                }
            },
            visualTransformation = if (cpasswordVisibility)
                VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            ),
            shape = RoundedCornerShape(50.dp),
            modifier = Modifier
                .width(300.dp)
                .height(60.dp),
            colors = TextFieldDefaults.textFieldColors(
                unfocusedIndicatorColor = MaterialTheme.colorScheme.surface,
                focusedIndicatorColor = MaterialTheme.colorScheme.surface,
                cursorColor = MaterialTheme.colorScheme.primary,
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                focusedLeadingIconColor = MaterialTheme.colorScheme.onSurface,
                unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurface,
                focusedLabelColor = MaterialTheme.colorScheme.onSurface,
                unfocusedLabelColor = MaterialTheme.colorScheme.onSurface,
                focusedTrailingIconColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTrailingIconColor = MaterialTheme.colorScheme.onSurface,
            )
        )
        Spacer(modifier = Modifier.height(50.dp))
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(50.dp))
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Button(
                onClick = {
                    if (email.isNotEmpty()) {
                        errorE = false
                        if (password.isNotEmpty()) {
                            errorP = false
                            if (cpassword.isNotEmpty()) {
                                errorC = false
                                if (password == cpassword) {
                                    errorCP = false
                                    vm.onSignUp(email, password)
                                } else {
                                    errorCP = true
                                }
                            } else {
                                errorC = true
                            }
                        } else {
                            errorP = true
                        }
                    } else {
                        errorE = true
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier.width(200.dp)
            ) {
                Text(
                    text = "Sign Up",
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            if (vm.signedIn.value) {
                navController.navigate(DestinationScreen.UserProfile.route)
            }
            vm.signedIn.value = false
        }
    }
}