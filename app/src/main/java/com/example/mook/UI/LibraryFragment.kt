package com.example.mook.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mook.R
import com.google.android.material.navigation.NavigationView


class LibraryFragment : Fragment(R.layout.fragment_library) {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_library, container, false)
        // requireActivity().findViewById<View>(R.id.libraryToolbar).visibility = View.GONE
        // requireActivity().findViewById<View>(R.id.mainToolbar).visibility = View.VISIBLE
        return view
    }
}