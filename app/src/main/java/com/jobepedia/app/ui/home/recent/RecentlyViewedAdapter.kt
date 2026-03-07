package com.jobepedia.app.ui.home.recent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jobepedia.app.data.model.Job
import com.jobepedia.app.databinding.ItemRecentJobBinding

class RecentlyViewedAdapter(
    private val jobs: List<Job>,
    private val onClick: (Job) -> Unit
) : RecyclerView.Adapter<RecentlyViewedAdapter.RecentlyViewedViewHolder>() {

    inner class RecentlyViewedViewHolder(val binding: ItemRecentJobBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentlyViewedViewHolder {
        val binding = ItemRecentJobBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecentlyViewedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecentlyViewedViewHolder, position: Int) {
        val job = jobs[position]
        holder.binding.recentJobTitle.text = job.title
        holder.binding.recentCompany.text = job.company
        holder.binding.recentLocation.text = job.location
        holder.itemView.setOnClickListener { onClick(job) }
    }

    override fun getItemCount(): Int = jobs.size
}
