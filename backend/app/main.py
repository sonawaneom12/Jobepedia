from fastapi import FastAPI
from app.routers import (
    jobs,
    recommendations,
    resume,
    applications,
    intelligence,
    notifications,
    feed,
    assistant,
    gov_jobs,
    analytics,
    recruiter,
)

app = FastAPI(title="Jobepedia AI Platform API", version="1.0.0")

app.include_router(jobs.router, prefix="/v1/jobs", tags=["jobs"])
app.include_router(recommendations.router, prefix="/v1/recommendations", tags=["recommendations"])
app.include_router(resume.router, prefix="/v1/resume", tags=["resume"])
app.include_router(applications.router, prefix="/v1/applications", tags=["applications"])
app.include_router(intelligence.router, prefix="/v1/intelligence", tags=["intelligence"])
app.include_router(notifications.router, prefix="/v1/notifications", tags=["notifications"])
app.include_router(feed.router, prefix="/v1/feed", tags=["feed"])
app.include_router(assistant.router, prefix="/v1/assistant", tags=["assistant"])
app.include_router(gov_jobs.router, prefix="/v1/gov-jobs", tags=["gov-jobs"])
app.include_router(analytics.router, prefix="/v1/analytics", tags=["analytics"])
app.include_router(recruiter.router, prefix="/v1/recruiter", tags=["recruiter"])


@app.get("/health")
def health() -> dict[str, str]:
    return {"status": "ok"}
