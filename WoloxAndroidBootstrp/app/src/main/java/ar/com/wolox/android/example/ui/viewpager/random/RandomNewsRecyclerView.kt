package ar.com.wolox.android.example.ui.viewpager.random

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ar.com.wolox.android.R
import ar.com.wolox.android.example.model.News
import ar.com.wolox.android.example.utils.CustomTimeFormat
import com.bumptech.glide.Glide
import org.ocpsoft.prettytime.PrettyTime
import org.ocpsoft.prettytime.units.Minute
import java.util.ArrayList
import java.util.Date

class RandomNewsRecyclerView() : RecyclerView.Adapter<RandomNewsRecyclerView.ViewHolder>() {

    private var dataSet = ArrayList<News>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.text_row_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.render(dataSet[position])
    }

    override fun getItemCount() = dataSet.size

    fun addData(listOfNews: ArrayList<News>) {
        dataSet.addAll(listOfNews)
        notifyDataSetChanged()
    }

    fun clear() {
        dataSet.clear()
        notifyDataSetChanged()
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun render(news: News) {

            val pretty = PrettyTime()
            val date = Date()
            pretty.registerUnit(Minute(), CustomTimeFormat())

            val tvCreatedTimeLabel: TextView = view.findViewById(R.id.tvCardCreatedTime)
            tvCreatedTimeLabel.text = pretty.format(date)

            val tvCardTitle: TextView = view.findViewById(R.id.tvCardTitle)
            tvCardTitle.text = "${news.id} ${news.commenter}"

            val tvCardDescription: TextView = view.findViewById(R.id.tvCardDescription)
            tvCardDescription.text = news.comment

            val imageViewCard: ImageView = view.findViewById(R.id.ivCard)
            Glide.with(view).load("http://goo.gl/gEgYUd").placeholder(R.drawable.ic_image_not_supported).into(imageViewCard)
        }
    }
}