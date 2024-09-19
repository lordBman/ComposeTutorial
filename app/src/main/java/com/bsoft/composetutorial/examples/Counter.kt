package com.bsoft.composetutorial.examples

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.bsoft.composetutorial.ui.theme.ComposeTutorialTheme

@Composable
fun Counter() {
    var count by remember { mutableIntStateOf(0) }
    Column(
        modifier= Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Text(text = count.toString(),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontFamily = FontFamily.Monospace,
            fontSize = TextUnit(value = 150f, type = TextUnitType.Sp)
        )
        Column {
            Text(text = "Hello Composer!",
                color = Color(color = 0xff2376ff),
                fontSize = TextUnit(value = 30f, type = TextUnitType.Sp),
                fontWeight = FontWeight(weight = 300),
                letterSpacing = TextUnit(value = 1.8f, type = TextUnitType.Sp),
            )
            Text(text = "This is my very first compose",
                color = Color.Gray,
                fontSize = TextUnit(value = 12f, type = TextUnitType.Sp),
                fontWeight = FontWeight(weight = 300),
                letterSpacing = TextUnit(value = 1.2f, type = TextUnitType.Sp),
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        CustomButton(label = "click me", onClick = { count += 1 })
    }
}

@Preview(showBackground = false, name = "Light Mode Preview", device = "id:pixel_4")
@Preview(showBackground = false, name = "Dark Mode Preview ", uiMode = Configuration.UI_MODE_NIGHT_YES, device = "id:pixel_4")
@Composable
fun GreetingPreview() {
    ComposeTutorialTheme {
        Surface {
            Counter()
        }
    }
}

@Composable
fun CustomButton(label: String, onClick: ()->Unit){
    Button(shape = RoundedCornerShape(size = 8.dp),
        border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.primary),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent, contentColor = MaterialTheme.colorScheme.primary),
        onClick = onClick) {
        Text(text = label, fontWeight = FontWeight(600))
    }
}