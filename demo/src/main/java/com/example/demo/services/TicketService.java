//Business logic
package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.entities.Ticket;
import com.example.demo.repositories.TicketRepository;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {
	@Autowired
	private TicketRepository ticketRepository;

	public List<Ticket> getAllTickets() {
		return ticketRepository.findAll();
	}

	public Optional<Ticket> getTicketById(Integer id) {
		return ticketRepository.findById(id);
	}

	public Ticket createTicket(Ticket ticket) {
		return ticketRepository.save(ticket);
	}

	public Ticket updateTicket(Integer id, Ticket ticketDetails) {
		Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new RuntimeException("Ticket not found"));
		ticket.setPassengerName(ticketDetails.getPassengerName());
		ticket.setSourceStation(ticketDetails.getSourceStation());
		ticket.setDestStation(ticketDetails.getDestStation());
		ticket.setEmail(ticketDetails.getEmail());
		return ticketRepository.save(ticket);
	}

	public void deleteTicket(Integer id) {
		ticketRepository.deleteById(id);
	}
}
