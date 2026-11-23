# Clinic Care Management System

Full-stack application with a Spring Boot backend and React frontend.

---

## 🗄️ Database Setup (PostgreSQL)

The backend connects to PostgreSQL using the configuration in:
`Clinic_Monday_27545-main/src/main/resources/application.properties`

1. Open your PostgreSQL terminal (psql or pgAdmin) and create the database:
   ```sql
   CREATE DATABASE "CLINIC";
   ```

2. In `application.properties`, confirm your PostgreSQL username and password:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/CLINIC
   spring.datasource.username=postgres
   spring.datasource.password=YOUR_POSTGRES_PASSWORD
   server.port=2028
   ```

3. Hibernate is set to `ddl-auto=update`, which will automatically generate the database tables (`office`, `doctor`, `appointment`, etc.) upon backend startup.

---

## 🚀 Running the Backend (Spring Boot)

In the root backend folder:

```bash
cd Clinic_Monday_27545-main
./mvnw spring-boot:run
```

The backend API will run on **http://localhost:2028**.

---

## 💻 Running the Frontend (React)

Open a new terminal in the `Frontend` folder:

```bash
cd Frontend
npm install
npm run dev
```

Open **http://localhost:3000** in your browser.

---

## 🔗 Live Backend Integration (Zero Mock Data)

The frontend is directly connected to the Spring Boot REST API:
* **Fetch All Offices**: `GET http://localhost:2028/api/offices/all`
* **Create New Office**: `POST http://localhost:2028/api/offices/save`
* **Delete Office**: `DELETE http://localhost:2028/api/offices/delete/{id}`
* **Update Action**: Shows the **"Not Yet"** dialog (scheduled for next class).
* **Live Search**: Real-time filtering by office name or office number across all database records.
