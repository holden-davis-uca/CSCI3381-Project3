package CSCI3381Project3Package;

public class Tweet {
	
	private int polarity;
	private long ID;
	private String user;
	private String content;
	
	//Default constructor
	public Tweet() {
		polarity = -1;
		ID = -1;
		user = "";
		content = "";
	}
	
	//Parameterized constructor
	public Tweet(int polarity, long ID, String user, String content) {
		this.polarity = polarity;
		this.ID = ID;
		this.user = user;
		this.content = content;
	}

	//Setters
	public void setPolarity(int polarity) {
		this.polarity = polarity;
	}

	public void setID(long iD) {
		ID = iD;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setContent(String content) {
		this.content = content;
	}

	//Getters
	public int getPolarity() {
		return polarity;
	}

	public long getID() {
		return ID;
	}

	public String getUser() {
		return user;
	}

	public String getContent() {
		return content;
	}
	
	//toString method, output string is in the same format as the data in an input file
	public String toString() {
		return (this.polarity + "," + this.ID + "," + this.user + "," + this.content);
	}
	
	//Comparing two tweets by IDs
	public boolean equals(Tweet rhs) {
		if (this.getID() != rhs.getID()) {
			return false;
		}
		else return true;
	}
}
