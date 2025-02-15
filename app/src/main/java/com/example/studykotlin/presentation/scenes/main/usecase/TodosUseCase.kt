package com.example.studykotlin.presentation.scenes.main.usecase

import com.example.studykotlin.presentation.scenes.main.entity.Todo

// TODOリストを管理するユースケース
class TodosUseCase {
    // サンプルデータを内部に保持するリスト。とりあえず初期値は固定値（実際はDBやリモートと連携することを想定）
    private val todos = mutableListOf(
        Todo(1, "Sample Todo 1", "This is a sample todo item."),
        Todo(2, "Sample Todo 2", "This is another sample todo item.")
    )

    fun load(): List<Todo> = todos

    fun add(todo: Todo): List<Todo> {
        todos.add(todo)
        return todos
    }
}