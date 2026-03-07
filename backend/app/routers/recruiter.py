from fastapi import APIRouter

router = APIRouter()


@router.post("/jobs")
def post_job() -> dict:
    return {"message": "Recruiter job post endpoint ready"}


@router.get("/applicants/{job_id}")
def list_applicants(job_id: str) -> dict:
    return {"job_id": job_id, "applicants": []}


@router.post("/shortlist/{application_id}")
def shortlist(application_id: str) -> dict:
    return {"application_id": application_id, "status": "shortlisted"}


@router.post("/chat/{application_id}")
def recruiter_chat(application_id: str, message: str) -> dict:
    return {"application_id": application_id, "message": message, "status": "sent"}
