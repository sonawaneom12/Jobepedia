from app.core.schemas import MatchBand


def score_job(user_skills: set[str], job_skills: set[str]) -> tuple[float, MatchBand]:
    if not job_skills:
        return 0.0, MatchBand.low

    overlap = len(user_skills.intersection(job_skills))
    score = overlap / len(job_skills)

    if score >= 0.75:
        return score, MatchBand.high
    if score >= 0.4:
        return score, MatchBand.medium
    return score, MatchBand.low
