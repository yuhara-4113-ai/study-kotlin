package com.example.studykotlin.presentation.scenes.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.studykotlin.presentation.scenes.main.usecase.TodosUseCase

// ViewModelProvider.Factoryを実装することで、
// ViewModelに必要な依存性（この場合はGetTodosUseCase）を外部から注入できるようにする
class MainViewModelFactory(
    // GetTodosUseCaseのインスタンスを受け取る
    private val todosUseCase: TodosUseCase
) : ViewModelProvider.Factory {

    // createメソッドはViewModelのインスタンスを生成する責務を持つ
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // 引数で渡されたクラスがMainViewModelと互換性があるか確認
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            // MainViewModelのインスタンス生成時に依存性を渡す
            return MainViewModel(todosUseCase) as T
        }
        // 指定されたクラスがMainViewModelでない場合は例外を投げる
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}