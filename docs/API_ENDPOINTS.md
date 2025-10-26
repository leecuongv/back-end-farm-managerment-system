# Farm Management System — API Contracts for Frontend

This file provides explicit request and response shapes (JSON examples and model field definitions) so the frontend team can integrate quickly. It expands the earlier brief summary into exact payload contracts and example bodies for each endpoint group.

Global notes
- Base path: controllers are under the application root (e.g. POST /auth/login). If you run the app behind a proxy that adds `/api`, prepend that prefix.
- Authentication: end-to-end uses JWT in Authorization header: `Authorization: Bearer <token>`. Where an endpoint requires authentication I mark it with "Auth: required".
- Timestamps use ISO-8601 (e.g., 2025-10-26T16:00:00).

Model definitions (primary fields)
- User
  - id: string
  - username: string
  - email: string
  - password: string (write-only; hashed in production)
  - authProvider: enum (e.g., GOOGLE, LOCAL)
  - googleId: string
  - fullName: string
  - role: enum (e.g., ADMIN, MANAGER, STAFF)
  - farmIds: string[]
  - isActive: boolean
  - createdAt: string (ISO datetime)

- Farm
  - id: string
  - name: string
  - location: string
  - createdAt: string

- Animal
  - id, farmId, tagId, species, animalType (enum), batchId, enclosureId, feedPlanId, birthDate (YYYY-MM-DD), status (enum)

- Enclosure
  - id, farmId, name, type (enum), capacity (int), currentOccupancy (int)

- InventoryItem
  - id, farmId, name, category (string), quantity (double), unit (string), lowStockThreshold (double)

- InventoryLog
  - id, farmId, itemId, batchCode, type (IN|OUT), quantity, notes, usageTarget { type: ENCLOSURE|ANIMAL, id }, recordedBy, date (ISO)

- Batch
  - id, farmId, batchCode, type (ANIMAL|CROP|INVENTORY), description, source, entryDate (YYYY-MM-DD), relatedItemIds[]

- Plot
  - id, farmId, name, area (double), location (string), createdAt

- Season
  - id, farmId, name, cropType, startDate (YYYY-MM-DD), endDate (YYYY-MM-DD), plotIds[], notes

- CropEvent
  - id, farmId, plotId, seasonId, eventType (string), date (YYYY-MM-DD), notes, recordedBy

- Task
  - id, farmId, title, description, assignedTo (user id), status (TODO|IN_PROGRESS|DONE), dueDate, createdAt, createdBy

- FeedPlan
  - id, farmId, name, stage (enum), description, feedDetails[]

- FinancialTransaction
  - id, farmId, type (EXPENSE|REVENUE), amount (decimal), description, category, relatedBatchId, date (YYYY-MM-DD), recordedBy

---

Example endpoint contracts (explicit JSON examples)

Authentication

- POST /auth/login
  - Auth: not required
  - Request JSON:

    {
      "email": "admin@example.com",
      "password": "secret"
    }

  - Success response (200):

    {
      "message": "Login successful",
      "token": "ey...",
      "tokenType": "Bearer",
      "expiresAt": "2025-10-27T16:00:00",
      "user": {
        "id": "u123",
        "username": "admin",
        "email": "admin@example.com",
        "role": "ADMIN",
        "farmIds": ["farm123"],
        "isActive": true
      }
    }


Users

- GET /users
  - Auth: required (admin)
  - Request: none
  - Response (200): [ User, ... ]

- POST /users
  - Auth: required (admin)
  - Request example:

    {
      "username": "john.doe",
      "email": "john@example.com",
      "password": "plain-pass",
      "fullName": "John Doe",
      "role": "MANAGER",
      "farmIds": ["farm123"]
    }

  - Response (200): created User object (id assigned). Frontend: store `id` and `email`.

- PUT /users/{id}/assign-farm
  - Auth: required (admin)
  - Request:

    {
      "farmId": "farm123",
      "role": "STAFF"
    }

  - Response: updated User

- POST /users/{id}/activate
  - Auth: required (admin)
  - Request:

    { "isActive": true }

  - Response: updated User

Farms

- GET /farms
  - Auth: required
  - Response: [ { id, name, location, createdAt }, ... ]

- POST /farms
  - Auth: required
  - Request example:

    {
      "name": "North Ridge Farm",
      "location": "Colorado"
    }

  - Response: created Farm (id assigned)

Animals

- GET /animals?farmId={farmId}
  - Auth: required
  - Response: [ Animal, ... ]

- POST /animals
  - Auth: required
  - Request example:

    {
      "farmId": "farm123",
      "tagId": "SOW-001",
      "species": "Pig",
      "animalType": "BREEDING_FEMALE",
      "birthDate": "2022-01-15",
      "status": "HEALTHY"
    }

  - Response: created Animal with id

Enclosures

- GET /enclosures?farmId={farmId}
  - Response: [ Enclosure, ... ]

- POST /enclosures
  - Request example:

    {
      "farmId": "farm123",
      "name": "Breeding Barn",
      "type": "BREEDING_PEN",
      "capacity": 20
    }

  - Response: created Enclosure

Inventory Items

- GET /inventory-items?farmId={farmId}
  - Response: [ InventoryItem, ... ]

- POST /inventory-items
  - Request example:

    {
      "farmId": "farm123",
      "name": "Starter Feed",
      "category": "FEED",
      "quantity": 150.5,
      "unit": "kg",
      "lowStockThreshold": 50
    }

  - Response: created InventoryItem

Inventory Logs

- GET /inventory-logs?farmId={farmId}
  - Response: [ InventoryLog, ... ]

- POST /inventory-logs
  - Request example:

    {
      "farmId": "farm123",
      "itemId": "item-001",
      "type": "OUT",
      "quantity": 5.0,
      "notes": "Fed sows",
      "usageTarget": { "type": "ENCLOSURE", "id": "enc-123" }
    }

  - Response: created InventoryLog

Inventory Audits

- GET /inventory-audits?farmId={farmId}
  - Response: [ InventoryAudit, ... ]

- POST /inventory-audits
  - Request: InventoryAudit JSON (see model)
  - Response: created InventoryAudit

Batches

- GET /batches?farmId={farmId}
  - Response: [ Batch, ... ]

- POST /batches
  - Request example:

    {
      "farmId": "farm123",
      "batchCode": "BATCH-001",
      "type": "CROP",
      "description": "Harvest batch",
      "entryDate": "2025-09-01",
      "relatedItemIds": ["item-001"]
    }

  - Response: created Batch

Plots

- GET /plots?farmId={farmId}
  - Response: [ Plot, ... ]

- POST /plots
  - Request example:

    {
      "farmId": "farm123",
      "name": "North Field A",
      "area": 1.5,
      "location": "GPS or description"
    }

  - Response: created Plot

Seasons

- GET /seasons?farmId={farmId}
  - Response: [ Season, ... ]

- POST /seasons
  - Request example:

    {
      "farmId": "farm123",
      "name": "Winter Wheat 2025",
      "cropType": "Wheat",
      "startDate": "2025-01-15",
      "endDate": "2025-05-30",
      "plotIds": ["plot123"]
    }

  - Response: created Season

- POST /seasons/{id}/harvests
  - Request: Batch JSON (see Batches example). Creates a harvest Batch linked to the season's farm.
  - Response: created Batch

Crop Events

- GET /crop-events?farmId={farmId}&plotId={plotId}
  - Response: [ CropEvent, ... ]

- POST /crop-events
  - Request example:

    {
      "farmId": "farm123",
      "plotId": "plot123",
      "seasonId": "season123",
      "eventType": "PLANTING",
      "date": "2025-01-16",
      "notes": "Planted seeds variety X"
    }

  - Response: created CropEvent

Animal Events

- GET /animal-events?animalId={animalId}
  - Response: [ AnimalEvent, ... ]

- POST /animal-events
  - Request example:

    {
      "animalId": "a123",
      "type": "VACCINATION",
      "date": "2025-02-01",
      "notes": "Rabies vaccine"
    }

  - Response: created AnimalEvent

Tasks

- GET /tasks?farmId={farmId}
  - Response: [ Task, ... ]

- POST /tasks
  - Request example:

    {
      "farmId": "farm123",
      "title": "Irrigate field A",
      "description": "Irrigate after planting",
      "assignedTo": "u234",
      "dueDate": "2025-01-20"
    }

  - Response: created Task

Feed Plans

- GET /feed-plans?farmId={farmId}
  - Response: [ FeedPlan, ... ]

- POST /feed-plans
  - Request example:

    {
      "farmId": "farm123",
      "name": "Starter Feed Plan",
      "stage": "STARTER",
      "description": "Feed schedule for starter piglets",
      "feedDetails": [ { "feedId": "item-001", "amount": 1.5 } ]
    }

  - Response: created FeedPlan

Financial Transactions

- GET /financial-transactions?farmId={farmId}
  - Response: [ FinancialTransaction, ... ]

- POST /financial-transactions
  - Request example:

    {
      "farmId": "farm123",
      "type": "EXPENSE",
      "amount": 1250.50,
      "description": "Purchased feed",
      "category": "SUPPLY",
      "date": "2025-09-01"
    }

  - Response: created FinancialTransaction

---

How the frontend should use this file
- Use the exact JSON field names shown above when calling endpoints.
- Include Authorization header for protected endpoints.
- Dates: send as YYYY-MM-DD for LocalDate fields, and ISO-8601 datetimes for createdAt/date fields where applicable.
- On creation endpoints (POST), expect the server to return the created resource with an `id` assigned.

Next steps I can take (pick one):
1. Expand each example into full request+response bodies using the controller @ExampleObject values where present.
2. Annotate which endpoints require which roles (ADMIN, MANAGER, STAFF) by scanning SecurityConfig and controller-level checks.
3. Generate an OpenAPI JSON file by starting the app and saving `/v3/api-docs` to `docs/openapi.json`.

If you pick 1 or 3 I will produce the files directly in the repo so frontend can import or use them.
