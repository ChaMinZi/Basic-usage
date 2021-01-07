package com.example.datatransfer

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts

class FirstFragment : Fragment(R.layout.fragment_first) {
    val requestPermission = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { map ->
        // !! 를 쓰기보다는 null 체크를 하는 것이 좋다.
        if (map[Manifest.permission.ACCESS_FINE_LOCATION]!!) {
            Toast.makeText(requireContext(),
                "ACCESS_FINE_LOCATION 성공", Toast.LENGTH_SHORT).show()
        }

        if (map[Manifest.permission.READ_EXTERNAL_STORAGE]!!) {
            Toast.makeText(requireContext(),
                "READ_EXTERNAL_STORAGE 성공", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button).setOnClickListener {
            requestPermission.launch(arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ))
        }
    }
}