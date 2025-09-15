# ⚡ Electricity Management System  

A **Spring Boot + Thymeleaf** web application to manage electricity billing.  
It provides separate dashboards for **Admin** and **Customer**, allowing bill generation, payment tracking, and customer management.  

---

## 🚀 Features  

### 🔑 Authentication  
- **Admin Login** (default)  
- **Customer Login** (registered customers only)  

### 🧑‍💼 Admin Dashboard  
- ➕ Add new bills for customers  
- 📋 View all registered customers  
- ❌ Delete customers (only if no pending dues)  
- 💡 View all bills in the system  

### 👤 Customer Dashboard  
- 📊 View **total consumption (kWh)**  
- 💵 See **last payment made**  
- ⚠️ Track **pending payment due**  
- 📄 View bill history (paid/unpaid/partial)  
- 💳 Pay bills (full or partial payment)  

---

## 🏗️ Tech Stack  

- **Backend:** Spring Boot (Java)  
- **Frontend:** Thymeleaf + HTML + CSS  
- **Database:** Apache Derby (Embedded)  
- **Build Tool:** Maven  

---

## ⚙️ Configuration  

Update your `application.properties`:  

spring.application.name=electricitymanagement  
spring.datasource.url=jdbc:derby:electricityDB;create=true  
spring.datasource.driver-class-name=org.apache.derby.jdbc.EmbeddedDriver  
spring.jpa.database-platform=org.hibernate.dialect.DerbyDialect  
spring.jpa.hibernate.ddl-auto=update  
spring.jpa.show-sql=true  

---

## ▶️ Running the Project  

1. Clone the repository:  
   git clone https://github.com/your-username/electricitymanagement.git  
   cd electricitymanagement  

2. Build and run the project:  
   mvn spring-boot:run  

3. Open the app in your browser:  
   http://localhost:8080  

---

## 🖼️ Screenshots  

### 🧑‍💼 Register Page
<img width="1920" height="823" alt="register" src="https://github.com/user-attachments/assets/099f2037-51c2-4409-a521-43dc897b33af" /> 

### 🔐 Login Page  
<img width="1920" height="818" alt="login" src="https://github.com/user-attachments/assets/156f72a7-8b52-437a-9623-96bccd5fafc4" />

User login with **Admin** or **Customer** credentials.  

### 👤 Customer Dashboard  
<img width="1920" height="736" alt="customer dashboard" src="https://github.com/user-attachments/assets/5242ba4a-2968-443a-b66c-74aa60e1c749" />

Check electricity consumption, payment status, and bills.  

---

## 👨‍💻 Author  
Developed by **Nandana Krishnan** 🚀  
