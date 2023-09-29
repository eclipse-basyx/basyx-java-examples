package sensor_msgs;

import java_rosbridge_client.core.utility.RosMessage;
import std_msgs.Header;

public class Joy extends RosMessage {
	private Header header;
	private Float[] axes;
	private Integer[] buttons;
	
	public Joy() {
		
	}

	public Joy(Header header, Float[] axes, Integer[] buttons) {
		this.header = header;
		this.axes = axes;
		this.buttons = buttons;
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public Float[] getAxes() {
		return axes;
	}

	public void setAxes(Float[] axes) {
		this.axes = axes;
	}

	public Integer[] getButtons() {
		return buttons;
	}

	public void setButtons(Integer[] buttons) {
		this.buttons = buttons;
	}
	
	
}
