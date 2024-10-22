package dev.azeredo.di.modules

import ProductRepository
import dev.azeredo.data.AppDatabase
import dev.azeredo.data.category.CategoryDao
import dev.azeredo.data.category.CategoryRepositoryImpl
import dev.azeredo.data.product.ProductDao
import dev.azeredo.data.product.ProductRepositoryImpl
import dev.azeredo.data.stockmovement.StockMovementDao
import dev.azeredo.data.stockmovement.StockMovementRepositoryImpl
import dev.azeredo.domain.repository.CategoryRepository
import dev.azeredo.domain.repository.StockMovementRepository
import org.koin.dsl.module

val RepositoryModule = module {
    single<CategoryRepository> { CategoryRepositoryImpl(get()) }
    single<ProductRepository> { ProductRepositoryImpl(get(),get()) }
    single<StockMovementRepository> { StockMovementRepositoryImpl(get()) }

}

val DaoModule = module {
    single<CategoryDao> { get<AppDatabase>().getCategoryDao() }
    single<ProductDao> { get<AppDatabase>().getProductDao() }
    single<StockMovementDao> { get<AppDatabase>().getStockMovementDao() }
}