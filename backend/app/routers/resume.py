from fastapi import APIRouter, UploadFile, File, HTTPException
from app.core.schemas import ResumeParsed
from app.services.parsing import simple_resume_parse
from app.services.in_memory_store import USER_SKILLS

router = APIRouter()


@router.post("/parse", response_model=ResumeParsed)
async def parse_resume(user_id: str, file: UploadFile = File(...)) -> ResumeParsed:
    filename = (file.filename or "").lower()
    if not (filename.endswith(".pdf") or filename.endswith(".docx") or filename.endswith(".txt")):
        raise HTTPException(status_code=400, detail="Only PDF, DOCX, TXT supported")

    content = (await file.read()).decode(errors="ignore")
    parsed = simple_resume_parse(content)
    USER_SKILLS[user_id] = parsed.skills
    return parsed
