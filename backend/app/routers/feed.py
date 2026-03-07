from fastapi import APIRouter
from app.services.in_memory_store import JOBS

router = APIRouter()


@router.get("/cards")
def feed_cards(limit: int = 20, offset: int = 0) -> list[dict]:
    jobs = list(JOBS.values())[offset:offset + limit]
    return [
        {
            "job_id": j.id,
            "title": j.title,
            "company": j.company,
            "location": j.location,
            "salary": j.salary,
            "apply_link": j.apply_link,
        }
        for j in jobs
    ]
