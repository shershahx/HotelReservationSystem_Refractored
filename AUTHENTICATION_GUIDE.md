# User Authentication System Guide

## Overview
The Hotel Reservation System now includes a complete **User Authentication System** with role-based access control. Users must log in to access the system.

---

## 🔐 Default Users

Three demo accounts are pre-configured:

| Username   | Password      | Role      | Capabilities |
|-----------|---------------|-----------|--------------|
| `admin`   | `admin123`    | ADMIN     | Full system access, user management, room management |
| `staff`   | `staff123`    | STAFF     | Room management, view all reservations |
| `customer`| `customer123` | CUSTOMER  | Make/cancel reservations, view own reservations |

---

## 🚀 Features

### 1. **Login & Registration**
- **Login**: Authenticate with username/password
- **Register**: Create new customer accounts
- **Session Management**: Track logged-in user throughout session

### 2. **Role-Based Access Control**

#### ADMIN Role
- ✅ Add/view rooms
- ✅ Make/cancel/view all reservations
- ✅ View all users
- ✅ Manage users (change roles, delete users)
- ✅ View system statistics

#### STAFF Role
- ✅ Add/view rooms
- ✅ Make/cancel/view all reservations
- ❌ Cannot manage users

#### CUSTOMER Role
- ✅ View available rooms
- ✅ Make reservations (automatically uses logged-in user)
- ✅ Cancel own reservations
- ✅ View own reservations only
- ❌ Cannot add rooms
- ❌ Cannot view other users' reservations

### 3. **User Management** (Admin Only)
- Change user roles
- Delete users
- View all registered users
- User statistics

### 4. **Enhanced Security**
- Password verification
- Encrypted user sessions
- Protected admin operations
- Cannot delete own account

---

## 📋 Usage Examples

### Example 1: Customer Making a Reservation
```
1. Login as "customer" / "customer123"
2. Choose option 3: Make Reservation
3. System automatically uses your User ID
4. Enter reservation details
5. Confirm booking
```

### Example 2: Admin Managing Users
```
1. Login as "admin" / "admin123"
2. Choose option 7: View All Users
3. Choose option 8: Manage Users
4. Change roles or delete users
```

### Example 3: Staff Adding Rooms
```
1. Login as "staff" / "staff123"
2. Choose option 2: Add Room
3. Enter room details
4. Room added to system
```

### Example 4: Register New Account
```
1. Choose option 2 on login screen
2. Enter desired username
3. Enter password (twice for confirmation)
4. Account created as CUSTOMER role
5. Login with new credentials
```

---

## 🔄 Menu Flow

### Initial Authentication Menu
```
╔════════════════════════════════════════╗
║   Hotel Reservation System - Login    ║
╚════════════════════════════════════════╝
1. Login
2. Register
3. Exit
```

### Main Menu (After Login)
```
╔════════════════════════════════════════╗
║      Hotel Reservation System          ║
╠════════════════════════════════════════╣
║ User: [username]                       ║
║ Role: [CUSTOMER/STAFF/ADMIN]           ║
╚════════════════════════════════════════╝
1. View All Rooms
2. Add Room (Admin/Staff only)
3. Make Reservation
4. Cancel Reservation
5. View Reservations
7. View All Users (Admin only)
8. Manage Users (Admin only)
9. My Profile
10. Logout
```

---

## 🛠️ Technical Implementation

### New Classes Added

#### **UserService.java**
- `registerUser()` - Create new accounts
- `authenticate()` - Verify credentials
- `getUserById()` - Retrieve user details
- `getAllUsers()` - List all users (admin)
- `updateUserRole()` - Change permissions (admin)
- `deleteUser()` - Remove accounts (admin)

#### **UserException.java**
- Custom exception for authentication errors
- User management errors

### Modified Classes

#### **HotelReservationSystem.java**
- Added authentication loop
- Role-based menu display
- Session management with `currentUser`
- Login/logout functionality
- User profile view

#### **User.java**
- Now fully integrated and interactive
- Used for all reservations
- Password verification active

---

## 🔑 Key Improvements

### Before Authentication
❌ Anonymous user IDs  
❌ No access control  
❌ User class was dead code  
❌ Anyone could perform admin tasks  

### After Authentication
✅ Secure login system  
✅ Role-based permissions  
✅ User class fully functional  
✅ Protected admin operations  
✅ Audit trail (who made which reservation)  

---

## 📊 Code Quality Enhancements

### Refactoring Techniques Applied
1. **Extract Method** - Authentication logic separated
2. **Introduce Parameter Object** - User session management
3. **Replace Conditional with Polymorphism** - Role-based behavior
4. **Encapsulate Field** - Private user management
5. **Custom Exceptions** - UserException for authentication errors

### Design Patterns
- **Service Layer Pattern** - UserService manages users
- **Session Management Pattern** - currentUser tracks state
- **Role-Based Access Control (RBAC)** - Permission system

---

## 🎯 Demo Scenarios

### Scenario 1: Customer Journey
1. Register new account
2. Login successfully
3. View available rooms
4. Make reservation (auto-fills user info)
5. View "My Reservations"
6. Logout

### Scenario 2: Admin Operations
1. Login as admin
2. View all users in system
3. View all reservations with usernames
4. Change customer to staff role
5. Delete inactive user
6. View system statistics

### Scenario 3: Access Control Test
1. Login as customer
2. Try to add room (DENIED)
3. Try to view all users (option not visible)
4. Login as admin
5. Same operations now ALLOWED

---

## 📈 Statistics & Reporting

The system now tracks:
- Total registered users
- Reservations per user
- Role distribution
- User activity (visible in reservations)

---

## 🔒 Security Features

1. **Password Verification** - Authenticated login
2. **Session Management** - Track current user
3. **Role Enforcement** - Menu options by role
4. **Protected Operations** - Admin-only functions
5. **Self-Protection** - Cannot delete own admin account

---

## 💡 Best Practices Demonstrated

✅ Separation of concerns (UserService)  
✅ Input validation (all user inputs)  
✅ Error handling (UserException)  
✅ Defensive programming (null checks)  
✅ Clean code (short methods, clear names)  
✅ Documentation (comprehensive comments)  

---

## 🎓 Educational Value

This implementation demonstrates:
- **Authentication Systems** - Real-world login flow
- **Authorization** - Role-based access control
- **Session Management** - Stateful applications
- **Service Layer Architecture** - Business logic separation
- **Exception Handling** - Domain-specific errors
- **Code Refactoring** - Dead code brought to life

---

## 🚨 Error Messages

Common authentication errors:
- `Invalid credentials` - Wrong username/password
- `User not found` - Username doesn't exist
- `Username already exists` - Registration conflict
- `Passwords don't match` - Confirmation mismatch
- `Access denied! Admin/Staff only` - Permission violation

---

## 🎉 Conclusion

The User Authentication System transforms the Hotel Reservation System from a simple demo into a **professional-grade application** with proper security, user management, and access control. This enhancement showcases advanced software engineering practices including:

- **Security by design**
- **Role-based architecture**
- **Service layer patterns**
- **Session management**
- **User experience optimization**

Perfect for demonstrating in your Software Re-Engineering course! 🎓✨
