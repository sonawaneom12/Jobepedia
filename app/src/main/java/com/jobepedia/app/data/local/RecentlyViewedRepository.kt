package com.jobepedia.app.data.local

import android.content.Context
import com.jobepedia.app.data.model.Job

object RecentlyViewedRepository {

    private const val LIMIT = 10

    suspend fun trackViewed(context: Context, job: Job) {
        val dao = AppDatabase.getInstance(context).recentlyViewedJobDao()
        dao.upsert(
            RecentlyViewedJobEntity(
                id = "${job.title}|${job.company}|${job.applyLink}",
                title = job.title,
                company = job.company,
                location = job.location,
                salary = job.salary,
                lastDate = job.lastDate,
                logoUrl = job.logoUrl,
                roleDetails = job.roleDetails,
                companyDetails = job.companyDetails,
                applyLink = job.applyLink,
                jobHighlights = job.jobHighlights,
                perksBenefits = job.perksBenefits,
                applicationProcess = job.applicationProcess,
                viewedAt = System.currentTimeMillis()
            )
        )
        dao.trimTo(LIMIT)
    }

    suspend fun getRecent(context: Context): List<Job> {
        return AppDatabase.getInstance(context)
            .recentlyViewedJobDao()
            .getRecent(LIMIT)
            .map {
                Job(
                    title = it.title,
                    company = it.company,
                    location = it.location,
                    salary = it.salary,
                    lastDate = it.lastDate,
                    logoUrl = it.logoUrl,
                    roleDetails = it.roleDetails,
                    companyDetails = it.companyDetails,
                    applyLink = it.applyLink,
                    jobHighlights = it.jobHighlights,
                    perksBenefits = it.perksBenefits,
                    applicationProcess = it.applicationProcess
                )
            }
    }
}
