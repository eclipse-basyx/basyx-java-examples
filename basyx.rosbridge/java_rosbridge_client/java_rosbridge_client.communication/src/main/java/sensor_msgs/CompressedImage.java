package sensor_msgs;

import java_rosbridge_client.core.utility.RosMessage;
import std_msgs.Header;

public class CompressedImage extends RosMessage {

	private Header header;
	private String format;
	Integer[] data;
	
	public CompressedImage() {
		
	}

	public CompressedImage(Header header, String format, Integer[] data) {
		this.header = header;
		this.format = format;
		this.data = data;
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public Integer[] getData() {
		return data;
	}

	public void setData(Integer[] data) {
		this.data = data;
	}
	
	
}
