package com.example.studykotlin.presentation.scenes.main.view

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studykotlin.R
import com.example.studykotlin.presentation.scenes.main.usecase.TodosUseCase
import com.example.studykotlin.presentation.scenes.main.viewmodel.MainViewModel
import com.example.studykotlin.presentation.scenes.main.viewmodel.MainViewModelFactory

/**
 * メイン画面のフラグメントクラス。
 * このクラスは、TODOリストの読み込みや追加を行う。
 */
class MainFragment : Fragment(R.layout.main) {

    // MainViewModelFactoryを使用してViewModelを生成
    // 「by viewModels」でViewModelのライフサイクルを自動的に管理できる
    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(TodosUseCase())
    }

    /**
     * フラグメントのビューが作成された後に呼び出されるライフサイクルメソッド
     * 主にビューの初期化、リスナーの設定、ViewModelとの接続などを行う
     *
     * ここでは以下の処理を実装:
     * - TextViewとボタンのビュー参照の取得
     * - ViewModelのTODOリストの監視設定
     * - ロードボタンと追加ボタンのクリックリスナー設定
     *
     * @param view onCreateViewで作成されたフラグメントのルートビュー
     * @param savedInstanceState 以前の状態が保存されている場合、その Bundle オブジェクト
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.todoRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        recyclerView.adapter = TodoAdapter(
            todos = viewModel.todos.value ?: emptyList(),
            onTodoCheckedChanged = { todo, isChecked ->
                viewModel.updateTodoStatus(todo.id, isChecked)
            }
        )

        // ViewModelのTODOリストを監視し、変更があればUIに反映する
        // viewLifecycleOwnerで画面のライフサイクルに合わせて適切に監視が終了する(以下の効果がある)
        // ・メモリリークを防ぐため
        // ・画面回転時などの設定変更時に適切にデータを監視し直すため
        // ・非表示のFragmentでの不要な更新を防ぐため
        viewModel.todos.observe(viewLifecycleOwner) { todos ->
            recyclerView.adapter = TodoAdapter(
                todos = todos,
                onTodoCheckedChanged = { todo, isChecked ->
                    viewModel.updateTodoStatus(todo.id, isChecked)
                }
            )
        }

        // 読み込みボタンがクリックされたときにTODOリストをロードする
        view.findViewById<Button?>(R.id.loadButton).apply {
            setOnClickListener {
                viewModel.loadTodos()
            }
        }

        // 追加ボタンがクリックされたときにハーフモーダルを表示し、追加するTODOの内容を入力可能にする
        view.findViewById<Button?>(R.id.addButton).apply {
            setOnClickListener {
                // ハーフモーダルのインスタンスを生成
                val bottomSheet = AddTodoBottomSheetFragment().apply {
                    // ハーフモーダルのリスナーを設定
                    setListener(object : AddTodoBottomSheetFragment.AddTodoListener {
                        // ハーフモーダルの追加ボタンタップ時に呼ばれる
                        override fun onTodoAdded(title: String, description: String) {
                            viewModel.addTodo(title, description)
                        }
                    })
                }
                // ハーフモーダルを表示
                bottomSheet.show(childFragmentManager, "AddTodoBottomSheet")
            }
        }

        // 削除ボタンがクリックされたときにTODOリストでチェックがついている物を削除する
        view.findViewById<Button?>(R.id.deleteButton).apply {
            setOnClickListener {
                viewModel.deleteTodo()
            }
        }
    }
}
