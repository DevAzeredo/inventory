package dev.azeredo.data

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import dev.azeredo.data.category.CategoryDao
import dev.azeredo.data.category.CategoryEntity
import dev.azeredo.data.category.ProductImageEntity
import dev.azeredo.data.product.ProductDao
import dev.azeredo.data.product.ProductEntity
import dev.azeredo.data.productimage.ProductImageDao
import dev.azeredo.data.stockmovement.StockMovementDao
import dev.azeredo.data.stockmovement.StockMovementEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

@Database(entities = [ProductEntity::class, CategoryEntity::class, StockMovementEntity::class, ProductImageEntity::class], version = 1)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getProductDao(): ProductDao
    abstract fun getProductImageDao(): ProductImageDao
    abstract fun getCategoryDao(): CategoryDao
    abstract fun getStockMovementDao(): StockMovementDao
}

// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}

fun getRoomDatabase(
    builder: RoomDatabase.Builder<AppDatabase>
): AppDatabase {
    return builder
//        .addMigrations(MIGRATIONS)
//        .fallbackToDestructiveMigrationOnDowngrade()
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}