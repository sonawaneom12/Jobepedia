# Jobepedia AI Platform Roadmap

## Implemented in this phase
- Modular FastAPI backend service in `backend/` with endpoints for all requested modules.
- Normalized job schema and ingestion APIs.
- Recommendation, skill-gap, resume parsing, assistant, gov-jobs, analytics, tracker, recruiter APIs.
- Architecture prepared for PostgreSQL + vector DB integration.

## Next engineering steps
1. Replace in-memory stores with PostgreSQL models + migrations.
2. Add async job collectors (crawler workers) for company/startup/gov sources.
3. Integrate embeddings provider + vector DB (Qdrant/Pinecone/pgvector).
4. Add OCR/text extraction for PDF/DOCX using robust parsers.
5. Add Firebase Admin notification dispatcher and deep-link templates.
6. Add auth (JWT/OAuth) + role-based access (candidate/recruiter/admin).
7. Add production observability: metrics, traces, alerting.
8. Connect Android app screens to these APIs.
