# 📚 Library Management System – Backend

Welcome to the backend of the **Library Management System**! This robust Spring Boot project empowers libraries or organizations to efficiently manage their digital book inventory and users, with a focus on security, scalability, and a smooth user experience.

---

## 🚀 Features

### 🛡️ Secure & Modern Authentication
- **JWT (JSON Web Token) Authentication**: All endpoints are protected via industry-standard JWT tokens, ensuring only authorized access.
- **Role-Based Access Control**:
    - **Admins** can manage users and have full control over book inventory.
    - **Regular Users** can browse and read available books.

### 📚 Book & User Management
- **Admin Dashboard**: Add, delete, or update books and users with ease.
- **User Experience**: Seamless signup, login, and exploration of the library catalog.

### 📨 Email Notifications
- **Welcome Emails**: New users receive a personalized welcome email upon registration.

### 🗄️ Database Integration & Persistence
- **Spring Data JPA**: Effortless interaction with a relational database for reliable data storage.
- **Repository Pattern**: Clean, maintainable code for managing Users and Books.

### ⚡️ Fast, Scalable, and Maintainable
- Built with Java & Spring Boot for high performance.
- Designed for easy extension and future feature additions.

---

## 🖥️ Frontend

A modern **React** frontend for this system is now available and under active development!

- Check it out here: [Library Management System Frontend](https://github.com/Aounil/Library-Management-System-Frontend)
- Note: The frontend is a work in progress—stay tuned for new features and improvements!

---

## 🏁 Getting Started

1. **Clone the repository**
   ```bash
   git clone https://github.com/Aounil/Library-Management-System-Backend-.git
   ```
2. **Configure your database** in `application.properties`.
3. **Build & run**
   ```bash
   ./mvnw spring-boot:run
   ```

---

## 🛠️ Tech Stack

- **Java** (100%)
- **Spring Boot**
- **Spring Security (JWT)**
- **Spring Data JPA**
- **Relational Database** (e.g., MySQL/PostgreSQL)
- **JavaMailSender** for email service

---

## 🤝 Contributions

Want to add features or improve the project? Pull requests are welcome! For major changes, please open an issue first to discuss what you would like to change.

---

## 📫 Contact

For questions or collaboration, reach out via [GitHub Issues](https://github.com/Aounil/Library-Management-System-Backend-/issues).

---

> Made with ❤️ by [Aounil](https://github.com/Aounil)