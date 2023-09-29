package sensor_msgs;

import java_rosbridge_client.core.utility.RosMessage;
import std_msgs.Header;

public class JointState extends RosMessage {
	private Header header;
	private String[] name;
	private Double[] position; 
	private Double[] velocity;
	private Double[] effort;
	
	public JointState() {
		
	}
	
	public JointState(String[] name, Double[] position, Double[] velocity, Double[] effort) {
		this.setName(name);
		this.setPosition(position);
		this.setVelocity(velocity);
		this.setEffort(effort);
	}

	public String[] getName() {
		return name;
	}

	public void setName(String[] name) {
		this.name = name;
	}

	public Double[] getPosition() {
		return position;
	}

	public void setPosition(Double[] position) {
		this.position = position;
	}

	public Double[] getVelocity() {
		return velocity;
	}

	public void setVelocity(Double[] velocity) {
		this.velocity = velocity;
	}
	
	public Double[] getEffort() {
		return effort;
	}

	public void setEffort(Double[] effort) {
		this.effort = effort;
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}
}
