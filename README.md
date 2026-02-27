# Share Price Comparison Web Application
This repository is dedicated to the group coursework aspect of our Software Architecture and Design Module. We will ultimately be creating a web application, that enables users to compare share prices over specified periods of time. 

# Architecture Style
So far, our rudimentary system follows a Hexagonal (Ports and Adapters) approach with Clean Architecture Principles: the application core depends on interfaces (ports), while infrastructure concerns (storage and external APIs) are implemented as adapters and wired together in the bootstrap layer.

This way of structuring the program means the core logic does not depend on databases, APIs, or UI code. The core defines ports (interfaces) describing what it needs and what it offers, and the outside world connects through adapters (implementations) that plug into those ports. This lets us swap things like storage or external services without changing the main application logic.

Similarly, the system follows the SOLID principles by separating responsibilities and relying on those abstractions rather than concrete implementations. Each class has a single clear role (e.g., comparison logic, data retrieval, caching, or external access), and behaviour can be extended by adding new implementations without modifying existing code. Small focused interfaces ensure components depend only on what they need, and high-level logic depends on contracts that are wired to concrete infrastructure at startup.

# Group Members

	 • Habsa Sharif
	 • Tobi Obafemi
	 • Seb Moreno
	 • Elijah Kuku

## Sprint 1 Objective
- Establish requirements and scope of project,
- Identify Simple Architecture Principle and componenents of system
- Agree on Code of Conduct
- Set up project management tools
- Create a java framework that matches the component diagram

## Planned Features for Project
- Retrieve share price data from local or external stores. 
- Store and fetch data locally
- Generate a graph to depict the price history of chosen stocks over specified time frames.
