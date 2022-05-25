package io.sivasai.model;

public class ResponseClass {

	private int ID;
	private int response_code;
	private String response_msg;
	
	public ResponseClass(){}
	
	public ResponseClass(int id,int response_code,String msg){
		this.ID = id;
		this.response_code = response_code;
		this.response_msg = msg;
	}
	
	public ResponseClass(int response_code,String msg){
		this.response_code = response_code;
		this.response_msg = msg;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getResponse_code() {
		return response_code;
	}

	public void setResponse_code(int response_code) {
		this.response_code = response_code;
	}

	public String getResponse_msg() {
		return response_msg;
	}

	public void setResponse_msg(String response_msg) {
		this.response_msg = response_msg;
	}
	
	
}
