package com.example.seydazimovnurbol.sunshinekotlin

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

var myTextView : TextView? = null
var progressDialog : ProgressDialog? = null
val siteName = "http://api.themoviedb.org/"
val pageNumber = "3/"
val categoryPopular = "popular"
val myAPIkey = "?api_key=d5df2545afeaa07dbc2a502135589aa2";
var mRecyclerView : RecyclerView? = null
var RecyclerViewContext : Context? = null
var myContext : Context? = null

var intent : Intent? = null

fun check() {
    println("in check");
    if (intent == null) println("bad")
    else println("good")
}
class MainActivity : AppCompatActivity() , RecyclerAdapter.RecyclerAdapterOnClickHandler {

    private var mRecyclerAdapter : RecyclerAdapter? = null

    override fun onClick(pos: Int) {
        println("${pos} is here! in onClick :)")
        intent = Intent(this, DetailActivity::class.java)
        println(intent != null)
        startActivity(intent)
    }

    private fun isNetworkConnected() : Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val intent = Intent(this, DetailActivity::class.java)
//        startActivity(intent)

        if (isNetworkConnected()) {
            progressDialog = ProgressDialog(this)
            progressDialog?.setMessage("Please wait...")
            progressDialog?.setCancelable(false)
            progressDialog?.show()
            val GET_REPO_URL : String = "${siteName}${pageNumber}movie/${categoryPopular}${myAPIkey}"
            println(GET_REPO_URL)
            println("nurba")


            mRecyclerView = findViewById(R.id.recyclerView) as RecyclerView?
            mRecyclerView?.layoutManager = LinearLayoutManager(this)
            mRecyclerView?.setHasFixedSize(true)


            myContext = this

            var names = mutableListOf<String>()

            startDownload(GET_REPO_URL)
        }
        else {
            AlertDialog.Builder(this).setTitle("No Internet Connection")
                    .setMessage("Please check your internet connection and try again")
                    .setPositiveButton(android.R.string.ok) { dialog, which -> }
                    .setIcon(android.R.drawable.ic_dialog_alert).show()
        }
    }

    private fun startDownload(downloadUrl: String) {
        println("in startDownload function")
        println(intent != null)
        DownloadRepositoryTask(this, this).execute(downloadUrl)
    }
    fun makeIntent(pos : Int) {
        println("${pos} is here!")
        if (intent != null) {
            println("intent is not null in makeIntent function")
        }   else {
            println("what the fuck?! intent is null in makeIntent function")
        }
    }
}
