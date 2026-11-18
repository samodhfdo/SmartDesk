package com.smartdesk.ticket.dto;

import com.smartdesk.ticket.Ticket;
import java.time.Instant;

public record TicketResponse(
        Long id,
        String title,
        String description,
        String priority,
        String status,
        Long categoryId,
        String categoryName,
        Long reporterId,
        String reporterName,
        Instant createdAt,
        Instant updatedAt
) {
    public static TicketResponse from(Ticket ticket) {
        return new TicketResponse(
                ticket.getId(),
                ticket.getTitle(),
                ticket.getDescription(),
                ticket.getPriority().name(),
                ticket.getStatus().name(),
                ticket.getCategory().getId(),
                ticket.getCategory().getName(),
                ticket.getReporter().getId(),
                ticket.getReporter().getFullName(),
                ticket.getCreatedAt(),
                ticket.getUpdatedAt()
        );
    }
}
