package com.example.braguia.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.braguia.R
import com.example.braguia.model.AppInfo
import com.example.braguia.model.Contact
import com.example.braguia.model.Partner
import com.example.braguia.model.Social

@Composable
fun AppInfoScreen(appInfo: AppInfo, innerPadding: PaddingValues) {

    LazyColumn(modifier = Modifier.padding(innerPadding)) {
        item { Text(text = appInfo.appName) }
        //TODO icon da app aqui??
        item { Text(text = appInfo.appDesc) }
        item { Text(text = appInfo.landingPageText) }
        items(appInfo.contacts) { contact ->
            ContactCard(contact = contact)
        }
        items(appInfo.socials) { social ->
            SocialCard(social = social)
        }
        //TODO fix nisto...
        item {
            LazyRow {
                items(appInfo.partners) { partner ->
                    repeat(5) { PartnerCard(partner = partner) }
                }
            }
        }
    }
}

@Composable
fun PartnerCard(partner: Partner) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = partner.partnerName)
        when (partner.partnerName) {
            "University of Minho" ->
                Image(
                    painter = painterResource(id = R.drawable.um),
                    contentDescription = null,
                    modifier = Modifier.size(150.dp)
                )

            else -> Icon(
                imageVector = Icons.Outlined.Handshake,
                contentDescription = "${partner.partnerName}Card"
            )
        }

        //UrlClickableText(url = partner.partnerUrl)
    }
}

@Composable
fun SocialCard(social: Social) {
    Column {
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
            UrlClickableText(url = social.socialUrl)
        }
    }
}


@Composable
fun ContactCard(contact: Contact) {
    Column {
        Text(text = contact.contactName)
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
            Icon(imageVector = Icons.Outlined.Description, contentDescription = "contactDescIcon")
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
                //TODO abrir o dialog de erro
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
        onClick = {

            val intent = Intent(Intent.ACTION_VIEW)
            intent.setData(Uri.parse(url));
            try {
                context.startActivity(intent)
            } catch (e: Exception) {
                //TODO abrir o dialog de erro
            }
        },
        text = AnnotatedString(text = url),
        style = TextStyle(
            color = Color(0xff64B5F6),
            textDecoration = TextDecoration.Underline
        )
    )
}

/*
Intent intent = new Intent(Intent.ACTION_MAIN);
intent.addCategory(Intent.CATEGORY_APP_EMAIL);
getActivity().startActivity(intent);

ou

Intent i = new Intent(Intent.ACTION_SENDTO);
i.setData(Uri.parse("mailto:support@mailname.com"));
i.putExtra(Intent.EXTRA_SUBJECT, "Feedback/Support");
startActivity(Intent.createChooser(emailIntent, "Send feedback"));


* */