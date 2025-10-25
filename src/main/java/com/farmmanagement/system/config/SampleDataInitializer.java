package com.farmmanagement.system.config;

import com.farmmanagement.system.model.Animal;
import com.farmmanagement.system.model.AnimalEvent;
import com.farmmanagement.system.model.AuthProvider;
import com.farmmanagement.system.model.Batch;
import com.farmmanagement.system.model.ConsumptionLog;
import com.farmmanagement.system.model.Enclosure;
import com.farmmanagement.system.model.Farm;
import com.farmmanagement.system.model.FeedDetail;
import com.farmmanagement.system.model.FeedPlan;
import com.farmmanagement.system.model.FinancialTransaction;
import com.farmmanagement.system.model.GrowthRecord;
import com.farmmanagement.system.model.HealthRecord;
import com.farmmanagement.system.model.InventoryItem;
import com.farmmanagement.system.model.InventoryLog;
import com.farmmanagement.system.model.ReproductionLog;
import com.farmmanagement.system.model.Role;
import com.farmmanagement.system.model.Task;
import com.farmmanagement.system.model.User;
import com.farmmanagement.system.repository.AnimalEventRepository;
import com.farmmanagement.system.repository.AnimalRepository;
import com.farmmanagement.system.repository.BatchRepository;
import com.farmmanagement.system.repository.EnclosureRepository;
import com.farmmanagement.system.repository.FarmRepository;
import com.farmmanagement.system.repository.FeedPlanRepository;
import com.farmmanagement.system.repository.FinancialTransactionRepository;
import com.farmmanagement.system.repository.InventoryItemRepository;
import com.farmmanagement.system.repository.InventoryLogRepository;
import com.farmmanagement.system.repository.TaskRepository;
import com.farmmanagement.system.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class SampleDataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(SampleDataInitializer.class);

    private final FarmRepository farmRepository;
    private final UserRepository userRepository;
    private final EnclosureRepository enclosureRepository;
    private final InventoryItemRepository inventoryItemRepository;
    private final FeedPlanRepository feedPlanRepository;
    private final BatchRepository batchRepository;
    private final AnimalRepository animalRepository;
    private final AnimalEventRepository animalEventRepository;
    private final InventoryLogRepository inventoryLogRepository;
    private final FinancialTransactionRepository financialTransactionRepository;
    private final TaskRepository taskRepository;

    public SampleDataInitializer(
        FarmRepository farmRepository,
        UserRepository userRepository,
        EnclosureRepository enclosureRepository,
        InventoryItemRepository inventoryItemRepository,
        FeedPlanRepository feedPlanRepository,
        BatchRepository batchRepository,
        AnimalRepository animalRepository,
        AnimalEventRepository animalEventRepository,
        InventoryLogRepository inventoryLogRepository,
        FinancialTransactionRepository financialTransactionRepository,
        TaskRepository taskRepository
    ) {
        this.farmRepository = farmRepository;
        this.userRepository = userRepository;
        this.enclosureRepository = enclosureRepository;
        this.inventoryItemRepository = inventoryItemRepository;
        this.feedPlanRepository = feedPlanRepository;
        this.batchRepository = batchRepository;
        this.animalRepository = animalRepository;
        this.animalEventRepository = animalEventRepository;
        this.inventoryLogRepository = inventoryLogRepository;
        this.financialTransactionRepository = financialTransactionRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public void run(String... args) {
        if (farmRepository.count() > 0) {
            log.info("Sample data already present. Skipping initializer.");
            return;
        }

        log.info("Seeding sample data for farm management system...");

        LocalDateTime now = LocalDateTime.now();
        LocalDate today = LocalDate.now();

        Farm farm1 = saveFarm("Sunny Acres Farm", "Dak Lak, Vietnam", now.minusYears(2));
        Farm farm2 = saveFarm("Green Valley Ranch", "Lam Dong, Vietnam", now.minusYears(1));

        User adminUser = saveUser(
            "admin",
            "admin@farm.local",
            "admin123",
            "Farm Administrator",
            Role.ADMIN,
            List.of(farm1.getId(), farm2.getId()),
            now.minusMonths(6),
            true
        );

        User managerUser = saveUser(
            "manager.sara",
            "sara@farm.local",
            "manager123",
            "Sara Nguyen",
            Role.MANAGER,
            List.of(farm1.getId()),
            now.minusMonths(4),
            true
        );

        User staffUser = saveUser(
            "staff.liem",
            "liem@farm.local",
            "staff123",
            "Liem Tran",
            Role.STAFF,
            List.of(farm2.getId()),
            now.minusMonths(2),
            true
        );

        InventoryItem farm1Feed = saveInventoryItem(farm1.getId(), "Grower Feed Mix", "FEED", 420.0, "kg", 100.0);
        InventoryItem farm1Vaccine = saveInventoryItem(farm1.getId(), "Porcine Vaccine", "MEDICINE", 12.0, "bottles", 3.0);
        InventoryItem farm2Feed = saveInventoryItem(farm2.getId(), "Dairy Cow Feed", "FEED", 600.0, "kg", 150.0);

        FeedPlan farm1FeedPlan = saveFeedPlan(
            farm1.getId(),
            "Breeding Sow Plan",
            FeedPlan.FeedPlanStage.GESTATION_LATE,
            "Daily ration for pregnant sows",
            List.of(
                feedDetail(farm1Feed.getId(), 3.5, "kg"),
                feedDetail(farm1Vaccine.getId(), 0.05, "bottle")
            )
        );

        FeedPlan farm2FeedPlan = saveFeedPlan(
            farm2.getId(),
            "Dairy Maintenance Plan",
            FeedPlan.FeedPlanStage.DEVELOPMENT,
            "Balanced diet for dairy herd",
            List.of(feedDetail(farm2Feed.getId(), 18.0, "kg"))
        );

        Enclosure farm1Enclosure = saveEnclosure(
            farm1.getId(),
            "Breeding Pen A",
            Enclosure.EnclosureType.BREEDING_PEN,
            25,
            14,
            List.of(
                consumptionLog(now.minusDays(2), farm1Feed.getId(), 32.0, managerUser.getId(), "Morning ration"),
                consumptionLog(now.minusDays(1), farm1Feed.getId(), 30.0, managerUser.getId(), "Evening ration")
            ),
            List.of(consumptionLog(now.minusDays(10), farm1Vaccine.getId(), 1.0, managerUser.getId(), "Vaccination cycle"))
        );

        Enclosure farm2Enclosure = saveEnclosure(
            farm2.getId(),
            "Dairy Barn 1",
            Enclosure.EnclosureType.DEVELOPMENT_PEN,
            40,
            28,
            List.of(consumptionLog(now.minusDays(3), farm2Feed.getId(), 450.0, staffUser.getId(), "Weekly bulk feed")),
            List.of()
        );

        Batch farm1Batch = saveBatch(
            farm1.getId(),
            "FARM1-AN-2024-Q1",
            Batch.BatchType.ANIMAL,
            "Q1 breeding herd",
            "In-house breeding",
            today.minusMonths(5),
            List.of(farm1Feed.getId(), farm1Vaccine.getId())
        );

        Batch farm2Batch = saveBatch(
            farm2.getId(),
            "FARM2-AN-2024-Q2",
            Batch.BatchType.ANIMAL,
            "Dairy herd expansion",
            "Local breeder",
            today.minusMonths(3),
            List.of(farm2Feed.getId())
        );

        Animal farm1Animal = saveAnimal(
            farm1.getId(),
            "SOW-1001",
            "Pig",
            Animal.AnimalType.BREEDING_FEMALE,
            farm1Batch.getId(),
            farm1Enclosure.getId(),
            farm1FeedPlan.getId(),
            today.minusYears(2),
            List.of(
                healthRecord(today.minusDays(30), "Vaccination", "Routine booster", managerUser.getId()),
                healthRecord(today.minusDays(7), "Checkup", "No issues detected", managerUser.getId())
            ),
            List.of(
                growthRecord(today.minusMonths(2), 182.0, managerUser.getId()),
                growthRecord(today.minusWeeks(3), 190.5, managerUser.getId())
            ),
            List.of(reproductionLog(
                today.minusMonths(6),
                "BOAR-21",
                today.minusMonths(3),
                today.minusMonths(3),
                12,
                10,
                "GOOD",
                "Smooth farrowing",
                false,
                "HIGH",
                "STRONG"
            ))
        );

        Animal farm2Animal = saveAnimal(
            farm2.getId(),
            "COW-204",
            "Cow",
            Animal.AnimalType.DEVELOPMENT,
            farm2Batch.getId(),
            farm2Enclosure.getId(),
            farm2FeedPlan.getId(),
            today.minusYears(3),
            List.of(healthRecord(today.minusMonths(1), "Hoof Care", "Seasonal trimming", staffUser.getId())),
            List.of(
                growthRecord(today.minusMonths(4), 480.0, staffUser.getId()),
                growthRecord(today.minusWeeks(4), 495.5, staffUser.getId())
            ),
            List.of()
        );

        AnimalEvent farm1EntryEvent = animalEvent(
            farm1.getId(),
            farm1Animal.getId(),
            AnimalEvent.AnimalEventType.ENTRY,
            today.minusMonths(8),
            "Selected for breeding program",
            350.0,
            managerUser.getId()
        );

        AnimalEvent farm2SelectionEvent = animalEvent(
            farm2.getId(),
            farm2Animal.getId(),
            AnimalEvent.AnimalEventType.SELECT_BREEDER,
            today.minusMonths(2),
            "Moved to primary dairy herd",
            0.0,
            staffUser.getId()
        );

        animalEventRepository.saveAll(List.of(farm1EntryEvent, farm2SelectionEvent));

        inventoryLogRepository.saveAll(List.of(
            inventoryLog(
                farm1.getId(),
                farm1Feed.getId(),
                farm1Batch.getBatchCode(),
                InventoryLog.LogType.OUT,
                62.0,
                "Two-day feed allocation",
                managerUser.getId(),
                now.minusDays(1),
                InventoryLog.TargetType.ENCLOSURE,
                farm1Enclosure.getId()
            ),
            inventoryLog(
                farm1.getId(),
                farm1Feed.getId(),
                "PO-2024-05-18",
                InventoryLog.LogType.IN,
                200.0,
                "Supplier delivery - Fresh Grain Co.",
                adminUser.getId(),
                now.minusDays(5),
                InventoryLog.TargetType.ENCLOSURE,
                farm1Enclosure.getId()
            ),
            inventoryLog(
                farm2.getId(),
                farm2Feed.getId(),
                farm2Batch.getBatchCode(),
                InventoryLog.LogType.OUT,
                180.0,
                "Weekly ration for dairy barn",
                staffUser.getId(),
                now.minusDays(3),
                InventoryLog.TargetType.ENCLOSURE,
                farm2Enclosure.getId()
            )
        ));

        financialTransactionRepository.saveAll(List.of(
            financialTransaction(
                farm1.getId(),
                FinancialTransaction.TransactionType.EXPENSE,
                new BigDecimal("1250.50"),
                "Bulk grower feed purchase",
                "Feed",
                farm1Batch.getId(),
                today.minusDays(9),
                adminUser.getId()
            ),
            financialTransaction(
                farm2.getId(),
                FinancialTransaction.TransactionType.REVENUE,
                new BigDecimal("8900.00"),
                "Milk sales contract - May",
                "Sales",
                farm2Batch.getId(),
                today.minusDays(4),
                staffUser.getId()
            )
        ));

        taskRepository.saveAll(List.of(
            task(
                farm1.getId(),
                "Schedule farrowing checks",
                "Monitor sows expected to farrow next month",
                managerUser.getId(),
                Task.TaskStatus.IN_PROGRESS,
                today.plusWeeks(2),
                now.minusDays(2),
                adminUser.getId()
            ),
            task(
                farm2.getId(),
                "Prepare feed inventory report",
                "Summarize weekly feed usage for dairy herd",
                staffUser.getId(),
                Task.TaskStatus.TODO,
                today.plusDays(5),
                now.minusDays(1),
                adminUser.getId()
            )
        ));

        log.info("Sample data seeding completed.");
    }

    private Farm saveFarm(String name, String location, LocalDateTime createdAt) {
        Farm farm = new Farm();
        farm.setName(name);
        farm.setLocation(location);
        farm.setCreatedAt(createdAt);
        return farmRepository.save(farm);
    }

    private User saveUser(
        String username,
        String email,
        String password,
        String fullName,
        Role role,
        List<String> farmIds,
        LocalDateTime createdAt,
        boolean active
    ) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setAuthProvider(AuthProvider.LOCAL);
        user.setFullName(fullName);
        user.setRole(role);
        user.setFarmIds(farmIds);
        user.setCreatedAt(createdAt);
        user.setActive(active);
        return userRepository.save(user);
    }

    private InventoryItem saveInventoryItem(
        String farmId,
        String name,
        String category,
        double quantity,
        String unit,
        double lowStockThreshold
    ) {
        InventoryItem item = new InventoryItem();
        item.setFarmId(farmId);
        item.setName(name);
        item.setCategory(category);
        item.setQuantity(quantity);
        item.setUnit(unit);
        item.setLowStockThreshold(lowStockThreshold);
        return inventoryItemRepository.save(item);
    }

    private FeedPlan saveFeedPlan(
        String farmId,
        String name,
        FeedPlan.FeedPlanStage stage,
        String description,
        List<FeedDetail> feedDetails
    ) {
        FeedPlan feedPlan = new FeedPlan();
        feedPlan.setFarmId(farmId);
        feedPlan.setName(name);
        feedPlan.setStage(stage);
        feedPlan.setDescription(description);
        feedPlan.setFeedDetails(feedDetails);
        return feedPlanRepository.save(feedPlan);
    }

    private Enclosure saveEnclosure(
        String farmId,
        String name,
        Enclosure.EnclosureType type,
        int capacity,
        int currentOccupancy,
        List<ConsumptionLog> feedConsumptionLogs,
        List<ConsumptionLog> medicineConsumptionLogs
    ) {
        Enclosure enclosure = new Enclosure();
        enclosure.setFarmId(farmId);
        enclosure.setName(name);
        enclosure.setType(type);
        enclosure.setCapacity(capacity);
        enclosure.setCurrentOccupancy(currentOccupancy);
        enclosure.setFeedConsumptionLogs(feedConsumptionLogs);
        enclosure.setMedicineConsumptionLogs(medicineConsumptionLogs);
        return enclosureRepository.save(enclosure);
    }

    private Batch saveBatch(
        String farmId,
        String batchCode,
        Batch.BatchType type,
        String description,
        String source,
        LocalDate entryDate,
        List<String> relatedItemIds
    ) {
        Batch batch = new Batch();
        batch.setFarmId(farmId);
        batch.setBatchCode(batchCode);
        batch.setType(type);
        batch.setDescription(description);
        batch.setSource(source);
        batch.setEntryDate(entryDate);
        batch.setRelatedItemIds(relatedItemIds);
        return batchRepository.save(batch);
    }

    private Animal saveAnimal(
        String farmId,
        String tagId,
        String species,
        Animal.AnimalType type,
        String batchId,
        String enclosureId,
        String feedPlanId,
        LocalDate birthDate,
        List<HealthRecord> healthRecords,
        List<GrowthRecord> growthRecords,
        List<ReproductionLog> reproductionLogs
    ) {
        Animal animal = new Animal();
        animal.setFarmId(farmId);
        animal.setTagId(tagId);
        animal.setSpecies(species);
        animal.setAnimalType(type);
        animal.setBatchId(batchId);
        animal.setEnclosureId(enclosureId);
        animal.setFeedPlanId(feedPlanId);
        animal.setBirthDate(birthDate);
        animal.setStatus(Animal.AnimalStatus.HEALTHY);
        animal.setHealthRecords(healthRecords);
        animal.setGrowthRecords(growthRecords);
        animal.setReproductionLogs(reproductionLogs);
        return animalRepository.save(animal);
    }

    private AnimalEvent animalEvent(
        String farmId,
        String animalId,
        AnimalEvent.AnimalEventType type,
        LocalDate date,
        String notes,
        double price,
        String recordedBy
    ) {
        AnimalEvent event = new AnimalEvent();
        event.setFarmId(farmId);
        event.setAnimalId(animalId);
        event.setType(type);
        event.setDate(date);
        event.setNotes(notes);
        event.setPrice(price);
        event.setRecordedBy(recordedBy);
        return event;
    }

    private InventoryLog inventoryLog(
        String farmId,
        String itemId,
        String batchCode,
        InventoryLog.LogType type,
        double quantity,
        String notes,
        String recordedBy,
        LocalDateTime date,
        InventoryLog.TargetType targetType,
        String targetId
    ) {
        InventoryLog logEntry = new InventoryLog();
        logEntry.setFarmId(farmId);
        logEntry.setItemId(itemId);
        logEntry.setBatchCode(batchCode);
        logEntry.setType(type);
        logEntry.setQuantity(quantity);
        logEntry.setNotes(notes);
        logEntry.setRecordedBy(recordedBy);
        logEntry.setDate(date);

        InventoryLog.UsageTarget usageTarget = new InventoryLog.UsageTarget();
        usageTarget.setType(targetType);
        usageTarget.setId(targetId);
        logEntry.setUsageTarget(usageTarget);

        return logEntry;
    }

    private FinancialTransaction financialTransaction(
        String farmId,
        FinancialTransaction.TransactionType type,
        BigDecimal amount,
        String description,
        String category,
        String relatedBatchId,
        LocalDate date,
        String recordedBy
    ) {
        FinancialTransaction transaction = new FinancialTransaction();
        transaction.setFarmId(farmId);
        transaction.setType(type);
        transaction.setAmount(amount);
        transaction.setDescription(description);
        transaction.setCategory(category);
        transaction.setRelatedBatchId(relatedBatchId);
        transaction.setDate(date);
        transaction.setRecordedBy(recordedBy);
        return transaction;
    }

    private Task task(
        String farmId,
        String title,
        String description,
        String assignedTo,
        Task.TaskStatus status,
        LocalDate dueDate,
        LocalDateTime createdAt,
        String createdBy
    ) {
        Task task = new Task();
        task.setFarmId(farmId);
        task.setTitle(title);
        task.setDescription(description);
        task.setAssignedTo(assignedTo);
        task.setStatus(status);
        task.setDueDate(dueDate);
        task.setCreatedAt(createdAt);
        task.setCreatedBy(createdBy);
        return task;
    }

    private FeedDetail feedDetail(String itemId, double quantityPerDay, String unit) {
        FeedDetail detail = new FeedDetail();
        detail.setFeedItemId(itemId);
        detail.setQuantityPerDay(quantityPerDay);
        detail.setUnit(unit);
        return detail;
    }

    private ConsumptionLog consumptionLog(
        LocalDateTime date,
        String itemId,
        double quantity,
        String recordedBy,
        String notes
    ) {
        ConsumptionLog logEntry = new ConsumptionLog();
        logEntry.setDate(date);
        logEntry.setItemId(itemId);
        logEntry.setQuantity(quantity);
        logEntry.setRecordedBy(recordedBy);
        logEntry.setNotes(notes);
        return logEntry;
    }

    private HealthRecord healthRecord(LocalDate date, String eventType, String notes, String recordedBy) {
        HealthRecord record = new HealthRecord();
        record.setDate(date);
        record.setEventType(eventType);
        record.setNotes(notes);
        record.setRecordedBy(recordedBy);
        return record;
    }

    private GrowthRecord growthRecord(LocalDate date, double weight, String recordedBy) {
        GrowthRecord record = new GrowthRecord();
        record.setDate(date);
        record.setWeight(weight);
        record.setRecordedBy(recordedBy);
        return record;
    }

    private ReproductionLog reproductionLog(
        LocalDate breedingDate,
        String sireId,
        LocalDate expectedFarrowDate,
        LocalDate farrowDate,
        int pigletsBorn,
        int pigletsWeaned,
        String qualityRating,
        String farrowingNotes,
        boolean difficultBirth,
        String milkSupply,
        String pigletHealthStatus
    ) {
        ReproductionLog record = new ReproductionLog();
        record.setBreedingDate(breedingDate);
        record.setSireId(sireId);
        record.setExpectedFarrowDate(expectedFarrowDate);
        record.setFarrowDate(farrowDate);
        record.setPigletsBorn(pigletsBorn);
        record.setPigletsWeaned(pigletsWeaned);
        record.setQualityRating(qualityRating);
        record.setFarrowingNotes(farrowingNotes);
        record.setDifficultBirth(difficultBirth);
        record.setMilkSupply(milkSupply);
        record.setPigletHealthStatus(pigletHealthStatus);
        return record;
    }
}
