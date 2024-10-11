package dev.azeredo.di.modules

import ProductRepository
import dev.azeredo.data.AppDatabase
import dev.azeredo.data.category.CategoryDao
import dev.azeredo.data.category.CategoryRepositoryImpl
import dev.azeredo.data.product.ProductDao
import dev.azeredo.data.product.ProductRepositoryImpl
import dev.azeredo.domain.repository.CategoryRepository
import org.koin.dsl.module

val RepositoryModule = module {
    single<CategoryRepository> { CategoryRepositoryImpl(get()) }
    single<ProductRepository> { ProductRepositoryImpl(get(),get()) }
}

val DaoModule = module {
    single<CategoryDao> { get<AppDatabase>().getCategoryDao() }
    single<ProductDao> { get<AppDatabase>().getProductDao() }
}