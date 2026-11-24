package com.smartdesk.ticket;

import com.smartdesk.ticket.dto.CreateTicketRequest;
import com.smartdesk.ticket.dto.TicketResponse;
import com.smartdesk.ticket.dto.UpdateTicketStatusRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.smartdesk.ticket.dto.UpdateTicketRequest;
import com.smartdesk.ticket.dto.AssignTicketRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TicketResponse create(@Valid @RequestBody CreateTicketRequest request) {
        return ticketService.create(request);
    }

    @GetMapping
    public List<TicketResponse> findAll() {
        return ticketService.findAll();
    }

    @GetMapping("/{id}")
    public TicketResponse findById(@PathVariable Long id) {
        return ticketService.findById(id);
    }

    @PatchMapping("/{id}/status")
    public TicketResponse updateStatus(@PathVariable Long id,
                                       @Valid @RequestBody UpdateTicketStatusRequest request) {
        return ticketService.updateStatus(id, request);
    }
    @PutMapping("/{id}")
    public TicketResponse updateTicket(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTicketRequest request) {

        return ticketService.updateTicket(id, request);
    }
    @PatchMapping("/{id}/assign")
    public TicketResponse assignTicket(
            @PathVariable Long id,
            @Valid @RequestBody AssignTicketRequest request) {

        return ticketService.assignTicket(id, request);
    }

    @GetMapping("/search")
    public Page<TicketResponse> searchTickets(
            @RequestParam(required = false) TicketStatus status,
            @RequestParam(required = false) TicketPriority priority,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        if (page < 0 || size < 1 || size > 100) {
            throw new IllegalArgumentException(
                    "Page must be >= 0 and size must be between 1 and 100"
            );
        }

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt")
                        .and(Sort.by(Sort.Direction.DESC, "id"))
        );

        return ticketService.searchTickets(
                status,
                priority,
                keyword,
                pageable
        );
    }

}
