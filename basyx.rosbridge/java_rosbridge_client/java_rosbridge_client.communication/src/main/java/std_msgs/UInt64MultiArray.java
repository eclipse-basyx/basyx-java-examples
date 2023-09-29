package std_msgs;

import java.math.BigInteger;

import java_rosbridge_client.core.utility.RosMessage;

public class UInt64MultiArray extends RosMessage{

	private MultiArrayLayout layout;
	private BigInteger[] data;
	
	public UInt64MultiArray() {
		
	}
	
	public UInt64MultiArray(MultiArrayLayout layout, BigInteger[] data) {
		this.setLayout(layout);
		this.setData(data);
	}

	public MultiArrayLayout getLayout() {
		return layout;
	}

	public void setLayout(MultiArrayLayout layout) {
		this.layout = layout;
	}

	public BigInteger[] getData() {
		return data;
	}

	public void setData(BigInteger[] data) {
		this.data = data;
	}
}
