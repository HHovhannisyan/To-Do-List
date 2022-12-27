package com.example.todolist.ui.adapter

import android.graphics.Color
import android.graphics.Paint
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import com.example.todolist.data.source.local.entity.Todo
import com.example.todolist.ui.fragments.HomeFragmentDirections
import com.example.todolist.viewmodel.TodoViewModel
import com.squareup.picasso.Picasso

@BindingAdapter(value = ["todo", "vm"])
fun isChecking(checkBox: CheckBox, todo: Todo, viewModel: TodoViewModel) {
    checkBox.setOnCheckedChangeListener(null)
    checkBox.isChecked = todo.checked

    checkBox.setOnCheckedChangeListener { _, isChecked ->
        if (isChecked) {
            viewModel.updateTodo(
                todo.id,
                todo.title,
                todo.description,
                true,
                todo.image, todo.date
            )
        } else {
            viewModel.updateTodo(
                todo.id,
                todo.title,
                todo.description,
                false,
                todo.image, todo.date
            )
        }
    }
}

@BindingAdapter("strikeThrough")
fun isStriked(textView: TextView, isCheck: Boolean) {
    if (isCheck) {
        textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        textView.setTextColor(Color.RED)
    } else {
        textView.paintFlags =
            textView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        textView.setTextColor(Color.BLACK)
    }
}

@BindingAdapter("changeColor")
fun changeBackgroundColor(relativeLayout: RelativeLayout, isCheck: Boolean) {
    if (isCheck) {
        relativeLayout.setBackgroundColor(Color.YELLOW)
    } else {
        relativeLayout.setBackgroundColor(Color.TRANSPARENT)
    }
}


@BindingAdapter("android:imageUrl")
fun getImg(imageView: ImageView, todo: Todo) {
    Picasso.get()
        .load(todo.image)
        .into(imageView)
}

@BindingAdapter("android:date")
fun getDate(textView: TextView, todo: Todo) {
    textView.text = todo.date
}


@BindingAdapter("android:isDeleteVisible")
fun deleteIcon(imageView: ImageView, isCheck: Boolean) {
    if (isCheck) {
        imageView.visibility = View.VISIBLE
    } else {
        imageView.visibility = View.INVISIBLE
    }
}

@BindingAdapter("android:delete")
fun delete(imageView: ImageView, viewModel: TodoViewModel) {
    imageView.setOnClickListener {
        viewModel.deleteSelected()
    }
}

@BindingAdapter("android:goToEdit")
fun goToViewFragment(imageView: ImageView, todo: Todo) {
    imageView.setOnClickListener { view ->
        view.findNavController()
            .navigate(HomeFragmentDirections.actionHomeFragmentToEditFragment(todo))
    }
}