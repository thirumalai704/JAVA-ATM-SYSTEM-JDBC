# ATM Management System (Java + JDBC)

A **console-based ATM Management System** developed using **Java and PostgreSQL (JDBC)** to simulate real-world banking operations such as account creation, deposits, withdrawals, balance enquiry, and user management.

This project focuses on strengthening **Java backend fundamentals** and **database interaction using JDBC**.

---

## 🚀 Features

- Create new bank account with PIN authentication  
- Deposit money securely  
- Withdraw money with balance validation  
- Check account balance  
- Update account details (Name / PIN)  
- Delete account securely  
- View all accounts (admin purpose)  

---

## 🛠 Tech Stack

- **Java**
- **JDBC**
- **PostgreSQL**
- **SQL**
- **Eclipse IDE**

---

## 🗄️ Database Structure

**Table Name:** `atm`

```sql
CREATE TABLE atm (
    account_number VARCHAR(20) PRIMARY KEY,
    name VARCHAR(50),
    pin INTEGER,
    balance DOUBLE PRECISION
);
