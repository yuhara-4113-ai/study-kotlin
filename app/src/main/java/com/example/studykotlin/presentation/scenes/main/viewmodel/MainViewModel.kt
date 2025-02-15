package com.example.studykotlin.presentation.scenes.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studykotlin.presentation.scenes.main.entity.Todo
import com.example.studykotlin.presentation.scenes.main.usecase.TodosUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * TODOリストの管理を担当するViewModelクラス。
 * MVVMアーキテクチャにおいて、UIとビジネスロジックを分離する役割を果たす。
 * ViewModelはActivity/Fragmentのライフサイクルより長く生存し、画面回転などの設定変更にも対応する。
 */
class MainViewModel(
    // UseCaseをコンストラクタインジェクションで受け取り、依存性の注入を実現し、テストを容易にする
    private val todosUseCase: TodosUseCase
) : ViewModel() {
    // 内部でのみ変更可能なMutableLiveData
    private val _todos = MutableLiveData<List<Todo>>()

    // LiveDataを使用することで、ライフサイクルを考慮したデータの受け渡しが可能
    // 外部に公開する読み取り専用のLiveData。カプセル化により、外部からの不正な変更を防ぐ
    val todos: LiveData<List<Todo>> get() = _todos

    // エラー状態を管理するLiveData
    // UIに表示するエラーメッセージを保持する
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    /**
     * TODOリストを非同期で読み込む
     * この関数は以下の特徴を持つ：
     * 1. コルーチンを使用した非同期処理
     * 2. エラーハンドリング
     * 3. IOスレッドでの実行
     * 4. ViewModelScopeによるライフサイクル管理
     */
    fun loadTodos() {
        // ViewModelScopeを使用してコルーチンを起動
        // このスコープはViewModelが破棄されると自動的にキャンセルされる
        viewModelScope.launch {
            try {
                // IOスレッドで実行し、メインスレッドをブロックしない
                _todos.value = withContext(Dispatchers.IO) {
                    todosUseCase.load()
                }
            } catch (e: Exception) {
                // エラーが発生した場合、エラーメッセージを設定
                _error.value = e.message
            }
        }
    }

    fun addTodo(title: String, description: String) {
        viewModelScope.launch {
            try {
                _todos.value = withContext(Dispatchers.IO) {
                    // 現在のtodosリストを取得（nullの場合は空リストとする）
                    val currentTodos = _todos.value ?: emptyList()
                    // 新しいIDを採番する。リストが空なら1、そうでなければ最大のidに1を加算
                    val newId = if (currentTodos.isEmpty()) 1 else currentTodos.maxOf { it.id } + 1
                    // 新しいTodoオブジェクトを作成
                    val newTodo = Todo(newId, title, description)
                    // 新しいTodoをUseCaseを通して追加する
                    todosUseCase.add(newTodo)
                }
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}