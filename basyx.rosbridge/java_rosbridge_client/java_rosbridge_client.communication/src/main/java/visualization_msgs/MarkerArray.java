package visualization_msgs;

import java_rosbridge_client.core.utility.RosMessage;

public class MarkerArray extends RosMessage{
	
	private Marker[] markers;
	
	public MarkerArray() {
		
	}
	
	public MarkerArray(Marker[] markers) {
		this.setMarkers(markers);	}

	public Marker[] getMarkers() {
		return markers;
	}

	public void setMarkers(Marker[] markers) {
		this.markers = markers;
	}

}
