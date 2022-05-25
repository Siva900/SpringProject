package io.sivasai.model;

public class ClientModel {
	
	private String ID;
	final private String Channel = "whatsapp";
	private String Auth_token;
	private String name;
	
	public ClientModel(){}
	
	public ClientModel(String ID, String Auth_token, String name){
		super();
		this.ID = ID;
		this.Auth_token = Auth_token;
		this.name = name;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getAuth_token() {
		return Auth_token;
	}

	public void setAuth_token(String auth_token) {
		Auth_token = auth_token;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getChannel() {
		return Channel;
	}
	
	@Override
	public String toString() {
		return "Client [ClientId = " + ID + ", ClientName = " + name + ", Auth_token = " + Auth_token + "]";
	}
}
