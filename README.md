# Multiple Choice Quiz API

This is a REST API for a quiz application where users can take a quiz, answer questions, and receive a summary of their performance. The API allows the starting of a quiz session, fetching random questions, submitting answers, and viewing quiz summaries.

## Features

-   Start a new quiz session
-   Get a random question
-   Submit an answer for a question
-   Get a summary of the quiz (correct and incorrect answers)
-   Ensure questions are not repeated within a session

## Prerequisites

Before running the project, ensure that you have the following installed:

-   Java 17 or higher
-   Maven or Gradle
-   A running instance of a database (H2, MySQL, PostgreSQL, etc.)
-   Postman or any API testing tool for testing the endpoints (optional)

## Getting Started

### Clone the Repository

```bash
git clone https://github.com/your-username/conceptile-quiz-api.git
cd conceptile-quiz-api

```

### Build the Project

Make sure you have Maven installed, then build the project using:

```bash
mvn clean install

```

This will download the required dependencies and compile the project.

### Database Setup

The application uses **H2** in-memory database for simplicity. The following configuration is defined in `application.properties`:

```properties
spring.application.name=quiz-api
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
spring.jpa.hibernate.ddl-auto=update
spring.datasource.initialization-mode=always  
spring.sql.init.mode=always

```

-   **H2 Database**: The app uses H2 as an in-memory database, which will be cleared when the application is stopped.
-   **H2 Console**: The H2 console is enabled for easy access to the database via the browser. You can access it at `http://localhost:8080/h2-console` after starting the application.
    -   JDBC URL: `jdbc:h2:mem:testdb`
    -   Username: `sa`
    -   Password: `password`

### Initial Data Setup

The initial data is provided in the `data.sql` file, which will automatically populate the database when the application starts.

-   **APP_USER Table**: Contains users with IDs and usernames.
-   **QUESTION Table**: Contains questions, options, and correct answers.

**Sample Data in `data.sql`:**

```sql
-- Creating APP_USER table
CREATE TABLE IF NOT EXISTS APP_USER (
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    USERNAME VARCHAR(255) NOT NULL
);

-- Creating QUESTION table
CREATE TABLE IF NOT EXISTS QUESTION (
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    OPTIONA VARCHAR(255),
    OPTIONB VARCHAR(255),
    OPTIONC VARCHAR(255),
    OPTIOND VARCHAR(255),
    CORRECT_ANSWER CHAR(1),
    QUESTION_TEXT VARCHAR(255)
);

-- Inserting data into APP_USER table
INSERT INTO APP_USER (ID, USERNAME) VALUES (1, 'admin'), (2, 'user');

-- Inserting data into QUESTION table
INSERT INTO QUESTION (ID, OPTIONA, OPTIONB, OPTIONC, OPTIOND, CORRECT_ANSWER, QUESTION_TEXT) 
VALUES 
(1, 'Paris', 'Berlin', 'Rome', 'Madrid', 'A', 'What is the capital of France?'),
(2, '3', '4', '5', '6', 'B', 'What is 2 + 2?'),
(3, 'Java', 'Python', 'JavaScript', 'C++', 'A', 'Which programming language is used for Android development?'),
(4, 'Mount Everest', 'K2', 'Kangchenjunga', 'Lhotse', 'C', 'What is the tallest mountain in the world?');

```

Once the application starts, this data will be automatically loaded into the H2 database.

### Run the Application

To run the application:

```bash
mvn spring-boot:run

```

Alternatively, you can run the application from your IDE (e.g., IntelliJ IDEA, Eclipse).

The application will start and be available at `http://localhost:8080`.

## API Endpoints

### 1. **Start Quiz Session**

-   **URL**: `/api/quiz/start`
-   **Method**: `GET`
-   **Description**: Starts a new quiz session for a user (currently hardcoded user with ID `1`).
-   **Response**: `Quiz session started!`

### 2. **Get Random Question**

-   **URL**: `/api/quiz/question`
    
-   **Method**: `GET`
    
-   **Description**: Fetches a random question that has not yet been asked in the current quiz session.
    
-   **Response**: A `Question` object containing the question details.
    
    **Response Example**:
    
    ```json
    {
      "questionId": 1,
      "questionText": "What is the capital of France?",
      "options": ["Paris", "Berlin", "Rome", "Madrid"]
    }
    
    ```
    

### 3. **Submit Answer**

-   **URL**: `/api/quiz/answer`
-   **Method**: `POST`
-   **Description**: Submits an answer for a question. The request body should include the `questionId` and `selectedOption`.
-   **Request Body**:
    
    ```json
    {
      "questionId": 1,
      "selectedOption": "4"
    }
    
    ```
    
-   **Response**: `"Correct Answer!"` or `"Incorrect Answer."` based on whether the answer is correct.

### 4. **Quiz Summary**

-   **URL**: `/api/quiz/summary`
    
-   **Method**: `GET`
    
-   **Description**: Provides a summary of the quiz, including the total number of questions answered, correct answers, and incorrect answers.
    
-   **Response**: A string with the summary.
    
    **Response Example**:
    
    ```
    Total answered: 5, Correct: 3, Incorrect: 2
    
    ```
    

## Example Workflow

1.  Start the quiz session by making a `GET` request to `/api/quiz/start`.
2.  Get a random question by making a `GET` request to `/api/quiz/question`.
3.  Submit your answer by making a `POST` request to `/api/quiz/answer` with the question ID and selected option.
4.  Get a summary of your quiz by making a `GET` request to `/api/quiz/summary`.

## Testing the API

You can use **Postman** or any other API testing tool to make requests to the above endpoints.

### Example Using Postman

-   **Start Quiz**:
    
    -   Method: `GET`
    -   URL: `http://localhost:8080/api/quiz/start`
-   **Get Random Question**:
    
    -   Method: `GET`
    -   URL: `http://localhost:8080/api/quiz/question`
-   **Submit Answer**:
    
    -   Method: `POST`
    -   URL: `http://localhost:8080/api/quiz/answer`
    -   Body (JSON):
        
        ```json
        {
          "questionId": 1,
          "selectedOption": "4"
        }
        
        ```
        
-   **Get Summary**:
    
    -   Method: `GET`
    -   URL: `http://localhost:8080/api/quiz/summary`

## Error Handling

-   **No Quiz Session Started**: If a request is made before starting a quiz session, the API will respond with a message saying: `"Please start a quiz session first."`
    
-   **No More Questions Available**: If all questions have been asked, the API will respond with: `"No more questions available."`
    
-   **Question Already Answered**: If an attempt is made to answer the same question more than once, the API will respond with: `"This question has already been answered."`
    

## License

This project is licensed under the MIT License - see the [LICENSE](https://chatgpt.com/c/LICENSE) file for details.

