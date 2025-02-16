package com.example.studykotlin.presentation.scenes.main.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.studykotlin.R
import com.example.studykotlin.presentation.scenes.main.entity.Todo

class TodoAdapter(
    private val todos: List<Todo>,
    private val onTodoCheckedChanged: (Todo, Boolean) -> Unit
) : RecyclerView.Adapter<TodoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_todo, parent, false)
        return TodoViewHolder(view) { todo, isChecked ->
            onTodoCheckedChanged(todo, isChecked)
        }
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(todos[position])
    }

    override fun getItemCount(): Int = todos.size
}