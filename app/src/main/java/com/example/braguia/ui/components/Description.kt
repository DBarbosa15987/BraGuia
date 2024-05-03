package com.example.braguia.ui.components

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import java.lang.Integer.max
import java.lang.Integer.min

@Composable
fun DescriptionDialog(text: String, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(10.dp),
        ) {
            LazyColumn(modifier = Modifier.padding(12.dp)) {
                item { Text(text = text) }
                item {
                    TextButton(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) {
                        Text(text = "Close", textAlign = TextAlign.Center)
                    }
                }
            }
        }
    }
}

@Composable
fun DescriptionShowMore(
    text: String,
    modifier: Modifier = Modifier,
    collapsedMaxLine: Int = 6,
    showMoreText: String = "...Show More",
    showLessText: String = "\nShow Less",
    pinId: Long
) {

    val key = "${pinId}_${text}"
    var isExpanded by remember(key) { mutableStateOf(false) }
    var clickable by remember(key) { mutableStateOf(false) }
    var lastCharIndex by remember(key) { mutableIntStateOf(0) }

    Box(modifier = modifier) {
        val annotatedString = buildAnnotatedString {
            if (clickable) {
                if (isExpanded) {
                    append(text)
                    pushStringAnnotation(tag = "LESS", annotation = showLessText)
                    append(showLessText)
                } else {
                    val adjustText = text.substring(startIndex = 0, endIndex = lastCharIndex)
                        .dropLast(showMoreText.length)
                        .dropLastWhile { Character.isWhitespace(it) || it == '.' }
                    append(adjustText)
                    append(showMoreText)
                }
            } else {
                append(text)
            }
        }
        ClickableText(modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
            text = annotatedString,
            maxLines = if (isExpanded) Int.MAX_VALUE else collapsedMaxLine,
            onTextLayout = { textLayoutResult ->
                if (!isExpanded && textLayoutResult.hasVisualOverflow) {
                    clickable = true
                    lastCharIndex = textLayoutResult.getLineEnd(collapsedMaxLine - 1)
                }
            },
            onClick = {
                if (isExpanded) {
                    annotatedString.getStringAnnotations("LESS", it, it).firstOrNull()
                        ?.let { isExpanded = !isExpanded }
                } else {
                    run { isExpanded = !isExpanded }
                }
            }
        )
    }
}

/*
@Composable
fun DescriptionShowMore(
    text: String,
    modifier: Modifier = Modifier,
    collapsedMaxLine: Int = 6,
    showMoreText: String = "... Show More",
    showLessText: String = "\tShow Less"
) {
    var isExpanded by remember { mutableStateOf(false) }
    var clickable by remember { mutableStateOf(false) }
    //var clickable = false
    var lastCharIndex by remember { mutableIntStateOf(0) }
    //var lastCharIndex = 0

    Box(modifier = modifier) {
        val annotatedString = buildAnnotatedString {
            Log.i("EXPANDED",clickable.toString())
            if (clickable) {
                if (isExpanded) {
                    append(text)
                    pushStringAnnotation(tag = "LESS", annotation = showLessText)
                    append(showLessText)
                } else {
                    //lastCharIndex = min(lastCharIndex,text.length)
                    Log.i("LAST_CHR_INDEX_AFTER_ASSIGNMENT",lastCharIndex.toString())
                    val adjustText = text.substring(startIndex = 0, endIndex = lastCharIndex)
                        .dropLast(showMoreText.length)
                        .dropLastWhile { Character.isWhitespace(it) || it == '.' }
                    Log.i("ADJUST",adjustText)
                    append(adjustText)
                    append(showMoreText)
                }
            } else {
                append(text)
            }
        }

        ClickableText(modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
            text = annotatedString,
            maxLines = if (isExpanded) Int.MAX_VALUE else collapsedMaxLine,
            onTextLayout = { textLayoutResult ->
                //Log.i("LAST_CHR_INDEX_BEFORE_IF",lastCharIndex.toString())
                if (!isExpanded && textLayoutResult.hasVisualOverflow) {
                    clickable = true
                    lastCharIndex = textLayoutResult.getLineEnd(collapsedMaxLine - 1)
                }
                Log.i("LAST_CHR_INDEX_AFTER_IF",lastCharIndex.toString())
            },
            onClick = {
                if (isExpanded) {
                    annotatedString.getStringAnnotations("LESS", it, it).firstOrNull()
                        ?.let { isExpanded = !isExpanded }
                } else {
                    run { isExpanded = !isExpanded }
                }
            }
        )
    }
}
*/



