
package com.smartdesk.ticket;

import org.springframework.data.jpa.domain.Specification;

public class TicketSpecification {

    public static Specification<Ticket> hasStatus(
            TicketStatus status) {

        return (root, query, cb) ->
                status == null
                        ? cb.conjunction()
                        : cb.equal(root.get("status"), status);
    }

    public static Specification<Ticket> hasPriority(
            TicketPriority priority) {

        return (root, query, cb) ->
                priority == null
                        ? cb.conjunction()
                        : cb.equal(root.get("priority"), priority);
    }

    public static Specification<Ticket> titleContains(
            String keyword) {

        return (root, query, cb) ->
                keyword == null || keyword.isBlank()
                        ? cb.conjunction()
                        : cb.like(
                        cb.lower(root.get("title")),
                        "%" + keyword.toLowerCase().trim() + "%"
                );
    }
}
