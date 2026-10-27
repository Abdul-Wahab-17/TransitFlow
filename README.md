# TransitFlow

TransitFlow is a Spring Boot based transport management system for handling customers, drivers, vehicles, schedules, fees, and salaries.

## Features

- Role-oriented flow for `admin`, `driver`, `customer`, and `superadmin`
- Customer and driver dashboards
- Admin dashboard for adding customers, drivers, and vehicles
- Search and edit workflows for operational records
- Active/inactive toggles for key entities
- Pending fee and salary views
- Schedule views for both customers and drivers
- MySQL persistence with Spring Data JPA
- Thymeleaf UI templates and Spring Security authentication

## Tech Stack

- Java 16
- Spring Boot `2.7.18`
- Spring Web
- Spring Data JPA
- Spring Security
- Thymeleaf
- MySQL
- Maven

## Project Structure

- `src/main/java/packages/project` - application source code
- `src/main/resources/templates` - Thymeleaf templates
- `src/main/resources/static` - static assets
- `src/main/resources/application.properties` - runtime configuration

## Prerequisites

- JDK 16
- Maven 3.8+
- MySQL 8+

## Configuration

Default local configuration is in `src/main/resources/application.properties`:

- `server.port=8080`
- `spring.datasource.url=jdbc:mysql://localhost:3306/tms`
- `spring.datasource.username=${DB_USERNAME:root}`
- `spring.datasource.password=${DB_PASSWORD:}`
- `spring.jpa.hibernate.ddl-auto=${DB_DDL_AUTO:update}`
- `spring.jpa.show-sql=${JPA_SHOW_SQL:false}`

Update these values for your local machine before running.

## Database Setup

1. Create the database:

```sql
CREATE DATABASE tms;
```

2. Ensure MySQL is running.
3. Set `DB_USERNAME` and `DB_PASSWORD` as needed, or edit `application.properties` for local-only use.
4. Start the app once; Hibernate will create/update tables automatically (`ddl-auto=update`).

## Run Locally

```bash
DB_USERNAME=root DB_PASSWORD=your-password mvn spring-boot:run
```

Application URL: `http://localhost:8080`

## Build

```bash
mvn clean package
```

## Test

```bash
mvn test
```

## Authentication and Access

- The app uses a custom login form and role redirect flow.
- App login endpoint: `POST /api/login/authenticate`
- Main pages: `/`, `/login`, `/about`, `/contact`

Role redirects from login:

- driver -> `/api/drivers/{loginId}`
- customer -> `/api/customers/{loginId}`
- admin -> `/api/admin/{loginId}`
- special superadmin fallback -> `/api/superadmin/6969`

## Key Routes (High Level)

- Admin area: `/api/admin/{loginId}`, `/admin/pendingFee`, `/admin/pendingSalary`, `/addDriver`, `/addCustomer`, `/addVehicle`
- Driver area: `/api/drivers/{loginId}`, `/drivers/{loginId}/schedule`
- Customer area: `/api/customers/{loginId}`, `/customers/{loginId}/schedule`

## Notes

- Password handling currently includes a hardcoded superadmin fallback path in `LoginController`.
- Database credentials are read from environment variables by default; for production, use managed secrets and replace the demo superadmin fallback.

## License

No license file is currently included in this repository.
