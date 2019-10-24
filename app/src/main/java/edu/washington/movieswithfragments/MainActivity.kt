package edu.washington.movieswithfragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_move_list.*
import org.json.JSONException
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    val movies = mutableListOf<Movie>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listview_movies.adapter = ArrayAdapter(this, R.layout.list_item, R.id.txt_item, movies)
        listview_movies.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val movie = parent.getItemAtPosition(position) as Movie
            Log.v(TAG, "You clicked on: $movie")
        }
    }

    fun handleSearchClick(v: View) {
        downloadMovieData(txt_search.text.toString())
    }

    private fun downloadMovieData(searchTerm: String) {
        var urlString = ""
        try {
            urlString = "https://itunes.apple.com/search?term=" + URLEncoder.encode(searchTerm, "UTF-8") + "&media=movie&entity=movie&limit=25"
            Log.v(TAG, urlString);
        } catch (uee: UnsupportedEncodingException) {
            Log.e(TAG, uee.toString())
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
            Response.ErrorListener { error -> Log.e(TAG, error.toString()) })
        VolleyService.requestQueue(this)?.add(request)
    }

    companion object {
        private val TAG = "MainActivity"
    }
}
