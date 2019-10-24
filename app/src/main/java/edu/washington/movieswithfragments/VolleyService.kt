package edu.washington.movieswithfragments

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class VolleyService {
    companion object {
        private var mRequestQueue: RequestQueue? = null

        fun requestQueue(c: Context): RequestQueue? {
            if (mRequestQueue == null) {
                mRequestQueue = Volley.newRequestQueue(c.applicationContext)
            }

            return mRequestQueue
        }
    }
}