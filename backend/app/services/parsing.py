import re
from app.core.schemas import ResumeParsed


def simple_resume_parse(text: str) -> ResumeParsed:
    lines = [line.strip() for line in text.splitlines() if line.strip()]
    name = lines[0] if lines else ""

    skill_candidates = [
        "python", "java", "kotlin", "docker", "kubernetes", "system design",
        "sql", "postgresql", "mongodb", "fastapi", "node.js", "android",
    ]
    lower = text.lower()
    skills = [s for s in skill_candidates if s in lower]

    sections = {
        "education": re.findall(r"education[:\-]\s*(.*)", text, flags=re.IGNORECASE),
        "experience": re.findall(r"experience[:\-]\s*(.*)", text, flags=re.IGNORECASE),
        "projects": re.findall(r"projects?[:\-]\s*(.*)", text, flags=re.IGNORECASE),
        "certifications": re.findall(r"certifications?[:\-]\s*(.*)", text, flags=re.IGNORECASE),
    }

    return ResumeParsed(
        name=name,
        skills=skills,
        education=sections["education"],
        experience=sections["experience"],
        projects=sections["projects"],
        certifications=sections["certifications"],
    )
