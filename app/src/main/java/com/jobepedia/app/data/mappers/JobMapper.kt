package com.jobepedia.app.data.mappers

import com.google.firebase.firestore.DocumentSnapshot
import com.jobepedia.app.data.model.Job

object JobMapper {

    fun fromDocument(document: DocumentSnapshot): Job {
        val title = document.getString("title").orEmpty().ifBlank { "Untitled Role" }
        val company = document.getString("company").orEmpty().ifBlank { "Company" }
        val location = document.getString("location").orEmpty().ifBlank { "Location not specified" }
        val salary = document.getString("salary").orEmpty().ifBlank { "Salary not disclosed" }
        val lastDate = document.getString("lastDate").orEmpty().ifBlank { "TBA" }
        val roleDetails = document.getString("roleDetails").orEmpty().ifBlank {
            "Detailed role information will be updated soon."
        }
        val companyDetails = document.getString("companyDetails").orEmpty().ifBlank {
            "Company profile will be updated soon."
        }

        return Job(
            title = title,
            company = company,
            location = location,
            salary = salary,
            lastDate = lastDate,
            logoUrl = document.getString("logoUrl").orEmpty(),
            roleDetails = roleDetails,
            companyDetails = companyDetails,
            applyLink = document.getString("applyLink").orEmpty()
        )
    }
}
