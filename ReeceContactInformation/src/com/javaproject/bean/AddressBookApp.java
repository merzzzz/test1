package com.javaproject.bean;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import com.javaproject.data.Address;

/**
 * Created 05th September 2018
 * 
 * @author Mergin
 *
 */
public class AddressBookApp {
	LinkedHashMap<String, List<Address>> addressBooks = new LinkedHashMap<String, List<Address>>();

	public AddressBookApp() {
		
		// TODO Auto-generated constructor stub
	}

	/**
	 * This method performs add, remove, print functionalities based on user choice getting users
	 * input via scanner and executing API's via switch statement
	 * 
	 * @return void
	 */
	public static void main(String[] args) {
		AddressBookApp app = new AddressBookApp();
		boolean exit = false;
		int count = 0;
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("~~~~~~~   Welcome to Address Book Application   ~~~~~~~");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		while (!exit) {
			try {
				System.out.println(count == 0 ? "\n 1.Add Address Book or Contact \n 2.Delete Contact \n 3.View Contact(s) \n 4.Exit \n\n Choose an option (1, 2, 3 or 4) and press Enter:"
						: "\n\nIf you wish to continue, choose an option (1, 2 or 3) or select 4 to Exit... \n 1.Add Address Book or Contact \n 2.Delete Contact \n 3.View Contact(s) \n 4.Exit \n\n Enter your selection here and press Enter: ");
				count++;
				int selectedOption;
				selectedOption = getInputIntFromUser();
				switch (selectedOption) {
				case 1:
					app.addNewContact();
					break;
				case 2:
					app.removeExistingContacts();
					break;
				case 3:
					app.printContactInfo();
					break;
				case 4:
					exit = true;
					System.out.println("\n You can now close the Application.");
					break;
				default:
					continue;
				}
			} catch (Exception e) {
				System.out.println("Please enter a valid option!!\n");
			}
		}
	}

	/**
	 * This method is used to add new contact (name and number) to new or existing address book
	 * Gets name and 10 digit number from user via scanner
	 * 
	 * @return void
	 */
	private void addNewContact() {
		String addressBookName = getAndValidateAddressBookName();
		String addName = getAndValidateContactName();
		String addPhoneNum = getAndValidateContactNumber();
		addContactsInAddressBook(addressBookName, addName, addPhoneNum);
		//To make the references null - for better GC practises
		addressBookName=null;addName=null;addPhoneNum=null;
	}

	/**
	 * This method is used to remove existing contacts
	 * gets input from user - user selects 1 to remove all contacts or 2 to remove specific contact
	 * 
	 * @return void
	 */
	private void removeExistingContacts() {
		if (addressBooks.isEmpty()) {
			System.out.println("No Contacts found.\n");
		} else {
			boolean endLoop = false;
			while (!endLoop) {
				System.out.print("\n Choose an option... \n 1. Remove ALL Contacts \n 2. Remove specific contact \n Enter your selection here and press Enter: ");
				int selectedNum = getInputIntFromUser();

				if (selectedNum == 1) {
					endLoop = removeAllContacts();

				} else if (selectedNum == 2) {
					endLoop = removeSelectedContacts();

				} else {
					endLoop = false;
					System.out.println("Invalid selection! Please select either 1 or 2.\n");
				}
			}
		
		}
	}
	
	/**
	 * This method is used to print contact info based on 
	 * Acceptance Criteria 4 (to print all contacts in an address book) and
	 * Acceptance Criteria 6 (to print a unique set of all contacts across multiple address books)
	 * 
	 * @return void
	 */
	private void printContactInfo() {
		if (addressBooks.isEmpty()) {
			System.out.println("No Contacts found.\n");
		} else {
			boolean endLoop = false;
			while (!endLoop) {
				System.out.print("\n Choose an option... \n 1. Display ALL contacts in AN address book. \n 2. Display unique set of ALL contacts across ALL address books. \n Enter your selection here and press Enter: ");
				int selectedNum = getInputIntFromUser();

				if (selectedNum == 1) {
					endLoop = printAnAddressBookContacts();

				} else if (selectedNum == 2) {
					endLoop = printAllUniqueContracts();

				} else {
					endLoop = false;
					System.out.println("Invalid number! Please select either 1 or 2.\n");
				}
			}
		}
	}
	
	/**
	 * Acceptance Criteria 5: Users should be able to maintain multiple address
	 * books. Asking user to add address book name
	 * @return addressBookName
	 */
	public String getAndValidateAddressBookName() {
		boolean validBkName = false;
		String addressBookName = null;
		while (!validBkName) {
			System.out.println("Enter a name for the Address book: \n");
			addressBookName = getInputStringFromUser();
			// Validating address book name. It should have minimum of 1 character and 
			// maximum of 40 char
			String pattern = "^[a-zA-Z0-9\\s_-]{1,40}$";
			if (null != addressBookName && addressBookName.matches(pattern)) {
				validBkName = true;
			} else {
				System.out.println("Invalid address book name! \n Please enter the Address book name again:");
			}
		}
		return addressBookName;
	}

	/**
	 * This method adds name and phone number in existing address book, if address book exists.
	 * Else add entry in new address book
	 * @param addressBookName, addName, addPhoneNum
	 * @return void
	 */
	private void addContactsInAddressBook(String addressBookName, String addName, String addPhoneNum) {
		if (addressBooks.containsKey(addressBookName)) {
			List<Address> modifiedAddress = new CopyOnWriteArrayList<Address>(addressBooks.get(addressBookName));
			modifiedAddress.add(new Address(addName.trim(), addPhoneNum.trim()));
			addressBooks.put(addressBookName, modifiedAddress);
			System.out.println("Added the contact (" + addName + "," + addPhoneNum + ") in to the Address Book '"
					+ addressBookName + "'");
		} else {
			List<Address> address = new CopyOnWriteArrayList<Address>();
			address.add(new Address(addName.trim(), addPhoneNum.trim()));
			addressBooks.put(addressBookName, address);
			System.out.println("Added the contact (" + addName + "," + addPhoneNum + ") in to the Address Book '"
					+ addressBookName + "'");
		}
	}

	/**
	 * This method gets contact number from user and validates it
	 * 
	 * @return contact number
	 */
	public String getAndValidateContactNumber() {
		boolean validNum = false;
		String addPhoneNum = null;
		while (!validNum) {
			System.out.print("Enter 10 digit phone number: ");
			addPhoneNum = getInputStringFromUser();
			// Validation code for phone number
			String pattern = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}";
			if (null != addPhoneNum && addPhoneNum.matches(pattern)) {
				validNum = true;
			} else {
				System.out.println("Invalid phone number! Phone number must contain 10 digits.\n");

			}
		}
		return addPhoneNum;
	}

	/**
	 * This method gets contact name from user and validates it
	 * 
	 * @return contact name
	 */
	public String getAndValidateContactName() {
		boolean validName = false;
		String addName = null;
		while (!validName) {
			System.out.print("Enter Contact name: ");
			addName = getInputStringFromUser();
			// Validating contact name. It should have minimum of 3 characters and 
			// maximum of 40 char
			String pattern = "^[a-zA-Z0-9\\s_-]{3,40}$";
			if (null != addName && addName.matches(pattern)) {
				validName = true;
			} else {
				System.out.println("Invalid contact name! Contact name must contain at least three characters.\n");
			}
		}
		return addName;
	}

	/**
	 * This method removes specific contacts
	 * 
	 * @return boolean value
	 */
	private boolean removeSelectedContacts() {
		boolean endLoop;
		endLoop=true;
		System.out.print("Enter contact name to be removed: ");
		String selectedAddrBook = getInputStringFromUser();
		List<Address> value = new ArrayList<Address>();
		addressBooks.forEach((key, val) -> {
			for (Address addr : val) {
				if (addr.getName().equals(selectedAddrBook)) {
					value.add(addr);
				}
				val.removeAll(value);
			}
		});
		System.out.println(value.isEmpty() || null == value ? "No such contact name found." : "Contact removed!\n");
		return endLoop;
	}

	/**
	 * This method removes ALL contacts
	 * 
	 * @return boolean value
	 */
	private boolean removeAllContacts() {
		boolean endLoop;
		endLoop=true;
		addressBooks.clear();
		System.out.println("\n All Contacts Removed.\n");
		return endLoop;
	}

	/**
	 * This method prints unique set of contacts across ALL address books
	 * 
	 * @return boolean if endLoop is true or false
	 */
	private boolean printAllUniqueContracts() {
		boolean endLoop;
		endLoop = true;
		List<Address> address = new ArrayList<Address>();
		addressBooks.forEach((k, v) -> {
			address.addAll(addressBooks.get(k));
		});
		// collecting as Set to avoid duplicate entries
		Set<Address> addressAsSet = address.stream().collect(Collectors.toSet());
		addressAsSet.forEach((setVal) -> System.out.println(setVal.getName() + ": " + setVal.getPhoneNumber()));
		return endLoop;
	}

	/**
	 * This method displays an address book's contacts
	 * 
	 * @return boolean if endLoop is true or false
	 */
	private boolean printAnAddressBookContacts() {
		boolean endLoop;
		endLoop = true;
		boolean endSelOption = false;
		while (!endSelOption) {
			System.out.print("\n NOTE: Address book name is CASE-SENSITIVE! \n Enter the address book name: ");
			String selectedAddrBook = getInputStringFromUser();
			if (addressBooks.containsKey(selectedAddrBook)) {
				endSelOption = true;
				System.out.println("List of all contacts in the address book: " + selectedAddrBook);
				System.out.println("-------------------------------------------------------------");
				List<Address> modifiedAddress = addressBooks.get(selectedAddrBook);
				modifiedAddress
						.forEach(address -> System.out.println(address.getName() + ": " + address.getPhoneNumber()));
			} else {
				endSelOption = false;
				System.out.println("No address book found in the name: '" + selectedAddrBook
						+ "'.Select one of the Address books from below: \n " + addressBooks.keySet());
			}
		}
		return endLoop;
	}
	
	/**
	 * method to get input from user
	 * 
	 * @return input string
	 */
	private String getInputStringFromUser() {
		String selectedValue;
		Scanner scanner = new Scanner(System.in);
		selectedValue = scanner.nextLine();
		return selectedValue;
	}
	
	/**
	 * method to get input from user
	 * 
	 * @return input int
	 */
	private static int getInputIntFromUser() {
		Scanner inpNum = new Scanner(System.in);
		int selectedNum = inpNum.nextInt();
		return selectedNum;
	}
}
