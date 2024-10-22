package dev.azeredo.data

// nao vejo problema de manter a interface e classe juntas nesse caso
interface DatabaseUtilsRepository {
    suspend fun getCurrentTimestamp(): Long
}

class DatabaseUtilsRepositoryImpl(
    private val dao: DatabaseUtilsDao
) : DatabaseUtilsRepository {

    override suspend fun getCurrentTimestamp(): Long {
        val timestampString = dao.getCurrentTimestamp()
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            .parse(timestampString)?.time ?: 0L
    }
}