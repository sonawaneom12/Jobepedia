from fastapi import APIRouter
from app.core.schemas import RecommendationItem
from app.services.in_memory_store import JOBS, USER_SKILLS
from app.services.recommendation import score_job

router = APIRouter()


@router.get("/{user_id}", response_model=list[RecommendationItem])
def get_recommendations(user_id: str) -> list[RecommendationItem]:
    user_skills = set(USER_SKILLS.get(user_id, []))
    ranked: list[RecommendationItem] = []

    for job in JOBS.values():
        score, band = score_job(user_skills, set(job.skills))
        ranked.append(
            RecommendationItem(job_id=job.id, relevance_score=round(score, 4), match_band=band)
        )

    ranked.sort(key=lambda x: x.relevance_score, reverse=True)
    return ranked
