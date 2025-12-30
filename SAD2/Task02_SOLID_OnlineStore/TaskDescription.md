# Task 02: SOLID Principles Implementation

## Description
The objective of this task was to refactor the **Online Store Management System** from Task 01 to adhere to the **SOLID Principles**. By applying these principles, the system becomes more modular, maintainable, and extensible.

## SOLID Principles Applied
- **Single Responsibility Principle (SRP)**: Each class now has one reason to change. For example, `AdminPanelManager` handles the UI logic for admins, while `Store` handles data management.
- **Open/Closed Principle (OCP)**: The system is open for extension but closed for modification. New payment methods can be added by implementing the `PaymentMethod` interface without changing existing code.
- **Liskov Substitution Principle (LSP)**: Subclasses like `Customer` and `Admin` can replace the base `User` class without affecting the correctness of the program.
- **Interface Segregation Principle (ISP)**: Interfaces are kept specific to their purpose, such as the `PaymentMethod` interface.
- **Dependency Inversion Principle (DIP)**: High-level modules like `CustomerShoppingManager` depend on abstractions (`PaymentMethod`) rather than concrete implementations.

## Requirements
- Refactor Task 01 code using SOLID principles.
- Use interfaces for flexible implementations.
- Maintain all core functionalities from the previous task.
