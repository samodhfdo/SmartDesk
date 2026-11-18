
package com.smartdesk.ticket;

import com.smartdesk.category.Category;
import com.smartdesk.category.CategoryRepository;
import com.smartdesk.common.ResourceNotFoundException;

import com.smartdesk.ticket.dto.CreateTicketRequest;
import com.smartdesk.ticket.dto.TicketResponse;
import com.smartdesk.ticket.dto.UpdateTicketRequest;
import com.smartdesk.ticket.dto.UpdateTicketStatusRequest;

import com.smartdesk.user.User;
import com.smartdesk.user.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartdesk.ticket.dto.AssignTicketRequest;

import java.util.List;

@Service
@Transactional
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    // Constructor Dependency Injection
    public TicketServiceImpl(
            TicketRepository ticketRepository,
            CategoryRepository categoryRepository,
            UserRepository userRepository) {

        this.ticketRepository = ticketRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    // CREATE A NEW TICKET
    @Override
    public TicketResponse create(CreateTicketRequest request) {

        Category category = categoryRepository
                .findById(request.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Category not found: " + request.categoryId()
                ));

        User reporter = userRepository
                .findById(request.reporterId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Reporter not found: " + request.reporterId()
                ));

        Ticket ticket = new Ticket(
                request.title().trim(),
                request.description().trim(),
                request.priority(),
                category,
                reporter
        );

        Ticket savedTicket = ticketRepository.save(ticket);

        return TicketResponse.from(savedTicket);
    }

    // GET ALL TICKETS
    @Override
    @Transactional(readOnly = true)
    public List<TicketResponse> findAll() {

        return ticketRepository.findAll()
                .stream()
                .map(TicketResponse::from)
                .toList();
    }

    // GET A TICKET BY ID
    @Override
    @Transactional(readOnly = true)
    public TicketResponse findById(Long id) {

        Ticket ticket = getTicketOrThrow(id);

        return TicketResponse.from(ticket);
    }

    // UPDATE TICKET STATUS
    @Override
    public TicketResponse updateStatus(
            Long id,
            UpdateTicketStatusRequest request) {

        Ticket ticket = getTicketOrThrow(id);

        ticket.updateStatus(request.status());

        Ticket savedTicket = ticketRepository.save(ticket);

        return TicketResponse.from(savedTicket);
    }

    // UPDATE TICKET DETAILS
    @Override
    public TicketResponse updateTicket(
            Long id,
            UpdateTicketRequest request) {

        Ticket ticket = getTicketOrThrow(id);

        ticket.updateDetails(
                request.title().trim(),
                request.description().trim(),
                request.priority()
        );

        Ticket updatedTicket = ticketRepository.save(ticket);

        return TicketResponse.from(updatedTicket);
    }

    @Override
    public TicketResponse assignTicket(
            Long id,
            AssignTicketRequest request) {

        Ticket ticket = getTicketOrThrow(id);

        User assignee = userRepository
                .findById(request.assigneeId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found: " + request.assigneeId()
                ));

        ticket.assignTo(assignee);

        Ticket updatedTicket = ticketRepository.save(ticket);

        return TicketResponse.from(updatedTicket);
    }

    // HELPER METHOD TO FIND A TICKET
    private Ticket getTicketOrThrow(Long id) {

        return ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Ticket not found: " + id
                ));
    }
}
