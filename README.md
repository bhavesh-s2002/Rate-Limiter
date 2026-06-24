# RateGuard - Distributed Rate Limiting Service

## Overview

RateGuard is a backend rate-limiting service built using Spring Boot, Redis, and PostgreSQL.

The project supports multiple rate-limiting algorithms and dynamically applies policies based on client subscription plans. It demonstrates backend design patterns, Redis-based distributed storage, database-driven configuration, and scalable request throttling.

## Features

* Fixed Window Rate Limiter
* Sliding Window Rate Limiter
* Token Bucket Rate Limiter
* Redis-backed storage
* PostgreSQL-backed client and policy management
* Dynamic policy selection based on client plan
* Filter-based request interception
* Strategy Pattern implementation
* Factory Pattern implementation
* Custom rate limit response headers
* Global exception handling
* Extensible architecture for adding new algorithms

---

## Tech Stack

### Backend

* Java 21
* Spring Boot 3

### Database

* PostgreSQL

### Cache / Distributed Storage

* Redis

### Build Tool

* Maven

### API Testing

* Postman

---

## Supported Algorithms

### 1. Fixed Window

Requests are counted within a fixed time window.

Example:

* Limit: 10 requests
* Window: 60 seconds

The 11th request within the same window is rejected.

---

### 2. Sliding Window

Tracks requests continuously using timestamps.

Provides smoother rate limiting than Fixed Window by preventing burst traffic near window boundaries.

---

### 3. Token Bucket

Tokens are generated at a configured refill rate.

Each request consumes one token.

Requests are allowed only when tokens are available.

Supports burst traffic while maintaining long-term request limits.

---

## Architecture

Request

↓

RateLimitFilter

↓

RateLimiterService

↓

PolicyService + ClientService

↓

RateLimiterFactory

↓

Selected Algorithm

(Fixed Window / Sliding Window / Token Bucket)

↓

Redis

---

## Design Patterns Used

### Strategy Pattern

Each rate-limiting algorithm is implemented independently.

Examples:

* FixedWindowRateLimiter
* SlidingWindowRateLimiter
* TokenBucketRateLimiter

All implement:

```java
RateLimiter
```

---

### Factory Pattern

RateLimiterFactory dynamically returns the correct implementation based on the configured policy.

```java
factory.getLimiter(policy.getType());
```

This removes algorithm-specific logic from the service layer.

---

## Project Structure

```text
com.springBoot.RateGuard

├── client
│   ├── Client
│   └── Plan
│
├── config
│   └── RedisConfig
│
├── controller
│   └── TestController
│
├── entity
│   ├── ClientEntity
│   └── PolicyEntity
│
├── exception
│   ├── GlobalExceptionHandler
│   └── RateLimitExceededException
│
├── factory
│   └── RateLimiterFactory
│
├── filter
│   └── RateLimitFilter
│
├── limiter
│   ├── RateLimiter
│   ├── FixedWindowRateLimiter
│   ├── SlidingWindowRateLimiter
│   └── TokenBucketRateLimiter
│
├── model
│   ├── RateLimitEntry
│   ├── SlidingWindowEntry
│   ├── TokenBucketEntry
│   └── RateLimitResult
│
├── policy
│   └── RateLimitPolicy
│
├── repository
│   ├── ClientRepository
│   └── PolicyRepository
│
├── service
│   ├── ClientService
│   ├── PolicyService
│   ├── RateLimiterService
│   └── RateLimiterServiceImpl
│
├── storage
│   ├── RedisFixedWindowStore
│   ├── RedisSlidingWindowStore
│   └── RedisTokenBucketStore
│
├── strategy
│   └── RateLimiterType
│
└── RateGuardApplication
```

---

## Database Schema

### clients

| Column    | Type    |
| --------- | ------- |
| id        | BIGINT  |
| client_id | VARCHAR |
| name      | VARCHAR |
| plan      | VARCHAR |

---

### policies

| Column       | Type    |
| ------------ | ------- |
| id           | BIGINT  |
| plan         | VARCHAR |
| algorithm    | VARCHAR |
| max_requests | INTEGER |
| window_size  | BIGINT  |
| capacity     | BIGINT  |
| refill_rate  | BIGINT  |

---

## Sample Policies

### FREE

* Algorithm: Fixed Window
* Limit: 10 requests/minute

### BASIC

* Algorithm: Sliding Window
* Limit: 20 requests/minute

### PREMIUM

* Algorithm: Token Bucket
* Capacity: 30 tokens
* Refill Rate: 1 token/second

---

## Request Flow

1. Client sends request.
2. RateLimitFilter intercepts request.
3. Client information is fetched from PostgreSQL.
4. Policy is fetched from PostgreSQL.
5. Factory selects the correct rate-limiting algorithm.
6. Algorithm retrieves state from Redis.
7. Request is allowed or rejected.
8. Response is returned with rate-limit information.

---

## API Testing

Header:

```http
X-CLIENT-ID: user1
```

Example:

```http
GET /test
```

Successful Response:

```http
200 OK
```

Rate Limit Exceeded:

```http
429 Too Many Requests
```

---

## Redis Usage

Redis stores algorithm state:

### Fixed Window

```text
fixed_window:user1
```

### Sliding Window

```text
sliding_window:user1
```

### Token Bucket

```text
token_bucket:user1
```

This enables fast access and prepares the system for distributed deployment.

---

## How To Run

### 1. Clone Repository

```bash
git clone https://github.com/your-username/RateGuard.git
```

### 2. Start PostgreSQL

Create database:

```sql
CREATE DATABASE rateguard;
```

### 3. Start Redis

Verify:

```bash
redis-cli ping
```

Expected:

```text
PONG
```

### 4. Configure Application

Create:

```text
application.yml
```

using the provided example file.

### 5. Run Application

```bash
mvn spring-boot:run
```

### 6. Test Using Postman

Add header:

```http
X-CLIENT-ID: user1
```

and hit:

```http
GET /test
```

---

## Future Improvements

* Docker support
* Kubernetes deployment
* Redis Cluster
* API Gateway integration
* Monitoring with Prometheus and Grafana
* Distributed token synchronization
* Admin dashboard

---

## Learning Outcomes

Through this project I learned:

* Spring Boot Architecture
* Redis Integration
* PostgreSQL Integration
* Strategy Pattern
* Factory Pattern
* Request Filtering
* Rate Limiting Algorithms
* Distributed State Management
* Backend System Design Concepts

---

## Author

Bhavesh Sharma

Backend Project: RateGuard – Distributed Rate Limiting Service
