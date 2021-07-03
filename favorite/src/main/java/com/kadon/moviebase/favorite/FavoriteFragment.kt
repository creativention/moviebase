package com.kadon.moviebase.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.kadon.moviebase.core.ui.MovieRecyclerViewAdapter
import com.kadon.moviebase.core.utils.K
import com.kadon.moviebase.favorite.databinding.FragmentFavoriteBinding
import com.kadon.moviebase.favorite.di.favoriteModul
import com.kadon.moviebase.ui.detail.DetailActivity
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules


class FavoriteFragment : Fragment() {

    private val favoriteViewModel: FavoriteViewModel by viewModel()

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        loadKoinModules(favoriteModul)
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {

            val movieAdapter = MovieRecyclerViewAdapter()
            movieAdapter.onMovieClick = {
                val intent = Intent(activity, DetailActivity::class.java)
                intent.putExtra(K.MOVIE_ID, it.movieId)
                startActivity(intent)
            }

            favoriteViewModel.favoriteMovie.observe(viewLifecycleOwner, { favoriteMovie ->
                movieAdapter.setData(favoriteMovie)
                binding.viewEmpty.root.visibility =
                    if (favoriteMovie.isNotEmpty()) View.GONE else View.VISIBLE
            })

            with(binding.recyclerviewMovieFavorite) {
                layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
                adapter = movieAdapter
            }

            with(binding.searchViewFave) {
                setOnClickListener {
                    isIconified = false
                }
                setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean = true

                    override fun onQueryTextChange(newText: String?): Boolean {
                        if (newText != null) movieAdapter.filter.filter(newText)
                        return true
                    }

                })
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}