package com.smartdesk.ticket.dto;

import com.smartdesk.ticket.TicketStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateTicketStatusRequest(@NotNull TicketStatus status) {
}
