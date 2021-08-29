package com.kadon.moviebase.ui.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.kadon.moviebase.core.ui.MoviePagingAdapter
import com.kadon.moviebase.core.utils.K
import com.kadon.moviebase.databinding.FragmentMovieBinding
import com.kadon.moviebase.ui.detail.DetailActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class MovieFragment : Fragment() {

    private val viewModel: MovieViewModel by viewModel()
    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!
    private val movieAdapter = MoviePagingAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            movieAdapter.onMovieClick = {
                val intent = Intent(activity, DetailActivity::class.java)
                intent.putExtra(K.MOVIE_ID, it.movieId)
                startActivity(intent)
            }

            with(binding.recyclerviewMovie) {
                layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
                adapter = movieAdapter
                visibility = View.INVISIBLE
            }

            with(binding.veilFrame) {
                setAdapter(movieAdapter)
                setLayoutManager(StaggeredGridLayoutManager(2, RecyclerView.VERTICAL))
                addVeiledItems(5)
                veil()
            }

            observeViewModel()
        }
    }

    private fun observeViewModel() {
        with(binding.veilFrame){
            visibility = View.GONE
            unVeil()
        }
        with(binding.recyclerviewMovie){
            visibility = View.VISIBLE
        }
        /*viewModel.movies.observe(viewLifecycleOwner, {
            lifecycleScope.launch {
                movieAdapter.submitData(it)
            }
        })*/

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.movies.collectLatest {
                movieAdapter.submitData(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.recyclerviewMovie.adapter = null
        _binding = null
    }

}