package edu.washington.movieswithfragments

class Movie(var title: String, var year: String, var description: String, var url: String) {
    init {
        year = year.subSequence(0, 4) as String
    }

    override fun toString(): String {
        return "$title ($year)"
    }
}