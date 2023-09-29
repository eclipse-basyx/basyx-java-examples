package sensor_msgs;

import java_rosbridge_client.core.utility.RosMessage;

public class JoyFeedbackArray extends RosMessage{

	private JoyFeedback[] array;
	
	public JoyFeedbackArray() {
		
	}

	public JoyFeedbackArray(JoyFeedback[] array) {
		this.array = array;
	}

	public JoyFeedback[] getArray() {
		return array;
	}

	public void setArray(JoyFeedback[] array) {
		this.array = array;
	}
	
}
