package com.example.todolist.data.repository

import androidx.lifecycle.LiveData
import com.example.todolist.data.source.local.LocalDataSource
import com.example.todolist.data.source.local.entity.Todo
import javax.inject.Inject

class TodoRepository @Inject constructor(private val localDataSource: LocalDataSource) :
    ITodoRepository {

    companion object {
        @Volatile
        private var instance: TodoRepository? = null

        fun getInstance(localData: LocalDataSource): TodoRepository =
            instance ?: synchronized(this) {
                instance ?: TodoRepository(localData)
            }
    }

    override fun getAllTodos(): LiveData<List<Todo>> {
        return localDataSource.getAllTodos()
    }

    override fun getAllCompleted(): LiveData<List<Todo>> {
        return localDataSource.getAllCompleted()
    }

    override suspend fun insert(todo: Todo) {
        localDataSource.insert(todo)
    }

    override suspend fun update(todo: Todo) {
        localDataSource.update(todo)
    }

    override suspend fun deleteSelectedTodos() {
        localDataSource.deleteSelectedTodos()
    }
}