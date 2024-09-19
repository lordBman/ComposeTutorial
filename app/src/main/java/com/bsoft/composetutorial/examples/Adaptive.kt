package com.bsoft.composetutorial.examples

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Email
import androidx.compose.material.icons.twotone.Home
import androidx.compose.material.icons.twotone.Search
import androidx.compose.material.icons.twotone.Settings
import androidx.compose.material.icons.twotone.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.bsoft.composetutorial.ui.theme.ComposeTutorialTheme

enum class AdaptiveDestinations(val title: String, val icon: ImageVector){
    Home(title = "Home", icon = Icons.TwoTone.Home),
    Message(title = "Messages", icon = Icons.TwoTone.Email),
    Explore(title = "Explore", icon = Icons.TwoTone.Search),
    Cart(title = "Cart", icon = Icons.TwoTone.ShoppingCart),
    Settings(title = "Profile", Icons.TwoTone.Settings)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Adaptive(){
    var currentDestination by rememberSaveable { mutableStateOf(AdaptiveDestinations.Home) }

    val colors = NavigationSuiteDefaults.itemColors(
        navigationBarItemColors = NavigationBarItemDefaults.colors(
            selectedIconColor = MaterialTheme.colorScheme.primary,
            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            selectedTextColor = MaterialTheme.colorScheme.primary,
            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    )

    /*val adaptiveInfo = currentWindowAdaptiveInfo()
    val customNavSuiteType = with(adaptiveInfo) {
        if (windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.MEDIUM || windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.EXPANDED) {
            NavigationSuiteType.NavigationDrawer
        } else {
            NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(adaptiveInfo)
        }
    }*/


    NavigationSuiteScaffold(
        navigationSuiteItems = {
            AdaptiveDestinations.entries.forEach {
                item(onClick = { currentDestination = it },
                    icon = { Icon(imageVector = it.icon, contentDescription = "") },
                    label = { Text(text = it.title) },
                    colors = colors,
                    selected = it.title == currentDestination.title)
            }
        }
    ) {
        Scaffold (
            topBar = {
                TopAppBar( title = { Text(currentDestination.title, color = MaterialTheme.colorScheme.onPrimary) }, colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary))
            }
        ){
            Surface(modifier = Modifier.padding(paddingValues = it)) {
                when(currentDestination){
                    AdaptiveDestinations.Home -> Page(message = "Home Page")
                    AdaptiveDestinations.Cart -> Page(message = "Cart")
                    AdaptiveDestinations.Explore -> Page(message = "Explore")
                    AdaptiveDestinations.Message -> Page(message = "Messages")
                    AdaptiveDestinations.Settings -> Page(message = "Settings")
                }
            }
        }
    }
}

@Preview(showBackground = false, name = "Light Mode Preview", device = "id:pixel_4", apiLevel = 33)
@Preview(showBackground = false, name = "Dark Mode Preview ", uiMode = Configuration.UI_MODE_NIGHT_YES, device = "id:pixel_4", apiLevel = 33)
@Preview(showBackground = false, name = "Light Mode Preview",
    device = "id:Nexus 10", apiLevel = 33,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_DESK
)
@Composable
fun AdaptivePreview() {
    ComposeTutorialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Adaptive()
        }
    }
}