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

class FirstFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFragmentResultListener("SecondToFirst") { requestKey, bundle ->
            // bundle에서 데이터 가지고 오기
            val data = bundle.getString("data", "")

            Toast.makeText(requireContext(), data, Toast.LENGTH_SHORT).show()
        }

        view.findViewById<Button>(R.id.button).setOnClickListener {

            setFragmentResult(
                "FirstToSecond",
                bundleOf("data" to "hello")
            )

            findNavController().navigate(R.id.action_firstFragment_to_secondFragment)
        }
    }

}