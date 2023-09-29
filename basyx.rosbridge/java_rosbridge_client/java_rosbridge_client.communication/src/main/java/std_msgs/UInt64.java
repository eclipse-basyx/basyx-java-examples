package std_msgs;

import java.math.BigInteger;

import java_rosbridge_client.core.utility.RosMessage;

public class UInt64 extends RosMessage{

	private BigInteger data;
	
	public UInt64() {
		
	}
	
	public UInt64(BigInteger data) {
		this.setData(data);
	}

	public BigInteger getData() {
		return data;
	}

	public void setData(BigInteger data) {
		this.data = data;
	}
	
	
	
}
