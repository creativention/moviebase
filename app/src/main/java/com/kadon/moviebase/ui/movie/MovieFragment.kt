package com.kadon.moviebase.ui.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.kadon.moviebase.core.data.Resource
import com.kadon.moviebase.core.ui.MovieRecyclerViewAdapter
import com.kadon.moviebase.core.utils.K
import com.kadon.moviebase.databinding.FragmentMovieBinding
import com.kadon.moviebase.ui.detail.DetailActivity
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class MovieFragment : Fragment() {

    private val movieViewModel: MovieViewModel by viewModel()
    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!
    private val movieAdapter = MovieRecyclerViewAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
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
            }

            movieViewModel.movieLiveData.observe(viewLifecycleOwner, { movies ->
                Timber.d("observe movieLiveData")

                if (movies != null) {
                    when (movies) {
                        is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                        is Resource.Success -> {
                            binding.progressBar.visibility = View.GONE
                            movieAdapter.setData(movies.data)
                        }
                        is Resource.Error -> {
                            Toast.makeText(requireContext(), "Something Error!", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            })

            initSearchFilter()
        }
    }

    private fun initSearchFilter() {
        with(binding.searchView) {
            setOnClickListener {
                isIconified = false
            }

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean = true

                override fun onQueryTextChange(newText: String?): Boolean {
                    //Log.d("TAG", "query = $newText")
                    if (newText != null) movieAdapter.filter.filter(newText)
                    return true
                }

            })
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}