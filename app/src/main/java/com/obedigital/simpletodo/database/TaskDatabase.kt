package com.obedigital.simpletodo.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.obedigital.simpletodo.depinj.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun  taskDao(): TaskDao

    class Callback @Inject constructor(
        private val database: Provider<TaskDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            // db operation
            val dao = database.get().taskDao()

            applicationScope.launch {
                dao.insert(Task("Membuat Sarapan", completed = true))
                dao.insert(Task("Periksa Token Listrik", important = true, completed = true))
                dao.insert(Task("Tunggu Orderan Gojek", important = true, completed = true))
                dao.insert(Task("Belajar memulai project kotlin", important = true, completed = false))
                dao.insert(Task("Belajar mvvm kotlin", important = true, completed = false))
                dao.insert(Task("Belajar Navigation Component", important = true, completed = false))
                dao.insert(Task("Belajar Room Library", important = false, completed = false))
                dao.insert(Task("Belajar Dagger Hilt", important = false, completed = false))
                dao.insert(Task("Belajar Coroutine", important = false, completed = false))
            }
        }
    }
}