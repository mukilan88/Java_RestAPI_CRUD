package com.example.demo.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.entities.Ticket;
import com.example.demo.services.TicketService;
import java.util.List;
@RestController
@RequestMapping("/api/tickets")
public class TicketController {
   @Autowired
   private TicketService ticketService;
   @GetMapping
   public List<Ticket> getAllTickets() {
       return ticketService.getAllTickets();
   }
   @GetMapping("/{id}")
   public ResponseEntity<Ticket> getTicketById(@PathVariable Integer id) {
       return ticketService.getTicketById(id)
               .map(ResponseEntity::ok)
               .orElse(ResponseEntity.notFound().build());
   }
   @PostMapping("/create")
   public Ticket createTicket(@RequestBody Ticket ticket) {
       return ticketService.createTicket(ticket);
   }
   @PutMapping("/update/{id}")
   public ResponseEntity<Ticket> updateTicket(@PathVariable Integer id, @RequestBody Ticket ticketDetails) {
       return ResponseEntity.ok(ticketService.updateTicket(id, ticketDetails));
   }
   @DeleteMapping("/delete/{id}")
   public ResponseEntity<Void> deleteTicket(@PathVariable Integer id) {
       ticketService.deleteTicket(id);
       return ResponseEntity.noContent().build();
   }
}
