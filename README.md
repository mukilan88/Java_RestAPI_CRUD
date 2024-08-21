Spring Boot REST CRUD Application with POSTMAN Client POSTMAN Tool
Postman is an interactive and automatic tool for verifying the APIs of the project. Postman is a Google Chrome App for interacting with HTTP APIs
Download POSTMAN
Url:
https://www.postman.com/downloads/
HTTP Methods
GET – Read Operation (Select Command)
POST – Write / Create Operation (Insert Command)
PUT – Update Operation (Update Command)
DELETE – Delete Operation (Delete Command)

Create table “ticket” in MySQL
mysql>CREATE TABLE Ticket (
    ticket_id INT AUTO_INCREMENT PRIMARY KEY,
    passenger_name VARCHAR(100) NOT NULL,
    source_station VARCHAR(100),
    dest_station VARCHAR(100),
    email VARCHAR(100)
);

Create a Spring Starter Project in STS
Name : demo
Type : Maven Project
Java Version: 8/17/21
Group : com.example
Artifact: demo
Package : com.example.demo
Click Next

Add the following project dependencies
o Spring Web
o Spring Data JPA
o MySQL Driver
o Lombok
o Spring Boot DevTools
Click Finish

com.example.demo.entities
- Create a package “com.example.demo.entities” in src/main/java folder
- Create a Java Bean class “Ticket” in “com.example.demo.entities”
package
Ticket.java
-----------------------------------------------------------------------------------
//pojo class
package com.example.demo.entities;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class Ticket {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ticket_id")
	private Long ticketId; 
	@Column(name = "passenger_name", nullable = false)
	private String passengerName;
	@Column(name = "source_station")
	private String sourceStation;
	@Column(name = "dest_station")
	private String destStation;
	@Column(name = "email")
	private String email;
	// Getters and Setters
	public Long getTicketId() {
		return ticketId;
	}
	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}
	public String getPassengerName() {
		return passengerName;
	}
	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}
	public String getSourceStation() {
		return sourceStation;
	}
	public void setSourceStation(String sourceStation) {
		this.sourceStation = sourceStation;
	}
	public String getDestStation() {
		return destStation;
	}
	public void setDestStation(String destStation) {
		this.destStation = destStation;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}

-----------------------------------------------------------------------------------
- Create a package “com.example.demo.repositories” in src/main/java
folder
Create an interface “TicketRepository” in
com.example.demo.repositories
-----------------------------------------------------------------------------------
package com.example.demo.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.entities.Ticket;
@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
}
-----------------------------------------------------------------------------------
- Create a package “com.example.demo.services” in src/main/java folder
- Create a Service class “TicketService” in 
com.example.demo.services
package
TicketService
-----------------------------------------------------------------------------------
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
-----------------------------------------------------------------------------------
- Create a package “com.example.demo.controllers” in src/main/java
folder
- Create a Rest Controller class “TicketController” in
com.example.demo.controllers 
package TicketController.java
-----------------------------------------------------------------------------------
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
-----------------------------------------------------------------------------------
- Update application.properties file of src/main/resources folder
Application.properties
-----------------------------------------------------------------------------------
spring.application.name=demo
# MySQL Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/java05
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
# Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect


-----------------------------------------------------------------------------------
Run the project
Right click on RestSpringBootCRUDProj -> Run As -> Spring Boot App

PostMan
Test the application using POSTMAN
Open POSTMAN and do the following requests

Post → Create Request
HTTP Method: Set the method to POST.
URL: Enter the URL of your API endpoint. For example: http://localhost:8080/api/tickets/create.
Set Up the Request Body
Click on the Body tab below the URL field.
Select the raw option.
Select JSON from the dropdown menu next to the raw option.
Paste the JSON content from your file into the text area.
{
    "passengerName": "John Doe",
    "sourceStation": "New York",
    "destStation": "Los Angeles",
    "email": "john.doe@example.com"
}
Click Send
{
    "passengerName": "John Doe",
    "sourceStation": "New York",
    "destStation": "Los Angeles",
    "email": "john.doe@example.com"
}
{
    "passengerName": "John Doe",
    "sourceStation": "New York",
    "destStation": "Los Angeles",
    "email": "john.doe@example.com"
}
Add some more records

GET All Data (Retrieve All Tickets)
HTTP Method: GET
URL: http://localhost:8080/api/tickets
Headers: None required.
Steps:
Open Postman and set the method to GET.
Enter the URL http://localhost:8080/api/tickets.
Click Send.
Response:
You should see a list of all tickets in your database.

GET a Single Ticket by ID (Find One Data)
HTTP Method: GET
URL: http://localhost:8080/api/tickets/{id} (Replace {id} with the actual ticket ID you want to retrieve)
Headers: None required.
Steps:
Open Postman and set the method to GET.
Enter the URL http://localhost:8080/api/tickets/{id} (e.g., http://localhost:8080/api/tickets/1).
Click Send.
Response:
You should see the ticket details for the specified ID.

PUT (Update an Existing Ticket)
HTTP Method: PUT
URL: http://localhost:8080/api/tickets/update/{id} (Replace {id} with the actual ticket ID you want to update)
Headers:
Content-Type: application/json
Body:
Select raw and JSON as the format.
Include the updated JSON data for the ticket.
{
    "passengerName": "Jane Doe",
    "sourceStation": "San Francisco",
    "destStation": "Chicago",
    "email": "jane.doe@example.com"
}


DELETE (Delete a Ticket by ID)
HTTP Method: DELETE
URL: http://localhost:8080/api/tickets/delete/{id} (Replace {id} with the actual ticket ID you want to delete)
Headers: None required.
Steps:
Open Postman and set the method to DELETE.
Enter the URL http://localhost:8080/api/tickets/delete/{id} (e.g., http://localhost:8080/api/tickets/delete/1).
Click Send.


