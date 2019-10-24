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

        val searchTerm = txt_search.text.toString()

        val fragment: MoveListFragment = MoveListFragment.newInstance(searchTerm)

        supportFragmentManager.beginTransaction().run {
            add(R.id.movie_fragment, fragment, searchTerm)
            commit()
        }
    }

    fun handleSearchClick(v: View): String {
//        downloadMovieData(txt_search.text.toString())
    }

    companion object {
        val TAG = "MainActivity"
    }
}
