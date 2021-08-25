package ar.com.wolox.android.example.ui.viewpager.random

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.com.wolox.android.R
import ar.com.wolox.android.databinding.FragmentRandomBinding
import ar.com.wolox.wolmo.core.fragment.WolmoFragment
import javax.inject.Inject

class RandomFragment @Inject constructor() : WolmoFragment<FragmentRandomBinding, RandomPresenter>(), RandomView {

    @Inject
    internal lateinit var context: Context

    override fun layout() = R.layout.fragment_random

    override fun init() {
        var array: Array<String> = arrayOf("green", "red", "blue")

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = CustomAdapter(array)
        binding.recyclerView.adapter = adapter
    }

    override fun setListeners() {
    }

    override fun setRandom(someNumber: Int) {
    }
}

class CustomAdapter(private val dataSet: Array<String>) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.text_row_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.textView.text = dataSet[position]
    }

    override fun getItemCount() = dataSet.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.tvCardTitle)
    }
}
