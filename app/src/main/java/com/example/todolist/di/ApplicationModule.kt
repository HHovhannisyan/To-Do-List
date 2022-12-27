package com.example.todolist.di

import android.content.Context
import com.example.todolist.data.repository.TodoRepository
import com.example.todolist.data.source.local.LocalDataSource
import com.example.todolist.data.source.local.room.TodoDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object ApplicationModule {

    @Provides
    fun provideRepository(@ApplicationContext context: Context): TodoRepository {
        val database = TodoDb.getInstance(context)
        val localDataSource = LocalDataSource.getInstance(database.todoDAO())
        return TodoRepository.getInstance(localDataSource)
    }
}