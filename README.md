# Share Price Comparison Web Application
This repository is dedicated to the group coursework aspect of our Software Architecture and Design Module. We will ultimately be creating a web application, that enables users to compare share prices over specified periods of time. 

# Architecture Style
So far, our rudimentary system follows a Hexagonal (Ports and Adapters) approach with Clean Architecture Principles: the application core depends on interfaces (ports), while infrastructure concerns (storage and external APIs) are implemented as adapters and wired together in the bootstrap layer.

This way of structuring the program means the core logic does not depend on databases, APIs, or UI code. The core defines ports (interfaces) describing what it needs and what it offers, and the outside world connects through adapters (implementations) that plug into those ports. This lets us swap things like storage or external services without changing the main application logic.

# Group Members

	 • Habsa Sharif

## Sprint 1 Objective
Establish requirements, architecture outline, and abstract system structure.

## Planned Features
- Retrieve share price data
- Store data locally
- Display graph
- Compare two companies
