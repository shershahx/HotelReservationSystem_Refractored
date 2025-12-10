# Hotel Reservation System - Class Diagrams

## Legacy System Class Diagram

```
┌─────────────────────────────────────────────────────────────────┐
│                  HotelReservationSystem (Main)                  │
├─────────────────────────────────────────────────────────────────┤
│ - roomService: RoomService                                      │
│ - reservationService: ReservationService                        │
├─────────────────────────────────────────────────────────────────┤
│ + main(args: String[]): void                                    │
│ - initializeSystem(): void                                      │
│ - viewAllRooms(): void                                          │
│ - addRoom(scanner: Scanner): void                               │
│ - makeReservation(scanner: Scanner): void                       │
│ - cancelReservation(scanner: Scanner): void                     │
│ - viewAllReservations(): void                                   │
└─────────────────────────────────────────────────────────────────┘
                     │
                     │ uses
         ┌───────────┴───────────┐
         │                       │
         ▼                       ▼
┌──────────────────┐    ┌──────────────────────┐
│   RoomService    │    │ ReservationService   │
├──────────────────┤    ├──────────────────────┤
│ - rooms: List    │    │ - reservations: List │
├──────────────────┤    ├──────────────────────┤
│ + addRoom()      │    │ + makeReservation()  │
│ + updateRoom()   │    │ + cancelReservation()│
│ + getRoomById()  │    │ + getReservationById()│
│ + getAllRooms()  │    │ + getAllReservations()│
│ + removeRoom()   │    │ + updateReservation()│
└──────────────────┘    └──────────────────────┘
         │                       │
         │ manages               │ manages
         ▼                       ▼
┌──────────────────┐    ┌──────────────────────┐
│      Room        │    │    Reservation       │
├──────────────────┤    ├──────────────────────┤
│ - roomId: int    │    │ - reservationId: int │
│ - roomType: str  │    │ - userId: int        │
│ - price: double  │    │ - roomId: int        │
│ - isAvailable    │    │ - checkInDate: Date  │
├──────────────────┤    │ - checkOutDate: Date │
│ + getters()      │    │ - totalCost: double  │
│ + setters()      │    ├──────────────────────┤
│ + toString()     │    │ + getters()          │
└──────────────────┘    │ + setters()          │
                        │ + toString()         │
                        └──────────────────────┘

┌──────────────────┐
│      User        │  (Unused in system)
├──────────────────┤
│ - userId: int    │
│ - username: str  │
│ - password: str  │
│ - role: String   │
├──────────────────┤
│ + getters()      │
│ + setters()      │
│ + toString()     │
└──────────────────┘
```

**Legacy System Problems:**
- No separation of concerns
- Data classes with no behavior
- Magic numbers in code
- Generic exception handling
- Exposed collections
- No validation

---

## Refactored System Class Diagram

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                     HotelReservationSystem (Main)                           │
│                         <<Presentation Layer>>                              │
├─────────────────────────────────────────────────────────────────────────────┤
│ - roomService: RoomService                                                  │
│ - reservationService: ReservationService                                    │
│ - scanner: Scanner                                                          │
├─────────────────────────────────────────────────────────────────────────────┤
│ + main(args: String[]): void                                                │
│ - initializeSystem(): void                                                  │
│ - runMainLoop(): void                                                       │
│ - displayMenu(): void                                                       │
│ - readMenuOption(): int                                                     │
│ - processMenuOption(option: int): boolean                                   │
│ - viewAllRooms(): void                                                      │
│ - addRoom(): void                                                           │
│ - makeReservation(): void                                                   │
│ - cancelReservation(): void                                                 │
│ - viewAllReservations(): void                                               │
│ - readRoomId(): int                                                         │
│ - readReservationId(): int                                                  │
│ - readUserId(): int                                                         │
│ - readCheckInDate(): Date                                                   │
│ - readCheckOutDate(): Date                                                  │
│ - verifyRoomAvailability(roomId: int): void                                 │
│ - validateDateRange(checkIn: Date, checkOut: Date): void                    │
│ - calculateReservationCost(roomId: int, checkIn: Date, checkOut: Date)     │
│ - displayReservationSuccess(reservation: Reservation): void                 │
│ - displayErrorMessage(e: Exception): void                                   │
│ - cleanup(): void                                                           │
└─────────────────────────────────────────────────────────────────────────────┘
                              │
                              │ uses
                  ┌───────────┴───────────┐
                  │                       │
                  ▼                       ▼
┌─────────────────────────────────┐  ┌─────────────────────────────────┐
│         RoomService             │  │     ReservationService          │
│      <<Service Layer>>          │  │      <<Service Layer>>          │
├─────────────────────────────────┤  ├─────────────────────────────────┤
│ - rooms: List<Room>             │  │ - reservations: List<Res>       │
├─────────────────────────────────┤  ├─────────────────────────────────┤
│ + addRoom(room): void           │  │ + makeReservation(res): void    │
│ + updateRoom(room): void        │  │ + cancelReservation(id): void   │
│ + getRoomById(id): Room         │  │ + getReservationById(id): Res   │
│ + getAllRooms(): List<Room>     │  │ + getAllReservations(): List    │
│ + getAvailableRooms(): List     │  │ + getReservationsByUserId(id)   │
│ + isRoomAvailable(id): boolean  │  │ + getReservationsByRoomId(id)   │
│ + markRoomAsOccupied(id): void  │  │ + updateReservation(res): void  │
│ + markRoomAsAvailable(id): void │  │ + getReservationCount(): int    │
│ + removeRoom(id): void          │  │ + calculateTotalRevenue(): dbl  │
│ + getAvailableRoomCount(): long │  │ + reservationExists(id): bool   │
│ + findRoomsByType(type): List   │  │ - findReservationById(id): Opt  │
│ - findRoomById(id): Optional    │  └─────────────────────────────────┘
└─────────────────────────────────┘                │
              │                                    │
              │ manages                            │ manages
              ▼                                    ▼
┌─────────────────────────────────┐  ┌─────────────────────────────────┐
│            Room                 │  │         Reservation             │
│      <<Domain Model>>           │  │      <<Domain Model>>           │
├─────────────────────────────────┤  ├─────────────────────────────────┤
│ - roomId: int                   │  │ - reservationId: int            │
│ - roomType: String              │  │ - userId: int                   │
│ - price: double                 │  │ - roomId: int                   │
│ - isAvailable: boolean          │  │ - checkInDate: Date             │
├─────────────────────────────────┤  │ - checkOutDate: Date            │
│ + Room(id, type, price, avail)  │  │ - totalCost: double             │
│ + getRoomId(): int              │  ├─────────────────────────────────┤
│ + getRoomType(): String         │  │ + Reservation(id, userId...)    │
│ + setRoomType(type): void       │  │ + getReservationId(): int       │
│ + getPrice(): double            │  │ + getUserId(): int              │
│ + setPrice(price): void         │  │ + setUserId(id): void           │
│ + isAvailable(): boolean        │  │ + getRoomId(): int              │
│ + setAvailable(available): void │  │ + setRoomId(id): void           │
│ + markAsOccupied(): void        │  │ + getCheckInDate(): Date        │
│ + markAsAvailable(): void       │  │ + setCheckInDate(date): void    │
│ + calculateCost(nights): double │  │ + getCheckOutDate(): Date       │
│ + toString(): String            │  │ + setCheckOutDate(date): void   │
└─────────────────────────────────┘  │ + getTotalCost(): double        │
                                     │ + setTotalCost(cost): void      │
┌─────────────────────────────────┐  │ + getNumberOfNights(): long     │
│            User                 │  │ + recalculateCost(price): void  │
│      <<Domain Model>>           │  │ + toString(): String            │
├─────────────────────────────────┤  └─────────────────────────────────┘
│ - userId: int                   │
│ - username: String              │
│ - password: String              │
│ - role: UserRole                │◄──┐
├─────────────────────────────────┤   │
│ + User(id, name, pass, role)    │   │
│ + getUserId(): int              │   │
│ + getUsername(): String         │   │
│ + setUsername(name): void       │   │
│ + verifyPassword(pass): boolean │   │
│ + changePassword(old, new)      │   │
│ + getRole(): UserRole           │   │
│ + setRole(role): void           │   │
│ + toString(): String            │   │
└─────────────────────────────────┘   │
                                      │
┌─────────────────────────────────┐   │
│       <<enumeration>>           │   │
│          UserRole               │───┘
├─────────────────────────────────┤
│ CUSTOMER                        │
│ ADMIN                           │
│ STAFF                           │
├─────────────────────────────────┤
│ - displayName: String           │
│ + getDisplayName(): String      │
│ + toString(): String            │
└─────────────────────────────────┘

┌─────────────────────────────────┐  ┌─────────────────────────────────┐
│       <<exception>>             │  │       <<exception>>             │
│       RoomException             │  │    ReservationException         │
├─────────────────────────────────┤  ├─────────────────────────────────┤
│ + RoomException(msg: String)    │  │ + ReservationException(msg)     │
│ + RoomException(msg, cause)     │  │ + ReservationException(msg,cause)│
└─────────────────────────────────┘  └─────────────────────────────────┘
          ▲                                      ▲
          │                                      │
          │ throws                               │ throws
          │                                      │
   RoomService                           ReservationService

┌─────────────────────────────────────────────────────────────────┐
│                          <<utility>>                            │
│                         AppConstants                            │
├─────────────────────────────────────────────────────────────────┤
│ + MILLISECONDS_PER_DAY: long                                    │
│ + DEFAULT_SINGLE_ROOM_ID: int                                   │
│ + DEFAULT_DOUBLE_ROOM_ID: int                                   │
│ + DEFAULT_SUITE_ROOM_ID: int                                    │
│ + DEFAULT_SINGLE_ROOM_PRICE: double                             │
│ + DEFAULT_DOUBLE_ROOM_PRICE: double                             │
│ + DEFAULT_SUITE_ROOM_PRICE: double                              │
│ + ROOM_TYPE_SINGLE: String                                      │
│ + ROOM_TYPE_DOUBLE: String                                      │
│ + ROOM_TYPE_SUITE: String                                       │
│ + MENU_VIEW_ROOMS: int                                          │
│ + MENU_ADD_ROOM: int                                            │
│ + MENU_MAKE_RESERVATION: int                                    │
│ + MENU_CANCEL_RESERVATION: int                                  │
│ + MENU_VIEW_RESERVATIONS: int                                   │
│ + MENU_EXIT: int                                                │
│ + ERROR_ROOM_NOT_FOUND: String                                  │
│ + ERROR_RESERVATION_NOT_FOUND: String                           │
│ + ERROR_ROOM_NOT_AVAILABLE: String                              │
│ + ERROR_INVALID_DATE_RANGE: String                              │
│ + ERROR_INVALID_PRICE: String                                   │
│ + ERROR_INVALID_ROOM_ID: String                                 │
│ + ERROR_INVALID_RESERVATION_ID: String                          │
│ + SUCCESS_ROOM_ADDED: String                                    │
│ + SUCCESS_RESERVATION_MADE: String                              │
│ + SUCCESS_RESERVATION_CANCELED: String                          │
└─────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────┐
│          <<utility>>                │
│           DateUtils                 │
├─────────────────────────────────────┤
│ + calculateDaysBetween(date1, date2)│
│     : long                          │
│ + isValidDateRange(date1, date2)    │
│     : boolean                       │
└─────────────────────────────────────┘

┌─────────────────────────────────────┐
│          <<utility>>                │
│        InputValidator               │
├─────────────────────────────────────┤
│ + isValidId(id: int): boolean       │
│ + isValidPrice(price: double): bool │
│ + isValidString(str: String): bool  │
└─────────────────────────────────────┘
```

---

## Package Diagram

```
┌────────────────────────────────────────────────────────────────┐
│                  hotelreservationsystem                        │
├────────────────────────────────────────────────────────────────┤
│  HotelReservationSystem.java                                   │
│  Room.java                                                     │
│  Reservation.java                                              │
│  User.java                                                     │
│  RoomService.java                                              │
│  ReservationService.java                                       │
└────────────────────────────────────────────────────────────────┘
                          │
                          │ contains
          ┌───────────────┼───────────────┬──────────────┐
          │               │               │              │
          ▼               ▼               ▼              ▼
┌──────────────┐  ┌─────────────┐  ┌──────────────┐  ┌─────────┐
│  constants   │  │    enums    │  │  exceptions  │  │  utils  │
├──────────────┤  ├─────────────┤  ├──────────────┤  ├─────────┤
│ AppConstants │  │  UserRole   │  │ RoomException│  │DateUtils│
└──────────────┘  └─────────────┘  │ Reservation  │  │ Input   │
                                   │   Exception  │  │Validator│
                                   └──────────────┘  └─────────┘
```

---

## Layered Architecture Diagram

```
┌─────────────────────────────────────────────────────────────────┐
│                    PRESENTATION LAYER                           │
│                                                                 │
│  ┌───────────────────────────────────────────────────────┐     │
│  │        HotelReservationSystem (Main)                  │     │
│  │  - Console-based UI                                   │     │
│  │  - Menu system                                        │     │
│  │  - User input/output                                  │     │
│  └───────────────────────────────────────────────────────┘     │
└─────────────────────────────────────────────────────────────────┘
                           ▼ uses
┌─────────────────────────────────────────────────────────────────┐
│                     SERVICE LAYER                               │
│                                                                 │
│  ┌─────────────────────┐      ┌──────────────────────────┐     │
│  │   RoomService       │      │  ReservationService      │     │
│  │  - Business logic   │      │  - Business logic        │     │
│  │  - Validation       │      │  - Validation            │     │
│  │  - Orchestration    │      │  - Orchestration         │     │
│  └─────────────────────┘      └──────────────────────────┘     │
└─────────────────────────────────────────────────────────────────┘
                           ▼ manages
┌─────────────────────────────────────────────────────────────────┐
│                    DOMAIN MODEL LAYER                           │
│                                                                 │
│  ┌──────────┐      ┌──────────────┐      ┌──────────────┐     │
│  │   Room   │      │ Reservation  │      │     User     │     │
│  │ - Data   │      │ - Data       │      │ - Data       │     │
│  │ - Logic  │      │ - Logic      │      │ - Logic      │     │
│  └──────────┘      └──────────────┘      └──────────────┘     │
└─────────────────────────────────────────────────────────────────┘
                           ▼ uses
┌─────────────────────────────────────────────────────────────────┐
│                  UTILITY/SUPPORT LAYER                          │
│                                                                 │
│  ┌──────────────┐  ┌──────────┐  ┌────────────┐  ┌─────────┐  │
│  │ AppConstants │  │ UserRole │  │   Room     │  │ DateUtils│ │
│  │              │  │  (Enum)  │  │ Exception  │  │          │ │
│  └──────────────┘  └──────────┘  └────────────┘  └─────────┘  │
│                                                                 │
│  ┌────────────┐  ┌──────────────────┐                          │
│  │ Reservation│  │  InputValidator  │                          │
│  │ Exception  │  │                  │                          │
│  └────────────┘  └──────────────────┘                          │
└─────────────────────────────────────────────────────────────────┘
```

---

## Sequence Diagram: Make Reservation Flow

### Legacy System
```
User -> Main: Enter reservation details
Main -> Main: Read all inputs (25+ lines)
Main -> RoomService: getRoomById(roomId)
RoomService -> Main: return Room
Main -> Main: Calculate cost with magic numbers
Main -> Reservation: new Reservation(...)
Main -> ReservationService: makeReservation(reservation)
Main -> RoomService: setRoomAvailability(roomId, false)
Main -> User: Display "Success"
```

### Refactored System
```
User -> Main: Choose "Make Reservation"
Main -> Main: readReservationId()
Main -> Main: readUserId()
Main -> Main: readRoomId()
Main -> RoomService: isRoomAvailable(roomId)
RoomService -> Main: return true/false
Main -> Main: verifyRoomAvailability()
Main -> Main: readCheckInDate()
Main -> Main: readCheckOutDate()
Main -> DateUtils: isValidDateRange(dates)
DateUtils -> Main: return true/false
Main -> Main: validateDateRange()
Main -> RoomService: getRoomById(roomId)
RoomService -> Main: return Room
Main -> DateUtils: calculateDaysBetween(dates)
DateUtils -> Main: return numberOfNights
Main -> Room: calculateCost(numberOfNights)
Room -> Main: return totalCost
Main -> Reservation: new Reservation(...) [with validation]
Reservation -> InputValidator: validate all fields
Main -> ReservationService: makeReservation(reservation)
Main -> RoomService: markRoomAsOccupied(roomId)
Main -> Main: displayReservationSuccess()
Main -> User: Display formatted success message
```

**Improvements:**
- Clear separation of concerns
- Each step is a focused method
- Validation at every level
- Reusable utility methods
- Better error handling

---

## Refactoring Impact Diagram

```
BEFORE:                          AFTER:
┌────────────────┐              ┌────────────────┐
│  Monolithic    │              │   Layered      │
│  Structure     │   ──────►    │  Architecture  │
│  (6 classes)   │              │  (12 classes)  │
└────────────────┘              └────────────────┘

┌────────────────┐              ┌────────────────┐
│  Long Methods  │              │Short, Focused  │
│  (20+ lines)   │   ──────►    │    Methods     │
│                │              │  (3-7 lines)   │
└────────────────┘              └────────────────┘

┌────────────────┐              ┌────────────────┐
│ Magic Numbers  │              │Named Constants │
│  Everywhere    │   ──────►    │  (AppConstants)│
│                │              │                │
└────────────────┘              └────────────────┘

┌────────────────┐              ┌────────────────┐
│   Generic      │              │   Custom       │
│  Exceptions    │   ──────►    │  Exceptions    │
│  (Exception)   │              │  (Specific)    │
└────────────────┘              └────────────────┘

┌────────────────┐              ┌────────────────┐
│  Data Classes  │              │  Rich Domain   │
│  (No behavior) │   ──────►    │    Models      │
│                │              │  (With logic)  │
└────────────────┘              └────────────────┘

┌────────────────┐              ┌────────────────┐
│No Validation   │              │ Comprehensive  │
│                │   ──────►    │  Validation    │
│                │              │  Everywhere    │
└────────────────┘              └────────────────┘
```

---

## Class Responsibility Collaboration (CRC) Cards

### HotelReservationSystem
**Responsibilities:**
- Display user interface
- Handle user input
- Coordinate services
- Display results

**Collaborators:**
- RoomService
- ReservationService
- DateUtils
- InputValidator
- AppConstants

---

### RoomService
**Responsibilities:**
- Manage room inventory
- Validate room operations
- Track room availability
- Query room information

**Collaborators:**
- Room
- RoomException
- InputValidator
- AppConstants

---

### ReservationService
**Responsibilities:**
- Manage reservations
- Validate reservations
- Calculate revenue
- Query reservation info

**Collaborators:**
- Reservation
- ReservationException
- InputValidator
- AppConstants

---

### Room (Domain Model)
**Responsibilities:**
- Store room data
- Validate room data
- Calculate costs
- Manage availability state

**Collaborators:**
- InputValidator

---

### Reservation (Domain Model)
**Responsibilities:**
- Store reservation data
- Validate reservation data
- Calculate nights
- Recalculate costs

**Collaborators:**
- DateUtils
- InputValidator
- AppConstants

---

### User (Domain Model)
**Responsibilities:**
- Store user data
- Validate user data
- Manage password
- Verify credentials

**Collaborators:**
- UserRole (enum)
- InputValidator

---

## Design Patterns Applied

### 1. Utility Class Pattern
```
┌─────────────┐
│  <<utility>>│
│  DateUtils  │
│             │
│ + static    │
│   methods   │
│             │
│ - private   │
│   constructor│
└─────────────┘
```
**Used in:** DateUtils, InputValidator, AppConstants

---

### 2. Null Object Pattern (Optional)
```
┌──────────────────┐
│  RoomService     │
├──────────────────┤
│ - findRoomById() │
│   returns        │
│   Optional<Room> │
└──────────────────┘
        │
        ▼
┌──────────────────┐
│  Optional<Room>  │
│  - Present       │
│  - Empty         │
└──────────────────┘
```

---

### 3. Immutable Object Pattern
```
┌──────────────────┐
│      Room        │
├──────────────────┤
│ - roomId: final  │  ← Cannot change
│                  │
│ + getRoomId()    │  ← No setter
└──────────────────┘
```

---

## Summary

The refactored system demonstrates:

✓ **Better Structure** - Layered architecture  
✓ **Clear Responsibilities** - Each class has one job  
✓ **Improved Encapsulation** - Protected data  
✓ **Rich Domain Models** - Behavior with data  
✓ **Type Safety** - Enums instead of strings  
✓ **Better Error Handling** - Custom exceptions  
✓ **Reusable Utilities** - Shared functionality  
✓ **Comprehensive Validation** - At all levels  
✓ **Maintainable Code** - Short, focused methods  
✓ **Professional Quality** - Production-ready  

---

**Document Version:** 1.0  
**Last Updated:** December 10, 2025
