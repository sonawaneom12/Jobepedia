package com.jobepedia.app.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecentlyViewedJobDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(job: RecentlyViewedJobEntity)

    @Query("SELECT * FROM recently_viewed_jobs ORDER BY viewedAt DESC LIMIT :limit")
    suspend fun getRecent(limit: Int): List<RecentlyViewedJobEntity>

    @Query(
        """
        DELETE FROM recently_viewed_jobs
        WHERE id NOT IN (
            SELECT id FROM recently_viewed_jobs
            ORDER BY viewedAt DESC
            LIMIT :limit
        )
        """
    )
    suspend fun trimTo(limit: Int)
}
