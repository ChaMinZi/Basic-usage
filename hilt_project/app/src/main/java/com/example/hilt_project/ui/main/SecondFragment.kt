package com.example.hilt_project.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.hilt_project.R
import com.example.hilt_project.data.MyRepository
import com.example.hilt_project.di.qualifier.ActivityHash
import com.example.hilt_project.di.qualifier.AppHash
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SecondFragment : Fragment(R.layout.fragment_second) {
    private val viewModel by viewModels<MainViewModel>()

    @Inject
    lateinit var repository: MyRepository

    @AppHash
    @Inject
    lateinit var applicationHash: String

    @ActivityHash
    @Inject
    lateinit var activityHash: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button).setOnClickListener {
            findNavController().navigate(R.id.action_secondFragment_to_mainFragment)
        }

        Log.d("SecondFragment", "${repository.hashCode()}")
        Log.d("SecondFragment", "appHash : $applicationHash")
        Log.d("SecondFragment", "activityHash : $activityHash")
        Log.d("SecondFragment", "viewModelHash : ${viewModel.getRepositoryHash()}")
    }
}