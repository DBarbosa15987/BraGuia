package com.example.braguia.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.braguia.R
import com.example.braguia.model.User
import com.example.braguia.ui.components.AlertConfirmDialog
import com.example.braguia.ui.components.AlertDialogTemplate
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun UserPageScreen(innerPadding: PaddingValues, user: User, logOff: () -> Unit) {

    val modifier: Modifier = if (user.userType.lowercase() == "premium") {
        Modifier
            .size(150.dp)
            .padding(5.dp)
            .clip(CircleShape)
            .border(
                BorderStroke(5.dp, Color.Yellow),
                CircleShape
            )
    } else {
        Modifier
            .size(150.dp)
            .padding(5.dp)
            .clip(CircleShape)
    }

    var logOffPopUp by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxWidth()
    ) {
        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Image(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = "UserProfileIcon",
                    modifier = modifier
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(20.dp))
            UserInfoColumn(user)
        }
        item {
            if (logOffPopUp) {
                AlertConfirmDialog(
                    title = stringResource(R.string.logOffDialogTitle),
                    text = stringResource(R.string.logOffDialogText),
                    cancelOption = { logOffPopUp = false },
                    cancelText = stringResource(id = R.string.cancel),
                    confirmOption = { logOffPopUp = false;logOff() },
                    confirmText = stringResource(R.string.logout),
                    onDismiss = { logOffPopUp = false },
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(30.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Button(onClick = { logOffPopUp = true }) {
                    Text(text = "Logout")
                }
            }
        }
    }
}

@Composable
fun UserInfoColumn(user: User) {
    Column {
        UserInfoCard("Name", "${user.firstName} ${user.lastName}", Icons.Filled.Badge)
        UserInfoCard("Username", user.username, Icons.Filled.PersonOutline)
        UserInfoCard("User Type", user.userType, Icons.Filled.WorkspacePremium)
        UserInfoCard("Email", user.email, Icons.Filled.Mail)
        UserInfoCard("Date Joined", formatDate(user.dateJoined), Icons.Filled.DateRange)
        UserInfoCard("Last Login", formatDate(user.lastLogin), Icons.AutoMirrored.Filled.Login)
    }
}

@Composable
fun UserInfoCard(attribute: String, value: String, icon: ImageVector) {
    if (value.isNotEmpty()) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
                Spacer(modifier = Modifier.width(10.dp))
                Icon(imageVector = icon, contentDescription = attribute)
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(text = attribute, fontWeight = FontWeight.Bold)
                    Text(text = value)
                }
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
    }
}

fun formatDate(input: String): String {
    try{
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSX", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd/MM/yyyy, HH:mm", Locale.getDefault())
        val date = inputFormat.parse(input)
        return outputFormat.format(date ?: input)
    }catch (e:Exception){
        return input
    }
}

@Composable
@Preview(showSystemUi = true)
fun UserPageScreenPrev() {
    UserPageScreen(
        innerPadding = PaddingValues(10.dp), user = User(
            "username",
            "userType",
            "lastLogin",
            "firstName",
            "lastName",
            "email",
            "dateJoined"
        )
    ) {

    }
}
