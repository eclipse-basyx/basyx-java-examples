package visualization_msgs;

import geometry_msgs.Point;
import geometry_msgs.Pose;
import java_rosbridge_client.core.utility.RosMessage;
import std_msgs.Header;

public class InteractiveMarkerFeedback extends RosMessage{
	
	private Short KEEP_ALIVE=0;
	private Short POSE_UPDATE=1;
	private Short MENU_SELECT=2;
	private Short BUTTON_CLICK=3;
	private Short MOUSE_DOWN=4;
	private Short MOUSE_UP=5;
	private Header header;
	private String client_id;
	private String marker_name;
	private String control_name;
	private Short event_type;
	private Pose pose;
	private Long menu_entry_id;
	private Point mouse_point;
	private Boolean mouse_point_valid;
	
	public InteractiveMarkerFeedback() {
		
	}
	
	public InteractiveMarkerFeedback(Short KEEP_ALIVE, Short POSE_UPDATE, Short MENU_SELECT, Short BUTTON_CLICK,
			Short MOUSE_DOWN, Short MOUSE_UP, Header header, String client_id, String marker_name, String control_name,
			Short event_type, Pose pose, Long menu_entry_id, Point mouse_point, Boolean mouse_point_valid) {
		this.setKEEP_ALIVE(KEEP_ALIVE);
		this.setPOSE_UPDATE(POSE_UPDATE);
		this.setMENU_SELECT(MENU_SELECT);
		this.setBUTTON_CLICK(BUTTON_CLICK);
		this.setMOUSE_DOWN(MOUSE_DOWN);
		this.setMOUSE_UP(MOUSE_UP);
		this.setHeader(header);
		this.setClient_id(client_id);
		this.setMarker_name(marker_name);
		this.setControl_name(control_name);
		this.setEvent_type(event_type);
		this.setPose(pose);
		this.setMenu_entry_id(menu_entry_id);
		this.setMouse_point(mouse_point);
		this.setMouse_point_valid(mouse_point_valid);
	}

	public InteractiveMarkerFeedback(Header header, String client_id, String marker_name, String control_name,
			Short event_type, Pose pose, Long menu_entry_id, Point mouse_point, Boolean mouse_point_valid) {
		this.setHeader(header);
		this.setClient_id(client_id);
		this.setMarker_name(marker_name);
		this.setControl_name(control_name);
		this.setEvent_type(event_type);
		this.setPose(pose);
		this.setMenu_entry_id(menu_entry_id);
		this.setMouse_point(mouse_point);
		this.setMouse_point_valid(mouse_point_valid);
	}
	
	public Short getKEEP_ALIVE() {
		return KEEP_ALIVE;
	}

	public void setKEEP_ALIVE(Short kEEP_ALIVE) {
		KEEP_ALIVE = kEEP_ALIVE;
	}

	public Short getPOSE_UPDATE() {
		return POSE_UPDATE;
	}

	public void setPOSE_UPDATE(Short pOSE_UPDATE) {
		POSE_UPDATE = pOSE_UPDATE;
	}

	public Short getMENU_SELECT() {
		return MENU_SELECT;
	}

	public void setMENU_SELECT(Short mENU_SELECT) {
		MENU_SELECT = mENU_SELECT;
	}

	public Short getBUTTON_CLICK() {
		return BUTTON_CLICK;
	}

	public void setBUTTON_CLICK(Short bUTTON_CLICK) {
		BUTTON_CLICK = bUTTON_CLICK;
	}

	public Short getMOUSE_DOWN() {
		return MOUSE_DOWN;
	}

	public void setMOUSE_DOWN(Short mOUSE_DOWN) {
		MOUSE_DOWN = mOUSE_DOWN;
	}

	public Short getMOUSE_UP() {
		return MOUSE_UP;
	}

	public void setMOUSE_UP(Short mOUSE_UP) {
		MOUSE_UP = mOUSE_UP;
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	public String getMarker_name() {
		return marker_name;
	}

	public void setMarker_name(String marker_name) {
		this.marker_name = marker_name;
	}

	public String getControl_name() {
		return control_name;
	}

	public void setControl_name(String control_name) {
		this.control_name = control_name;
	}

	public Short getEvent_type() {
		return event_type;
	}

	public void setEvent_type(Short event_type) {
		this.event_type = event_type;
	}

	public Pose getPose() {
		return pose;
	}

	public void setPose(Pose pose) {
		this.pose = pose;
	}

	public Long getMenu_entry_id() {
		return menu_entry_id;
	}

	public void setMenu_entry_id(Long menu_entry_id) {
		this.menu_entry_id = menu_entry_id;
	}

	public Point getMouse_point() {
		return mouse_point;
	}

	public void setMouse_point(Point mouse_point) {
		this.mouse_point = mouse_point;
	}

	public Boolean getMouse_point_valid() {
		return mouse_point_valid;
	}

	public void setMouse_point_valid(Boolean mouse_point_valid) {
		this.mouse_point_valid = mouse_point_valid;
	}

}
