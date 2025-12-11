# Default Login Credentials

## System comes with 3 pre-configured accounts:

### 👑 Administrator Account
- **Username:** `admin`
- **Password:** `admin123`
- **Role:** ADMIN
- **Permissions:** 
  - All customer features
  - Add new rooms
  - View all users
  - Manage users (update/delete)

### 👔 Staff Account
- **Username:** `staff`
- **Password:** `staff123`
- **Role:** STAFF
- **Permissions:**
  - View all rooms
  - View all reservations
  - Make reservations
  - Cancel reservations

### 👤 Customer Account
- **Username:** `customer`
- **Password:** `customer123`
- **Role:** CUSTOMER
- **Permissions:**
  - View available rooms
  - Make reservations
  - View own reservations
  - Cancel own reservations

---

## How to Use

1. **Start the program**
2. **Choose Option 1 (Login)**
3. **Enter username and password** from the accounts above
4. **Try different roles** to see role-based access control

## Registration

You can also **register new accounts**:
- Choose Option 2 (Register) from login menu
- New accounts default to CUSTOMER role
- Admin can change roles later via "Manage Users" menu

---

## Security Notes

⚠️ **These are demo credentials** - In production:
- Change default passwords immediately
- Use stronger password requirements
- Implement password hashing
- Add password reset functionality
