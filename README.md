# Classroom Management System

A comprehensive JavaFX application for managing classroom information with authentication, CRUD operations, and modern UI design.

## 🚀 Features

### Authentication System

- **User Login/Register**: Secure authentication with password hashing (SHA-256)
- **Session Management**: User session handling with logout functionality
- **Role-based Access**: Support for ADMIN and USER roles
- **Password Security**: Secure password storage and verification

### Classroom Management

- **View Classrooms**: Display all classrooms in a responsive table
- **Add Classrooms**: Create new classroom entries with validation
- **Edit Classrooms**: Modify existing classroom information
- **Delete Classrooms**: Remove classrooms with confirmation dialogs
- **Search & Filter**: Advanced search and filtering capabilities

### User Interface

- **Modern Design**: Clean, responsive UI with BootstrapFX styling
- **Real-time Updates**: Live data updates using Observer pattern
- **Confirmation Dialogs**: User-friendly confirmation for critical actions
- **Status Feedback**: Toast messages and alerts for user feedback

## 🏗️ Architecture

This project follows **Clean Architecture** principles with clear separation of concerns:

```
┌─────────────────────────────────────────────────────────────┐
│                    Presentation Layer                      │
│  ┌─────────────┐ ┌─────────────┐ ┌─────────────┐        │
│  │   Views     │ │ Controllers │ │   Models    │        │
│  └─────────────┘ └─────────────┘ └─────────────┘        │
└─────────────────────────────────────────────────────────────┘
┌─────────────────────────────────────────────────────────────┐
│                     Business Layer                         │
│  ┌─────────────┐ ┌─────────────┐ ┌─────────────┐        │
│  │  Use Cases  │ │   Factory   │ │   DTOs      │        │
│  └─────────────┘ └─────────────┘ └─────────────┘        │
└─────────────────────────────────────────────────────────────┘
┌─────────────────────────────────────────────────────────────┐
│                   Persistence Layer                       │
│  ┌─────────────┐ ┌─────────────┐ ┌─────────────┐        │
│  │   Gateways  │ │    DAOs     │ │  Database   │        │
│  └─────────────┘ └─────────────┘ └─────────────┘        │
└─────────────────────────────────────────────────────────────┘
```

### Design Patterns

- **Observer Pattern**: For UI updates and real-time data synchronization
- **Factory Pattern**: For creating different types of rooms
- **MVC Pattern**: For UI organization and data flow
- **Repository Pattern**: For data access abstraction

## 🛠️ Technology Stack

- **Java**: Core programming language
- **JavaFX**: UI framework for desktop applications
- **BootstrapFX**: Modern UI styling
- **MySQL**: Database management system
- **JDBC**: Database connectivity
- **Maven**: Build and dependency management
- **Lombok**: Boilerplate code reduction

## 📋 Prerequisites

- Java 17 or higher
- MySQL 8.0 or higher
- Maven 3.6 or higher

## 🚀 Installation & Setup

### 1. Clone the Repository

```bash
git clone <repository-url>
cd phonghoc
```

### 2. Database Setup

```sql
-- Create database
CREATE DATABASE rooms;
USE rooms;

-- Run the room table script
source room.sql;

-- Run the users table script
source users.sql;
```

### 3. Configure Database Connection

Edit `src/main/java/vn/giadinh/phonghoc/persistence/dao/InitializeDAO.java`:

```java
String username = "your_username";
String password = "your_password";
String url = "jdbc:mysql://localhost:3306/rooms?useSSL=false&serverTimezone=UTC";
```

### 4. Build and Run

```bash
# Clean and compile
mvn clean compile

# Run the application
mvn javafx:run
```

## 👤 Default Users

The system comes with pre-configured users:

| Username | Password | Role  | Email             |
| -------- | -------- | ----- | ----------------- |
| admin    | 123456   | ADMIN | admin@example.com |
| user1    | 123456   | USER  | user1@example.com |
| user2    | 123456   | USER  | user2@example.com |

## 📊 Database Schema

### Rooms Table

```sql
CREATE TABLE rooms (
    id VARCHAR(50) PRIMARY KEY,
    building_block VARCHAR(100) NOT NULL,
    area DOUBLE NOT NULL,
    num_light_bulbs INT NOT NULL,
    start_date_of_operation DATE NOT NULL,
    sufficient_light BOOLEAN NOT NULL,
    is_standard BOOLEAN NOT NULL,
    room_type VARCHAR(50) NOT NULL,
    has_projector BOOLEAN DEFAULT FALSE,
    num_computers INT DEFAULT 0,
    capacity INT DEFAULT 0,
    has_sink BOOLEAN DEFAULT FALSE
);
```

### Users Table

```sql
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    role VARCHAR(20) DEFAULT 'USER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE
);
```

## 🎯 Core Features

### Authentication

- **Login**: Username/password authentication with SHA-256 hashing
- **Register**: New user registration with comprehensive validation
- **Logout**: Secure session termination
- **Password Security**: Encrypted password storage

### Classroom Operations

- **Create**: Add new classrooms with validation
- **Read**: View classroom list with search and filtering
- **Update**: Edit classroom information
- **Delete**: Remove classrooms with confirmation

### Search & Filter

- **Keyword Search**: Search by room ID, building, or room type
- **Building Filter**: Filter by building block
- **Status Filter**: Filter by standard compliance
- **Light Filter**: Filter by lighting conditions

## 🔧 Project Structure

```
phonghoc/
├── src/main/java/vn/giadinh/phonghoc/
│   ├── business/                    # Business Logic Layer
│   │   ├── factory/                # Factory patterns
│   │   └── usecase/                # Use cases
│   ├── dto/                        # Data Transfer Objects
│   ├── entity/                     # Domain entities
│   ├── persistence/                # Data Access Layer
│   │   ├── dao/                    # Data Access Objects
│   │   └── gateway/                # Repository interfaces
│   ├── presentation/               # UI Layer
│   │   ├── controller/             # Controllers
│   │   ├── model/                  # View models
│   │   ├── observer/               # Observer pattern
│   │   └── view/                   # View controllers
│   ├── shared/                     # Shared utilities
│   │   ├── common/                 # Common utilities
│   │   ├── enums/                  # Enumerations
│   │   └── utils/                  # Utility classes
│   └── Main.java                   # Application entry point
├── src/main/resources/
│   └── vn/giadinh/phonghoc/       # FXML files
├── pom.xml                         # Maven configuration
├── room.sql                        # Database schema
├── users.sql                       # User data
└── README.md                       # This file
```

## 🎨 UI Features

### Modern Design

- **Gradient Backgrounds**: Beautiful gradient backgrounds
- **Card Layout**: Information organized in cards
- **Responsive Design**: Adapts to different screen sizes
- **Color-coded Actions**: Different colors for different actions

### User Experience

- **Confirmation Dialogs**: Prevent accidental actions
- **Real-time Feedback**: Immediate status updates
- **Loading States**: Visual feedback during operations
- **Error Handling**: Clear error messages

## 🔒 Security Features

### Authentication Security

- **Password Hashing**: SHA-256 encryption
- **Input Validation**: Comprehensive input sanitization
- **SQL Injection Prevention**: Prepared statements
- **Session Management**: Secure session handling

### Data Protection

- **Parameterized Queries**: Prevent SQL injection
- **Input Sanitization**: Clean user inputs
- **Error Masking**: Hide sensitive information in errors

## 🧪 Testing

### Manual Testing

- **Authentication Flow**: Test login/logout/register
- **CRUD Operations**: Test all classroom operations
- **Search & Filter**: Test search functionality
- **Error Handling**: Test error scenarios

### Validation Testing

- **Input Validation**: Test form validations
- **Database Operations**: Test data persistence
- **UI Updates**: Test real-time updates

## 🚀 Deployment

### Development

```bash
mvn clean javafx:run
```

### Production Build

```bash
mvn clean package
java -jar target/phonghoc-1.0.0.jar
```

## 📝 API Documentation

### Authentication Endpoints

- `POST /auth/login` - User login
- `POST /auth/register` - User registration
- `POST /auth/logout` - User logout

### Classroom Endpoints

- `GET /rooms` - Get all classrooms
- `POST /rooms` - Create new classroom
- `PUT /rooms/{id}` - Update classroom
- `DELETE /rooms/{id}` - Delete classroom
- `GET /rooms/search` - Search classrooms

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🆘 Support

For support and questions:

- Create an issue in the repository
- Check the troubleshooting guide in `AUTH_TROUBLESHOOTING.md`
- Review the changelog in `CHANGELOG.md`

## 🔄 Version History

### v2.0.0 (2024-12-19)

- ✅ Authentication system (Login/Register/Logout)
- ✅ CRUD operations for classrooms
- ✅ Search and filter functionality
- ✅ Modern UI with BootstrapFX
- ✅ Observer pattern implementation
- ✅ Comprehensive error handling

### v1.0.0 (2024-12-18)

- ✅ Basic classroom viewing
- ✅ Add classroom functionality
- ✅ Clean Architecture implementation

## 🙏 Acknowledgments

- **JavaFX Team**: For the excellent UI framework
- **BootstrapFX**: For modern UI styling
- **MySQL**: For reliable database management
- **Clean Architecture**: For architectural guidance

---

**Built with ❤️ using Java, JavaFX, and MySQL**
