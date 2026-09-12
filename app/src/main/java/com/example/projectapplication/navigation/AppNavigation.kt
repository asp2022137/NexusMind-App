package com.example.projectapplication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projectapplication.ui.screens.*

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash" // App එක ආරම්භ වන්නේ Splash Screen එකෙන්
    ) {

        // 1. Splash Screen
        composable("splash") {
            SplashScreen(navController = navController)
        }

        // 2. Login Screen
        composable("login") {
            LoginScreen(navController = navController)
        }

        // 3. Register / Sign Up Screen
        composable("register") {
            RegisterScreen(navController = navController)
        }

        // 4. Forgot Password Screen
        composable("forgot_password") {
            ForgotPasswordScreen(navController = navController)
        }

        // 5. Student Home Dashboard
        composable("home") {
            HomeScreen(navController = navController)
        }

        // 6. AI Assessment / Prediction Screen
        composable("prediction") {
            PredictionScreen(navController = navController)
        }

        // 7. Mental Health Analysis Report Screen
        composable("report") {
            ReportScreen(navController = navController)
        }

        // 8. AI Recommendations Screen
        composable("recommendation") {
            RecommendationScreen(navController = navController)
        }

        // 9. Assessment History Screen
        composable("history") {
            HistoryScreen(navController = navController)
        }

        // 10. Student Profile Screen
        composable("profile") {
            ProfileScreen(navController = navController)
        }

        // 11. App Settings Screen
        composable("settings") {
            SettingsScreen(navController = navController)
        }

        // 12. Notifications Screen
        composable("notification") {
            NotificationScreen(navController = navController)
        }
    }
}