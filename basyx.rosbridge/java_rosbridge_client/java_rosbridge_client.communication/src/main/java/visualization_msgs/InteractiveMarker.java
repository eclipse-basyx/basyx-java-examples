package visualization_msgs;

import geometry_msgs.Pose;
import java_rosbridge_client.core.utility.RosMessage;
import std_msgs.Header;

public class InteractiveMarker extends RosMessage{
	
	private Header header;
	private Pose pose;
	private String name;
	private String description;
	private Float scale;
	private MenuEntry[] menu_entries;
	private InteractiveMarkerControl[] controls;
	
	public InteractiveMarker() {
		
	}
	
	public InteractiveMarker(Header header, Pose pose, String name, String description,
			Float scale, MenuEntry[] menu_entries, InteractiveMarkerControl[] controls) {
		this.setHeader(header);
		this.setPose(pose);
		this.setName(name);
		this.setDescription(description);
		this.setScale(scale);
		this.setMenu_entries(menu_entries);
		this.setControls(controls);
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public Pose getPose() {
		return pose;
	}

	public void setPose(Pose pose) {
		this.pose = pose;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Float getScale() {
		return scale;
	}

	public void setScale(Float scale) {
		this.scale = scale;
	}

	public MenuEntry[] getMenu_entries() {
		return menu_entries;
	}

	public void setMenu_entries(MenuEntry[] menu_entries) {
		this.menu_entries = menu_entries;
	}

	public InteractiveMarkerControl[] getControls() {
		return controls;
	}

	public void setControls(InteractiveMarkerControl[] controls) {
		this.controls = controls;
	}

}
