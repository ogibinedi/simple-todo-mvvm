package com.obedigital.simpletodo.depinj

import android.app.Application
import androidx.room.Room
import com.obedigital.simpletodo.database.TaskDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
//import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
//@InstallIn(ApplicationComponent::class)
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(
        app: Application,
        callback: TaskDatabase.Callback
    ) = Room.databaseBuilder(app, TaskDatabase::class.java, "task_database")
            .fallbackToDestructiveMigration()
            .addCallback(callback)
            .build()
    @Provides
    fun provideTaskDao(db: TaskDatabase) = db.taskDao()

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())
}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope
