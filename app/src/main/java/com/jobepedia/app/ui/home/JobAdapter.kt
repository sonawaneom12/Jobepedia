package com.jobepedia.app.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jobepedia.app.data.model.Job
import com.jobepedia.app.databinding.ItemJobBinding

class JobAdapter(private val jobs: List<Job>) :
    RecyclerView.Adapter<JobAdapter.JobViewHolder>() {

    inner class JobViewHolder(val binding: ItemJobBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val binding =
            ItemJobBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JobViewHolder(binding)
    }

    override fun getItemCount() = jobs.size

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        val job = jobs[position]

        holder.binding.jobTitle.text = job.title
        holder.binding.company.text = job.company
        holder.binding.location.text = job.location
        holder.binding.salary.text = job.salary
        holder.binding.lastDate.text = "Last Date: ${job.lastDate}"
    }
}
