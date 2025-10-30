# API Filtering and Sorting Guide

All GET endpoints now support filtering and sorting parameters. This document describes the available query parameters for each endpoint.

## Common Parameters

All list endpoints support these common parameters:

- `sortBy`: Field name to sort by (default varies by endpoint)
- `sortDirection`: `asc` or `desc` (default varies by endpoint)

## Endpoints

### 1. Animals (`GET /animals`)

**Filters:**
- `farmId` (required): Farm identifier
- `status`: Animal status (e.g., HEALTHY, SICK, SOLD, DEAD)
- `species`: Animal species (e.g., Pig, Cow, Chicken)

**Sorting:**
- `sortBy`: Default is `tagId`
- `sortDirection`: Default is `asc`

**Example:**
```
GET /animals?farmId=farm-123&status=HEALTHY&species=Pig&sortBy=birthDate&sortDirection=desc
```

### 2. Tasks (`GET /tasks`)

**Filters:**
- `farmId` (required): Farm identifier
- `status`: Task status (e.g., TODO, IN_PROGRESS, COMPLETED)

**Sorting:**
- `sortBy`: Default is `dueDate`
- `sortDirection`: Default is `asc`

**Example:**
```
GET /tasks?farmId=farm-123&status=TODO&sortBy=dueDate&sortDirection=asc
```

### 3. Inventory Items (`GET /inventory-items`)

**Filters:**
- `farmId` (required): Farm identifier
- `category`: Item category (e.g., FEED, MEDICINE, FERTILIZER, SEED)

**Sorting:**
- `sortBy`: Default is `name`
- `sortDirection`: Default is `asc`

**Example:**
```
GET /inventory-items?farmId=farm-123&category=FEED&sortBy=quantity&sortDirection=asc
```

### 4. Financial Transactions (`GET /financial-transactions`)

**Filters:**
- `farmId` (required): Farm identifier
- `type`: Transaction type (EXPENSE or REVENUE)
- `startDate`: Start date for date range filter (ISO format: 2025-01-01T00:00:00)
- `endDate`: End date for date range filter (ISO format: 2025-12-31T23:59:59)

**Sorting:**
- `sortBy`: Default is `date`
- `sortDirection`: Default is `desc`

**Examples:**
```
GET /financial-transactions?farmId=farm-123&type=EXPENSE&sortBy=amount&sortDirection=desc

GET /financial-transactions?farmId=farm-123&startDate=2025-01-01T00:00:00&endDate=2025-12-31T23:59:59

GET /financial-transactions?farmId=farm-123&type=REVENUE&startDate=2025-10-01T00:00:00&endDate=2025-10-31T23:59:59
```

### 5. Batches (`GET /batches`)

**Filters:**
- `farmId` (required): Farm identifier
- `type`: Batch type (e.g., ANIMAL, CROP, INVENTORY)

**Sorting:**
- `sortBy`: Default is `entryDate`
- `sortDirection`: Default is `desc`

**Example:**
```
GET /batches?farmId=farm-123&type=ANIMAL&sortBy=batchCode&sortDirection=asc
```

### 6. Inventory Logs (`GET /inventory-logs`)

**Filters:**
- `farmId` (required): Farm identifier
- `type`: Log type (IN or OUT)

**Sorting:**
- `sortBy`: Default is `date`
- `sortDirection`: Default is `desc`

**Example:**
```
GET /inventory-logs?farmId=farm-123&type=OUT&sortBy=date&sortDirection=desc
```

### 7. Animal Events (`GET /animal-events`)

**Filters:**
- `animalId`: Filter by specific animal
- `farmId`: Filter by farm
- `type`: Event type (e.g., ENTRY, SALE, DEATH, SELECT_BREEDER)

**Sorting:**
- `sortBy`: Default is `date`
- `sortDirection`: Default is `desc`

**Examples:**
```
GET /animal-events?animalId=animal-123&sortBy=date&sortDirection=desc

GET /animal-events?farmId=farm-123&type=ENTRY&sortBy=date&sortDirection=desc
```

### 8. Plots (`GET /plots`)

**Filters:**
- `farmId`: Filter by farm (optional - returns all if omitted)

**Sorting:**
- `sortBy`: Default is `name`
- `sortDirection`: Default is `asc`

**Example:**
```
GET /plots?farmId=farm-123&sortBy=area&sortDirection=desc
```

### 9. Seasons (`GET /seasons`)

**Filters:**
- `farmId`: Filter by farm (optional)
- `status`: Season status (e.g., ACTIVE, COMPLETED, PLANNED)

**Sorting:**
- `sortBy`: Default is `startDate`
- `sortDirection`: Default is `desc`

**Example:**
```
GET /seasons?farmId=farm-123&status=ACTIVE&sortBy=startDate&sortDirection=desc
```

### 10. Crop Events (`GET /crop-events`)

**Filters:**
- `farmId`: Filter by farm
- `plotId`: Filter by specific plot
- `eventType`: Event type (e.g., PLANTING, IRRIGATION, HARVEST)

**Sorting:**
- `sortBy`: Default is `date`
- `sortDirection`: Default is `desc`

**Examples:**
```
GET /crop-events?plotId=plot-123&sortBy=date&sortDirection=desc

GET /crop-events?farmId=farm-123&eventType=HARVEST&sortBy=date&sortDirection=desc
```

### 11. Enclosures (`GET /enclosures`)

**Filters:**
- `farmId` (required): Farm identifier
- `type`: Enclosure type (e.g., BREEDING_PEN, DEVELOPMENT_PEN, FATTENING_PEN, YOUNG_PEN)

**Sorting:**
- `sortBy`: Default is `name`
- `sortDirection`: Default is `asc`

**Example:**
```
GET /enclosures?farmId=farm-123&type=BREEDING_PEN&sortBy=capacity&sortDirection=desc
```

### 12. Feed Plans (`GET /feed-plans`)

**Filters:**
- `farmId` (required): Farm identifier
- `stage`: Feed plan stage (e.g., GESTATION_EARLY, GESTATION_LATE, LACTATION, STARTER, DEVELOPMENT, FATTENING)

**Sorting:**
- `sortBy`: Default is `name`
- `sortDirection`: Default is `asc`

**Example:**
```
GET /feed-plans?farmId=farm-123&stage=LACTATION&sortBy=name&sortDirection=asc
```

## Implementation Details

### Backend Changes

1. **Repositories**: Added overloaded methods with `Sort` parameter to all repository interfaces
2. **Controllers**: Updated all GET endpoints to accept filter and sort query parameters
3. **Sorting**: Uses Spring Data's `Sort` class for database-level sorting
4. **Filtering**: Uses Spring Data MongoDB's query methods for efficient filtering

### Notes

- All sorting is performed at the database level for optimal performance
- Filters can be combined (e.g., status AND species for animals)
- Date filters use ISO 8601 format (YYYY-MM-DDTHH:mm:ss)
- All parameters are optional unless marked as required
- Invalid sort fields will be handled by Spring Data (may return empty or unsorted)

## Future Enhancements

Consider adding:
- Pagination support (`page` and `size` parameters)
- Full-text search capabilities
- More complex query operators (greater than, less than, contains)
- Field selection (sparse fieldsets)
- Multiple sort fields
