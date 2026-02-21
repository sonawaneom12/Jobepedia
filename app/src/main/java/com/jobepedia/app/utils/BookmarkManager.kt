package com.jobepedia.app.utils

import android.content.Context
import com.jobepedia.app.data.model.Job
import org.json.JSONObject

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

        return saved.mapNotNull { deserialize(it) }
    }

    private fun serialize(job: Job): String {
        return JSONObject().apply {
            put("title", job.title)
            put("company", job.company)
            put("location", job.location)
            put("salary", job.salary)
            put("lastDate", job.lastDate)
            put("logoUrl", job.logoUrl)
            put("roleDetails", job.roleDetails)
            put("companyDetails", job.companyDetails)
            put("applyLink", job.applyLink)
        }.toString()
    }

    private fun deserialize(raw: String): Job? {
        return try {
            val json = JSONObject(raw)
            Job(
                title = json.optString("title"),
                company = json.optString("company"),
                location = json.optString("location"),
                salary = json.optString("salary"),
                lastDate = json.optString("lastDate"),
                logoUrl = json.optString("logoUrl"),
                roleDetails = json.optString("roleDetails"),
                companyDetails = json.optString("companyDetails"),
                applyLink = json.optString("applyLink")
            )
        } catch (_: Exception) {
            null
        }
    }
}
