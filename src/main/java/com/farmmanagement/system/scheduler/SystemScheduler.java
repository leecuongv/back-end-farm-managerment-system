package com.farmmanagement.system.scheduler;

import com.farmmanagement.system.model.InventoryItem;
import com.farmmanagement.system.model.Task;
import com.farmmanagement.system.repository.InventoryItemRepository;
import com.farmmanagement.system.repository.TaskRepository;
import com.farmmanagement.system.service.NotificationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class SystemScheduler {

    private final InventoryItemRepository inventoryItemRepository;
    private final TaskRepository taskRepository;
    private final NotificationService notificationService;

    public SystemScheduler(InventoryItemRepository inventoryItemRepository,
                           TaskRepository taskRepository,
                           NotificationService notificationService) {
        this.inventoryItemRepository = inventoryItemRepository;
        this.taskRepository = taskRepository;
        this.notificationService = notificationService;
    }

    /**
     * Runs every day at 8 AM to check for low-stock items.
     */
    @Scheduled(cron = "0 0 8 * * ?")
    public void checkLowStockItems() {
        List<InventoryItem> items = inventoryItemRepository.findAll();
        for (InventoryItem item : items) {
            if (item.getQuantity() < item.getLowStockThreshold()) {
                // In a real app, you'd notify the farm manager
                notificationService.sendNotification(
                    "admin@farm.com",
                    "Low Stock Warning",
                    "Item '" + item.getName() + "' is running low. Current quantity: " + item.getQuantity()
                );
            }
        }
    }

    /**
     * Runs every day at 7 AM to check for tasks due soon.
     */
    @Scheduled(cron = "0 0 7 * * ?")
    public void checkTaskDueDates() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        List<Task> tasks = taskRepository.findAll();
        for (Task task : tasks) {
            if (task.getDueDate() != null && task.getDueDate().equals(tomorrow)) {
                // In a real app, you'd find the user's email and send a real notification
                notificationService.sendNotification(
                    task.getAssignedTo(), // This should be a user email in a real app
                    "Task Reminder",
                    "The task '" + task.getTitle() + "' is due tomorrow."
                );
            }
        }
    }
}
