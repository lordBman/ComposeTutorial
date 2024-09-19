package com.bsoft.composetutorial

import android.content.res.Configuration
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bsoft.composetutorial.examples.Counter
import com.bsoft.composetutorial.examples.Lists
import com.bsoft.composetutorial.examples.Signin
import com.bsoft.composetutorial.ui.theme.ComposeTutorialTheme

enum class ComposeTutorialScreen(val title: String) {
    Start(title = "Tutorials"),
    Counter(title = "Counter"),
    SignIn(title = "Login Page")
}


@Composable
fun ComposeTutorialApp(){
    val navController = rememberNavController();

    NavHost(navController = navController, startDestination = ComposeTutorialScreen.Counter.title , builder = {
        composable(route = ComposeTutorialScreen.Start.title) {
            Lists()
        }
        composable(route = ComposeTutorialScreen.Counter.title) {
            Counter()
        }
        composable(route = ComposeTutorialScreen.SignIn.title) {
            Signin()
        }
    } )
}

@Preview(showBackground = false, name = "Light Mode Preview", device = "id:pixel_4")
@Preview(showBackground = false, name = "Dark Mode Preview ", uiMode = Configuration.UI_MODE_NIGHT_YES, device = "id:pixel_4")
@Composable
fun ComposeTutorialAppPreview() {
    ComposeTutorialTheme {
        Surface {
            ComposeTutorialApp()
        }
    }
}