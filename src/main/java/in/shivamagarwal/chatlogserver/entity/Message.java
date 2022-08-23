package in.shivamagarwal.chatlogserver.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_message")
public class Message implements Comparable<Message> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String username;
	
	private String message;
	
	private Long timestamp;
	
	@Column(name = "is_sent")
	private Integer isSent;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	
	public Integer getIsSent() {
		return isSent;
	}

	public void setIsSent(Integer isSent) {
		this.isSent = isSent;
	}
	

	@Override
	public int compareTo(Message o) {
		if (this.getTimestamp() < o.getTimestamp()) {
			return 1;
		}
		else {
			return -1;
		}
	}
		
}
