package com.linusba.support.contact.ui;

/**
 * Response Object for Contact handling
 */
public class ContactResponse {
    public ContactResponse(String name, String phoneNumber){
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    private final String name, phoneNumber;

    /**
     * Gets the contacts name
     * @return Name as String
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the Contacts Phone Number
     * @return phoneNumer as String
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }
}
