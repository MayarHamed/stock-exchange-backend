# Stock Exchange Backend

A Spring Boot backend application providing REST APIs for managing stocks and exchanges. Supports CRUD operations, stock assignment to exchanges, and business-level exception handling.

---

## Features

- CRUD operations for **Stocks** and **Exchanges**.
- Assign and remove stocks from exchanges.
- Custom exception handling (`BusinessException`) with meaningful HTTP status codes.
- Supports validation and error messages returned as strings.
- Connects to a H2 database (by default).
- RESTful API endpoints for frontend integration.

---

## Tech Stack

- **Backend:** Java 17, Spring Boot
- **Database:** H2 for in-memory testing
- **Build Tool:** Maven

---

## Getting Started

### Prerequisites

- Java 17+
- Maven 3+

---

### Installation

1. Clone the repository:

```bash
git clone https://github.com/YourUsername/stock-exchange-backend.git
cd stock-exchange-backend
```

2. Build and run the app:

```
mvn clean install
mvn spring-boot:run
```

### API Endpoints

## Stocks

- GET /api/stocks - List all stocks
- POST /api/stocks - Add a new stock
- PUT /api/stocks/{id}/price - Update stock price
- DELETE /api/stocks/{id} - Delete a stock

## Exchanges

- GET /api/exchanges - List all exchanges
- POST /api/exchanges - Add a new exchange
- DELETE /api/exchanges/{id} - Delete an exchange
- POST /api/exchanges/{exchangeId}/stocks/{stockId} - Assign stock to exchange
- DELETE /api/exchanges/{exchangeId}/stocks/{stockId} - Remove stock from exchange

### Error Handling

Returns string messages for business exceptions with proper HTTP status codes.
