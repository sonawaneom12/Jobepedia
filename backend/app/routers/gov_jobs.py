from fastapi import APIRouter
from app.services.in_memory_store import GOV_JOBS

router = APIRouter()


@router.get("", response_model=list[dict])
def list_gov_jobs() -> list[dict]:
    return GOV_JOBS
