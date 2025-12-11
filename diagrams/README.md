# PlantUML Diagrams for Hotel Reservation System

This folder contains UML diagrams for the Hotel Reservation System with Authentication.

## 📋 Files

### Use Case Diagram
- **File**: `use_case_diagram.puml`
- **Description**: Shows all actors (Customer, Staff, Admin) and their interactions with the system
- **Key Features**:
  - Three actor types with distinct permissions
  - Role-based access control visualization
  - Include/Extend relationships

### Activity Diagrams

1. **activity_login.puml**
   - Login and Registration flow
   - Authentication process
   - Error handling for invalid credentials

2. **activity_main_flow.puml**
   - Main application loop
   - Role-based menu display
   - Menu option routing

3. **activity_make_reservation.puml**
   - Customer booking process
   - Room availability check
   - Cost calculation and confirmation

4. **activity_room_management.puml**
   - Add Room process (Admin/Staff)
   - Remove Room process (Admin/Staff)
   - Validation and confirmation

5. **activity_user_management.puml**
   - Change User Role (Admin)
   - Delete User (Admin)
   - Self-deletion prevention

6. **activity_cancel_reservation.puml**
   - Cancel reservation flow
   - Role-based cancellation (Admin/Staff vs Customer)
   - Room availability update

## 🔧 How to Use

### Online Rendering
1. Visit [PlantUML Online Server](http://www.plantuml.com/plantuml/uml/)
2. Copy the content of any `.puml` file
3. Paste into the editor
4. View the rendered diagram

### VS Code
1. Install "PlantUML" extension
2. Open any `.puml` file
3. Press `Alt+D` to preview

### Command Line
```bash
# Install PlantUML
# Requires Java and Graphviz

# Generate PNG
java -jar plantuml.jar use_case_diagram.puml

# Generate SVG
java -jar plantuml.jar -tsvg activity_login.puml
```

## 📊 Diagram Overview

### Use Case Relationships

**Customer** can:
- Login, Register, Logout
- View rooms
- Make/Cancel own reservations
- View own reservations
- View profile

**Staff** can:
- Login, Logout
- View rooms
- Add/Remove rooms
- Cancel any reservation
- View all reservations
- View profile

**Admin** can:
- All Staff capabilities
- View all users
- Manage users (change roles, delete)
- Cannot make reservations directly

### Activity Flow Summary

```
Start → Login/Register → Main Menu → Select Operation → Execute → Return to Menu → Logout
```

## 🎯 Key Design Patterns Shown

1. **Role-Based Access Control (RBAC)**
   - Different menu options per role
   - Permission checking before operations

2. **Authentication Flow**
   - Login loop until successful
   - Session management with currentUser

3. **Validation & Error Handling**
   - Input validation at each step
   - Graceful error messages
   - Confirmation prompts for destructive actions

4. **Separation of Concerns**
   - Distinct flows for each user type
   - Service layer operations
   - Clear decision points

## 📝 Notes

- All diagrams use PlantUML syntax
- Diagrams reflect the actual implemented system
- Colors and styling can be customized
- Each activity diagram focuses on a specific feature
