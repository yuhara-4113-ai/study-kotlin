package com.example.studykotlin.presentation.scenes.main.entity

// TODOデータモデルを定義
data class Todo(
    val id: Int,
    val title: String,
    val description: String,
    var isChecked: Boolean
)