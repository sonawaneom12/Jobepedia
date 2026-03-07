from uuid import uuid4
from fastapi import APIRouter
from app.core.schemas import ApplicationRecord, ApplicationStatus
from app.services.in_memory_store import APPLICATIONS

router = APIRouter()


@router.post("/{user_id}/{job_id}", response_model=ApplicationRecord)
def create_application(user_id: str, job_id: str, status: ApplicationStatus) -> ApplicationRecord:
    rec = ApplicationRecord(id=str(uuid4()), user_id=user_id, job_id=job_id, status=status)
    APPLICATIONS[rec.id] = rec
    return rec


@router.patch("/{application_id}", response_model=ApplicationRecord)
def update_status(application_id: str, status: ApplicationStatus) -> ApplicationRecord:
    rec = APPLICATIONS[application_id]
    rec.status = status
    APPLICATIONS[application_id] = rec
    return rec


@router.get("/{user_id}", response_model=list[ApplicationRecord])
def list_applications(user_id: str) -> list[ApplicationRecord]:
    return [x for x in APPLICATIONS.values() if x.user_id == user_id]
