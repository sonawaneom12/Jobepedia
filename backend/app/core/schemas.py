from enum import Enum
from typing import Literal
from pydantic import BaseModel, Field


class JobType(str, Enum):
    full_time = "Full-time"
    internship = "Internship"
    remote = "Remote"
    contract = "Contract"


class MatchBand(str, Enum):
    high = "High Match"
    medium = "Medium Match"
    low = "Low Match"


class ApplicationStatus(str, Enum):
    saved = "Saved"
    applied = "Applied"
    under_review = "Under Review"
    interview = "Interview Scheduled"
    rejected = "Rejected"
    offer = "Offer"


class JobIn(BaseModel):
    title: str
    company: str
    location: str
    salary: str = ""
    experience: str = ""
    skills: list[str] = Field(default_factory=list)
    apply_link: str
    job_type: JobType
    description: str = ""
    source: Literal["company_career", "startup_board", "gov_site", "api"] = "api"


class JobOut(JobIn):
    id: str


class ResumeParsed(BaseModel):
    name: str = ""
    skills: list[str] = Field(default_factory=list)
    education: list[str] = Field(default_factory=list)
    experience: list[str] = Field(default_factory=list)
    projects: list[str] = Field(default_factory=list)
    certifications: list[str] = Field(default_factory=list)


class RecommendationItem(BaseModel):
    job_id: str
    relevance_score: float
    match_band: MatchBand


class SkillGapResult(BaseModel):
    have: list[str]
    missing: list[str]
    learning_suggestions: list[str]


class IntelligenceResult(BaseModel):
    estimated_salary_range: str
    company_rating: float
    hiring_difficulty: str
    interview_tips: list[str]


class AssistantQuery(BaseModel):
    query: str


class AssistantResponse(BaseModel):
    answer: str


class ApplicationRecord(BaseModel):
    id: str
    user_id: str
    job_id: str
    status: ApplicationStatus
