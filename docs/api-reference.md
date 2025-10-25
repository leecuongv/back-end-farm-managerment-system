# Farm Management API Reference

This document lists the current REST endpoints exposed by the Farm Management backend along with their expected inputs and response shapes. Unless noted otherwise, the server responds with standard HTTP status codes (2xx for success, 4xx for client errors, 5xx for unexpected failures) and payloads encoded as JSON.

## Authentication

### POST `/auth/login`
- **Purpose:** Authenticate a user with an email/password pair.
- **Request Headers:** None.
- **Request Body:**
  ```json
  {
    "email": "admin@farm.local",
    "password": "admin123"
  }
  ```
- **Success Response (200):**
  ```json
  {
    "message": "Login successful",
    "user": {
      "id": "<userId>",
      "username": "admin",
      "email": "admin@farm.local",
      "fullName": "Farm Administrator",
      "role": "ADMIN",
      "farmIds": ["<farmId>"],
      "active": true,
      "createdAt": "2024-01-10T08:30:00"
    }
  }
  ```
  Notes: The `password` field is stripped before returning the user object.
- **Error Responses:**
  - `401` with body `"Invalid credentials"` when email/password mismatch.
  - `403` with body `"Account not activated by administrator."` when `isActive == false`.

### POST `/auth/google`
- **Purpose:** Placeholder endpoint for Google OAuth login callback.
- **Request Headers:** None.
- **Request Body:** Arbitrary map containing the Google `id_token` payload (verification not yet implemented).
- **Success Response (200):**
  ```json
  {
    "message": "Google login endpoint hit. Verification logic needed."
  }
  ```

## Farms

### GET `/farms`
- **Purpose:** List all farms in the system.
- **Request Headers:** None.
- **Query Parameters:** None.
- **Success Response (200):** Array of farm objects.
  ```json
  [
    {
      "id": "<farmId>",
      "name": "Sunny Acres Farm",
      "location": "Dak Lak, Vietnam",
      "createdAt": "2023-05-01T09:00:00"
    }
  ]
  ```

### POST `/farms`
- **Purpose:** Create a new farm record.
- **Request Headers:**
  - `user-id` (required) — ID of the administrator performing the action.
- **Request Body:**
  ```json
  {
    "name": "Green Valley Ranch",
    "location": "Lam Dong, Vietnam"
  }
  ```
- **Success Response (200):** Newly created farm object (same shape as GET response) with generated `id` and `createdAt` timestamp.

### GET `/farms/{id}`
- **Purpose:** Retrieve a farm by ID.
- **Success Response (200):** Farm object.
- **Error Response:** `404` when the farm does not exist.

### PUT `/farms/{id}`
- **Purpose:** Update farm name/location.
- **Request Headers:** `user-id` (required).
- **Request Body:**
  ```json
  {
    "name": "Updated Farm Name",
    "location": "Updated Location"
  }
  ```
- **Success Response (200):** Updated farm object.
- **Error Response:** `404` when the farm does not exist.

### DELETE `/farms/{id}`
- **Purpose:** Remove a farm.
- **Request Headers:** `user-id` (required).
- **Success Response (200):** Empty body (`{}`).
- **Error Response:** `404` when the farm does not exist.

## Users

### GET `/users`
- **Purpose:** List all users.
- **Success Response (200):** Array of user objects.

### POST `/users`
- **Purpose:** Create a new user (admin only).
- **Request Headers:** `user-id` (required, admin ID).
- **Request Body:**
  ```json
  {
    "username": "manager.sara",
    "email": "sara@farm.local",
    "password": "manager123",
    "fullName": "Sara Nguyen",
    "role": "MANAGER",
    "farmIds": ["<farmId>"],
    "active": true
  }
  ```
- **Success Response (200):** Created user object (password echoed back as stored; hashing not yet applied).

### GET `/users/{id}`
- **Purpose:** Retrieve user details by ID.
- **Success Response (200):** User object.
- **Error Response:** `404` when the user does not exist.

### PUT `/users/{id}/assign-farm`
- **Purpose:** Append a farm to the user and update their role.
- **Request Headers:** `user-id` (required, admin ID).
- **Request Body:**
  ```json
  {
    "farmId": "<farmId>",
    "role": "MANAGER"
  }
  ```
- **Success Response (200):** Updated user object with `farmIds` containing the new farm and `role` set accordingly.
- **Error Response:** `404` when the user does not exist.

### POST `/users/{id}/activate`
- **Purpose:** Toggle a user account's active status.
- **Request Headers:** `user-id` (required, admin ID).
- **Request Body:**
  ```json
  {
    "isActive": true
  }
  ```
- **Success Response (200):** Updated user object reflecting the new `active` state.
- **Error Response:** `404` when the user does not exist.

## Animals

### GET `/animals`
- **Purpose:** List animals belonging to a farm.
- **Query Parameters:** `farmId` (required).
- **Success Response (200):** Array of animal objects.
  ```json
  [
    {
      "id": "<animalId>",
      "farmId": "<farmId>",
      "tagId": "SOW-1001",
      "species": "Pig",
      "animalType": "BREEDING_FEMALE",
      "status": "HEALTHY",
      "enclosureId": "<enclosureId>",
      "feedPlanId": "<feedPlanId>",
      "healthRecords": [],
      "growthRecords": [],
      "reproductionLogs": []
    }
  ]
  ```

### POST `/animals`
- **Purpose:** Create a new animal record.
- **Request Headers:** `user-id` (required).
- **Request Body:** Full animal document (see response shape above).
- **Success Response (200):** Newly created animal object.

### GET `/animals/{id}`
- **Purpose:** Retrieve an animal by ID.
- **Success Response (200):** Animal object.
- **Error Response:** `404` when not found.

### PUT `/animals/{id}`
- **Purpose:** Update mutable animal properties.
- **Request Headers:** `user-id` (required).
- **Request Body:** Partial animal document containing fields to overwrite (e.g. `tagId`, `status`, `enclosureId`, `feedPlanId`).
- **Success Response (200):** Updated animal object.
- **Error Response:** `404` when not found.

### DELETE `/animals/{id}`
- **Purpose:** Remove an animal record.
- **Request Headers:** `user-id` (required).
- **Success Response (200):** Empty body.
- **Error Response:** `404` when not found.

## Animal Events

### GET `/animal-events`
- **Purpose:** List events for a specific animal.
- **Query Parameters:** `animalId` (required).
- **Success Response (200):** Array of animal event objects.

### POST `/animal-events`
- **Purpose:** Create a new animal event (entry, sale, death, breeder selection, etc.).
- **Request Headers:** `user-id` (required).
- **Request Body:**
  ```json
  {
    "farmId": "<farmId>",
    "animalId": "<animalId>",
    "type": "ENTRY",
    "date": "2024-05-01",
    "notes": "Joined breeding group",
    "price": 0,
    "recordedBy": "<userId>"
  }
  ```
- **Success Response (200):** Newly created event object.

## Batches

### GET `/batches`
- **Purpose:** List batches for a farm.
- **Query Parameters:** `farmId` (required).
- **Success Response (200):** Array of batch objects.

### POST `/batches`
- **Purpose:** Create a new batch record.
- **Request Headers:** `user-id` (required).
- **Request Body:**
  ```json
  {
    "farmId": "<farmId>",
    "batchCode": "FARM1-AN-2024-Q1",
    "type": "ANIMAL",
    "description": "Q1 breeding herd",
    "source": "In-house breeding",
    "entryDate": "2024-01-01",
    "relatedItemIds": ["<inventoryItemId>"]
  }
  ```
- **Success Response (200):** Newly created batch object.

### GET `/batches/{id}`
- **Purpose:** Retrieve a batch by ID.
- **Success Response (200):** Batch object.
- **Error Response:** `404` when not found.

## Enclosures

### GET `/enclosures`
- **Purpose:** List enclosures for a farm.
- **Query Parameters:** `farmId` (required).
- **Success Response (200):** Array of enclosure objects.

### POST `/enclosures`
- **Purpose:** Create a new enclosure.
- **Request Headers:** `user-id` (required).
- **Request Body:**
  ```json
  {
    "farmId": "<farmId>",
    "name": "Breeding Pen A",
    "type": "BREEDING_PEN",
    "capacity": 25,
    "currentOccupancy": 14,
    "feedConsumptionLogs": [],
    "medicineConsumptionLogs": []
  }
  ```
- **Success Response (200):** Newly created enclosure object.

### PUT `/enclosures/{id}`
- **Purpose:** Update enclosure metadata.
- **Request Headers:** `user-id` (required).
- **Request Body:** Fields to update (`name`, `type`, `capacity`, etc.).
- **Success Response (200):** Updated enclosure object.
- **Error Response:** `404` when not found.

### DELETE `/enclosures/{id}`
- **Purpose:** Remove an enclosure.
- **Request Headers:** `user-id` (required).
- **Success Response (200):** Empty body.
- **Error Response:** `404` when not found.

## Feed Plans

### GET `/feed-plans`
- **Purpose:** List feed plans for a farm.
- **Query Parameters:** `farmId` (required).
- **Success Response (200):** Array of feed plan objects.

### POST `/feed-plans`
- **Purpose:** Create a new feed plan.
- **Request Headers:** `user-id` (required).
- **Request Body:**
  ```json
  {
    "farmId": "<farmId>",
    "name": "Breeding Sow Plan",
    "stage": "GESTATION_LATE",
    "description": "Daily ration for pregnant sows",
    "feedDetails": [
      { "feedItemId": "<inventoryItemId>", "quantityPerDay": 3.5, "unit": "kg" }
    ]
  }
  ```
- **Success Response (200):** Newly created feed plan object.

### PUT `/feed-plans/{id}`
- **Purpose:** Update feed plan content.
- **Request Headers:** `user-id` (required).
- **Request Body:** Fields to update (`name`, `description`, `stage`, `feedDetails`).
- **Success Response (200):** Updated feed plan object.
- **Error Response:** `404` when not found.

### DELETE `/feed-plans/{id}`
- **Purpose:** Remove a feed plan.
- **Request Headers:** `user-id` (required).
- **Success Response (200):** Empty body.
- **Error Response:** `404` when not found.

## Inventory Items

### GET `/inventory-items`
- **Purpose:** List inventory items for a farm.
- **Query Parameters:** `farmId` (required).
- **Success Response (200):** Array of inventory item objects.

### POST `/inventory-items`
- **Purpose:** Create a new inventory item.
- **Request Headers:** `user-id` (required).
- **Request Body:**
  ```json
  {
    "farmId": "<farmId>",
    "name": "Grower Feed Mix",
    "category": "FEED",
    "quantity": 420.0,
    "unit": "kg",
    "lowStockThreshold": 100.0
  }
  ```
- **Success Response (200):** Newly created inventory item object.

### PUT `/inventory-items/{id}`
- **Purpose:** Update inventory item details.
- **Request Headers:** `user-id` (required).
- **Request Body:** Fields to update (`name`, `category`, `quantity`, `unit`, `lowStockThreshold`).
- **Success Response (200):** Updated inventory item object.
- **Error Response:** `404` when not found.

### DELETE `/inventory-items/{id}`
- **Purpose:** Remove an inventory item from the catalog.
- **Request Headers:** `user-id` (required).
- **Success Response (200):** Empty body.
- **Error Response:** `404` when not found.

## Inventory Logs

### GET `/inventory-logs`
- **Purpose:** List inventory movement logs for a farm.
- **Query Parameters:** `farmId` (required).
- **Success Response (200):** Array of inventory log objects.

### POST `/inventory-logs`
- **Purpose:** Record inventory in/out activity.
- **Request Headers:** `user-id` (required).
- **Request Body:**
  ```json
  {
    "farmId": "<farmId>",
    "itemId": "<inventoryItemId>",
    "batchCode": "FARM1-AN-2024-Q1",
    "type": "OUT",
    "quantity": 62.0,
    "notes": "Two-day feed allocation",
    "recordedBy": "<userId>",
    "date": "2024-05-20T08:00:00",
    "usageTarget": {
      "type": "ENCLOSURE",
      "id": "<enclosureId>"
    }
  }
  ```
- **Success Response (200):** Newly created inventory log object.

## Inventory Audits

### GET `/inventory-audits`
- **Purpose:** List inventory audit sessions for a farm.
- **Query Parameters:** `farmId` (required).
- **Success Response (200):** Array of inventory audit objects.

### POST `/inventory-audits`
- **Purpose:** Create a new inventory audit record.
- **Request Headers:** `user-id` (required).
- **Request Body:**
  ```json
  {
    "farmId": "<farmId>",
    "date": "2024-05-10",
    "conductedBy": "<userId>",
    "status": "COMPLETED",
    "items": [
      {
        "itemId": "<inventoryItemId>",
        "itemName": "Dairy Cow Feed",
        "systemQuantity": 600,
        "actualQuantity": 590,
        "discrepancy": -10,
        "notes": "Minor loss"
      }
    ]
  }
  ```
- **Success Response (200):** Newly created inventory audit object.

## Financial Transactions

### GET `/financial-transactions`
- **Purpose:** List financial transactions for a farm.
- **Query Parameters:** `farmId` (required).
- **Success Response (200):** Array of financial transaction objects.

### POST `/financial-transactions`
- **Purpose:** Log a revenue or expense entry.
- **Request Headers:** `user-id` (required).
- **Request Body:**
  ```json
  {
    "farmId": "<farmId>",
    "type": "EXPENSE",
    "amount": 1250.5,
    "description": "Bulk grower feed purchase",
    "category": "Feed",
    "relatedBatchId": "<batchId>",
    "date": "2024-05-15",
    "recordedBy": "<userId>"
  }
  ```
- **Success Response (200):** Newly created financial transaction object.

## Tasks

### GET `/tasks`
- **Purpose:** List tasks for a farm.
- **Query Parameters:** `farmId` (required).
- **Success Response (200):** Array of task objects.

### POST `/tasks`
- **Purpose:** Create a task and assign it to a user.
- **Request Headers:** `user-id` (required).
- **Request Body:**
  ```json
  {
    "farmId": "<farmId>",
    "title": "Schedule farrowing checks",
    "description": "Monitor sows expected to farrow next month",
    "assignedTo": "<userId>",
    "status": "IN_PROGRESS",
    "dueDate": "2024-06-05",
    "createdAt": "2024-05-20T08:00:00",
    "createdBy": "<userId>"
  }
  ```
- **Success Response (200):** Newly created task object.

### PUT `/tasks/{id}`
- **Purpose:** Update task metadata.
- **Request Headers:** `user-id` (required).
- **Request Body:** Fields to update (`title`, `description`, `status`, `dueDate`, `assignedTo`).
- **Success Response (200):** Updated task object.
- **Error Response:** `404` when not found.

### DELETE `/tasks/{id}`
- **Purpose:** Remove a task.
- **Request Headers:** `user-id` (required).
- **Success Response (200):** Empty body.
- **Error Response:** `404` when not found.

---

### Notes
- Headers labeled `user-id` are used by the backend to record audit events. Supply the authenticated user's ID when performing state-changing operations.
- Data validation is minimal in the current implementation; ensure the referenced IDs actually exist to avoid persistence errors.
- Timestamps are expressed in ISO 8601 format. Dates without times are serialized as `YYYY-MM-DD`.
