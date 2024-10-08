package dev.azeredo.platform

import androidx.room.RoomDatabase
import dev.azeredo.data.AppDatabase
import dev.azeredo.data.getDatabaseBuilder
import org.koin.dsl.module

actual fun platformModule() = module {
    single<RoomDatabase.Builder<AppDatabase>> { getDatabaseBuilder() }
}