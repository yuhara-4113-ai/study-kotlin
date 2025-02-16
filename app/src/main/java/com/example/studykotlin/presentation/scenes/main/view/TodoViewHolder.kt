package com.example.studykotlin.presentation.scenes.main.view

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.studykotlin.R
import com.example.studykotlin.presentation.scenes.main.entity.Todo

class TodoViewHolder(
    view: View,
    private val onCheckedChanged: (Todo, Boolean) -> Unit
) : RecyclerView.ViewHolder(view) {

    private val titleTextView: TextView = view.findViewById(R.id.todoTextView)
    private val checkBox: CheckBox = view.findViewById(R.id.todoCheckBox)
    private val itemGroup: ConstraintLayout = view.findViewById(R.id.todoItemGroup)

    fun bind(todo: Todo) {
        titleTextView.text = todo.title + "：" + todo.description
        checkBox.isChecked = todo.isChecked

        itemGroup.setOnClickListener {
            checkBox.isChecked = !checkBox.isChecked
            onCheckedChanged(todo, checkBox.isChecked)
        }

        checkBox.setOnCheckedChangeListener { _, isChecked ->
            onCheckedChanged(todo, isChecked)
        }
    }
}