package com.javaproject.test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.javaproject.bean.AddressBookApp;
import com.javaproject.data.Address; 

/**
 * @author Mergin
 *
 */
public class AddressBookAppTest {
	AddressBookApp addressBook = new AddressBookApp();
	Object[] expectedAdds = new Object[3];
	LinkedHashMap<String, List<Address>> addressBooks = new LinkedHashMap<String, List<Address>>();
	List<Address> list = new ArrayList<Address>();
    @Before
    public void initInputs(){
		
		list.add(new Address("Nats", "0543678934"));
		list.add(new Address("Mali", "8754246807"));
		list.add(new Address("Cels", "9532567889"));
		addressBooks.put("Book1", list);
		addressBooks.put("Book2", list);
    } 
    @Test
	public void testJUnitGetAndValidateContactNumber(){ 
    	String addPhoneNum = "9537836793";
		assertEquals(addPhoneNum, addressBook.getAndValidateContactNumber());
	} 
    @Test
   	public void testJUnitGetAndValidateContactName(){ 
       	String contactName = "Shelley";
   		assertEquals(contactName, addressBook.getAndValidateContactName());
   	}
    @Test
   	public void testJUnitGetAndValidateAddressBookName(){ 
       	String addressBookName = "Book1";
   		assertEquals(addressBookName, addressBook.getAndValidateAddressBookName());
   	}
    
    @Test
	public void testAddContactsInAddressBook() {
		addressBooks.put("Book3", list);
		assertEquals("Adding 1 more address info to map", 3, addressBooks.size());
		} 
    
    @Test
	public void testingHelloWorld() {
    	AddressBookApp add = new AddressBookApp();
		assertEquals("Here is test for Address book: ", "Book1", add.getAndValidateAddressBookName());
	} 
    
}


 
