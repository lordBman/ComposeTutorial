package com.bsoft.composetutorial.examples

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.bsoft.composetutorial.R
import com.bsoft.composetutorial.ui.theme.ComposeTutorialTheme

@Composable
fun CustomUsernameInput(value: String, onChange: (String)-> Unit){
    OutlinedTextField(textStyle = TextStyle(fontSize = TextUnit(value = 14f, type = TextUnitType.Sp)),
        modifier = Modifier.fillMaxWidth(),
        leadingIcon = {
                 Icon(imageVector = ImageVector.vectorResource(id = R.drawable.radix_icons__person), contentDescription = "")
        },
        shape = RoundedCornerShape(8.dp),
        singleLine = true, value = value, label = { Text(text = "Username", fontSize = TextUnit(value = 14f, type = TextUnitType.Sp)) }, onValueChange = onChange)
}

@Composable
fun CustomPasswordInput(value: String, onChange: (String)-> Unit){
    var show by remember {  mutableStateOf(false) }
    OutlinedTextField(textStyle = TextStyle(fontSize = TextUnit(value = 14f, type = TextUnitType.Sp)),
        modifier = Modifier.fillMaxWidth(),
        visualTransformation = if(show){ VisualTransformation.None } else { PasswordVisualTransformation() },
        leadingIcon = {
            Icon(imageVector = ImageVector.vectorResource(id = R.drawable.ri__lock_2_line), contentDescription = "")
        },
        trailingIcon = {
            Icon(modifier = Modifier.clickable(onClick = { show = !show }), imageVector = ImageVector.vectorResource(id = if (show) { R.drawable.streamline__visible } else { R.drawable.streamline__invisible_2 }), contentDescription = "")
        },
        shape = RoundedCornerShape(8.dp),
        singleLine = true, value = value, label = { Text(text = "Password", fontSize = TextUnit(value = 14f, type = TextUnitType.Sp)) }, onValueChange = onChange)
}

@Composable
fun CustomButton(){
    Button(shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp), onClick = { /*TODO*/ }) {
        Text(text = "Submit", fontWeight = FontWeight.ExtraLight, fontSize = TextUnit(value = 16f, type = TextUnitType.Sp))
    }
}

data class SignInRequest( val username: String, val password: String );

@Composable
fun Signin(){
    var request by remember {
        mutableStateOf(SignInRequest(username = "", password = ""))
    }
    Box(contentAlignment = Alignment.Center) {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom) {
            Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp)) {
                Text(text = "Sign In", fontWeight = FontWeight.ExtraLight, fontSize = TextUnit(value = 32f, type = TextUnitType.Sp))
                CustomUsernameInput(value = request.username, onChange = { request = request.copy(username = it) } )
                Spacer(modifier = Modifier.height(10.dp))
                CustomPasswordInput(value = request.password, onChange = { request = request.copy(password = it) } )
                Spacer(modifier = Modifier.height(4.dp))
                Row {
                    Text(text = "Forgotten password ? ", fontSize= TextUnit(value=12f, type= TextUnitType.Sp))
                    Text(text = "Click here", fontSize= TextUnit(value=12f, type= TextUnitType.Sp), color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(10.dp))
                CustomButton()
                Spacer(modifier = Modifier.height(4.dp))
                Row {
                    Text(text = "Don't have an account ? ", fontSize= TextUnit(value=12f, type= TextUnitType.Sp))
                    Text(text = "Register", fontSize= TextUnit(value=12f, type= TextUnitType.Sp), color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                }
            }
        }
        Text(text = "Bsoft", fontWeight = FontWeight.ExtraBold, fontFamily = FontFamily.Serif, color = MaterialTheme.colorScheme.primary, fontSize = TextUnit(value = 70f, type = TextUnitType.Sp))
    }
}

@Preview(showBackground = false, name = "Light Mode Preview", device = "id:pixel_4", apiLevel = 33)
@Preview(showBackground = false, name = "Dark Mode Preview ", uiMode = Configuration.UI_MODE_NIGHT_YES, device = "id:pixel_4",
    apiLevel = 33
)
@Composable
fun SigninPreview() {
    ComposeTutorialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Signin()
        }
    }
}