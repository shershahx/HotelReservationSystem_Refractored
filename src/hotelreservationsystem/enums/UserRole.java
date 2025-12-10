package hotelreservationsystem.enums;

/**
 * Enumeration for user roles in the system
 * Refactoring Technique: Replace Type Code with Class/Enum
 * 
 * @author Refactored by Software Re-Engineering
 */
public enum UserRole {
    CUSTOMER("Customer"),
    ADMIN("Admin"),
    STAFF("Staff");
    
    private final String displayName;
    
    UserRole(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    @Override
    public String toString() {
        return displayName;
    }
}
