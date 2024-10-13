package dev.azeredo.di

import dev.azeredo.domain.usecase.product.AddProduct
import dev.azeredo.domain.usecase.product.getAllProducts
import dev.azeredo.data.AppDatabase
import dev.azeredo.data.getRoomDatabase
import dev.azeredo.di.modules.DaoModule
import dev.azeredo.di.modules.RepositoryModule
import dev.azeredo.domain.usecase.category.AddCategory
import dev.azeredo.domain.usecase.category.GetAllCategories
import dev.azeredo.platform.platformModule
import dev.azeredo.presentation.addproduct.AddProductViewModel
import dev.azeredo.presentation.productlist.ProductListViewModel
import dev.azeredo.presentation.inbound.InboundViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import dev.azeredo.domain.usecase.product.removeProduct
import dev.azeredo.domain.usecase.product.updateProduct

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
}

val domainModule = module {
    // product
    factory { getAllProducts(get()) }
    factory { AddProduct(get()) }
    factory { removeProduct(get()) }
    factory { updateProduct(get()) }
    // category
    factory { AddCategory(get()) }
    factory { GetAllCategories(get()) }
}