package com.nedra.ecommerce.notification;



import com.nedra.ecommerce.kafka.demand.DemandConfirmation;
import com.nedra.ecommerce.notification.Notification;
import com.nedra.ecommerce.notification.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationRepository notificationRepository;

    // Get all notifications
    @GetMapping
    public ResponseEntity<List<Notification>> getAllNotifications() {
        List<Notification> notifications = notificationRepository.findAll();
        return ResponseEntity.ok(notifications);
    }
    @GetMapping("/demand")
    public ResponseEntity<List<DemandConfirmation>> getDemandConfirmations() {
        List<DemandConfirmation> demandConfirmations = notificationRepository.findByType(NotificationType.DEMAND_CONFIRMATION)
                .stream()
                .map(Notification::getDemandConfirmation)
                .collect(Collectors.toList());
        return ResponseEntity.ok(demandConfirmations);
    }
    // Get notifications by type
    /*@GetMapping("/type/{type}")
    public ResponseEntity<List<Notification>> getNotificationsByType(@PathVariable String type) {
        List<Notification> notifications = notificationRepository.findByType(type);
        return ResponseEntity.ok(notifications);
    }*/
}
