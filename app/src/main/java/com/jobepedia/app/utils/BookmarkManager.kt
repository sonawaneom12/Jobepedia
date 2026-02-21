package com.jobepedia.app.utils

import android.content.Context
import com.jobepedia.app.data.model.Job

object BookmarkManager {

    private const val PREF_NAME = "bookmarks"

    fun saveJob(context: Context, job: Job) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val set = prefs.getStringSet("saved", mutableSetOf()) ?: mutableSetOf()

        val updated = set.toMutableSet()

        updated.add(serialize(job))

        prefs.edit().putStringSet("saved", updated).apply()
    }

    fun removeJob(context: Context, job: Job) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val set = prefs.getStringSet("saved", mutableSetOf()) ?: mutableSetOf()
        val updated = set.toMutableSet()
        updated.remove(serialize(job))
        prefs.edit().putStringSet("saved", updated).apply()
    }

    fun isSaved(context: Context, job: Job): Boolean {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val saved = prefs.getStringSet("saved", emptySet()) ?: emptySet()
        return serialize(job) in saved
    }

    fun getSavedJobs(context: Context): List<Job> {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val saved = prefs.getStringSet("saved", emptySet()) ?: emptySet()

        return saved.mapNotNull {
            val parts = it.split("|")

            if (parts.size == 5) {
                Job(parts[0], parts[1], parts[2], parts[3], parts[4])
            } else {
                null
            }
        }
    }

    private fun serialize(job: Job): String {
        return "${job.title}|${job.company}|${job.location}|${job.salary}|${job.lastDate}"
    }

}
