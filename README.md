
# GitRepoExplorer


### Application that retrieves information about repositories and their branches for a given GitHub user

The application exposes an endpoint enabling clients to send requests containing a GitHub username.
Using the OpenFeign framework, it fetches information about the user's repositories from the GitHub API and filters out only the non-forked ones.
Then, the application persists this data in the PostreSQL database and returns a response to the client with the gathered information.
The application supports diverse HTTP requests, allowing manipulation of data stored in the database using Hibernate.
There are two primary entities: Repository (Repo) and corresponding to this repository branches (Branch) with relation One-To-Many between them.
Application also utilizes Flyway for managing database migrations.


### Specification
- Spring Boot RESTful API 
- Follows Facade design pattern
- Built on a modular monolith architecture
- Handles thrown exceptions using exception handlers
- Establishes a clear separation between entities and DTOs
- Stores data in SQL database (PostreSQL)
- Fetches data from API using OpenFeign framework
- Utilizes Flyway for database migration

### Technologies

![Static Badge](https://img.shields.io/badge/17-Java-orange?style=for-the-badge) &nbsp;
![Static Badge](https://img.shields.io/badge/apache_maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white) &nbsp;
![Static Badge](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring) &nbsp;
![Static Badge](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white) &nbsp;
![Static Badge](https://img.shields.io/badge/Hibernate-4EA94B?style=for-the-badge&logo=Hibernate&logoColor=white) &nbsp;
![Static Badge](https://img.shields.io/badge/Flyway-9B489A?style=for-the-badge&logo=flyway) &nbsp;
![Static Badge](https://img.shields.io/badge/Docker-2CA5E0?style=for-the-badge&logo=docker&logoColor=white) &nbsp;


### Endpoints
The application exposes the following endpoints that operate with data fetched from the GitHub API or stored in the PostgreSQL database.

#### GitHub API

|            ENDPOINT             |                  REQUEST DATA                  |                            RESPONSE BODY                            |                 FUNCTION                 |
|:-------------------------------:|:----------------------------------------------:|:-------------------------------------------------------------------:|:----------------------------------------:|
|   GET github/repos/{username}   |            username (PATH VARIABLE)            | list of user's non-forked repositories with corrresponding branches |    returns user data from GitHub API     |


#### Database
 - operations on repositories:

|            ENDPOINT             |                  REQUEST DATA                  |            RESPONSE BODY            |                                       FUNCTION                                        |
|:-------------------------------:|:----------------------------------------------:|:-----------------------------------:|:-------------------------------------------------------------------------------------:|
|       GET database/repos        |           pageable (OPTIONAL, BODY)            | all repositories stored in database |                           returns all database repositories                           |
|     GET database/repo/{id}      |               id (PATH VARIABLE)               |        requested repository         |                         returns repository with specified id                          |
|       POST database/repo        |             repository data (BODY)             |          added repository           |                              adds repository to database                              |
|      DELETE database/repos      |                    -------                     |              --------               |                                    clears database                                    |
|    DELETE database/repo/{id}    |               id (PATH VARIABLE)               |              --------               |                         deletes repository with specified id                          |
|     PUT database/repo/{id}      | id (PATH VARIABLE),<br/>repository data (BODY) |         updated repository          |        updates repository with specified id using the provided repository data        |
|    PATCH database/repo/{id}     | id (PATH VARIABLE),<br/>repository data (BODY) |         updated repository          | partially updates the repository with specified id using the provided repository data |

- operations on branches:

|            ENDPOINT             |                  REQUEST DATA                  |            RESPONSE BODY            |                                       FUNCTION                                        |
|:-------------------------------:|:----------------------------------------------:|:-----------------------------------:|:-------------------------------------------------------------------------------------:|
| GET database/repo/{id}/branches |               id (PATH VARIABLE)               |         requested branches          |           returns branches that corresponds to repository with specified id           |
|    GET database/branch/{id}     |               id (PATH VARIABLE)               |          requested branch           |                           returns branch with specified id                            |
| POST database/repo/{id}/branch  |   id (PATH VARIABLE),<br/>branch data (BODY)   |   current branches for repository   |                      adds branch to repository with specified id                      |
|   DELETE database/branch/{id}   |               id (PATH VARIABLE)               |              --------               |                           deletes branch with specified id                            |
|   PATCH database/branch/{id}    |   id (PATH VARIABLE),<br/>branch data (BODY)   |           updated branch            |     partially updates the branch with specified id using the provided branch data     |



### Usage
To test the above endpoints you can run the application and use Swagger by following this link:  
http://localhost:8080/swagger-ui/index.html#/  

Alternatively, you can use any web API test application (e.g., using Postman) and send requests to  
http://localhost:8080. Append the endpoints and provide the required request data.






