Spring Boot application following a modular monolith architecture, specializing in CRUD operations. The application facilitates diverse HTTP requests, allowing manipulation of data stored in a PostgreSQL database using Hibernate.
The application exposes an endpoint enabling clients to send requests containing a GitHub username. Leveraging the OpenFeign framework, it fetches information about the user's repositories from the GitHub API. Subsequently, the application persists this data in the database and returns a response to the client with the gathered information
Provides a set of CRUD operations for two primary entities: Repo (repository) and Branch. There exists a One-to-Many relationship between Repositories and Branches
The application utilizes Flyway for managing database migrations.

