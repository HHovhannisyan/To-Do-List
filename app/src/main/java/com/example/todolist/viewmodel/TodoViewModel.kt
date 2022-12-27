package com.example.todolist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.repository.TodoRepository
import com.example.todolist.data.source.local.entity.Todo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(private val repository: TodoRepository) : ViewModel() {

    fun getAllTodos(): LiveData<List<Todo>> = repository.getAllTodos()

    fun addTodo(title: String, desc: String, img: Int, date: String) {
        viewModelScope.launch {
            repository.insert(Todo(0, title, desc, false, img, date))
        }
    }

    fun updateTodo(id: Int, title: String, desc: String, checked: Boolean, img: Int, date: String) {
        viewModelScope.launch {
            repository.update(Todo(id, title, desc, checked, img, date))
        }
    }

    fun deleteSelected() {
        viewModelScope.launch {
            repository.deleteSelectedTodos()
        }
    }
}
