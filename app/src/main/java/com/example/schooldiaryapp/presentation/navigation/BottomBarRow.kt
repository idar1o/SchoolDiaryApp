package com.example.schooldiaryapp.presentation.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState


@Composable
fun BottomBarRow(
    navHostController: NavHostController
) {

    val tabList = listOf(
        BottomBarRoutes.SCHEDULE,
        BottomBarRoutes.TASKS,
        BottomBarRoutes.GRADES,
        BottomBarRoutes.ANNOUNCEMENT
    )

    val navStackBackEntry by navHostController.currentBackStackEntryAsState()
    val currentDestination = navStackBackEntry?.destination


    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        tabList.forEach { tab ->
            BottomBarItems(
                tab = tab,
                currentDestination = currentDestination,
                navHostController = navHostController
            )
        }
    }

}

@Composable
fun BottomBarItems(
    tab: BottomBarRoutes,
    currentDestination: NavDestination?,
    navHostController: NavHostController
) {

    val selected = currentDestination?.hierarchy?.any { it.route == tab.routes } == true

    val contentColor =
        if (selected) androidx.compose.ui.graphics.Color.Cyan else MaterialTheme.colorScheme.onPrimary

    IconButton(onClick = {
        navHostController.navigate(tab.routes)
    }) {
//        Column {
            Icon(
                painter = painterResource(id = tab.icon),
                contentDescription = "",
                tint = contentColor,
                modifier = Modifier.size(30.dp)
            )
//            val title = stringResource(id = tab.title)
//            Text(
//                text = "$title"
//            )
//        }
    }


}