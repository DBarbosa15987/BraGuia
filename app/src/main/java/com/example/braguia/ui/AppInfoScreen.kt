package com.example.braguia.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Facebook
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.Handshake
import androidx.compose.material.icons.outlined.Link
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.braguia.R
import com.example.braguia.model.AppInfo
import com.example.braguia.model.AppInfoDB
import com.example.braguia.model.Contact
import com.example.braguia.model.Partner
import com.example.braguia.model.Social

@Composable
fun AppInfoScreen(appInfo: AppInfo, innerPadding: PaddingValues) {

    LazyColumn(modifier = Modifier.padding(innerPadding)) {
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(25.dp)
                )
                Text(
                    appInfo.appName,
                    fontFamily = FontFamily.Cursive,
                    textAlign = TextAlign.Center,
                    fontSize = 60.sp,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(25.dp)
                )
                Text(
                    appInfo.appDesc,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(25.dp)
                )
            }
        }
        items(appInfo.contacts) { contact ->
            ContactCard(contact = contact)
            Spacer(modifier = Modifier.height(10.dp))
        }
        items(appInfo.socials) { social ->
            SocialCard(social = social)
            Spacer(modifier = Modifier.height(10.dp))
        }
        item {
            LazyRow(
                horizontalArrangement = Arrangement.Center,
                contentPadding = PaddingValues(10.dp)
            ) {
                items(appInfo.partners) { partner ->
                    PartnerCard(partner = partner)
                    Spacer(modifier = Modifier.width(10.dp))
                }
            }
        }
    }
}


@Composable
fun PartnerCard(partner: Partner) {
    val context = LocalContext.current
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = partner.partnerName,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        when (partner.partnerName) {
            "University of Minho" ->
                Image(
                    painter = painterResource(id = R.drawable.um),
                    contentDescription = null,
                    modifier = Modifier
                        .size(150.dp)
                        .clickable { goToLink(partner.partnerUrl, context) }
                )

            else -> Icon(
                imageVector = Icons.Outlined.Handshake,
                contentDescription = "${partner.partnerName}Card",
                modifier = Modifier
                    .size(150.dp)
                    .clickable { goToLink(partner.partnerUrl, context) }
            )
        }
    }
}

@Composable
fun SocialCard(social: Social) {
    Column {
        Text(
            text = social.socialName,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            when (social.socialName) {
                "Facebook" -> Icon(
                    imageVector = Icons.Outlined.Facebook,
                    contentDescription = "FacebookCard"
                )

                else -> Icon(
                    imageVector = Icons.Outlined.Groups,
                    contentDescription = "${social.socialApp}Card"
                )
            }
            Spacer(modifier = Modifier.width(5.dp))
            UrlClickableText(url = social.socialUrl)
        }
    }
}


@Composable
fun ContactCard(contact: Contact) {

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = contact.contactName,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Outlined.Phone, contentDescription = "contactPhoneIcon")
            Spacer(modifier = Modifier.width(5.dp))
            PhoneCallClickableText(contact.contactPhone)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Outlined.Link, contentDescription = "contactUrlIcon")
            Spacer(modifier = Modifier.width(5.dp))
            UrlClickableText(url = contact.contactUrl)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Outlined.Mail, contentDescription = "contactMailIcon")
            Spacer(modifier = Modifier.width(5.dp))
            Text(text = contact.contactMail)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Outlined.Description,
                contentDescription = "contactDescIcon"
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(text = contact.contactDesc)
        }
    }
}

@Composable
fun PhoneCallClickableText(phoneNumber: String) {
    val context = LocalContext.current
    ClickableText(
        onClick = {
            val u = Uri.parse("tel:$phoneNumber")
            val i = Intent(Intent.ACTION_DIAL, u)

            try {
                context.startActivity(i)
            } catch (e: Exception) {
                Log.e("APPINFO", "error in phone app intent")
            }
        },
        text = AnnotatedString(text = phoneNumber),
        style = TextStyle(
            color = Color(0xff64B5F6),
            textDecoration = TextDecoration.Underline
        )
    )
}

@Composable
fun UrlClickableText(url: String) {
    val context = LocalContext.current
    ClickableText(
        onClick = { goToLink(url, context) },
        text = AnnotatedString(text = url),
        style = TextStyle(
            color = Color(0xff64B5F6),
            textDecoration = TextDecoration.Underline
        )
    )
}

private fun goToLink(url: String, context: Context) {

    val intent = Intent(Intent.ACTION_VIEW)
    intent.setData(Uri.parse(url));
    try {
        context.startActivity(intent)
    } catch (e: Exception) {
        Log.e("APPINFO", "error in browser intent")
    }
}
