package geometry_msgs;

import java_rosbridge_client.core.utility.RosMessage;

public class Transform extends RosMessage{
	private Vector3 translation;
	private Quaternion rotation;
	
	public Transform() {
		
	}

	public Transform(Vector3 translation, Quaternion rotation) {
		this.translation = translation;
		this.rotation = rotation;
	}

	public Vector3 getTranslation() {
		return translation;
	}

	public void setTranslation(Vector3 translation) {
		this.translation = translation;
	}

	public Quaternion getRotation() {
		return rotation;
	}

	public void setRotation(Quaternion rotation) {
		this.rotation = rotation;
	}
	
	

}
