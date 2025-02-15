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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_todo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val titleEditText = view.findViewById<EditText>(R.id.titleEditText)
        val descriptionEditText = view.findViewById<EditText>(R.id.descriptionEditText)
        val addButton = view.findViewById<Button>(R.id.confirmButton)

        addButton.setOnClickListener {
            val title = titleEditText.text.toString()
            val description = descriptionEditText.text.toString()
            if (title.isNotEmpty()) {
                listener?.onTodoAdded(title, description)
                dismiss()
            }
        }
    }
}