package com.jobepedia.app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recently_viewed_jobs")
data class RecentlyViewedJobEntity(
    @PrimaryKey val id: String,
    val title: String,
    val company: String,
    val location: String,
    val salary: String,
    val lastDate: String,
    val logoUrl: String,
    val roleDetails: String,
    val companyDetails: String,
    val applyLink: String,
    val jobHighlights: String,
    val perksBenefits: String,
    val applicationProcess: String,
    val viewedAt: Long
)
