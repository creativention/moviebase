package com.kadon.moviebase.ui.movie

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.kadon.moviebase.R
import com.kadon.moviebase.core.data.Resource
import com.kadon.moviebase.core.ui.MovieRecyclerViewAdapter
import com.kadon.moviebase.core.utils.K
import com.kadon.moviebase.databinding.FragmentMovieBinding
import com.kadon.moviebase.ui.detail.DetailActivity
import org.koin.android.viewmodel.ext.android.viewModel

class MovieFragment : Fragment() {

    private val movieViewModel: MovieViewModel by viewModel()
    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!
    private val movieAdapter = MovieRecyclerViewAdapter()
    private var pageNumber : Int = 1

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
        if (activity!=null){
            movieAdapter.onMovieClick = {
                val intent = Intent(activity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_MOVIE_DATA, it)
                startActivity(intent)
            }

            /*movieViewModel.movieLiveData.observe(viewLifecycleOwner, Observer { movies ->
                if (movies != null) {
                    when (movies) {
                        is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                        is Resource.Success -> {
                            binding.progressBar.visibility = View.GONE
                            movieAdapter.setData(movies.data)
                        }
                        is Resource.Error -> {
                            Toast.makeText(requireContext(), "Something Error!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })*/

            with(binding.recyclerviewMovie){
                layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
                adapter = movieAdapter
                recyclerViewListener(binding.recyclerviewMovie)
            }

            movieViewModel.loadMore(K.CATEGORY_POPULAR, pageNumber).observe(viewLifecycleOwner, Observer { movies ->
                if (movies != null) {
                    when (movies) {
                        is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                        is Resource.Success -> {
                            binding.progressBar.visibility = View.GONE
                            movieAdapter.setData(movies.data)
                        }
                        is Resource.Error -> {
                            Toast.makeText(requireContext(), "Something Error!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
        }
    }

    private fun recyclerViewListener(recyclerView: RecyclerView){
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(rv: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(rv, dx, dy)
                if (!rv.canScrollVertically(1) && dy > 0) {
                    Log.d("MovieFragment","Reach bottom")
                    //TODO : Get next page
                    binding.progressBarBottom.visibility = View.VISIBLE
                    movieViewModel.loadMore(K.CATEGORY_POPULAR, pageNumber++).observe(viewLifecycleOwner, Observer { movies ->
                        if (movies != null) {
                            when (movies) {
                                is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                                is Resource.Success -> {
                                    binding.progressBarBottom.visibility = View.GONE
                                    movieAdapter.setData(movies.data)
                                }
                                is Resource.Error -> {
                                    Toast.makeText(requireContext(), "Something Error!", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    })
                }
            }
        })
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.movie_action_menu, menu)
        ((menu?.findItem(R.id.app_bar_search)?.actionView) as SearchView).apply {
            maxWidth = Integer.MAX_VALUE
            isIconfiedByDefault
        }
    }

}