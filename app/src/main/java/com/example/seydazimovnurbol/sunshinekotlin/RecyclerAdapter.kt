package com.example.seydazimovnurbol.sunshinekotlin

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import org.jetbrains.anko.find
import android.content.Context
import com.squareup.picasso.Picasso


class RecyclerAdapter(var context: Context, var clickHandler: RecyclerAdapterOnClickHandler, var names: MutableList<String>, var images : MutableList<String>) : RecyclerView.Adapter <RecyclerAdapter.ViewHolder>() {

    private val mClickHandler = clickHandler

    interface RecyclerAdapterOnClickHandler {
        fun onClick(pos: Int, s: String, substring: String);
    }

    override fun getItemCount(): Int {
        return names.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.setOnClickListener {
            println("in holder.itemView.setOnClickListener")
            mClickHandler.onClick(position, names[position], images[position].substring(1))
        }

        println(position)
//        holder.title.text = names[position]
        var imageLink = "http://image.tmdb.org/t/p/w780/${images[position].substring(1)}"
        println(imageLink)
        Picasso.with(context).load(imageLink).into(holder.image);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view : View = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        RecyclerViewContext = parent.context

        return ViewHolder(view)
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) , View.OnClickListener {
        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            println("in Viewholder on Click")
        }

        val title : TextView = view.find<TextView>(R.id.movieTitle)
        val image : ImageView = view.find<ImageView>(R.id.movieImage)


    }
}