package com.example.todolist.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.todolist.R
import com.example.todolist.databinding.FragmentViewBinding
import com.example.todolist.utils.hideKeyboard
import com.example.todolist.utils.shortToast
import com.example.todolist.viewmodel.TodoViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint

class ViewFragment : Fragment() {
    private lateinit var viewModel: TodoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mTodo = ViewFragmentArgs.fromBundle(requireArguments()).todo
        val binding = FragmentViewBinding.inflate(inflater).apply {
            todo = mTodo
        }
        val activity = requireNotNull(this.activity)

        viewModel = ViewModelProvider(this)[TodoViewModel::class.java]


        binding.editButton.setOnClickListener {
            binding.editButton.visibility = View.INVISIBLE
            binding.submitButton.visibility = View.VISIBLE
            binding.title.isEnabled = true
            binding.description.isEnabled = true
        }

        binding.submitButton.setOnClickListener {
            val updatedTitle = binding.title.text.toString()
            val updatedDesc = binding.description.text.toString()

            val mDate = Calendar.getInstance().time
            val formatter = SimpleDateFormat("dd MMMM yyyy HH:mm", Locale.getDefault())
            val currentDate = formatter.format(mDate)

            if (updatedTitle.isNotBlank() && updatedDesc.isNotBlank()) {
                viewModel.updateTodo(
                    mTodo!!.id,
                    updatedTitle,
                    updatedDesc,
                    mTodo.checked,
                    mTodo.image, currentDate
                )
                activity.hideKeyboard()
                findNavController().popBackStack()
            } else {
                context?.shortToast(getString(R.string.fill_all_fields))
            }
        }
        return binding.root
    }
}
