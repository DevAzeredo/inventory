package dev.azeredo

import ProductRepository
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import dev.azeredo.presentation.tst.ProductScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext

@Composable
fun App() {
    MaterialTheme {
        KoinContext {
            ProductScreen()
        }
    }
}
