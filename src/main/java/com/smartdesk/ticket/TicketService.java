package com.smartdesk.ticket;

import com.smartdesk.ticket.dto.CreateTicketRequest;
import com.smartdesk.ticket.dto.TicketResponse;
import com.smartdesk.ticket.dto.UpdateTicketRequest;
import com.smartdesk.ticket.dto.UpdateTicketStatusRequest;
import com.smartdesk.ticket.dto.AssignTicketRequest;

import java.util.List;

public interface TicketService {

    TicketResponse create(CreateTicketRequest request);

    List<TicketResponse> findAll();

    TicketResponse findById(Long id);

    TicketResponse updateStatus(
            Long id,
            UpdateTicketStatusRequest request
    );

    TicketResponse updateTicket(
            Long id,
            UpdateTicketRequest request
    );

    TicketResponse assignTicket(
            Long id,
            AssignTicketRequest request
    );
}