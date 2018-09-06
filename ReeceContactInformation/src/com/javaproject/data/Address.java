package com.javaproject.data;

public class Address {

	private String name;
	//Using String for phone num in order to avoid leading zeroes problem of using long or BigInteger
	private String phoneNumber;
	
	public Address(String addName, String phoneNum) {
		this.name=addName;
		this.phoneNumber=phoneNum;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	@Override
	//overrided equals() and hashCode() to check the object's equality
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof Address)) {
            return false;
        }

        Address contact = (Address) o;

        return contact.name.equals(name) &&
        		contact.phoneNumber.equals(phoneNumber);
    }
	
	 @Override
	    public int hashCode() {
	        int result = 17;
	        result = 31 * result + name.hashCode();
	        result = 7 * result + phoneNumber.hashCode();
	        return result;
	    } 
}
