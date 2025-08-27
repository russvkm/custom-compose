package com.example.customcomponent

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
private fun CustomButton(
    text: String,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle.Default,
    textColor: Color = Color.White,
    border: BorderStroke? = null,
    enabled: Boolean = true,
    loading: Boolean = false,
    icon: Int? = null,
    onClick: () -> Unit,
) {
    Button(
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
        onClick = {
            if (!loading) {
                onClick
            }
        },
        modifier = modifier,
        shape = RoundedCornerShape(50),
        border = border,
        enabled = enabled,
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != null) {
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = "Icon",
                    colorFilter = ColorFilter.tint(textColor)
                )
                Spacer(Modifier.width(16.dp))
            }
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = text,
                    color = textColor,
                    style = textStyle,
                    modifier = Modifier.alpha(if (loading) 0f else 1f)
                )
                if (loading) {
                    CircularProgressIndicator(color = textColor, modifier = Modifier.size(24.dp))
                }
            }
        }
    }
}

@Composable
fun SolidButton(
    text: String,
    modifier: Modifier,
    backgroundColor: Color = Color.Blue,
    textStyle: TextStyle = TextStyle.Default,
    textColor: Color = Color.White,
    enabled: Boolean = true,
    loading: Boolean = false,
    icon: Int? = null,
    onClick: () -> Unit
) {
    CustomButton(
        text = text,
        backgroundColor = backgroundColor,
        textStyle = textStyle,
        textColor = textColor,
        modifier = modifier,
        enabled = enabled,
        loading = loading,
        icon = icon,
        onClick = onClick
    )
}