package com.example.studykotlin.presentation.scenes.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.studykotlin.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddTodoBottomSheetFragment : BottomSheetDialogFragment() {
    interface AddTodoListener {
        fun onTodoAdded(title: String, description: String)
    }

    private var listener: AddTodoListener? = null

    fun setListener(listener: AddTodoListener) {
        this.listener = listener
    }

    /**
     * ビューが生成された際に呼び出されるライフサイクルメソッド。
     * レイアウトXMLファイルをインフレートし、ビューを生成する。
     *
     * @param inflater レイアウトXMLファイルをインフレートするためのLayoutInflater
     *                 インフレート = インタラクション可能なViewオブジェクトに変換(view.findViewByIdとかを使えるようにする)
     * @param container フラグメントがアタッチされる親ビューグループ
     * @param savedInstanceState 以前の状態が保存されている場合、その Bundle オブジェクト
     * @return 生成されたビュー
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // レイアウトXMLファイルをインフレートし、ビューを生成する
        // containerは親のFrameLayout
        // falseは生成したViewを自動でcontainerに追加しない（FragmentTransactionが行う）
        // TODO: ここがよくわからない。「お作法的なもの」として深く考えなくてOK?
        return inflater.inflate(R.layout.fragment_add_todo, container, false)
    }

    /**
     * onViewCreatedメソッドは、ビューが生成された後に呼び出されるライフサイクルメソッド。
     * 主にビューの初期化、リスナーの設定、ViewModelとの接続などを行う。
     *
     * @param view onCreateViewで作成されたフラグメントのルートビュー
     * @param savedInstanceState 以前の状態が保存されている場合、その Bundle オブジェクト
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val titleEditText = view.findViewById<EditText>(R.id.titleEditText)
        val descriptionEditText = view.findViewById<EditText>(R.id.descriptionEditText)
        val addButton = view.findViewById<Button>(R.id.confirmButton)

        // 追加ボタンが押された際の処理
        addButton.setOnClickListener {
            val title = titleEditText.text.toString()
            val description = descriptionEditText.text.toString()
            // 入力された内容をリスナーに通知。 MainFragmentでリスナーを実装しており、それが発火する
            listener?.onTodoAdded(title, description)
            // ダイアログを閉じる
            dismiss()
        }
    }
}