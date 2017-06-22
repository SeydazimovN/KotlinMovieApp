package com.example.seydazimovnurbol.sunshinekotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.telecom.Call.Details
import android.content.Intent
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso


class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        if (intent.hasExtra("name")) {
            var nameTextView = findViewById(R.id.nameTextView) as TextView
            nameTextView.setText(intent.getStringExtra("name"))
        }
        if (intent.hasExtra("image")) {
            var imageView = findViewById(R.id.imageView) as ImageView
            var imageLink = "http://image.tmdb.org/t/p/w780/${intent.getStringExtra("image")}"
            Picasso.with(this).load(imageLink).into(imageView)
        }
    }
}
