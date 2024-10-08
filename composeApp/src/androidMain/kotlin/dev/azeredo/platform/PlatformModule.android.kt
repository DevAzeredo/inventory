package dev.azeredo.platform

import androidx.room.RoomDatabase
import data.getDatabaseBuilder
import dev.azeredo.data.AppDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule() = module {
    single<RoomDatabase.Builder<AppDatabase>> { getDatabaseBuilder(get()) }
}