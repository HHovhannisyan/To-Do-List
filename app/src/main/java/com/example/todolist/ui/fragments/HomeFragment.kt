package com.example.todolist.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.example.todolist.R
import com.example.todolist.databinding.FragmentHomeBinding
import com.example.todolist.ui.adapter.ListAdapter
import com.example.todolist.utils.hideKeyboard
import com.example.todolist.viewmodel.TodoViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var mLayoutManager: RecyclerView.LayoutManager

    private lateinit var viewModel: TodoViewModel

    private lateinit var listAdapter: ListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        activity?.hideKeyboard()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[TodoViewModel::class.java]


        mLayoutManager = StaggeredGridLayoutManager(1, VERTICAL)
        listAdapter = ListAdapter(viewModel)

        binding.addTaskBtn.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_addFragment)
        )



        binding.rvTodo.layoutManager = StaggeredGridLayoutManager(1, VERTICAL)
        binding.rvTodo.adapter = listAdapter

        viewModel.getAllTodos().observe(viewLifecycleOwner) { list ->
            listAdapter.setData(list)
        }


    }
}
