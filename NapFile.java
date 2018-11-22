import java.lang.String;

public class NapFile {
	private String fileName, description;
	private String username, ip, port, connSpeed; 
	
	public NapFile(String fileName, String description, String username, String ip, String port, String connSpeed) {
		this.fileName = fileName;
		this.description = description;
		this.username = username;
		this.ip = ip;
		this.port = port;
		this.connSpeed = connSpeed;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getConnSpeed() {
		return connSpeed;
	}

	public void setConnSpeed(String connSpeed) {
		this.connSpeed = connSpeed;
	}
	
	public String toString() {
		String returnString =  "File: " + this.fileName +"\nDescription: " + this.description + "\nUsername: " + username + 
				"\nIP: " + this.ip + "\nPort: " + port + "\nConnectionSpeed: " + connSpeed + "\n";
		return returnString;	
	}
}
