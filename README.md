## Overview

This project is a Spring Boot RESTful API built on top of my previous JDBC + SOLID refactoring work.
Domain: Game Inventory with Backpacks and Items (Weapon/Potion).
It demonstrates layered architecture, design patterns (Singleton/Factory/Builder), and clean REST endpoints.

Tech Stack
Java 17+
Spring Boot (Web, JDBC)
PostgreSQL
JDBC via JdbcTemplate
---
## Architecture (Layered)

Controller → Service → Repository → Database

Controller: REST endpoints only (no business logic)

Service: validation + business rules + exceptions

Repository: SQL access using JdbcTemplate

Database: PostgreSQL tables with FK relationship

---
## Database Schema
CREATE TABLE backpacks (
id SERIAL PRIMARY KEY,
owner varchar(100) NOT NULL,
max_weight FLOAT NOT NULL CHECK (max_weight > 0)
);

CREATE TABLE items (
id SERIAL PRIMARY KEY,
name VARCHAR(100) NOT NULL CHECK (name <> ''),
weight FLOAT NOT NULL CHECK (weight > 0),
gold_value INT NOT NULL DEFAULT 0 CHECK (gold_value >= 0),
type VARCHAR(10) NOT NULL CHECK (type IN ('WEAPON','POTION')),
backpack_id INT NOT NULL,
damage INT DEFAULT 0 CHECK (damage >= 0),
heal_amount INT DEFAULT 0 CHECK (heal_amount >= 0),
FOREIGN KEY (backpack_id) REFERENCES backpacks(id) ON DELETE RESTRICT
);

---
## REST API Endpoints
**Items**

GET /api/items — list all items

GET /api/items/{id} — get item by id

POST /api/items — create item (Weapon/Potion)

PUT /api/items/{id} — update basic fields (name/weight/goldValue)

DELETE /api/items/{id} — delete item

**Backpacks**

GET /api/backpacks — list backpacks

POST /api/backpacks — create backpack

GET /api/backpacks/{id}/items — items inside a backpack (relationship demo)

---
## Example JSON
**Create Weapon (POST /api/items)**

{
"name": "Steel Sword",
"weight": 6,
"goldValue": 200,
"type": "WEAPON",
"backpackId": 2,
"damage": 30,
"healAmount": 0
}

**Create Backpack (POST /api/backpacks)**

{
"owner": "Gerald",
"maxWeight": 150
}

---
## Error Handling

**Global exception handling returns JSON errors:**

400 Bad Request for invalid input (e.g. weight <= 0)

404 Not Found for missing resources (e.g. /api/items/99999)

500 Internal Server Error for database/server failures

---
## Design Patterns
**Singleton**

patterns/ConfigManager is a Singleton that stores allowed item types (WEAPON, POTION) and provides a single shared instance via getInstance().

**Factory**

patterns/GameItemFactory creates Weapon or Potion based on request type and returns the base type GameItem.

**Builder**

patterns/WeaponBuilder and patterns/PotionBuilder construct complex objects with fluent method calls and optional fields.

---
## Component Principles (REP / CCP / CRP)

REP: reusable modules are separated (repository/service/utils/patterns)

CCP: classes that change together are grouped (items controller/service/repo; backpacks controller/service/repo)

CRP: packages are focused and avoid forcing unrelated dependencies

---
## SOLID & OOP Summary

SRP: controllers handle HTTP only; services handle validation; repositories handle SQL

OCP/LSP: GameItem is abstract; Weapon and Potion extend it without breaking base behavior

ISP: small interfaces (Sellable, Usable)

DIP: services depend on repository abstraction/injection (Spring DI)

---
## How to Run

Create PostgreSQL database gameinventory and run the schema SQL.

Configure src/main/resources/application.properties:

URL, username, password

server port (e.g. 8081)

Run: EndtermApplication

Test endpoints using browser/Postman:

http://localhost:8081/api/items

http://localhost:8081/api/backpacks

---

## Screenshots

Postman screenshots are stored in: docs/screenshots/
They demonstrate successful CRUD, validation errors (400), not found errors (404), and backpack-item relationship.

---
## Reflection

This project helped me understand how to migrate a layered JDBC application into a Spring Boot REST API, apply SOLID in a modern backend structure, and integrate classic design patterns in a real working system.