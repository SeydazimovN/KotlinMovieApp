package com.example.seydazimovnurbol.sunshinekotlin

import android.content.Context
import android.os.AsyncTask
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

/**
 * Created by root on 6/15/17.
 */
class DownloadRepositoryTask(var context: Context, var clickHandler: RecyclerAdapter.RecyclerAdapterOnClickHandler) : AsyncTask<String, Void, String>() {

    @Throws(IOException::class)
    private fun downloadData(urlString: String): String {
        var inputStream: InputStream? = null
        try {
            val url = URL(urlString)
            val conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "GET"
            conn.connect()

            inputStream = conn.inputStream
            return inputStream.bufferedReader().use { it.readText() }
        } finally {
            if (inputStream != null) {
                inputStream.close()
            }
        }
    }

    override fun doInBackground(vararg params: String?): String? {
        return downloadData(params[0] as String)
    }

    override fun onPostExecute(result: String?) {
        progressDialog?.hide()

        var obj : JSONObject = JSONObject(result)
        var objArray : JSONArray? = null
        objArray = obj.getJSONArray("results")
        val len = objArray.length()
        var data = mutableListOf<String>()
        var titles = mutableListOf<String>()
        var images = mutableListOf<String>()
        for (i in 0 .. len - 1) {
//            println(objArray[i])
            val movieString = objArray[i].toString()
            val movieJSON = JSONObject(movieString)
            val title = movieJSON.getString("title")
            val vote_count = movieJSON.getInt("vote_count")
            val releaseDate = movieJSON.getString("release_date")
            val overview = movieJSON.getString("overview")
            val image = movieJSON.getString("poster_path")
            val rating = movieJSON.getInt("vote_average")
            println("${title} ${rating} ${releaseDate}")
            titles.add(title)
            images.add(image)
        }

        println(data)

        println("myContext")
        println(myContext != null)
        if (myContext != null) {
            mRecyclerView?.adapter = RecyclerAdapter(context, clickHandler, titles, images)
        }
        super.onPostExecute(result)
    }
}
