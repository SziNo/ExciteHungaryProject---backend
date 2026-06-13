# Leave Calendar — Backend

REST API for the Leave Calendar application, handling team member leave requests and on-call rotation scheduling.

**Frontend repository:** [https://github.com/SziNo/ExciteHungaryProject---frontend](https://github.com/SziNo/ExciteHungaryProject---frontend)

**Live demo:** [https://excite-hungary-project-frontend.vercel.app](https://excite-hungary-project-frontend.vercel.app)

---

## Tech Stack

- **Java 21**
- **Spring Boot 4.1**
- **Spring Data JPA** + **Hibernate**
- **PostgreSQL**
- **Maven**
- **Lombok**
- **Docker**
- **Railway** (deployment)

---

## Local Setup

### Prerequisites

- Java 21+
- Maven

No local PostgreSQL needed — the dev profile uses an **H2 in-memory database** that starts automatically.

### Steps

```bash
git clone <this-repo-url>
cd backend
./mvnw spring-boot:run
```

The application starts on [http://localhost:8080](http://localhost:8080) with the `dev` profile active by default.

On startup, the following team members are seeded automatically:

- Alice, Bob, Charlie, Diana

---

## Profiles

| Profile | Database             | Activated by                  |
| ------- | -------------------- | ----------------------------- |
| `dev`   | H2 in-memory         | default                       |
| `prod`  | PostgreSQL (Railway) | `SPRING_PROFILES_ACTIVE=prod` |

---

## API Endpoints

### Team Members

```
GET /api/members
```

### Leave Requests

```
GET    /api/leaves                  # get all (optional: ?memberId=&status=)
POST   /api/leaves                  # create new leave request
PATCH  /api/leaves/{id}/status      # update status (?status=APPROVED|PENDING|REJECTED)
```

#### POST /api/leaves — request body

```json
{
  "teamMemberId": 1,
  "startDate": "2026-06-30",
  "endDate": "2026-07-02",
  "reason": "Vacation"
}
```

### On-Call Schedule

```
GET /api/oncall?from=2026-06-01&to=2026-08-31
```

Returns weekly on-call rotation with a `hasConflict` flag when the on-call person has an approved leave that week.

---

## Deployment

The application runs in a Docker container on Railway with a PostgreSQL database.

### Build and run with Docker locally

```bash
docker build -t leave-calendar .
docker run -p 8080:8080 leave-calendar
```

### Environment variables (prod)

| Variable                 | Description       |
| ------------------------ | ----------------- |
| `SPRING_PROFILES_ACTIVE` | Set to `prod`     |
| `PGHOST`                 | PostgreSQL host   |
| `PGPORT`                 | PostgreSQL port   |
| `PGDATABASE`             | Database name     |
| `PGUSER`                 | Database user     |
| `PGPASSWORD`             | Database password |

On Railway, the PostgreSQL variables are injected automatically when a PostgreSQL service is linked to the project.

---

## Assumptions

- Team members are fixed and seeded on first startup
- On-call rotation is calculated from a fixed epoch date and repeats every 4 weeks in order: Alice → Bob → Charlie → Diana
- Overlapping leave requests for the same person are rejected unless the existing one has status `REJECTED`
- On-call reassignment is not automatic — conflicts are highlighted only
