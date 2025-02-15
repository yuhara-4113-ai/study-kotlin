package com.example.studykotlin.presentation.scenes.main.view

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.studykotlin.R
import com.example.studykotlin.presentation.scenes.main.usecase.TodosUseCase
import com.example.studykotlin.presentation.scenes.main.viewmodel.MainViewModel
import com.example.studykotlin.presentation.scenes.main.viewmodel.MainViewModelFactory

class MainFragment : Fragment(R.layout.main) {

    // MainViewModelFactoryを使用してViewModelを生成
    // 「by viewModels」でViewModelのライフサイクルを自動的に管理できる
    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(TodosUseCase())
    }

    /**
     * アクティビティ作成時に呼び出されるライフサイクルメソッド。
     * アクティビティの初期化処理を行う。
     *
     * @param savedInstanceState 以前の状態情報を含むBundle。アクティビティが再作成された場合に
     *                          以前の状態を復元するために使用される。nullの場合は新規作成を意味する。
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 画面遷移する際は必要
//        val navController = findNavController()
//        view.findViewById<Button>(R.id.yourButton).setOnClickListener {
//            navController.navigate(R.id.action_firstFragment_to_secondFragment)
//        }

        // --------------------------------------------

        // レイアウト内のTextViewを取得
        val todoTitle: TextView = view.findViewById(R.id.textView)
        // 読み込みボタンを取得
        val loadButton: Button = view.findViewById(R.id.loadButton)
        // 追加ボタンを取得
        val addButton: Button = view.findViewById(R.id.addButton)

        // ViewModelのTODOリストを監視し、UIを更新する
        viewModel.todos.observe(viewLifecycleOwner) { todos ->
            // TODOリストのタイトルをTextViewに表示する
            todoTitle.text = todos.joinToString("\n") { it.title }
        }

        // ボタンがクリックされたときにTODOリストをロードする
        loadButton.setOnClickListener {
            viewModel.loadTodos()
        }

        // ボタンがクリックされたときにTODOリストをロードする
        addButton.setOnClickListener {
            viewModel.addTodo()
        }
    }
}
