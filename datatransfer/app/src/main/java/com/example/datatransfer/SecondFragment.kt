package com.example.datatransfer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController

class SecondFragment : Fragment(R.layout.fragment_second) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFragmentResultListener("FirstToSecond") { requestKey, bundle ->
            // bundle에서 데이터 가지고 오기
            val data = bundle.getString("data", "")

            Toast.makeText(requireContext(), data, Toast.LENGTH_SHORT).show()
        }

        view.findViewById<Button>(R.id.button2).setOnClickListener {
            // bundle에 보낼 데이터 저장하기
            setFragmentResult(
                "SecondToFirst",
                bundleOf("data" to "world")
            )

            findNavController().navigate(R.id.action_secondFragment_to_firstFragment)
        }
    }
}