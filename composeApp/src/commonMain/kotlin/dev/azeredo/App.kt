package dev.azeredo

import Product
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dev.azeredo.domain.model.Category
import dev.azeredo.presentation.addproduct.AddProductScreen
import dev.azeredo.presentation.inbound.InboundScreen
import dev.azeredo.presentation.productlist.ProductListScreen
import kotlinx.serialization.json.Json
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
                composable(
                    "AddProductScreen/{product}",
                    arguments = listOf(navArgument("product") { type = NavType.StringType })
                ) { backStackEntry ->
                    val productJson = backStackEntry.arguments?.getString("product")
                    val product = productJson?.let {
                        Json.decodeFromString<Product>(it)
                    }
                    AddProductScreen(
                        navController,
                        product ?: Product(0, "", Category(0, ""), 0.0, 0.0, 0, 0)
                    )
                }
                composable("InboundScreen") { InboundScreen(navController) }
            }
        }
    }
}
