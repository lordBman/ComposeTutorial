package com.bsoft.composetutorial.examples

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bsoft.composetutorial.ui.theme.ComposeTutorialTheme

enum class PageRoute(val title: String, val icon: ImageVector){
    Home("Home", icon = Icons.Outlined.Home),
    Chat("Chat", icon = Icons.Filled.MailOutline),
    Settings("Settings", icon = Icons.Outlined.Settings)
}

@Composable
fun CustomBottomBar(navController: NavHostController){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: return

    val colors = NavigationBarItemDefaults.colors(
        selectedIconColor = MaterialTheme.colorScheme.primary,
        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
        selectedTextColor = MaterialTheme.colorScheme.primary,
        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
    )

    NavigationBar{
        PageRoute.entries.forEach{
            NavigationBarItem(selected = currentRoute == it.title,
                onClick = { navController.navigate(it.title) },
                icon = { Icon(imageVector = it.icon, contentDescription = "") },
                label = { Text(text = it.title) },
                colors = colors
            )
        }
    }
}

@Composable
fun BottomNavigationExample(){
    val navController = rememberNavController()

    Scaffold( bottomBar =  { CustomBottomBar(navController = navController) }) {
        NavHost(navController = navController, modifier = Modifier.padding(it) , startDestination = PageRoute.Home.title, builder = {
            composable(route = PageRoute.Home.title) {
                Page(message = PageRoute.Home.title)
            }
            composable(route = PageRoute.Chat.title) {
                Page(message = PageRoute.Chat.title)
            }
            composable(route = PageRoute.Settings.title) {
                Page(message = PageRoute.Settings.title)
            }
        })
    }
}

@Preview(showBackground = false, name = "Light Mode Preview", device = "id:pixel_4", apiLevel = 33)
@Preview(showBackground = false, name = "Dark Mode Preview ", uiMode = Configuration.UI_MODE_NIGHT_YES, device = "id:pixel_4",
    apiLevel = 33
)
@Composable
fun BottomNavigationExamplePreview() {
    ComposeTutorialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            BottomNavigationExample()
        }
    }
}