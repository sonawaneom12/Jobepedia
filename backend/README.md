# Jobepedia AI Backend (FastAPI)

This backend is a modular foundation for transforming Jobepedia into an AI-powered career platform.

## Modules included
- Smart job aggregation (normalized schema + ingestion endpoints)
- AI recommendation API contract (embedding-ready)
- Resume parsing endpoint (PDF/DOCX text extraction scaffold)
- Skill gap analyzer endpoint
- Application tracker (status workflow)
- Job intelligence endpoint
- Smart notifications payload endpoint
- Swipe feed endpoint
- AI summarizer endpoint
- AI assistant chatbot endpoint
- Government jobs endpoint
- Advanced search filters endpoint
- Analytics endpoint
- Recruiter portal endpoints

## Run locally
```bash
cd backend
python -m venv .venv
source .venv/bin/activate
pip install -r requirements.txt
uvicorn app.main:app --reload --port 8000
```

## API docs
- Swagger: `http://localhost:8000/docs`

## Notes
This commit establishes a production-friendly modular architecture and API surfaces. AI/ML/vector-db internals are intentionally implemented behind service interfaces for incremental upgrades.
