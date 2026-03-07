from fastapi import APIRouter
from app.core.schemas import IntelligenceResult
from app.services.in_memory_store import JOBS

router = APIRouter()


@router.get("/{job_id}", response_model=IntelligenceResult)
def job_intelligence(job_id: str) -> IntelligenceResult:
    job = JOBS[job_id]
    salary_range = job.salary or "Market competitive"
    return IntelligenceResult(
        estimated_salary_range=salary_range,
        company_rating=4.1,
        hiring_difficulty="Medium",
        interview_tips=[
            "Practice role-specific fundamentals",
            "Prepare project walkthroughs",
            "Revise core skills listed in the JD",
        ],
    )
