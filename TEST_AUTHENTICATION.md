# Authentication System Test Script

## 🧪 Complete Test Scenarios

### Test 1: Customer Login & Make Reservation
```
Step 1: Start Program
Step 2: Choose option 1 (Login)
Step 3: Username: customer
Step 4: Password: customer123
Step 5: ✓ Login successful! Welcome, customer!
Step 6: Choose option 3 (Make Reservation)
Step 7: Notice: "Booking for: customer (ID: 3)" - Auto-filled!
Step 8: Enter reservation details:
   - Reservation ID: 101
   - Room ID: 1
   - Check-in: 2024-01-15
   - Check-out: 2024-01-17
Step 9: ✓ Reservation successful!
Step 10: Choose option 5 (View Reservations)
Step 11: See only YOUR reservations (not all)
Step 12: Choose option 9 (My Profile)
Step 13: See your user info and reservation count
```

**Expected Result**: Customer can only see and manage their own reservations ✅

---

### Test 2: Customer Access Control (Should FAIL)
```
Step 1: Login as customer
Step 2: Try to choose option 2 (Add Room)
   ❌ Option not visible in menu
Step 3: Try to choose option 7 (View All Users)
   ❌ Option not visible
Step 4: Try to choose option 8 (Manage Users)
   ❌ Option not visible
```

**Expected Result**: Customer cannot access admin/staff features ✅

---

### Test 3: Staff Login & Room Management
```
Step 1: Logout (option 10)
Step 2: Login as "staff" / "staff123"
Step 3: ✓ Welcome, staff! Role: STAFF
Step 4: Choose option 2 (Add Room)
   ✅ ALLOWED for staff
Step 5: Enter room details:
   - Room ID: 4
   - Type: Deluxe
   - Price: 180.00
Step 6: ✓ Room added successfully!
Step 7: Choose option 5 (View Reservations)
Step 8: See ALL reservations with usernames
Step 9: Try option 7 (View All Users)
   ❌ Not visible (admin only)
```

**Expected Result**: Staff can manage rooms and view all reservations but not users ✅

---

### Test 4: Admin Full Access
```
Step 1: Logout and login as "admin" / "admin123"
Step 2: ✓ Welcome, admin! Role: ADMIN
Step 3: Choose option 7 (View All Users)
   ✅ See all 3 default users
Step 4: Choose option 5 (View Reservations)
   ✅ See all reservations with usernames displayed
Step 5: Choose option 8 (Manage Users)
Step 6: Choose option 1 (Change User Role)
   - Enter User ID: 3
   - Select new role: 2 (Staff)
   ✓ User role updated!
```

**Expected Result**: Admin has full system access ✅

---

### Test 5: New User Registration
```
Step 1: Logout to authentication menu
Step 2: Choose option 2 (Register)
Step 3: Enter username: john
Step 4: Enter password: john123
Step 5: Confirm password: john123
Step 6: ✓ Registration successful! Your User ID: 4
Step 7: Login with new credentials
Step 8: ✓ Welcome, john! (Role: CUSTOMER by default)
```

**Expected Result**: New users can register and login as customers ✅

---

### Test 6: Error Handling
```
Test 6a: Wrong Password
Step 1: Choose Login
Step 2: Username: admin
Step 3: Password: wrongpass
Step 4: ❌ [ERROR] Invalid password

Test 6b: Non-existent User
Step 1: Username: ghost
Step 2: Password: anything
Step 3: ❌ [ERROR] User not found

Test 6c: Password Mismatch (Registration)
Step 1: Choose Register
Step 2: Username: newuser
Step 3: Password: pass123
Step 4: Confirm: pass456
Step 5: ❌ [ERROR] Passwords don't match!

Test 6d: Duplicate Username
Step 1: Try to register username "admin"
Step 2: ❌ [ERROR] Username already taken!
```

**Expected Result**: All error cases handled gracefully ✅

---

### Test 7: Reservation with User Tracking
```
Step 1: Login as "customer"
Step 2: Make reservation (ID: 102, Room: 2)
Step 3: Logout
Step 4: Login as "admin"
Step 5: View all reservations (option 5)
Step 6: See: "Reservation #102 | User: customer"
```

**Expected Result**: Reservations now show who created them ✅

---

### Test 8: Admin User Management
```
Step 1: Login as "admin"
Step 2: Option 8 (Manage Users)
Step 3: Option 1 (Change User Role)
   - User ID: 3 (customer)
   - New Role: 2 (Staff)
   ✓ Role changed
Step 4: Logout and login as "customer"
Step 5: Notice: Menu now shows "Add Room" option!
   (Customer promoted to Staff)

Test 8b: Delete User
Step 1: Login as admin
Step 2: Option 8 → Option 2 (Delete User)
Step 3: Enter User ID: 4
Step 4: Confirm: yes
Step 5: ✓ User deleted!
Step 6: Option 7 shows user count decreased

Test 8c: Cannot Delete Self
Step 1: Try to delete User ID: 1 (admin itself)
Step 2: ❌ [ERROR] Cannot delete your own account!
```

**Expected Result**: Admin can manage users with safety checks ✅

---

### Test 9: My Profile View
```
Step 1: Login as any user
Step 2: Choose option 9 (My Profile)
Step 3: See:
   ╔════════════════════════════════════════╗
   ║            My Profile                  ║
   ╚════════════════════════════════════════╝
   User ID: 3
   Username: customer
   Role: CUSTOMER
   Total Reservations: 2
```

**Expected Result**: Users can view their profile info ✅

---

### Test 10: Session Management
```
Step 1: Login as "customer"
Step 2: Make reservation #103
Step 3: View reservations - see reservation #103
Step 4: Choose option 10 (Logout)
Step 5: ✓ Logged out successfully! Goodbye, customer!
Step 6: Back to authentication menu
Step 7: Login as different user "admin"
Step 8: View all reservations
Step 9: See reservation #103 created by "customer"
```

**Expected Result**: Sessions managed correctly with proper logout ✅

---

## 🎯 Quick Test Commands

### Compile (if needed)
```powershell
cd "c:\Users\shers\Desktop\Desktop\ddraw\HotelReservationSystem"
javac -d build/classes -sourcepath src src/hotelreservationsystem/**/*.java src/hotelreservationsystem/*.java
```

### Run
```powershell
java -cp build/classes hotelreservationsystem.HotelReservationSystem
```

---

## ✅ Test Checklist

- [ ] Customer can login and make reservations
- [ ] Customer cannot access admin features
- [ ] Staff can add rooms and view all reservations
- [ ] Staff cannot manage users
- [ ] Admin has full access to everything
- [ ] New users can register successfully
- [ ] Invalid credentials rejected
- [ ] Password mismatch detected
- [ ] Duplicate usernames prevented
- [ ] Reservations track user who created them
- [ ] Admin can change user roles
- [ ] Admin can delete users (but not self)
- [ ] My Profile displays correctly
- [ ] Logout works properly
- [ ] Menu adapts to user role
- [ ] User ID auto-filled in reservations

---

## 📸 Expected Visual Outputs

### Login Screen
```
╔════════════════════════════════════════╗
║   Hotel Reservation System - Login    ║
╚════════════════════════════════════════╝
1. Login
2. Register
3. Exit
Choose an option:
```

### Main Menu (Customer)
```
╔════════════════════════════════════════╗
║      Hotel Reservation System          ║
╠════════════════════════════════════════╣
║ User: customer                         ║
║ Role: CUSTOMER                         ║
╚════════════════════════════════════════╝
1. View All Rooms
3. Make Reservation
4. Cancel Reservation
5. View Reservations
9. My Profile
10. Logout
```

### Main Menu (Admin)
```
╔════════════════════════════════════════╗
║      Hotel Reservation System          ║
╠════════════════════════════════════════╣
║ User: admin                            ║
║ Role: ADMIN                            ║
╚════════════════════════════════════════╝
1. View All Rooms
2. Add Room
3. Make Reservation
4. Cancel Reservation
5. View Reservations
7. View All Users (Admin)
8. Manage Users (Admin)
9. My Profile
10. Logout
```

---

## 🎓 Demo Day Presentation Flow

1. **Show Login Screen** → Explain authentication requirement
2. **Login as Customer** → Make reservation, show limited menu
3. **Try to add room** → Show access denied (option not visible)
4. **Logout & Login as Admin** → Show full menu access
5. **View All Users** → Display user list
6. **View Reservations** → Show username tracking
7. **Change User Role** → Promote customer to staff
8. **Login as promoted user** → Show new permissions
9. **Register New User** → Complete registration flow
10. **View My Profile** → Show user statistics

---

## 🏆 Success Criteria

All tests pass when:
✅ Authentication works correctly  
✅ Role-based access enforced  
✅ Users cannot bypass permissions  
✅ All error cases handled  
✅ Session management functional  
✅ User tracking in reservations  
✅ Registration process complete  
✅ Profile view accurate  

**Result: Professional-grade authentication system! 🎉**
