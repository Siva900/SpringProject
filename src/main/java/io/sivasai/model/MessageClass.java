package io.sivasai.model;

public class MessageClass {
	
	private int ID;
	private String client_id;
	final private String Channel = "whatsapp";
	private String recipient_number;
	private String message_body;
	private int sent_status;
	private String scheduled_time;
	private String arrival_time;
	private int response_code;
	private String response_msg;
	private int tries_count;
	
	public MessageClass(){}
	
	public MessageClass(int ID, String client_id, String recipient_number,String message_body, String scheduled_time, String arrival_time){
		super();
		this.ID = ID;
		this.client_id = client_id;
		this.recipient_number = recipient_number;
		this.message_body = message_body;
		this.scheduled_time = scheduled_time;
		this.arrival_time = arrival_time;
	}
	
	public MessageClass(String client_id, String recipient_number,String message_body, String scheduled_time, String arrival_time){
		super();
		this.client_id = client_id;
		this.recipient_number = recipient_number;
		this.message_body = message_body;
		this.scheduled_time = scheduled_time;
		this.arrival_time = arrival_time;
	}
	
	public MessageClass(int id,int triesCount){
		super();
		this.ID = id;
		this.tries_count = triesCount;
	}

	public MessageClass(int ID, String client_id, String recipient_number,String message_body, String scheduled_time, String arrival_time,int sent_status){
		super();
		this.ID = ID;
		this.client_id = client_id;
		this.recipient_number = recipient_number;
		this.message_body = message_body;
		this.scheduled_time = scheduled_time;
		this.arrival_time = arrival_time;
		this.sent_status = sent_status;
	}
	
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	public String getRecipient_number() {
		return recipient_number;
	}

	public void setRecipient_number(String recipient_number) {
		this.recipient_number = recipient_number;
	}

	public String getMessage_body() {
		return message_body;
	}

	public void setMessage_body(String message_body) {
		this.message_body = message_body;
	}

	public int getSent_status() {
		return sent_status;
	}

	public void setSent_status(int sent_status) {
		this.sent_status = sent_status;
	}

	public String getScheduled_time() {
		return scheduled_time;
	}

	public void setScheduled_time(String scheduled_time) {
		this.scheduled_time = scheduled_time;
	}

	public String getArrival_time() {
		return arrival_time;
	}

	public void setArrival_time(String arrival_time) {
		this.arrival_time = arrival_time;
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

	public String getChannel() {
		return Channel;
	}
	
	public int getTries_count() {
		return tries_count;
	}

	public void setTries_count(int tries_count) {
		this.tries_count = tries_count;
	}
	
	@Override
	public String toString() {
		return "Message [message_id = " + ID + ", message = " + message_body + ", recipient phone number = "
				+ recipient_number + ", scheduled_at = " + scheduled_time + ", client_id=" + client_id
				+ ", arrived_time = " + arrival_time + ", sent_status = " + sent_status + "]";
	}
	
}
