package visualization_msgs;

import java.math.BigInteger;

import java_rosbridge_client.core.utility.RosMessage;

public class InteractiveMarkerInit extends RosMessage{
	
	private String server_id;
	private BigInteger seq_num;
	private InteractiveMarker[] markers;
	
	public InteractiveMarkerInit() {
		
	}
	
	public InteractiveMarkerInit(String server_id, BigInteger seq_num, InteractiveMarker[] markers) {
		this.setServer_id(server_id);
		this.setSeq_num(seq_num);
		this.setMarkers(markers);
	}

	public String getServer_id() {
		return server_id;
	}

	public void setServer_id(String server_id) {
		this.server_id = server_id;
	}

	public BigInteger getSeq_num() {
		return seq_num;
	}

	public void setSeq_num(BigInteger seq_num) {
		this.seq_num = seq_num;
	}

	public InteractiveMarker[] getMarkers() {
		return markers;
	}

	public void setMarkers(InteractiveMarker[] markers) {
		this.markers = markers;
	}
	
	

}
