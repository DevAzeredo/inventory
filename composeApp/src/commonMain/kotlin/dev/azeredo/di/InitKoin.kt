package dev.azeredo.di

import addProduct
import getAllProducts
import dev.azeredo.data.AppDatabase
import dev.azeredo.data.getRoomDatabase
import dev.azeredo.di.modules.DaoModule
import dev.azeredo.di.modules.RepositoryModule
import dev.azeredo.platform.platformModule
import dev.azeredo.presentation.addproduct.AddProductViewModel
import dev.azeredo.presentation.productlist.ProductListViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import removeProduct
import updateProduct

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
}

val domainModule = module {
    factory { getAllProducts(get()) }
    factory { addProduct(get()) }
    factory { removeProduct(get()) }
    factory { updateProduct(get()) }
}