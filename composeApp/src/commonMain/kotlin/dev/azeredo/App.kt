package dev.azeredo

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.azeredo.presentation.addproduct.AddProductScreen
import dev.azeredo.presentation.productlist.ProductListScreen
import org.koin.compose.KoinContext

@Composable
fun App() {
    MaterialTheme {
        KoinContext {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = "ProductListScreen"
            ) {
                composable("ProductListScreen") { ProductListScreen(navController) }
                composable("AddProductScreen") { AddProductScreen(navController) }
            }
        }
    }
}
