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
     * ビューが生成されたときに呼ばれるコールバック関数。
     * 画面の初期化処理を行う。
     * ここでは、RecyclerView(1行ずつのTODO要素)の初期化やボタンのクリックリスナーの設定を行う。
     * @param view 生成されたビュー
     * @param savedInstanceState 保存されたインスタンスの状態
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1行ずつのTODO要素。RecyclerView(スクロール可能なリスト)のインスタンス生成
        // todoRecyclerView垂直方向(デフォルト)のリストでアイテムを表示する
        val todoRecyclerView = view.findViewById<RecyclerView>(R.id.todoRecyclerView)
        // RecyclerViewのlayoutManagerを設定し、以下の管理を実施させる
        // ・アイテムビューのサイズを計測し、配置する。
        // ・画面外にスクロールして見えなくなったビューをいつ再利用するかを決定する。
        // ・スクロールの動作を処理する。
        todoRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        // 一時的にアダプターがない状態になるため「No adapter attached; skipping layout」のエラーが発生する。
        // ただし、以下のviewModel.todos.observeでアダプターを設定するため、機能的に問題ない。でもエラーが発生してるのは気持ち悪いので直したい

        // ViewModelのTODOリストを監視し、変更があればUIに反映する
        // viewLifecycleOwnerで画面のライフサイクルに合わせて適切に監視が終了する(以下の効果がある)
        // ・メモリリークを防ぐため
        // ・画面回転時などの設定変更時に適切にデータを監視し直すため
        // ・非表示のFragmentでの不要な更新を防ぐため
        viewModel.todos.observe(viewLifecycleOwner) { todos ->
            // TodoAdapterを設定し、TODOリストの表示と管理を行う
            todoRecyclerView.adapter = TodoAdapter(
                // ViewModelのTODOリストを初期値として設定
                todos = todos,
                // チェックボックスの状態が変更されたときに呼ばれるコールバック関数
                onTodoCheckedChanged = { todo, isChecked ->
                    // ViewModelのTODOリストのステータスを更新
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
