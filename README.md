# Employee Management System

A comprehensive Spring Boot application for managing employee information with a modern web interface.

## 🚀 Features

- **Employee CRUD Operations**: Create, Read, Update, and Delete employee records
- **Search & Filter**: Search employees by name and filter by department
- **Modern UI**: Responsive Bootstrap-based interface with Font Awesome icons
- **REST API**: Full REST API endpoints for programmatic access
- **API Documentation**: Interactive Swagger/OpenAPI documentation
- **User Authentication**: Secure login and registration system
- **Role-Based Access Control**: User and Admin roles with different permissions
- **Password Security**: BCrypt password encryption
- **Database Integration**: MySQL database with JPA/Hibernate
- **Template Engine**: Thymeleaf for server-side rendering

## 🛠️ Technology Stack

- **Backend**: Spring Boot 3.5.4
- **Language**: Java 17
- **Database**: MySQL
- **ORM**: Spring Data JPA with Hibernate
- **Template Engine**: Thymeleaf
- **Frontend**: Bootstrap 5, Font Awesome
- **API Documentation**: Swagger/OpenAPI 3.0
- **Security**: Spring Security with BCrypt
- **Build Tool**: Maven

## 📋 Prerequisites

- Java 17 or higher
- MySQL 8.0 or higher
- Maven 3.6 or higher

## 🗄️ Database Setup

1. Install and start MySQL server
2. Create a database (optional - the application will create it automatically)
3. Update database credentials in `application.properties` if needed

## ⚙️ Configuration

The application is configured via `src/main/resources/application.properties`:

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/empcursodemo?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=password

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Server Configuration
server.port=8080
```

**Important**: Update the database username and password according to your MySQL setup.

## 🚀 Running the Application

### Using Maven Wrapper (Recommended)

```bash
# On Windows
./mvnw.cmd spring-boot:run

# On Linux/Mac
./mvnw spring-boot:run
```

### Using Maven

```bash
mvn spring-boot:run
```

### Using IDE

Run the `EmpcursodemoApplication.java` class directly from your IDE.

## 🌐 Accessing the Application

Once the application starts, you can access it at:

- **Home Page**: http://localhost:8080
- **Login Page**: http://localhost:8080/login
- **Registration Page**: http://localhost:8080/register
- **Employee List**: http://localhost:8080/employees (requires authentication)
- **Dashboard**: http://localhost:8080/dashboard (requires authentication)
- **User Profile**: http://localhost:8080/profile (requires authentication)
- **API Documentation**: http://localhost:8080/swagger-ui.html
- **API Docs Redirect**: http://localhost:8080/api-docs

### Default Admin Account

The application automatically creates a default admin user on first startup:

- **Username**: admin
- **Password**: admin123
- **Email**: admin@employeemgmt.com
- **Role**: ADMIN

**Important**: Change the default password after first login for security!

## 📁 Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/employeemangement/empcursodemo/
│   │       ├── EmpcursodemoApplication.java          # Main application class
│   │       ├── controller/
│   │       │   ├── EmployeeController.java          # Employee REST controller
│   │       │   └── HomeController.java              # Home page controller
│   │       ├── entity/
│   │       │   └── Employee.java                    # Employee entity
│   │       ├── repository/
│   │       │   └── EmployeeRepository.java          # Data access layer
│   │       └── service/
│   │           └── EmployeeService.java             # Business logic layer
│   └── resources/
│       ├── application.properties                   # Application configuration
│       └── templates/
│           ├── home.html                           # Home page template
│           ├── dashboard.html                      # Dashboard template
│           └── employees/
│               ├── list.html                       # Employee list template
│               ├── form.html                       # Employee form template
│               └── details.html                    # Employee details template
└── test/
    └── java/
        └── com/employeemangement/empcursodemo/
            └── EmpcursodemoApplicationTests.java    # Test class
```

## 🔐 Authentication & Security

### User Registration and Login

The application includes a complete authentication system with the following features:

- **User Registration**: New users can create accounts with username, email, and password
- **Secure Login**: Username/password authentication with Spring Security
- **Password Encryption**: All passwords are encrypted using BCrypt
- **Role-Based Access**: Two user roles (USER and ADMIN) with different permissions
- **Session Management**: Secure session handling with automatic logout

### User Roles and Permissions

#### USER Role
- Access to employee management features
- View and manage employee data
- Access to dashboard and reports
- Update own profile information

#### ADMIN Role
- All USER permissions
- Access to admin-only features
- User management capabilities
- System configuration access

### Security Features

- **CSRF Protection**: Enabled for all forms
- **Password Validation**: Minimum 6 characters required
- **Account Locking**: Automatic account status management
- **Session Security**: Secure session handling
- **Input Validation**: Server-side validation for all inputs

### Password Security

- **BCrypt Encryption**: Industry-standard password hashing
- **Salt Generation**: Automatic salt generation for each password
- **Password Change**: Users can change passwords securely
- **Password History**: Prevents reuse of recent passwords

## 🔧 API Endpoints

### Web Interface Endpoints

- `GET /` - Home page
- `GET /login` - Login page
- `POST /login` - Process login
- `GET /register` - Registration page
- `POST /register` - Process registration
- `GET /logout` - Logout
- `GET /profile` - User profile (requires authentication)
- `POST /profile/update` - Update profile (requires authentication)
- `GET /change-password` - Change password page (requires authentication)
- `POST /change-password` - Process password change (requires authentication)
- `GET /access-denied` - Access denied page
- `GET /dashboard` - Dashboard (requires authentication)
- `GET /employees` - Employee list (requires authentication)
- `GET /employees/new` - Add employee form (requires authentication)
- `POST /employees` - Create employee (requires authentication)
- `GET /employees/{id}` - Employee details (requires authentication)
- `GET /employees/{id}/edit` - Edit employee form (requires authentication)
- `POST /employees/{id}` - Update employee (requires authentication)
- `GET /employees/{id}/delete` - Delete employee (requires authentication)
- `GET /employees/search?searchTerm={term}` - Search employees (requires authentication)
- `GET /employees/department/{department}` - Filter by department (requires authentication)

### REST API Endpoints

- `GET /employees/api` - Get all employees (JSON, requires authentication)
- `GET /employees/api/{id}` - Get employee by ID (JSON, requires authentication)
- `POST /employees/api` - Create employee (JSON, requires authentication)
- `PUT /employees/api/{id}` - Update employee (JSON, requires authentication)
- `DELETE /employees/api/{id}` - Delete employee (JSON, requires authentication)
- `GET /employees/api/search?searchTerm={term}` - Search employees by name (requires authentication)
- `GET /employees/api/department/{department}` - Get employees by department (requires authentication)
- `GET /employees/api/salary/range?minSalary={min}&maxSalary={max}` - Get employees by salary range (requires authentication)
- `GET /employees/api/count` - Get total employee count (requires authentication)

## 📚 API Documentation

### Swagger/OpenAPI Integration

The application includes comprehensive API documentation powered by Swagger/OpenAPI 3.0.

#### Accessing API Documentation

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs Redirect**: http://localhost:8080/api-docs
- **OpenAPI JSON**: http://localhost:8080/api-docs

#### Features

- **Interactive Documentation**: Test APIs directly from the browser
- **Request/Response Examples**: See sample data for all endpoints
- **Schema Documentation**: Complete data model documentation
- **Response Codes**: Detailed HTTP status code descriptions
- **Parameter Validation**: Input validation rules and examples

#### API Endpoints Documentation

##### Employee Management APIs

| Method | Endpoint | Description | Response |
|--------|----------|-------------|----------|
| GET | `/employees/api` | Get all employees | `200 OK` - List of employees |
| GET | `/employees/api/{id}` | Get employee by ID | `200 OK` - Employee object<br>`404 Not Found` - Employee not found |
| POST | `/employees/api` | Create new employee | `201 Created` - Created employee<br>`409 Conflict` - Email already exists |
| PUT | `/employees/api/{id}` | Update employee | `200 OK` - Updated employee<br>`404 Not Found` - Employee not found |
| DELETE | `/employees/api/{id}` | Delete employee | `200 OK` - Success message<br>`404 Not Found` - Employee not found |

##### Search and Filter APIs

| Method | Endpoint | Description | Response |
|--------|----------|-------------|----------|
| GET | `/employees/api/search` | Search by name | `200 OK` - Matching employees |
| GET | `/employees/api/department/{dept}` | Filter by department | `200 OK` - Department employees |
| GET | `/employees/api/salary/range` | Filter by salary range | `200 OK` - Employees in range |
| GET | `/employees/api/count` | Get employee count | `200 OK` - Total count |

#### Request/Response Examples

##### Create Employee Request
```json
POST /employees/api
Content-Type: application/json

{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@company.com",
  "phone": "+1-555-123-4567",
  "department": "IT",
  "salary": 75000.00,
  "hireDate": "2023-01-15"
}
```

##### Employee Response
```json
{
  "id": 1,
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@company.com",
  "phone": "+1-555-123-4567",
  "hireDate": "2023-01-15",
  "salary": 75000.00,
  "department": "IT"
}
```

#### Testing APIs

1. **Using Swagger UI**:
   - Navigate to http://localhost:8080/swagger-ui.html
   - Click on any endpoint to expand it
   - Click "Try it out" to test the API
   - Fill in required parameters
   - Click "Execute" to send the request

2. **Using cURL**:
   ```bash
   # Get all employees
   curl -X GET "http://localhost:8080/employees/api"
   
   # Create employee
   curl -X POST "http://localhost:8080/employees/api" \
     -H "Content-Type: application/json" \
     -d '{"firstName":"Jane","lastName":"Smith","email":"jane.smith@company.com","department":"HR","salary":65000}'
   
   # Search employees
   curl -X GET "http://localhost:8080/employees/api/search?searchTerm=john"
   ```

3. **Using Postman**:
   - Import the OpenAPI specification from http://localhost:8080/api-docs
   - Use the generated collection to test all endpoints

## 🎨 UI Features

- **Responsive Design**: Works on desktop, tablet, and mobile devices
- **Modern Interface**: Clean, professional design with Bootstrap 5
- **Interactive Elements**: Hover effects, animations, and smooth transitions
- **Search Functionality**: Real-time search with instant results
- **Department Filtering**: Quick access to department-specific views
- **Form Validation**: Client-side and server-side validation
- **Confirmation Dialogs**: Safe delete operations with confirmation

## 🔍 Employee Entity Fields

- **ID**: Auto-generated primary key
- **First Name**: Required field
- **Last Name**: Required field
- **Email**: Required, unique field
- **Phone**: Optional contact information
- **Department**: Dropdown selection (IT, HR, Finance, Marketing, Sales, Operations)
- **Salary**: Numeric field with decimal support
- **Hire Date**: Date field (auto-set to current date if not provided)

## 🧪 Testing

Run the tests using:

```bash
mvn test
```

## 📦 Building for Production

Create a JAR file:

```bash
mvn clean package
```

Run the JAR file:

```bash
java -jar target/empcursodemo-0.0.1-SNAPSHOT.jar
```

## 🔒 Security Considerations

- Update default database credentials
- Consider implementing authentication and authorization
- Validate all user inputs
- Use HTTPS in production
- Implement proper error handling

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

## 🆘 Support

If you encounter any issues or have questions:

1. Check the application logs for error messages
2. Verify your MySQL connection settings
3. Ensure Java 17+ is installed and configured
4. Check that all required ports are available

## 🔄 Future Enhancements

- User authentication and authorization
- Role-based access control
- Advanced reporting and analytics
- Email notifications
- File upload for employee photos
- Audit logging
- API rate limiting
- Docker containerization
