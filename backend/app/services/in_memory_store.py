from app.core.schemas import JobOut, ApplicationRecord


JOBS: dict[str, JobOut] = {}
APPLICATIONS: dict[str, ApplicationRecord] = {}
USER_SKILLS: dict[str, list[str]] = {}
GOV_JOBS: list[dict] = [
    {
        "id": "gov-1",
        "exam": "UPSC CSE",
        "notification": "Civil Services 2026 notification released",
        "eligibility": "Graduate",
        "important_dates": "Apply by 2026-03-30",
        "apply_link": "https://upsc.gov.in",
    },
    {
        "id": "gov-2",
        "exam": "SSC CGL",
        "notification": "SSC CGL Tier-I registration open",
        "eligibility": "Graduate",
        "important_dates": "Apply by 2026-04-12",
        "apply_link": "https://ssc.nic.in",
    },
]
