from uuid import uuid4
from fastapi import APIRouter, Query
from app.core.schemas import JobIn, JobOut, SkillGapResult
from app.services.in_memory_store import JOBS, USER_SKILLS

router = APIRouter()


@router.post("", response_model=JobOut)
def create_job(payload: JobIn) -> JobOut:
    job = JobOut(id=str(uuid4()), **payload.model_dump())
    JOBS[job.id] = job
    return job


@router.get("", response_model=list[JobOut])
def list_jobs(
    location: str | None = None,
    experience: str | None = None,
    min_salary: str | None = Query(default=None, alias="salary"),
    remote_only: bool = False,
    skills: list[str] | None = None,
    company_type: str | None = None,
    industry: str | None = None,
) -> list[JobOut]:
    jobs = list(JOBS.values())

    if location:
        jobs = [j for j in jobs if location.lower() in j.location.lower()]
    if experience:
        jobs = [j for j in jobs if experience.lower() in j.experience.lower()]
    if min_salary:
        jobs = [j for j in jobs if min_salary.lower() in j.salary.lower()]
    if remote_only:
        jobs = [j for j in jobs if j.job_type.value.lower() == "remote"]
    if skills:
        wanted = {s.lower() for s in skills}
        jobs = [j for j in jobs if wanted.intersection({x.lower() for x in j.skills})]
    if company_type:
        jobs = jobs
    if industry:
        jobs = jobs

    return jobs


@router.post("/{user_id}/{job_id}/skill-gap", response_model=SkillGapResult)
def skill_gap(user_id: str, job_id: str) -> SkillGapResult:
    user_skills = set(USER_SKILLS.get(user_id, []))
    job = JOBS[job_id]
    required = set(job.skills)
    have = sorted(list(user_skills.intersection(required)))
    missing = sorted(list(required.difference(user_skills)))
    suggestions = [f"Learn {m} via beginner-to-advanced roadmap" for m in missing]
    return SkillGapResult(have=have, missing=missing, learning_suggestions=suggestions)
