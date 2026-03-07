# Firebase setup for Jobepedia (jobs + notifications)

This app expects Firestore `jobs` collection documents to include specific fields.

## 1) Firestore schema
Use `firebase/firestore_jobs_schema.json` as the source of truth.

Minimum fields to provide in each `jobs` document:
- `title` (string)
- `company` (string)
- `category` (string)
- `location` (string)
- `salary` (string)
- `lastDate` (string)
- `applyLink` (string)

Optional but recommended:
- `logoUrl`
- `roleDetails`
- `companyDetails`
- `jobHighlights`
- `perksBenefits`
- `applicationProcess`

> The app now has fallback mapping for missing optional fields, but you should still populate them for best UX.
>
> The Job Detail screen reads these exact Firestore keys for rich sections:
> - `jobHighlights`
> - `perksBenefits`
> - `applicationProcess`

## 2) Push notification topic
The app subscribes users to `job_alerts` when notifications are enabled.
You must publish FCM messages to topic `job_alerts` from Firebase Console/Admin SDK.

## 3) Google services
Ensure `app/google-services.json` matches package `com.jobepedia.app` and your target Firebase project.
