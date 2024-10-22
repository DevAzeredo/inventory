package dev.azeredo

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

import cafe.adriel.voyager.navigator.Navigator
import dev.azeredo.presentation.productlist.ProductListScreen

@Composable
fun App() {
    MaterialTheme {
        Navigator(ProductListScreen())
    }
}
