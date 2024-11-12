package dev.azeredo.di

import dev.azeredo.domain.usecase.product.AddProduct
import dev.azeredo.domain.usecase.product.getAllProducts
import dev.azeredo.data.AppDatabase
import dev.azeredo.data.getRoomDatabase
import dev.azeredo.di.modules.DaoModule
import dev.azeredo.di.modules.RepositoryModule
import dev.azeredo.domain.usecase.category.AddCategory
import dev.azeredo.domain.usecase.category.AddProductImageUseCase
import dev.azeredo.domain.usecase.category.GetAllCategories
import dev.azeredo.domain.usecase.product.GetProductById
import dev.azeredo.platform.platformModule
import dev.azeredo.presentation.addproduct.AddProductViewModel
import dev.azeredo.presentation.productlist.ProductListViewModel
import dev.azeredo.presentation.inbound.InboundViewModel
import dev.azeredo.presentation.outbound.OutboundViewModel
import dev.azeredo.presentation.stockmovement.StockMovementViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import dev.azeredo.domain.usecase.product.removeProduct
import dev.azeredo.domain.usecase.product.UpdateProduct
import dev.azeredo.domain.usecase.stockmovement.SaveMovements
import dev.azeredo.domain.usecase.stockmovement.SaveStockMovements
import dev.azeredo.domain.usecase.productimage.RemoveProductImageUseCase

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        modules(
            domainModule,
            DaoModule,
            RepositoryModule,
            appModule,
            platformModule()
        )
        config?.invoke(this)
    }
}

val appModule = module {
    single<AppDatabase> {
        getRoomDatabase(get())
    }
    viewModelOf(::ProductListViewModel)
    viewModelOf(::AddProductViewModel)
    viewModelOf(::InboundViewModel)
    viewModelOf(::OutboundViewModel)
    viewModelOf(::StockMovementViewModel)
}

val domainModule = module {
    // product
    factory { getAllProducts(get()) }
    factory { AddProduct(get()) }
    factory { removeProduct(get()) }
    factory { UpdateProduct(get()) }
    factory { GetProductById(get()) }
    // product image
    factory { RemoveProductImageUseCase(get()) }
    factory { AddProductImageUseCase(get()) }
    // category
    factory { AddCategory(get()) }
    factory { GetAllCategories(get()) }
    // stockMovement
    factory { SaveStockMovements(get()) }
    factory { SaveMovements(get()) }
}