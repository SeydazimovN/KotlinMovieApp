package com.example.seydazimovnurbol.sunshinekotlin

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceFragment
import android.support.annotation.IntegerRes
import android.support.v4.graphics.ColorUtils
import android.widget.TextView
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View

var myTextView : TextView? = null
var progressDialog : ProgressDialog? = null
val siteName = "http://api.themoviedb.org/"
val pageNumber = "3/"
val categoryPopular = "popular"
val categoryTopRated = "top_rated"
var category = categoryPopular
val myAPIkey = "?api_key=d5df2545afeaa07dbc2a502135589aa2";
var mRecyclerView : RecyclerView? = null
var RecyclerViewContext : Context? = null

var intent : Intent? = null

class MainActivity : AppCompatActivity() , RecyclerAdapter.RecyclerAdapterOnClickHandler {

    private var mRecyclerAdapter : RecyclerAdapter? = null

    override fun onClick(pos: Int, name : String, image : String) {
        println("${pos} is here! in onClick :)")
        intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("name", name)
        intent.putExtra("image", image)
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

        if (isNetworkConnected()) {
            progressDialog = ProgressDialog(this)
            progressDialog?.setMessage("Please wait...")
            progressDialog?.setCancelable(false)
            progressDialog?.show()

            mRecyclerView = findViewById(R.id.recyclerView) as RecyclerView?
            mRecyclerView?.layoutManager = LinearLayoutManager(this)
            mRecyclerView?.setHasFixedSize(true)

            var names = mutableListOf<String>()

            startDownload(getRepoURL())
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

    fun changeToPopular(view : View) {
        category = categoryPopular
        startDownload(getRepoURL());
        changeButtonColor(category)
    }

    private fun  changeButtonColor(category: String) {
        var popularButton = findViewById(R.id.popularButton)
        var topRatedButton = findViewById(R.id.topRatedButton)

        if (category == categoryPopular) {
            popularButton.setBackgroundColor(Color.MAGENTA)
            topRatedButton.setBackgroundColor(Color.CYAN)
        } else {
            popularButton.setBackgroundColor(Color.CYAN)
            topRatedButton.setBackgroundColor(Color.MAGENTA)
        }
    }

    fun changeToTopRated(view : View) {
        println("button 'Top Rated' is clicked.")
        category = categoryTopRated
        startDownload(getRepoURL());
        changeButtonColor(category)
    }
    fun getRepoURL() : String{
        return "${siteName}${pageNumber}movie/${category}${myAPIkey}"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.settings_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        if (id == R.id.action_settings) {
            var intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}

class SettingsFragment : PreferenceFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.preferences)
    }
}