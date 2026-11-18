package com.smartdesk.ticket.dto;

import com.smartdesk.ticket.TicketPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateTicketRequest(
        @NotBlank @Size(max = 140) String title,
        @NotBlank @Size(max = 5000) String description,
        @NotNull TicketPriority priority,
        @NotNull Long categoryId,
        @NotNull Long reporterId
) {
}
