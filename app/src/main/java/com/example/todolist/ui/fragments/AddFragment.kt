package com.example.todolist.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.todolist.R
import com.example.todolist.databinding.FragmentAddBinding

import com.example.todolist.utils.hideKeyboard
import com.example.todolist.utils.shortToast
import com.example.todolist.viewmodel.TodoViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates


@AndroidEntryPoint
class AddFragment : Fragment() {
    private var image by Delegates.notNull<Int>()
    private lateinit var viewModel: TodoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val activity = requireNotNull(this.activity)
        val binding = FragmentAddBinding.inflate(inflater)

        viewModel = ViewModelProvider(this)[TodoViewModel::class.java]


        binding.submitButton.setOnClickListener {
            val title = binding.title.text.toString()
            val description = binding.description.text.toString()

            val indexValue = binding.spinner.selectedItemPosition

            image = if (indexValue == 0) {
                R.drawable.shopping
            } else {
                R.drawable.work
            }

            val mDate = Calendar.getInstance().time
            val formatter = SimpleDateFormat("dd MMMM yyyy,  HH:mm", Locale.getDefault())
            val currentDate = formatter.format(mDate)


            if (title.isNotBlank() && description.isNotBlank()) {
                viewModel.addTodo(title, description, image, currentDate)
                activity.hideKeyboard()
                findNavController().popBackStack()
            } else {
                context?.shortToast(getString(R.string.fill_all_fields))
            }
        }

        val adapter = ArrayAdapter.createFromResource(
            requireContext(), R.array.categories,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter

        return binding.root
    }

}
