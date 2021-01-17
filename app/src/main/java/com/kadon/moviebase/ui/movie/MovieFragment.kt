package com.kadon.moviebase.ui.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.kadon.moviebase.core.data.Resource
import com.kadon.moviebase.core.domain.model.MovieModel
import com.kadon.moviebase.core.ui.MovieRecyclerViewAdapter
import com.kadon.moviebase.databinding.FragmentMovieBinding
import com.kadon.moviebase.ui.detail.DetailActivity
import org.koin.android.viewmodel.ext.android.viewModel

class MovieFragment : Fragment() {

    private val movieViewModel: MovieViewModel by viewModel()
    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity!=null){
            val movieAdapter = MovieRecyclerViewAdapter()
            movieAdapter.onMovieClick = {
                val intent = Intent(activity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_MOVIE_DATA, it)
                startActivity(intent)
            }

            //Dummy Test
            var dummyList = arrayListOf<MovieModel>()
            for (i in 1..5){
                var data1 = MovieModel(
                    i+1,
                    "Wonder Woman comes into conflict with the Soviet Union during the Cold War in the 1980s and finds a formidable foe by the name of the Cheetah.",
                    "en",
                    "Wonder Woman 1984",
                    false,
                    "Wonder Woman 1984",
                    "/8UlWHLMpgZm9bx6QYh0NFoq67TZ.jpg",
                    "/srYya1ZlI97Au4jUYAktDe3avyA.jpg",
                    "2020-12-16",
                    3280.252,7.2, false, 2685, false
                )
                if (i==2){
                    data1.movieTitle = "Wonder Woman comes into conflict with the Soviet Union during the Cold War in the 1980s and finds a formidable foe by the name of the Cheetah."
                }
                if (i==3){
                    data1.movieTitle = "Wonder Woman comes into conflict with the Soviet Union"
                }
                dummyList.add(data1)
            }

            movieViewModel.movieLiveData.observe(viewLifecycleOwner, Observer { movies ->
                if (movies!=null){
                    when(movies){
                        is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                        is Resource.Success -> {
                            binding.progressBar.visibility = View.GONE
                            movieAdapter.setData(movies.data)
                        }
                        is Resource.Error -> {
                            Toast.makeText(requireContext(), "Something Error!", Toast.LENGTH_SHORT).show()
                            movieAdapter.setData(dummyList)
                        }
                    }
                }
            })

            with(binding.recyclerviewMovie){
                layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
                adapter = movieAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}