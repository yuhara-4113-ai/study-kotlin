package com.example.studykotlin.presentation.scenes.main.usecase

import com.example.studykotlin.presentation.scenes.main.entity.Todo

/**
 * TODOリストのビジネスロジックを担当するUseCaseクラス。
 */
class TodosUseCase {
    // サンプルデータを内部に保持するリスト。とりあえず初期値は固定値（実際はDBやリモートと連携することを想定）
    private val todos = mutableListOf(
        Todo(1, "初期値1", "初期値の説明1", isChecked = false),
        Todo(2, "初期値2", "初期値の説明2", isChecked = false),
    )

    /**
     * TODOリストを取得する
     * @return List<Todo> 保持している全てのTODOアイテムのリスト
     */
    fun load(): List<Todo> = todos

    /**
     * TODOリストに新しいTODOアイテムを追加する
     * @param todo 追加するTODOアイテム
     */
    fun add(todo: Todo) {
        todos.add(todo)
    }

    /**
     * TODOリストでチェックがONになっている要素を削除する
     */
    fun deleteCheckedTodos() {
        todos.removeAll { it.isChecked }
    }
}