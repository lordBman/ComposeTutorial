package com.bsoft.composetutorial.examples

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.bsoft.composetutorial.R
import com.bsoft.composetutorial.ui.theme.ComposeTutorialTheme
import java.util.Calendar

open class Message(val message: String, val sender: String? = null, val time: Calendar = Calendar.getInstance(), val mine: Boolean = true)

class Mine(message: String) : Message(message = message)

@Composable
fun ColumnScope.MinView(mine: Mine){
    Column(modifier = Modifier
        .align(alignment = Alignment.End)
        .padding(end = 8.dp, start = 100.dp)) {
        Text(text = "Me", fontSize = TextUnit(12f, TextUnitType.Sp), fontWeight = FontWeight.Bold, modifier = Modifier.align(alignment = Alignment.End))
        Surface(color = MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(topStart = 12.dp, bottomEnd = 12.dp, bottomStart = 12.dp)) {
            Text(modifier = Modifier.padding(10.dp), text = mine.message, fontSize = TextUnit(15f, TextUnitType.Sp), color = MaterialTheme.colorScheme.onPrimary)
        }
        Text(text = "${mine.time.get(Calendar.HOUR_OF_DAY)}:${mine.time.get(Calendar.MINUTE)}", fontSize = TextUnit(12f, TextUnitType.Sp))
    }
}

class Received(sender: String, message: String, time: Calendar) : Message(message = message, sender = sender, time = time, mine = false)

@Composable
fun ColumnScope.ReceivedView(received: Received){
    Column(modifier = Modifier
        .align(alignment = Alignment.Start)
        .padding(start = 8.dp, end = 100.dp)) {
        received.sender?.let { Text(text = it, fontSize = TextUnit(12f, TextUnitType.Sp)) }
        Surface(border = BorderStroke(width = 1.4.dp, color = MaterialTheme.colorScheme.primary), shape = RoundedCornerShape(topEnd = 10.dp, bottomEnd = 10.dp, bottomStart = 10.dp)) {
            Text( modifier = Modifier.padding(10.dp), text = received.message, fontSize = TextUnit(15f, TextUnitType.Sp), color = MaterialTheme.colorScheme.primary)
        }
        Text(text = "${received.time.get(Calendar.HOUR_OF_DAY)}:${received.time.get(Calendar.MINUTE)}", fontSize = TextUnit(12f, TextUnitType.Sp), modifier = Modifier.align(alignment = Alignment.End))
    }
}

@Composable
fun MessageContainer(modifier: Modifier = Modifier, messages: List<Message>){
    Column(modifier = modifier.fillMaxWidth()){
        messages.map {
            if(it.mine){
                MinView(mine = (it as Mine))
            }else{
                ReceivedView(received = (it as Received))
            }

            if(messages.last() != it){
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}

@Composable
fun ChatInput(modifier: Modifier = Modifier, value: String, onChange: (String)-> Unit){
    val textStyle = TextStyle(fontWeight = FontWeight.Light, fontFamily = FontFamily.Serif, letterSpacing = TextUnit(1.2f, TextUnitType.Sp), fontSize = TextUnit(value = 16f, type = TextUnitType.Sp))

    OutlinedTextField( textStyle = textStyle, modifier = modifier,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent, focusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(0.dp),
        singleLine = true, value = value, placeholder = { Text(text = "Type your message..", style = textStyle) }, onValueChange = onChange)
}

@Composable
fun TopBanner(name: String, modifier: Modifier = Modifier){
    val initModifier = modifier
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.primary)
        .padding(horizontal = 12.dp, vertical = 4.dp)

    Box(contentAlignment =  Alignment.BottomStart, modifier = initModifier) {
        Text(name, color = MaterialTheme.colorScheme.onPrimary, fontSize = TextUnit(18f, TextUnitType.Sp))
    }
}

class SendWorker(context: Context, parameters: WorkerParameters): Worker(context, parameters){
    override fun doWork(): Result {
        val message: String = this.inputData.getString("message")!!

        val dataBuilder = Data.Builder().also {
            it.putString("message", message)
        }

        return Result.success(dataBuilder.build())
    }
}

@Composable
fun Chat(){
    var value by remember {  mutableStateOf("") }
    var messages by remember{ mutableStateOf(listOf(
        Mine("Hello, there"),
        Received(message = "I am fine how have you been ?", sender = "Blessing", time = Calendar.getInstance())
    ))}

    val context = LocalContext.current
    val manager = WorkManager.getInstance(context)

    fun send(){
        val request = OneTimeWorkRequestBuilder<SendWorker>().setInputData(Data.Builder().putString("message", value).build()).build();
        manager.getWorkInfoByIdLiveData(request.id).observeForever {
            info -> run {
                if (info.state == WorkInfo.State.SUCCEEDED) {
                    val init = messages.toMutableList();

                    init.add(Mine(message = info.outputData.getString("message")!!));
                    messages = init;
                }
            }
        }
        value = "";
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(contentAlignment = Alignment.CenterEnd, modifier = Modifier.height(140.dp)){
            Column(modifier = Modifier.fillMaxWidth()){
                TopBanner(name = "Nobel Okelekele", modifier = Modifier.weight(weight = 1f))
                Surface(modifier = Modifier.weight(weight = 1f)) {}
            }
            Surface(modifier = Modifier.padding(12.dp), color = Color.White, shape = CircleShape, border = BorderStroke(2.dp, color = MaterialTheme.colorScheme.primary)){
                Image(modifier = Modifier.size(80.dp), colorFilter = ColorFilter.tint(color = Color.Gray), imageVector = ImageVector.vectorResource(R.drawable.radix_icons__person), contentDescription = "" )
            }
        }
        MessageContainer(modifier = Modifier.weight(weight = 1f), messages = messages)
        Surface(color = MaterialTheme.colorScheme.surfaceContainer) {
            Surface(color = MaterialTheme.colorScheme.surface, modifier = Modifier.padding(8.dp), shape = CircleShape){
                Row( verticalAlignment = Alignment.CenterVertically ) {
                    ChatInput(value = value, modifier = Modifier.weight(weight = 1f), onChange = {value = it})
                    Button(onClick = { send() }, contentPadding = PaddingValues(0.dp),modifier = Modifier
                        .padding(2.dp)
                        .size(50.dp), shape = CircleShape) {
                        Icon(imageVector = ImageVector.vectorResource(id = R.drawable.send_2), modifier = Modifier.size(24.dp), contentDescription = "")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = false, name = "Light Mode Preview", device = "id:pixel_4", apiLevel = 33)
@Preview(showBackground = false, name = "Dark Mode Preview ", uiMode = Configuration.UI_MODE_NIGHT_YES, device = "id:pixel_4",
    apiLevel = 33
)
@Composable
fun ChatPreview() {
    WorkManager.initialize(LocalContext.current, androidx.work.Configuration.Builder().build())
    ComposeTutorialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Chat()
        }
    }
}