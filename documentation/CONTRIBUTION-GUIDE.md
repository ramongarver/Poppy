# Poppy - Contribution guide

## 📚 Introduction

Poppy is an internal management application tailored for ESN. The application plays a critical role in facilitating the complex administrative tasks associated with managing volunteers, organizing workgroups, and planning activities for international students. Its key function lies in simplifying the labor-intensive process of assigning volunteers to activities. Poppy seeks to automate and streamline the complete lifecycle, from storing volunteer information and workgroup details to automating the assignment of coordinators for activities slots. Poppy is exposed through a REST API, making it flexible for integration with any client applications.

## 🚀 Getting started

To contribute to Poppy, you'll first need to get a copy of the project repository. Here's how:

- Fork the Poppy repository on GitHub to your own GitHub account.
- Clone your forked repository to your local machine.

🔒 Rule: The main branch is protected; all changes must be introduced through a pull request.

## 💻 Code style

Poppy is built on Spring Boot and adheres to JetBrains Java code style guidelines:

- Use IntelliJ for coding, although the choice of IDE is up to the developer.
- Follow the JetBrains code style guidelines for Spring Boot.
- Comment your code and use meaningful variable and function names.

## 🔄 Contribution process

1. Create a new branch for each feature or bugfix.
2. Develop your changes.
3. Push changes to your fork.
4. Submit a pull request against the main branch.
5. Pass all checks including quality control with Sonar.
6. Upon successful review, the code will be merged via merge commits.

## 🐛 Reporting bugs and suggesting improvements

To report bugs or suggest improvements, create a GitHub issue with the appropriate label and include necessary information to reproduce the issue or understand the improvement.

## 🧪 Testing

Testing is a crucial part of the development process in the Poppy project. To maintain high-quality code, make sure to adhere to the following guidelines:

- **Modifying existing functionality**. If you're modifying existing functionality, it's essential to adapt the existing tests to accommodate these changes. The goal is to ensure that the altered function still meets its intended behavior and doesn't introduce regressions.

  1. Locate the relevant unit or integration tests that are related to the functionality you're modifying.
  2. Update the tests to reflect the changes you've made to the code.
  3. Run the tests to ensure they all pass with the updated functionality.
  4. If applicable, add new test cases to cover any new edge cases introduced by your changes.

- **Adding New Functionality**. When introducing new features or functionalities, new tests must be written to cover these additions:
  1. Write unit tests that focus on the new functionality, ensuring it works as intended in isolation.
  2. If your feature interacts with other parts of the system, consider adding integration tests.
  3. Make sure to cover edge cases and error-handling scenarios.
  4. Run all tests to ensure that they pass and that the new functionality works cohesively with the existing code.

## 🤝 Code of conduct

We strive to foster an inclusive and respectful environment:

- Be courteous and polite.
- Respect different perspectives and contributions.
- Avoid any form of harassment or discrimination.

## 👏 Acknowledging contributors

We value every contribution and thank all our contributors.

## 📞 Contact information

If you have questions, need further clarification, or would like to discuss issues related to the project, feel free to reach out via [email](mailto:rgarver@correo.ugr.es).
