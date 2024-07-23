# Interview App Testing

This project is dedicated to testing the [Interview App](https://interview-app-plum.vercel.app/) using Java, Selenium, JUnit5, and Allure Reports. The objective is to ensure a robust and efficient testing framework that provides detailed reports and evidence for any test failures.

## Project Highlights

1. **Technology Stack**
    - Java
    - Selenium
    - JUnit5
    - Allure Reports

2. **Design Pattern**
    - Implemented the Page Object Model (POM) for a structured and maintainable test design.
    - Implemented the Singleton design pattern with Factory pattern to manage WebDriver instances efficiently and ensure a single instance is created and reused, promoting resource optimization and consistency.


3. **Reporting**
    - Utilized Allure Report to capture detailed evidence including:
        - Screenshots
        - HTML+CSS of the page upon test failure
        - Browser error logs, if any

4. **Utility Classes**
    - **Driver**: Manages WebDriver instances.
    - **Configuration Reader**: Handles global configuration settings.
    - **WebUtils**: Provides commonly used methods to interact with web elements.

## Installation and Running Tests

1. **Clone the Repository**
    ```bash
    git clone https://github.com/patvariali/automate-me
    ```

2. **Run Tests**
    ```bash
    ./gradlew clean test
    ```

3. **Generate Allure Report**
    ```bash
    ./gradlew allureServe
    ```
