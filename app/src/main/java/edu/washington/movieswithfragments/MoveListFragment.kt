package edu.washington.movieswithfragments


import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_move_list.*
import java.io.UnsupportedEncodingException
import java.net.URLEncoder

/**
 * A simple [Fragment] subclass.
 */
class MoveListFragment : Fragment() {
    val movies = mutableListOf<Movie>()

    companion object {
        private val ARG_PARAM_KEY = "my key"

        fun newInstance(argument: String): MoveListFragment {
            val args= Bundle().apply {
                putString(ARG_PARAM_KEY, argument)
            }
            val fragment = MoveListFragment().apply {
                setArguments(args)
            }
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_move_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        savedInstanceState.getString()

        listview_movies.adapter = ArrayAdapter(activity!!.applicationContext, R.layout.list_item, R.id.txt_item, movies)
        listview_movies.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val movie = parent.getItemAtPosition(position) as Movie
        }
    }

    private fun downloadMovieData(searchTerm: String) {
        var urlString = ""
        try {
            urlString = "https://itunes.apple.com/search?term=" + URLEncoder.encode(searchTerm, "UTF-8") + "&media=movie&entity=movie&limit=25"
            Log.v(MainActivity.TAG, urlString);
        } catch (uee: UnsupportedEncodingException) {
            Log.e(MainActivity.TAG, uee.toString())
            return
        }

        val request = JsonObjectRequest(
            Request.Method.GET, urlString, null,
            Response.Listener { response ->
                val results = response.getJSONArray("results")
                (listview_movies.adapter as ArrayAdapter<Movie>).clear()
                for (i in 0 until results.length()) {
                    val track = results.getJSONObject(i)
                    if (track.getString("wrapperType") == "track") {
                        track.apply {
                            val movie = Movie(
                                getString("trackName"),
                                getString("releaseDate"),
                                getString("longDescription"),
                                getString("trackViewUrl"))
                            movies.add(movie)
                        }
                    }
                }

                (listview_movies.adapter as ArrayAdapter<Movie>).notifyDataSetChanged()
            },
            Response.ErrorListener { error -> Log.e(MainActivity.TAG, error.toString()) })
        VolleyService.requestQueue(activity!!.applicationContext)?.add(request)
    }

}
