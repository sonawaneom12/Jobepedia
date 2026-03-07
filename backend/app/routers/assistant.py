from fastapi import APIRouter
from app.core.schemas import AssistantQuery, AssistantResponse

router = APIRouter()


@router.post("/query", response_model=AssistantResponse)
def ask_assistant(payload: AssistantQuery) -> AssistantResponse:
    q = payload.query.lower()
    if "backend" in q and "pune" in q:
        return AssistantResponse(answer="Try filtering backend roles by Pune + 2-5 yrs + Python/Node.js skills.")
    if "data scientist" in q:
        return AssistantResponse(answer="Start with Python, statistics, SQL, ML basics, projects, and model deployment.")
    if "ai engineer" in q:
        return AssistantResponse(answer="Focus on Python, ML/DL, LLMs, vector DBs, APIs, and MLOps.")
    return AssistantResponse(answer="I can help with jobs, skills, interview prep, and learning plans.")
