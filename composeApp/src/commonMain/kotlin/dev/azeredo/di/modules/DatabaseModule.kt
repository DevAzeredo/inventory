package dev.azeredo.di.modules

import ProductRepository
import dev.azeredo.data.AppDatabase
import dev.azeredo.data.ProductDao
import dev.azeredo.data.ProductRepositoryImpl
import dev.azeredo.data.getRoomDatabase
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val RepositoryModule = module {

}