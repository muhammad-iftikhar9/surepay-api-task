# SurePay API Test Automation Framework

This project provides an automated testing framework designed to validate the JSONPlaceholder API, with a primary focus on ensuring the correctness of email formats in comments on posts made by a specific user.
## Project Structure

```
src
├── main
│   └── java
│       └── com
│           └── surepay
│               ├── model
│               │   ├── Address.java
│               │   ├── Comment.java
│               │   ├── Company.java
│               │   ├── Geo.java
│               │   ├── Post.java
│               │   └── User.java
│               ├── restclient
│               │   └── HttpRestClient.java
│               ├── service
│               │   ├── CommentService.java
│               │   ├── PostService.java
│               │   └── UserService.java
│               └── utils
│                   ├── ConfigurationManager.java
│                   └── ValidationUtils.java
└── test
    ├── java
    │   └── com
    │       └── surepay
    │           └── apitests
    │               └── ValidateCommentEmailTest.java
    └── resources
        └── configuration
            └── configuration.json

```

## Tools and Technologies

This project utilizes the following major libraries and tools:
- Java 21 — Programming language
- Maven — Build and dependency management
- REST Assured — for API testing
- JUnit 5 — for writing and executing tests
- AssertJ — for fluent assertions
- Jackson Databind — for JSON processing
- Lombok — to reduce boilerplate code
- Allure — for test reporting and visualization

## Setup and Execution 
### Cloning the Repository
To clone the project, run the following command: 
```
git clone https://github.com/muhammad-iftikhar9/surepay-api-task.git
```
### Running the Tests
To run all tests:
```
mvn clean test
```

### Allure Reporting
This project integrates with Allure to provide rich, interactive test reports.

#### Viewing the Allure Report
After running the tests, you can generate and view the Allure report by running:
```
allure serve "target/allure-results"
```
This will do the following: 
- Generate the report from test results
- Start a local server 
- Automatically open the report in your default browser and show test results.

## Managing Tests with Tags

As the application grows, we can use tags like @Tag("Regression") or @Tag("EmailValidation") to categorize and run specific sets of tests.
### Categorizing Tests

- **Email Validation Tests**:  
  These tests focus on verifying that email addresses in API responses are correctly formatted and handled.

- **Regression Tests**:  
  A broader set of tests that ensure new changes do not break existing functionality. They include tests for critical workflows like email validation, API responses, and more.

### Running Tagged Tests
You can run specific tagged tests using Maven and JUnit 5 tags.

To run only email validation tests:

```bash
mvn clean test -Dgroups=EmailValidation
```

## API Endpoints
The framework tests the following JSONPlaceholder API endpoints:

- `/users` - Get detail information about user.
- `/posts` - Get all posts created by a specific user.
- `/comments` - Get all comments on a specific post.

## Test Documentation
The test documentation for this project is automatically generated using Allure reporting. This includes details about the test execution, coverage, and any defects encountered during the tests.

### Test Coverage
The tests cover the key functionality of the JSONPlaceholder API, particularly focusing on the email validation in comments on user posts. 
All the essential workflows, including user retrieval, post fetching, and comment checking, are thoroughly tested. Test coverage is tracked through the Allure report, where you can view detailed insights into which tests have been executed, their statuses (passed, failed, skipped), and the specific test cases covered.
### Test Cases
- Verifies that the email addresses in the comments on posts made by a specific user are correctly formatted. <br>
  The framework validates email addresses using the following regex pattern:
```
^[A-Za-z0-9+_.-]+@(.+)$
```
- Ensures that the API returns an empty list when querying for a user that does not exist. 
- Verify that for a user with no posts the API returns an empty list.
- Verify that the API returns an empty list when querying for comments on a post with no comments

### Framework Layers

- **Test Layer** (`apitests/`)  <br>
  This layer contains the actual test cases of the project with robust assertions and reporting.
- **Service Layer** (`service/`) <br>
  Implements the business logic and API interaction flows and manage data transformation between layers.
- **Data Layer** (`model/`) <br>
  Defines the data structures and data models for API responses and ensures data integrity and seamless JSON to Java object mapping.
- **API Client Layer** (`restclient/`) <br>
  This layer Handles HTTP communication with the API and manages API requests and responses.
- **Utility Layer** (`utils/`) <br>
  This layer contains common utility functions and helpers and provide resuable functions and utilities that can be used across the project to avoid code duplication and improve maintainability. 
- **Allure Reporting Layer** (`target/allure-results/`) <br>
  This layer generates test execution reports and provides detailed test insights and execution analytics.
- **Maven Build Layer** (`pom.xml`) <br>
  It manages project dependencies and the build lifecycle.

This layered architecture ensures:
- Extensible architecture that allows adding new features without modifying existing code, adhering to the Open/Closed Principle.
- Encourages reusability and prevents code duplication by applying the DRY (Don't Repeat Yourself) principle.
- Follows the KISS (Keep It Simple, Stupid) principle to maintain simplicity and clarity in implementations.
- Establishes clear boundaries between components, ensuring strong encapsulation.
- Utilizes well-defined interfaces and contracts to provide effective abstraction across layers.
- Prioritizes maintainability, making the system easy to update, extend, and adapt to changes.
- Ensures scalability, preparing the project for future growth and enhancements.

### CircleCI Integration
This project is integrated with CircleCI to automatically run tests in a Continuous Integration pipeline. Upon each push request, CircleCI will trigger the execution of all test cases to ensure the integrity of the codebase.