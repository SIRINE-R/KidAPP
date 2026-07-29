package com.example.kidapp.presentation.screens.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.kidapp.presentation.screens.signup.SignUpScreen
import com.example.kidapp.ui.theme.KidAppTheme
import com.example.kidapp.util.Routes
import com.example.myapplication.kid_activity.presentation.screens.AddProfileScreen
import com.example.myapplication.kid_activity.presentation.screens.KidActivityScreen
import com.example.myapplication.kid_activity.presentation.screens.KidHomeScreen
import com.example.myapplication.kid_activity.presentation.screens.KidSplashScreen
import com.example.myapplication.kid_activity.presentation.screens.LoginScreen
import com.example.myapplication.kid_activity.presentation.screens.ProfilesScreen
import com.example.myapplication.kid_activity.presentation.screens.SearchScreen
import com.example.kidapp.presentation.screens.settings.SettingsScreen
import com.example.myapplication.kid_activity.presentation.screens.VerificationScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Log.d("KidApp_Debug", "MainActivity: onCreate started")
        setContent {
            Log.d("KidApp_Debug", "MainActivity: setContent composing")
            KidAppTheme {
                val navigation = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        navController = navigation,
                        startDestination = Routes.SPLASH.name
                    ) {
                        composable(Routes.SPLASH.name) {
                            KidSplashScreen(navigation)
                        }
                        composable(
                            route = Routes.PROFILES.name + "/{email}/{parent_id}",
                            arguments = listOf(
                                navArgument("email") { type = NavType.StringType },
                                navArgument("parent_id") { type = NavType.IntType }
                            )
                        ) {
                            val email = it.arguments?.getString("email")
                            val parentId = it.arguments?.getInt("parent_id")
                            ProfilesScreen(navigation, email = email, id = parentId)
                        }
                        composable(Routes.SIGNUP.name) {
                            SignUpScreen(navigation)
                        }
                        composable(Routes.LOGIN.name) {
                            LoginScreen(navigation)
                        }
                        composable(
                            route = Routes.ADDING.name + "/{email}/{parent_id}",
                            arguments = listOf(
                                navArgument("email") { type = NavType.StringType },
                                navArgument("parent_id") { type = NavType.IntType }
                            )
                        ) {
                            val email = it.arguments?.getString("email")
                            val parentId = it.arguments?.getInt("parent_id")
                            AddProfileScreen(navigation, email = email, id = parentId)
                        }
                        composable(
                            route = Routes.VERIFYING.name + "/{email}/{parent_id}",
                            arguments = listOf(
                                navArgument("email") { type = NavType.StringType },
                                navArgument("parent_id") { type = NavType.IntType }
                            )
                        ) {
                            val email = it.arguments?.getString("email")
                            val parentId = it.arguments?.getInt("parent_id")
                            VerificationScreen(navigation, email = email, id = parentId)
                        }
                        composable(
                            route = Routes.SETTINGS.name + "/{email}/{parent_id}",
                            arguments = listOf(
                                navArgument("email") { type = NavType.StringType },
                                navArgument("parent_id") { type = NavType.IntType }
                            )
                        ) {
                            val email = it.arguments?.getString("email")
                            val parentId = it.arguments?.getInt("parent_id")
                            SettingsScreen(navigation, email = email, id = parentId)
                        }
                        composable(
                            route = Routes.ACTIVITY.name + "/{email}/{parent_id}",
                            arguments = listOf(
                                navArgument("email") { type = NavType.StringType },
                                navArgument("parent_id") { type = NavType.IntType }
                            )
                        ) {
                            val email = it.arguments?.getString("email")
                            val parentId = it.arguments?.getInt("parent_id")
                            KidActivityScreen(navigation, email = email, id = parentId)
                        }
                        composable(Routes.KIDHOME.name + "/{icon_profile}" ,
                            arguments = listOf(
                                navArgument("icon_profile") { type = NavType.StringType }
                            )) {
                            val iconProfile = it.arguments?.getString("icon_profile")
                            KidHomeScreen(
                                navController = navigation,
                                iconProfile = iconProfile
                            )
                        }
                        composable(Routes.SEARCH.name + "/{icon_profile}",
                            arguments = listOf(
                                navArgument("icon_profile") { type = NavType.StringType }
                            )) {
                            val iconProfile = it.arguments?.getString("icon_profile")
                            SearchScreen(navigation, iconProfile = iconProfile )
                        }

                    }
                }
            }
        }
    }
}