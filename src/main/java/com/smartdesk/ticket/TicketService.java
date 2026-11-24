package com.smartdesk.ticket;

import com.smartdesk.ticket.dto.CreateTicketRequest;
import com.smartdesk.ticket.dto.TicketResponse;
import com.smartdesk.ticket.dto.UpdateTicketRequest;
import com.smartdesk.ticket.dto.UpdateTicketStatusRequest;
import com.smartdesk.ticket.dto.AssignTicketRequest;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

    Page<TicketResponse> searchTickets(
            TicketStatus status,
            TicketPriority priority,
            String keyword,
            Pageable pageable
    );
}