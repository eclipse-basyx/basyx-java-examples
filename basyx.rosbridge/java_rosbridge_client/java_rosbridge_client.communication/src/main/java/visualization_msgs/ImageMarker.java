package visualization_msgs;

import geometry_msgs.Point;
import java_rosbridge_client.core.utility.RosMessage;
import msg_elements.Duration;
import std_msgs.ColorRGBA;
import std_msgs.Header;

public class ImageMarker extends RosMessage{
	
	private Short CIRCLE=0;
	private Short LINE_STRIP=1;
	private Short LINE_LIST=2;
	private Short POLYGON=3;
	private Short POINTS=4;
	private Short ADD=0;
	private Short REMOVE=1;
	private Header header;
	private String ns;
	private Integer id;
	private Integer type;
	private Integer action;
	private Point position;
	private Float scale;
	private ColorRGBA outline_color;
	private Short filled;
	private ColorRGBA fill_color;
	private Duration lifetime;
	private Point[] points;
	private ColorRGBA[] outline_colors;
	
	public ImageMarker() {
		
	}
	
	public ImageMarker(Short CIRCLE, Short LINE_STRIP, Short LINE_LIST, Short POLYGON, Short POINTS, 
			Short ADD, Short REMOVE, Header header, String ns, Integer id, Integer type, Integer action,
			Point position, Float scale, ColorRGBA outline_color, Short filled, ColorRGBA fill_color,
			Duration lifetime, Point[] points, ColorRGBA[] outline_colors) {
		this.setCIRCLE(CIRCLE);
		this.setLINE_STRIP(LINE_STRIP);
		this.setLINE_LIST(LINE_LIST);
		this.setPOLYGON(POLYGON);
		this.setPOINTS(POINTS);
		this.setADD(ADD);
		this.setREMOVE(REMOVE);
		this.setHeader(header);
		this.setNs(ns);
		this.setId(id);
		this.setType(type);
		this.setAction(action);
		this.setPosition(position);
		this.setScale(scale);
		this.setOutline_color(outline_color);
		this.setFilled(filled);
		this.setFill_color(fill_color);
		this.setLifetime(lifetime);
		this.setPoints(points);
		this.setOutline_colors(outline_colors);
	}
	
	public ImageMarker(Header header, String ns, Integer id, Integer type, Integer action,
			Point position, Float scale, ColorRGBA outline_color, Short filled, ColorRGBA fill_color,
			Duration lifetime, Point[] points, ColorRGBA[] outline_colors) {
		this.setHeader(header);
		this.setNs(ns);
		this.setId(id);
		this.setType(type);
		this.setAction(action);
		this.setPosition(position);
		this.setScale(scale);
		this.setOutline_color(outline_color);
		this.setFilled(filled);
		this.setFill_color(fill_color);
		this.setLifetime(lifetime);
		this.setPoints(points);
		this.setOutline_colors(outline_colors);
	}

	public Short getCIRCLE() {
		return CIRCLE;
	}

	public void setCIRCLE(Short cIRCLE) {
		CIRCLE = cIRCLE;
	}

	public Short getLINE_STRIP() {
		return LINE_STRIP;
	}

	public void setLINE_STRIP(Short lINE_STRIP) {
		LINE_STRIP = lINE_STRIP;
	}

	public Short getLINE_LIST() {
		return LINE_LIST;
	}

	public void setLINE_LIST(Short lINE_LIST) {
		LINE_LIST = lINE_LIST;
	}

	public Short getPOLYGON() {
		return POLYGON;
	}

	public void setPOLYGON(Short pOLYGON) {
		POLYGON = pOLYGON;
	}

	public Short getPOINTS() {
		return POINTS;
	}

	public void setPOINTS(Short pOINTS) {
		POINTS = pOINTS;
	}

	public Short getADD() {
		return ADD;
	}

	public void setADD(Short aDD) {
		ADD = aDD;
	}

	public Short getREMOVE() {
		return REMOVE;
	}

	public void setREMOVE(Short rEMOVE) {
		REMOVE = rEMOVE;
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public String getNs() {
		return ns;
	}

	public void setNs(String ns) {
		this.ns = ns;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getAction() {
		return action;
	}

	public void setAction(Integer action) {
		this.action = action;
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public Float getScale() {
		return scale;
	}

	public void setScale(Float scale) {
		this.scale = scale;
	}

	public ColorRGBA getOutline_color() {
		return outline_color;
	}

	public void setOutline_color(ColorRGBA outline_color) {
		this.outline_color = outline_color;
	}

	public Short getFilled() {
		return filled;
	}

	public void setFilled(Short filled) {
		this.filled = filled;
	}

	public ColorRGBA getFill_color() {
		return fill_color;
	}

	public void setFill_color(ColorRGBA fill_color) {
		this.fill_color = fill_color;
	}

	public Duration getLifetime() {
		return lifetime;
	}

	public void setLifetime(Duration lifetime) {
		this.lifetime = lifetime;
	}

	public Point[] getPoints() {
		return points;
	}

	public void setPoints(Point[] points) {
		this.points = points;
	}

	public ColorRGBA[] getOutline_colors() {
		return outline_colors;
	}

	public void setOutline_colors(ColorRGBA[] outline_colors) {
		this.outline_colors = outline_colors;
	}

}
