package com.zynksoftware.base.ui.pager.firstscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.sp
import com.zynksoftware.base.BuildConfig

@Composable
fun Fragment1Compose(
    state: FirstState,
    onDeveloperButtonClick: () -> Unit,
    shouldShowDevOptions: Boolean
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Cyan),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (shouldShowDevOptions) {
            ClickableText(
                text = AnnotatedString("Tap here to open developer activity"),
                onClick = {
                    onDeveloperButtonClick.invoke()
                },
                style = TextStyle(
                    color = Color.Black,
                    fontStyle = FontStyle.Italic,
                    fontSize = 20.sp
                )
            )
            Text(
                text = "Version Code: ${BuildConfig.VERSION_CODE}",
                style = TextStyle(
                    color = Color.Black,
                    fontStyle = FontStyle.Italic,
                    fontSize = 20.sp
                )
            )
        }
    }
}