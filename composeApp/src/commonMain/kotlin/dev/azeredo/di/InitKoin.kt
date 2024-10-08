package dev.azeredo.di

import ProductRepository
import dev.azeredo.data.AppDatabase
import dev.azeredo.data.ProductDao
import dev.azeredo.data.ProductRepositoryImpl
import dev.azeredo.data.getRoomDatabase
import dev.azeredo.di.modules.RepositoryModule
import dev.azeredo.platform.platformModule
import dev.azeredo.presentation.tst.ProductViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        modules(
            RepositoryModule,
            appModule,
            platformModule()
        )
        config?.invoke(this)
    }
}

val appModule = module {
    single { "TESTANDO O KOIn" }

    single<AppDatabase> {
        getRoomDatabase(get())
    }
    single<ProductDao> { get<AppDatabase>().getProductDao() }
    single<ProductRepository> { ProductRepositoryImpl(get()) }
    viewModelOf(::ProductViewModel)
}