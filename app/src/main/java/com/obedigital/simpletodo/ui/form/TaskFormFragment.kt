package com.obedigital.simpletodo.ui.form

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.obedigital.simpletodo.R
import com.obedigital.simpletodo.databinding.FragmentFormTaskBinding
import com.obedigital.simpletodo.util.exhaustive
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

            textInputEtTask.addTextChangedListener{
                viewModel.taskName = it.toString()
            }

            cbImportantTask.setOnCheckedChangeListener{ _, isChecked ->
                viewModel.taskImportant = isChecked
            }

            fabTaskSave.setOnClickListener {
                viewModel.onSaveClick()
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.addEditTaskEvent.collect{ event ->
                when(event) {
                    is TaskFormViewModel.AddEditTaskEvent.NavigateBackWithResult -> {
                        binding.textInputEtTask.clearFocus()
                        setFragmentResult(
                            "add_edit_request",
                            bundleOf("add_edit_result" to event.result)
                        )
                        findNavController().popBackStack()
                    }
                    is TaskFormViewModel.AddEditTaskEvent.ShowInvalidInputMessage -> {
                        Snackbar.make(requireView(), event.msg, Snackbar.LENGTH_LONG).show()
                    }
                }.exhaustive
            }
        }
    }
}