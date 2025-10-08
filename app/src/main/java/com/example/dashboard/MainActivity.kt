package com.example.dashboard

import android.R.attr.enabled
import android.icu.text.CaseMap
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Toast
import android.widget.ToggleButton
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dashboard.ui.theme.DashboardTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.lang.Integer.expand


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(android.graphics.Color.BLACK),
            navigationBarStyle = SystemBarStyle.dark(android.graphics.Color.BLACK)
        )
        setContent {
            DashboardContent()
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardContent() {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    var color by remember { mutableStateOf(Color.Black) }
    var context = LocalContext.current
    var scroll = rememberScrollState(0)
    var termsAccepted by remember { mutableStateOf(false) }
    var toastcounter by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding(),
                title = {
                    Text(
                        "Dashboard",
                        color = Color.White
                    ) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.DarkGray
                )
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(top = 8.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                }
                .verticalScroll(scroll)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.DarkGray
                )
            ) {
                Header()
            }
            Spacer(modifier = Modifier.height(4.dp))
            TextFieldContents(
                modifier = Modifier
                    .padding(top=16.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Gender()
            Spacer(modifier = Modifier.height(4.dp))
            Notifications()
            Spacer(modifier = Modifier.height(4.dp))
            TermsAndConditions(
                onread = { accepted ->
                    termsAccepted = accepted
                }
            )
            Spacer(modifier = Modifier.height(4.dp))
            Button(
                onClick = {
                    if(termsAccepted){
                        color = Color.Green
                        if(toastcounter!=1){
                            Toast.makeText(context, "Details Submitted", Toast.LENGTH_SHORT).show()
                            toastcounter=1
                        }
                    }
                    else{
                        color = Color.Black
                        Toast.makeText(context, "Please accept the terms and conditions", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)
                    .height(55.dp),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    containerColor = color,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Submit",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
@Composable
fun Header() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = "Profile",
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center,
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Cyan, CircleShape)
        )
    }
}
@Composable
fun TextFieldContents(modifier: Modifier = Modifier) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
            ) {
                focusManager.clearFocus()
                keyboardController?.hide()
              },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1E1E1E)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name", color = Color.Cyan) },
                placeholder = { Text("Enter your name") },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Cyan,
                    unfocusedBorderColor = Color.Cyan,
                    cursorColor = Color.Cyan,
                    focusedLabelColor = Color.Cyan,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email", color = Color.Cyan) },
                placeholder = { Text("Enter your email") },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Cyan,
                    unfocusedBorderColor = Color.Cyan,
                    cursorColor = Color.Cyan,
                    focusedLabelColor = Color.Cyan,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )
        }
    }
}
@Composable
fun Gender() {
    var selectedGender by remember { mutableStateOf<String?>(null) }
    var expand by remember { mutableStateOf(false) }
    val rotation by animateFloatAsState(if (expand) 180f else 0f, label = "")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 20.dp)
            .clickable(onClick = { expand = !expand }),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1E1E1E)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Gender",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = if (expand) "Collapse" else "Expand",
                    tint = Color.Cyan,
                    modifier = Modifier.rotate(rotation)
                )
            }

            AnimatedVisibility(visible = expand) {
                Column {
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 1.dp)
                    ) {
                        RadioButton(
                            selected = selectedGender == "Male",
                            onClick = { selectedGender = "Male" },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color.Cyan,
                                unselectedColor = Color.Gray
                            )
                        )
                        Text(
                            text = "Male",
                            color = Color.White,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 1.dp)
                    ) {
                        RadioButton(
                            selected = selectedGender == "Female",
                            onClick = { selectedGender = "Female" },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color.Cyan,
                                unselectedColor = Color.Gray
                            )
                        )
                        Text(
                            text = "Female",
                            color = Color.White,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 1.dp)
                    ) {
                        RadioButton(
                            selected = selectedGender == "Others",
                            onClick = { selectedGender = "Others" },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color.Cyan,
                                unselectedColor = Color.Gray
                            )
                        )
                        Text(
                            text = "Others",
                            color = Color.White,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }

                    if (selectedGender == "Others") {
                        var otherGender by remember { mutableStateOf("") }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            OutlinedTextField(
                                value = otherGender,
                                onValueChange = { otherGender = it },
                                label = { Text("Specify Gender", color = Color.Cyan) },
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color.Cyan,
                                    unfocusedBorderColor = Color.Cyan,
                                    cursorColor = Color.Cyan,
                                    focusedLabelColor = Color.Cyan,
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun Notifications(){
    var notifications by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 20.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1E1E1E)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ){
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ){
                Text(
                    text = "Notifications",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Switch(
                    checked = notifications,
                    onCheckedChange = {
                        notifications = it
                        if (it) {
                            Toast.makeText(context, "Notifications Enabled", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Notifications Disabled", Toast.LENGTH_SHORT).show()
                        }
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.Cyan,
                        checkedTrackColor = Color.Cyan.copy(alpha = 0.4f),
                        uncheckedThumbColor = Color.Gray,
                        uncheckedTrackColor = Color.DarkGray
                    )
                )
            }
        }
    }
}
@Composable
fun TermsAndConditions(onread: (Boolean)->Unit) {
    var termsAndConditions by remember { mutableStateOf(false) }
    var read by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        TextButton(
            onClick = { termsAndConditions = true },
            colors = ButtonDefaults.textButtonColors(
                contentColor = Color.Cyan
            )
        ) {
            Text(
                text = "Terms and Conditions",
                color = Color.Black
            )
        }
    }

    if (termsAndConditions) {
        AlertDialog(
            onDismissRequest = {
                termsAndConditions = false
                read = true
                onread(true)
            },
            title = { Text("Terms and Conditions") },
            text = {
                Text(
                    text = "By using this application, you agree to the following terms and conditions:\n" +
                            "1. This app is designed purely for educational and demonstration purposes.  \n" +
                            "   It does not collect, store, or share any personal information.\n" +
                            "2. The features provided (such as inputs, toggles, and settings) are for user interaction only and have no real-world effect.\n" +
                            "3. The app content and design belong to the developer and may not be redistributed or modified without permission.\n" +
                            "4. The developer is not responsible for any data loss, inaccuracies, or issues caused by misuse of the app.\n" +
                            "5. By continuing to use this app, you acknowledge that you have read and understood these terms.\n"
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        termsAndConditions = false
                        onread(true)
                    }
                ) {
                    Text("OK", color = Color.Cyan)
                }
            },
            containerColor = Color(0xFF2C2C2C),
            titleContentColor = Color.White,
            textContentColor = Color.White
        )
    }
}




