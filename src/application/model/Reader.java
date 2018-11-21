package application.model;

import javafx.beans.property.*;
public class Reader {

	private StringProperty account;
	private StringProperty password;
	private StringProperty role;
	private StringProperty name;
	private StringProperty contact;

	public Reader(){}

	public Reader(String usersID, String password, String role, String name, String contact){
		this.account =  new SimpleStringProperty(usersID);
		this.contact =  new SimpleStringProperty(contact);
		this.name = new SimpleStringProperty(name) ;
		this.password =  new SimpleStringProperty(password);
		this.role =  new SimpleStringProperty(role);
	}

	public String getUsersID(){ return account.get(); }


	public void setUsersID(java.lang.String usersID) { this.account.set(usersID); }

	public String getPassword(){ return password.get(); }

	public void setPassword(String password) { this.password.set(password); }

	public String getRole() { return role.get(); }

	public void setRole(String role) { this.role.set(role); }

	public String getName() { return name.get(); }

	public void setName(String name) { this.name.set(name); }

	public String getContact() { return contact.get(); }

	public void setContact(String contact) { this.contact.set(contact); }
	 
}
