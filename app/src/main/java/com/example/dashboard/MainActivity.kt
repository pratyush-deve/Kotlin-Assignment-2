package com.example.dashboard

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.dashboard.navigation.MovieApp
import com.example.dashboard.viewmodel.UserViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(android.graphics.Color.BLACK),
            navigationBarStyle = SystemBarStyle.dark(android.graphics.Color.BLACK)
        )
        setContent {
            MovieApp()
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardContent(
    navController: NavController,
    viewModel: UserViewModel
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    var context = LocalContext.current
    var scroll = rememberScrollState(0)
    var termsAccepted by remember { mutableStateOf(false) }
    var gengerSelected by remember { mutableStateOf(false) }
    var nameselected by remember { mutableStateOf(false) }
    var emailselected by remember { mutableStateOf(false) }
    var toastcounter by remember { mutableStateOf(0) }
    var showDialog by remember { mutableStateOf(false) }
    val submissionComplete by viewModel.submissionComplete

    //themes color
    var isDarkTheme by viewModel.isDarkTheme
    LaunchedEffect(isDarkTheme) {
        // Update Submit button color automatically when theme changes
        viewModel.colorSubmit.value = if (isDarkTheme) Color.White else Color.Black
    }
    val backgroundColor = if (isDarkTheme) Color(0xFF232121) else Color(0xFFFFFFFF)
    val cardColor = if (isDarkTheme) Color(0xFFF1F0F0) else Color(0xFF000000)
    val textColor = if (isDarkTheme) Color(color = 0xFF020202) else Color(color = 0xFFFFFFFF)
    val toggleColor = if (isDarkTheme) Color(color = 0xFF071D6B) else Color(color = 0xFF21DAF3)//For all Cyan colors
    val oppositeColor = if (isDarkTheme) Color.White else Color.Black//used in places where opp of textcolor needed
    val colorsubmit by viewModel.colorSubmit

    //submit button booleans
    val isNameFilled = viewModel.name.value.isNotEmpty()
    val isEmailFilled = viewModel.email.value.isNotEmpty()
    val isGenderSelected = viewModel.gender.value.isNotEmpty()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
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
                    containerColor = Color.Black
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color.Black,
                modifier=Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ){
                    Text(
                        text = "Social Media Links",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = Color.White
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Icon(
                            painter = painterResource(id = R.drawable.insta),
                            contentDescription = "Insta",
                            modifier = Modifier
                                .size(25.dp)
                                .clip(CircleShape)
                                .clickable(onClick = {
                                    Toast.makeText(context, "Opening Instagram",Toast.LENGTH_SHORT).show()
                                })
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.facebook),
                            contentDescription = "facebook",
                            modifier = Modifier
                                .size(25.dp)
                                .clip(CircleShape)
                                .clickable(onClick = {
                                    Toast.makeText(context, "Opening Facebook",Toast.LENGTH_SHORT).show()

                                })
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.youtube),
                            contentDescription = "youtube",
                            modifier = Modifier
                                .size(25.dp)
                                .clip(CircleShape)
                                .clickable(onClick = {
                                    Toast.makeText(context, "Opening Youtube",Toast.LENGTH_SHORT).show()

                                })
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Text(
                            text="Instagram",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White,
                            modifier = Modifier
                                .clickable(onClick = {
                                    Toast.makeText(context, "Opening Instagram",Toast.LENGTH_SHORT).show()

                                })
                        )
                        Text(
                            text="Facebook",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White,
                            modifier = Modifier
                                .clickable(onClick = {
                                    Toast.makeText(context, "Opening Facebook",Toast.LENGTH_SHORT).show()

                                })
                        )
                        Text(
                            text="Youtube",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White,
                            modifier = Modifier
                                .clickable(onClick = {
                                    Toast.makeText(context, "Opening Youtube",Toast.LENGTH_SHORT).show()

                                })
                        )
                    }
                }
            }
        },

    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(top = 8.dp)
                .background(backgroundColor)
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
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
                    .border(
                        width = 3.dp,
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color.Cyan,
                                Color.Blue,
                                Color.Magenta
                            )
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ),
                colors = CardDefaults.cardColors(
                    containerColor = cardColor
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Header()
            }
            Spacer(modifier = Modifier.height(4.dp))
            TextFieldContents(
                modifier = Modifier
                    .padding(top=16.dp),
                onread1 = { accepted ->
                    nameselected = accepted
                },
                onread2 = { accepted ->
                    emailselected = accepted
                },
                toggleColor = toggleColor,
                textColor = textColor,
                cardColor = cardColor,
                viewModel = viewModel,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Gender(
                viewModel = viewModel,
                cardColor = cardColor,
                textColor = textColor,
                toggleColor=toggleColor,
                onread = { accepted ->
                    gengerSelected = accepted
                }
            )
            Spacer(modifier = Modifier.height(4.dp))
            Notifications(
                cardColor = cardColor,
                textColor = textColor,
                toggleColor = toggleColor
            )
            Spacer(modifier = Modifier.height(4.dp))
            Settings(
                cardColor = cardColor,
                toggleColor = toggleColor,
                textColor = textColor,
                isDarkTheme = isDarkTheme,
                onThemeChange = { isDarkTheme = it }
            )
            Spacer(modifier = Modifier.height(4.dp))
            Contents(
                cardColor = cardColor,
                textColor = textColor
            )
            Spacer(modifier = Modifier.height(4.dp))
            TermsAndConditions(
                onread = { accepted ->
                    termsAccepted = accepted
                },
                textColor = textColor,
                cardColor = cardColor

            )
            Spacer(modifier = Modifier.height(4.dp))
            Button(
                onClick = {
                    val isNameFilled = viewModel.name.value.isNotEmpty()
                    val isEmailFilled = viewModel.email.value.isNotEmpty()
                    val isGenderSelected = viewModel.gender.value.isNotEmpty()

                    if (termsAccepted && isNameFilled && isEmailFilled && isGenderSelected && !submissionComplete) {
                        showDialog = true
                    } else if (!isNameFilled) {
                        Toast.makeText(context, "You cannot leave Name empty", Toast.LENGTH_SHORT).show()
                    } else if (!isEmailFilled) {
                        Toast.makeText(context, "You cannot leave Email empty", Toast.LENGTH_SHORT).show()
                    } else if (!isGenderSelected) {
                        Toast.makeText(context, "Please select a gender", Toast.LENGTH_SHORT).show()
                    } else if (!termsAccepted) {
                        Toast.makeText(context, "Please accept the terms and conditions", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)
                    .padding(bottom = 8.dp)
                    .height(55.dp),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorsubmit,
                    contentColor = textColor
                )
            ) {
                Text(
                    text = "Submit",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
        }
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Confirm Submission?", color = Color.White) },
                text = {
                    Text(
                        "Your details will be submitted and saved for future use.",
                        color = Color.White
                    )
                },
                confirmButton = {
                    TextButton(onClick = {
                        showDialog = false
                        viewModel.colorSubmit.value = Color.Green
                        viewModel.submissionComplete.value = true
                        navController.navigate("MovieList")
                    }) {
                        Text("OK", color = Color.Cyan)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("Cancel", color = Color.Cyan)
                    }
                },
                containerColor = Color(0xFF2C2C2C),
                titleContentColor = Color.White,
                textContentColor = Color.White
            )
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
fun TextFieldContents(
    onread1:(Boolean)->Unit,
    onread2:(Boolean)->Unit,
    modifier: Modifier = Modifier,
    toggleColor: Color,
    textColor: Color,
    cardColor: Color,
    viewModel: UserViewModel,
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .border(
                width = 5.dp,
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color.Cyan,
                        Color.Blue,
                        Color.Magenta
                    )
                ),
                shape = RoundedCornerShape(20.dp)
            )
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
            ) {
                focusManager.clearFocus()
                keyboardController?.hide()
              },

        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardColor
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
                value = viewModel.name.value,
                onValueChange = {
                    viewModel.name.value = it
                    onread1(it.isNotEmpty())
                },
                maxLines = 1,
                label = { Text("Name", color = toggleColor) },
                placeholder = { Text("Enter your name") },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = toggleColor,
                    unfocusedBorderColor = toggleColor,
                        cursorColor = toggleColor,
                    focusedLabelColor = toggleColor,
                    focusedTextColor = textColor,
                    unfocusedTextColor = textColor
                )
            )

            OutlinedTextField(
                value = viewModel.email.value,
                onValueChange = {
                    viewModel.email.value = it
                    onread2(it.isNotEmpty())
                },
                maxLines = 1,
                label = { Text("Email", color = toggleColor) },
                placeholder = { Text("Enter your email") },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = toggleColor,
                    unfocusedBorderColor = toggleColor,
                    cursorColor = toggleColor,
                    focusedLabelColor = toggleColor,
                    focusedTextColor = textColor,
                    unfocusedTextColor = textColor
                )
            )
        }
    }
}
@Composable
fun Gender(
    viewModel: UserViewModel,
    onread: (Boolean)->Unit,
    cardColor: Color,
    textColor: Color,
    toggleColor: Color
) {
    var selectedGender by remember { mutableStateOf(viewModel.gender.value.ifEmpty { null }) }
    var otherGender by remember { mutableStateOf("") }
    var expand by remember { mutableStateOf(false) }
    val rotation by animateFloatAsState(if (expand) 180f else 0f, label = "")
    val scope = rememberCoroutineScope()
    var otherTextInput by remember { mutableStateOf(false) }
    var checkColor by remember { mutableStateOf(Color.LightGray) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 20.dp)
            .border(
                width = 5.dp,
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color.Cyan,
                        Color.Blue,
                        Color.Magenta
                    )
                ),
                shape = RoundedCornerShape(20.dp)
            )
            .clickable(onClick = { expand = true}),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardColor
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
                    color = textColor
                )
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = if (expand) "Collapse" else "Expand",
                    tint = toggleColor,
                    modifier = Modifier
                        .rotate(rotation)
                        .clickable { expand = !expand }
                )
            }

            AnimatedVisibility(
                visible = expand,
                enter = expandVertically(),
                exit = shrinkVertically()
                ) {
                Column {
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 1.dp)
                    ) {
                        RadioButton(
                            selected = selectedGender == "Male",
                            onClick = {
                                selectedGender = "Male"
                                viewModel.gender.value = "Male"
                                onread(true)
                                scope.launch { // 👈 launch coroutine for delay
                                    delay(1000)
                                    expand = false
                                }
                            },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = toggleColor,
                                unselectedColor = Color.Gray
                            )
                        )
                        Text(
                            text = "Male",
                            color = textColor,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(vertical = 1.dp)
                    ) {
                        RadioButton(
                            selected = selectedGender == "Female",
                            onClick = {
                                selectedGender = "Female"
                                viewModel.gender.value = "Female"
                                onread(true)
                                scope.launch { // 👈 launch coroutine for delay
                                    delay(1000)
                                    expand = false
                                }                            },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = toggleColor,
                                unselectedColor = Color.Gray
                            )
                        )
                        Text(
                            text = "Female",
                            color = textColor,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 1.dp)
                    ){
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(vertical = 1.dp)
                        ) {
                            RadioButton(
                                selected = selectedGender == "Others",
                                onClick = {
                                    selectedGender = "Others"
                                    onread(true)
                                    expand = true
                                },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = toggleColor,
                                    unselectedColor = Color.Gray
                                )
                            )
                            Text(
                                text = "Others",
                                color = textColor,
                                modifier = Modifier.padding(start = 4.dp)
                            )
                        }
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = "Check",
                            tint = Color.Cyan,
                            modifier = Modifier
                                .clickable(onClick = {
                                    if(otherTextInput){
                                        viewModel.gender.value = otherGender
                                        checkColor = Color.Green
                                        scope.launch {
                                            delay(1000)
                                            expand=false
                                        }
                                    }
                                })
                                .padding(end=8.dp)
                                .background(
                                    color = checkColor,
                                    shape = CircleShape
                                )
                                .padding(4.dp)
                        )
                    }
                    if (selectedGender == "Others") {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            OutlinedTextField(
                                value = otherGender,
                                onValueChange = {
                                    otherGender = it
                                    otherTextInput=true
                                },
                                label = { Text("Specify Gender", color = toggleColor) },
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = toggleColor,
                                    unfocusedBorderColor = toggleColor,
                                    cursorColor = toggleColor,
                                    focusedLabelColor = toggleColor,
                                    focusedTextColor = toggleColor,
                                    unfocusedTextColor = toggleColor
                                )
                            )
                        }
                    }
                    if(selectedGender!="Male" && selectedGender!="Female" && selectedGender!="Others"){
                        onread(false)
                    }
                }
            }
        }
    }
}
@Composable
fun Notifications(
    cardColor: Color,
    textColor: Color,
    toggleColor: Color
){
    var notifications by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 20.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ){
        Column(
            modifier = Modifier
                .border(
                    width = 5.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.Cyan,
                            Color.Blue,
                            Color.Magenta
                        )
                    ),
                    shape = RoundedCornerShape(20.dp)
                )
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
                    color = textColor,
                    modifier = Modifier
                        .padding(start = 4.dp)
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
                        checkedThumbColor = toggleColor,
                        checkedTrackColor = toggleColor.copy(alpha = 0.4f),
                        uncheckedThumbColor = Color.Gray,
                        uncheckedTrackColor = Color.DarkGray
                    )
                )
            }
        }
    }
}
@Composable
fun TermsAndConditions(
    onread: (Boolean)->Unit,
    textColor: Color,
    cardColor: Color
) {
    var termsAndConditions by remember { mutableStateOf(false) }
    var read by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        val oppositeColor = if (textColor.luminance() > 0.5f) Color.Black else Color.White
        TextButton(
            onClick = { termsAndConditions = true },
            colors = ButtonDefaults.textButtonColors(
                contentColor = oppositeColor

            )
        ) {
            Text(
                text = "Terms and Conditions",
                color = oppositeColor
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
                    Text("OK", color = textColor)
                }
            },
            containerColor = cardColor,
            titleContentColor = textColor,
            textContentColor = textColor
        )
    }
}
@Composable
fun Settings(
    cardColor: Color,
    textColor: Color,
    toggleColor: Color,
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit
){
    var expand by remember { mutableStateOf(false) }
    val rotation by animateFloatAsState(if (expand) 180f else 0f, label = "")
    var volume by remember { mutableStateOf(0.5f) }
    var showDisplay by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 20.dp)
            .border(
                width = 5.dp,
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color.Cyan,
                        Color.Blue,
                        Color.Magenta
                    )
                ),
                shape = RoundedCornerShape(20.dp)
            )
            .clickable(onClick = {expand=true}),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ){
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
                    text = "Settings",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = if (expand) "Collapse" else "Expand",
                    tint = toggleColor,
                    modifier = Modifier
                        .rotate(rotation)
                        .clickable { expand = !expand }
                )
            }
            AnimatedVisibility(
                expand,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                Column(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Volume",
                            color = textColor,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = " ${(volume * 100).toInt()}%",
                            color = textColor,
                            fontSize = 14.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Slider(
                        value = volume,
                        onValueChange = { volume = it },
                        valueRange = 0f..1f,
                        colors = SliderDefaults.colors(
                            thumbColor = toggleColor,
                            activeTrackColor = toggleColor,
                            inactiveTrackColor = Color.Gray
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedButton(
                        onClick = {
                            showDisplay = true
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = textColor
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Themes",
                            color = textColor
                        )
                    }
                }
            }
        }
    }
    val oppositeColor = if (textColor.luminance() > 0.5f) Color.Black else Color.White
    if(showDisplay){
        AlertDialog(
            onDismissRequest = {showDisplay=false},
            title = { Text(
                "Display Settings",
                color = textColor
            ) },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDisplay = false
                    }
                ) {
                    Text("OK", color = toggleColor)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDisplay = false
                    }
                ) {
                    Text("Cancel", color = toggleColor)
                }
            },
            text = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = if (isDarkTheme) "Dark Theme" else "Light Theme",
                        color = textColor
                    )
                    Switch(
                        checked = isDarkTheme,
                        onCheckedChange = {
                            onThemeChange(it)
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = toggleColor,
                            checkedTrackColor = toggleColor.copy(alpha = 0.4f),
                            uncheckedThumbColor = Color.Gray,
                            uncheckedTrackColor = Color.DarkGray
                        )
                    )
                }
            },
            containerColor = oppositeColor,
        )
    }
}
@Composable
fun Contents(
    cardColor: Color,
    textColor: Color
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 20.dp)
            .border(
                width = 5.dp,
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color.Cyan,
                        Color.Blue,
                        Color.Magenta
                    )
                ),
                shape = RoundedCornerShape(20.dp)
            ),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 5.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.Cyan,
                            Color.Blue,
                            Color.Magenta
                        )
                    ),
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(16.dp)
        ) {
            Text(
                text = "Recent Activities",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = textColor,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            )
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 200.dp)

        ) {
            item() {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .border(
                            width = 2.dp,
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color.Cyan,
                                    Color.Blue,
                                    Color.Magenta
                                )
                            ),
                            shape = RoundedCornerShape(20.dp)
                        ),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = cardColor
                    ),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Recent Login",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = textColor
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Account was logged in on 8/09/2025.",
                            fontSize = 15.sp,
                            color = textColor.copy(alpha = 0.8f)
                        )
                    }
                }
            }
            item() {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .border(
                            width = 2.dp,
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color.Cyan,
                                    Color.Blue,
                                    Color.Magenta
                                )
                            ),
                            shape = RoundedCornerShape(20.dp)
                        ),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = cardColor
                    ),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Logout",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = textColor
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Account was logged off from all devices on 9/09/2025.",
                            fontSize = 15.sp,
                            color = textColor.copy(alpha = 0.8f)
                        )
                    }
                }
            }
            item() {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .border(
                            width = 2.dp,
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color.Cyan,
                                    Color.Blue,
                                    Color.Magenta
                                )
                            ),
                            shape = RoundedCornerShape(20.dp)
                        ),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = cardColor
                    ),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Recent Login",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = textColor
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Account was logged in on 1/10/2025.",
                            fontSize = 15.sp,
                            color = textColor.copy(alpha = 0.8f)
                        )
                    }
                }
            }
            item() {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .border(
                            width = 2.dp,
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color.Cyan,
                                    Color.Blue,
                                    Color.Magenta
                                )
                            ),
                            shape = RoundedCornerShape(20.dp)
                        ),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = cardColor
                    ),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Updates",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = textColor
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Name was edited from abc to xyz",
                            fontSize = 15.sp,
                            color = textColor.copy(alpha = 0.8f)
                        )
                    }
                }
            }
            item() {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .border(
                            width = 2.dp,
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color.Cyan,
                                    Color.Blue,
                                    Color.Magenta
                                )
                            ),
                            shape = RoundedCornerShape(20.dp)
                        ),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = cardColor
                    ),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Updates",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = textColor
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Email was changed from abc@gmail.com to xyz@gmail.com",
                            fontSize = 15.sp,
                            color = textColor.copy(alpha = 0.8f)
                        )
                    }
                }
            }
        }

    }
}



