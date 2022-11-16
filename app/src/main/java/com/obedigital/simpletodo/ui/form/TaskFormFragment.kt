package com.obedigital.simpletodo.ui.form

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.obedigital.simpletodo.R
import com.obedigital.simpletodo.databinding.FragmentFormTaskBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskFormFragment : Fragment(R.layout.fragment_form_task) {
    private val viewModel: TaskFormViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentFormTaskBinding.bind(view)

        binding.apply {
            textInputEtTask.setText(viewModel.taskName)
            cbImportantTask.isChecked = viewModel.taskImportant
            cbImportantTask.jumpDrawablesToCurrentState()
            llDateCreated.isVisible = viewModel.task != null
            tvLabelCreated.isVisible = viewModel.task != null
             tvDateCreated.isVisible = viewModel.task != null
            tvDateCreated.text = viewModel.task?.createdDateFormatted
        }
    }
}