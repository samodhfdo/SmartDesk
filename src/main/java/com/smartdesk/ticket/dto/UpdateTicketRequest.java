package com.smartdesk.ticket.dto;

import com.smartdesk.ticket.TicketPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateTicketRequest(

        @NotBlank
        @Size(max = 140)
        String title,

        @NotBlank
        String description,

        @NotNull
        TicketPriority priority

) {}