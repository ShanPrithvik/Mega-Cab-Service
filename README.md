# 🚖 Mega Cab Service

Mega Cab Service is a web-based application that allows to book trips, manage drivers, vehicles, and track ride status. This system is built using **Java** for the backend and **React** for the frontend.

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Project Structure](#project-structure)
- [Database Schema](#database-schema)
- [Contribution Guidelines](#contribution-guidelines)
- [Future Enhancements](#future-enhancements)

## 🌟 Overview

Mega Cab Service is designed to streamline cab booking operations. Customers can register, book rides, and receive fare estimates, while administrators can manage vehicles and user as well as manager can manage bookings.

## 🚀 Features


### Admin Features:

- Manage Vehicles
- Manage Users

### Manager Features:

- Book a Cab for Customer (with fare calculation)
- View Booking History

### Driver Features:

- View and Manage the Assigend Trips




## 📂 Project Structure

```
**Backend**

MegaCabService/
├── Web Pages/
│
└── Source Packages/
    ├── config/
    ├── controller/
    ├── dao/
    ├── model/
    │    └── model.enums/
    ├── service/
    └── utils/
    
Libraries/
Configuration Files/



***Frontend**

AD-PR-FRONT-END/
├── ad-pr-front-end/
├── node_modules/          # Node.js dependencies (auto-generated)
├── public/                # Static assets (e.g., images, fonts)
├── src/                   # Source code (React/TypeScript components)
│
├── .gitignore             # Files and folders to exclude from Git
├── components.json        # Component configuration (possibly for auto-imports)
├── eslint.config.js       # ESLint configuration (code linting rules)
├── index.html             # Main HTML file (entry point for Vite)
├── package-lock.json      # Locks package versions (auto-generated)
├── package.json           # Project metadata and dependencies
├── postcss.config.js      # PostCSS configuration (for CSS processing)
├── README.md              # Project documentation
├── tailwind.config.js     # Tailwind CSS configuration
├── tsconfig.app.json      # TypeScript configuration for the app
├── tsconfig.json          # Base TypeScript configuration
├── tsconfig.node.json     # TypeScript configuration for Node.js
└── vite.config.ts         # Vite configuration (build tool)

```


## 📊 Database Schema

**Users Table**

```sql
CREATE TABLE Users (
    Id INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(255) NOT NULL,
    Email VARCHAR(255) UNIQUE NOT NULL,
    Password VARCHAR(255) NOT NULL,
    UserRole ENUM('ADMIN', 'MANAGER', 'DRIVER') NOT NULL,
    IsActive BOOLEAN DEFAULT TRUE
);
```

**Customers Table**

```sql
CREATE TABLE Customers (
    Id INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(255) NOT NULL,
    PhoneNumber VARCHAR(20) UNIQUE NOT NULL,
    NIC VARCHAR(20) UNIQUE NOT NULL
);
```

**Vehicles Table**

```sql
CREATE TABLE Vehicles (
    Id INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(255) NOT NULL,
    Model VARCHAR(255) NOT NULL,
    NumberPlate VARCHAR(50) UNIQUE NOT NULL,
    VehicleType ENUM('TUK', 'BIKE', 'CAR', 'VAN') NOT NULL,
    VehicleColor ENUM('BLACK', 'WHITE', 'BLUE', 'RED') NOT NULL,
    VehicleStatus ENUM('AVAILABLE', 'ASSIGNED', 'MAINTENANCE') DEFAULT 'AVAILABLE'
);
```

**Drivers Table**

```sql
CREATE TABLE drivers (
    Id INT PRIMARY KEY AUTO_INCREMENT,
    UserId INT NOT NULL,
    BookingId INT DEFAULT NULL,
    DriverStatus ENUM('AVAILABLE', 'BOOKED', 'INACTIVE') DEFAULT 'AVAILABLE',
    FOREIGN KEY (UserId) REFERENCES users(Id) ON DELETE CASCADE
);
```

**Bookings Table**

```sql
CREATE TABLE bookings (
    Id INT PRIMARY KEY AUTO_INCREMENT,
    CustomerId INT NOT NULL,
    DriverId INT NOT NULL,
    VehicleId INT NOT NULL,
    PickUpLocation VARCHAR(255) NOT NULL,
    DropOfLocation VARCHAR(255) NOT NULL,
    RideFare DOUBLE NOT NULL,
    BookingStatus ENUM('PENDING', 'ACCEPTED', 'STARTED', 'COMPLETED', 'CANCELLED') DEFAULT 'PENDING',
    PaymentStatus ENUM('PENDING', 'PAID', 'CANCELLED') DEFAULT 'PENDING',
    FOREIGN KEY (CustomerId) REFERENCES customers(Id),
    FOREIGN KEY (DriverId) REFERENCES drivers(Id),
    FOREIGN KEY (VehicleId) REFERENCES vehicles(Id)
);
```


## 🤝 Contribution Guidelines

1. Fork the repository.
2. Create a new branch (`git checkout -b feature/new-feature`).
3. Make your changes and commit (`git commit -m 'Add new feature'`).
4. Push the branch (`git push origin feature/new-feature`).
5. Open a Pull Request.

## 📈 Future Enhancements

- Add support for ride scheduling.
- Implement real-time driver tracking using WebSockets.
- Enable multi-language support.
- Integrate payment gateways.

## 📄 License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.

---

🚀 **Mega Cab Service** – "Your ride, your way!"

