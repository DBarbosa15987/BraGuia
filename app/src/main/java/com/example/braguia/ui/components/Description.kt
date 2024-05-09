package com.example.braguia.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

@Composable
fun DescriptionShowMore(
    text: String,
    modifier: Modifier = Modifier,
    collapsedMaxLine: Int = 6,
    showMoreText: String = "...Show More",
    showLessText: String = "\nShow Less",
    seed: Long
) {

    val key = "${seed}_${text}"
    var isExpanded by remember(key) { mutableStateOf(false) }
    var clickable by remember(key) { mutableStateOf(false) }
    var lastCharIndex by remember(key) { mutableIntStateOf(0) }
    val textStyle = LocalTextStyle.current
    val textColor = LocalContentColor.current

    Box(modifier = modifier) {
        val annotatedString = buildAnnotatedString {
            if (clickable) {
                if (isExpanded) {
                    withStyle(textStyle.toSpanStyle().copy(color = textColor)) {
                        append(text)
                        pushStringAnnotation(tag = "LESS", annotation = showLessText)
                        append(showLessText)
                    }
                } else {
                    withStyle(textStyle.toSpanStyle().copy(color = textColor)){
                        val adjustText = text.substring(startIndex = 0, endIndex = lastCharIndex)
                            .dropLast(showMoreText.length)
                            .dropLastWhile { Character.isWhitespace(it) || it == '.' }
                        append(adjustText)
                        append(showMoreText)
                    }
                }
            } else {
                withStyle(textStyle.toSpanStyle().copy(color = textColor)) { append(text) }
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



