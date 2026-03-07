from collections import Counter
from fastapi import APIRouter
from app.services.in_memory_store import JOBS

router = APIRouter()


@router.get("/summary")
def analytics_summary() -> dict:
    jobs = list(JOBS.values())
    skills = Counter(s for j in jobs for s in j.skills)
    companies = Counter(j.company for j in jobs)
    cities = Counter(j.location for j in jobs)
    return {
        "most_in_demand_skills": skills.most_common(10),
        "top_hiring_companies": companies.most_common(10),
        "most_hiring_cities": cities.most_common(10),
        "average_salary_by_role": "Derived in data warehouse stage",
    }
