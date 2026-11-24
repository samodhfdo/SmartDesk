package com.smartdesk.ticket;

import com.smartdesk.category.Category;
import com.smartdesk.user.User;
import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 140)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TicketPriority priority;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TicketStatus status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "reporter_id", nullable = false)
    private User reporter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignee_id")
    private User assignee;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    protected Ticket() {
    }

    public Ticket(String title, String description, TicketPriority priority,
                  Category category, User reporter) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.category = category;
        this.reporter = reporter;
        this.status = TicketStatus.OPEN;
    }

    @PrePersist
    void onCreate() {
        Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = Instant.now();
    }


    public void updateStatus(TicketStatus newStatus) {

        if (newStatus == null) {
            throw new IllegalArgumentException(
                    "Ticket status cannot be null"
            );
        }

        boolean validTransition = switch (this.status) {
            case OPEN -> newStatus == TicketStatus.IN_PROGRESS;
            case IN_PROGRESS -> newStatus == TicketStatus.RESOLVED;
            case RESOLVED -> newStatus == TicketStatus.CLOSED;
            case CLOSED -> false;
        };

        if (!validTransition) {
            throw new IllegalArgumentException(
                    "Invalid status transition from "
                            + this.status + " to " + newStatus
            );
        }

        this.status = newStatus;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public TicketPriority getPriority() { return priority; }
    public TicketStatus getStatus() { return status; }
    public Category getCategory() { return category; }
    public User getReporter() { return reporter; }
    public User getAssignee() { return assignee; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }

    public void updateDetails(
            String title,
            String description,
            TicketPriority priority) {

        this.title = title;
        this.description = description;
        this.priority = priority;
    }
    public void assignTo(User assignee) {
        this.assignee = assignee;
    }
}
