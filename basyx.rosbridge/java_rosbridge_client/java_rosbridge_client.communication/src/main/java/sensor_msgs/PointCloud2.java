package sensor_msgs;

import java_rosbridge_client.core.utility.RosMessage;
import std_msgs.Header;

public class PointCloud2 extends RosMessage{
	private Header header;
	private Long height;
	private Long width;
	private PointField[] fields;
	private Boolean is_bigendian;
	private Long point_step;
	private Long row_step;
	private Integer[] data;
	Boolean is_dense;
	
	public PointCloud2() {
		
	}

	public PointCloud2(Header header, Long height, Long width, PointField[] fields, Boolean is_bigendian,
			Long point_step, Long row_step, Integer[] data, Boolean is_dense) {
		this.header = header;
		this.height = height;
		this.width = width;
		this.fields = fields;
		this.is_bigendian = is_bigendian;
		this.point_step = point_step;
		this.row_step = row_step;
		this.data = data;
		this.is_dense = is_dense;
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public Long getHeight() {
		return height;
	}

	public void setHeight(Long height) {
		this.height = height;
	}

	public Long getWidth() {
		return width;
	}

	public void setWidth(Long width) {
		this.width = width;
	}

	public PointField[] getFields() {
		return fields;
	}

	public void setFields(PointField[] fields) {
		this.fields = fields;
	}

	public Boolean getIs_bigendian() {
		return is_bigendian;
	}

	public void setIs_bigendian(Boolean is_bigendian) {
		this.is_bigendian = is_bigendian;
	}

	public Long getPoint_step() {
		return point_step;
	}

	public void setPoint_step(Long point_step) {
		this.point_step = point_step;
	}

	public Long getRow_step() {
		return row_step;
	}

	public void setRow_step(Long row_step) {
		this.row_step = row_step;
	}

	public Integer[] getData() {
		return data;
	}

	public void setData(Integer[] data) {
		this.data = data;
	}

	public Boolean getIs_dense() {
		return is_dense;
	}

	public void setIs_dense(Boolean is_dense) {
		this.is_dense = is_dense;
	}
	
	
}
