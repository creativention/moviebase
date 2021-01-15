package com.kadon.moviebase.ui.tvshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kadon.moviebase.R

class TvShowFragment : Fragment() {

    private lateinit var tvShowViewModel: TvShowViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        tvShowViewModel =
                ViewModelProvider(this).get(TvShowViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_tv_show, container, false)

        tvShowViewModel.text.observe(viewLifecycleOwner, Observer {

        })
        return root
    }
}