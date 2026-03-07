from fastapi import APIRouter

router = APIRouter()


@router.get("/events")
def notification_events() -> list[dict]:
    return [
        {
            "type": "new_match",
            "message": "A new job matches your skills",
            "deep_link": "jobepedia://job/123",
        },
        {
            "type": "deadline_near",
            "message": "A saved job deadline is near",
            "deep_link": "jobepedia://job/456",
        },
    ]
