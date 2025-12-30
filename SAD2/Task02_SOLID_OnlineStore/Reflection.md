# Reflection - Task 02

## What I learned
In this task, I learned the practical importance of the SOLID principles in software design. I understood how to break down large, monolithic classes into smaller, more focused ones. Implementing the `PaymentMethod` interface was a great way to see the Open/Closed Principle in action, as it allows the system to support multiple payment types easily.

## What was challenging
The most challenging part was deciding how to split the responsibilities between classes without over-complicating the architecture. For instance, separating the `Admin` logic from the `AdminPanelManager` required careful thought to ensure that the admin's actions were still intuitive while keeping the UI logic separate from the business logic.

## What I improved from previous tasks
Compared to Task 01, I significantly improved the **Maintainability** and **Scalability** of the code. In the previous task, adding a new feature often required modifying existing classes. Now, thanks to the Dependency Inversion and Open/Closed principles, I can extend the system's functionality with minimal changes to the core codebase.
