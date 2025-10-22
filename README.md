# Nyx App

## Prerequisites

- Java 17 or higher
- PostgreSQL
- Maven

## Setup Instructions

### 1. Database Configuration

Create a PostgreSQL database and user for the application:
```sql
CREATE DATABASE nyx_db;
CREATE USER nyx_user WITH PASSWORD 'your_password';
GRANT ALL PRIVILEGES ON DATABASE nyx_db TO nyx_user;
```

### 2. Environment Variables

Create a `.env` file in the root directory (`nyx-app/.env`) with the following variables:

# Database Configuration
```env
DB_URL=jdbc:postgresql://localhost:5432/nyx_db
DB_USER=nyx_user
DB_PASS=your_password

# JWT Configuration
JWT_SECRET_KEY=your_base64_encoded_secret_key
```

**To generate a JWT secret key:**
```bash
echo -n "your-secret-phrase" | base64
```

### 3. Email Configuration (Optional - for password reset functionality)

If you want to enable the password reset feature, add the following variables to your `.env` file:
```env
# Email Configuration
MAIL_USERNAME=your.email@gmail.com
MAIL_PASSWORD=your_gmail_app_password
```
**Note:** Without email configuration, the password reset endpoint will not send emails, but the rest of the application will work normally.

### 4. Run the Application
```bash
mvn spring-boot:run
```
The application will start on `http://localhost:8080`
