package application.model;

public class Reader {

	private String account;
	private String password;
	private String role;
	private String name;
	private String contact;

	public Reader(){}

	public Reader(String usersID, String password, String role, String name, String contact){
		this.account = usersID;
		this.contact = contact;
		this.name = name;
		this.password = password;
		this.role = role;
	}

	public String getUsersID(){ return account; }


	public void setUsersID(java.lang.String usersID) { this.account = usersID; }

	public String getPassword(){ return password; }

	public void setPassword(String password) { this.password = password; }

	public String getRole() { return role; }

	public void setRole(String role) { this.role = role; }

	public String getName() { return name; }

	public void setName(String name) { this.name = name; }

	public String getContact() { return contact; }

	public void setContact(String contact) { this.contact = contact; }
	 
}
