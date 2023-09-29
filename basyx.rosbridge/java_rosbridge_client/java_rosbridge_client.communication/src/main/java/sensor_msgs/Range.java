package sensor_msgs;

import java_rosbridge_client.core.utility.RosMessage;
import std_msgs.Header;

public class Range extends RosMessage {
	
	private Header header;
	
	private Integer ULTRASOUND=0;
	private Integer INFRARED=1;
	
	private Integer radiation_type;
	
	private Float field_of_view;
	
	private Float min_range;
	private Float max_range;
	
	private Float range;
	
	public Range() {
		
	}

	public Range(Header header, Integer uLTRASOUND, Integer iNFRARED, Integer radiation_type, Float field_of_view,
			Float min_range, Float max_range, Float range) {
		this.header = header;
		this.ULTRASOUND = uLTRASOUND;
		this.INFRARED = iNFRARED;
		this.radiation_type = radiation_type;
		this.field_of_view = field_of_view;
		this.min_range = min_range;
		this.max_range = max_range;
		this.range = range;
	}
	
	public Range(Header header, Integer radiation_type, Float field_of_view,
			Float min_range, Float max_range, Float range) {
		this.header = header;
		
		this.radiation_type = radiation_type;
		this.field_of_view = field_of_view;
		this.min_range = min_range;
		this.max_range = max_range;
		this.range = range;
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public Integer getULTRASOUND() {
		return ULTRASOUND;
	}

	public void setULTRASOUND(Integer uLTRASOUND) {
		ULTRASOUND = uLTRASOUND;
	}

	public Integer getINFRARED() {
		return INFRARED;
	}

	public void setINFRARED(Integer iNFRARED) {
		INFRARED = iNFRARED;
	}

	public Integer getRadiation_type() {
		return radiation_type;
	}

	public void setRadiation_type(Integer radiation_type) {
		this.radiation_type = radiation_type;
	}

	public Float getField_of_view() {
		return field_of_view;
	}

	public void setField_of_view(Float field_of_view) {
		this.field_of_view = field_of_view;
	}

	public Float getMin_range() {
		return min_range;
	}

	public void setMin_range(Float min_range) {
		this.min_range = min_range;
	}

	public Float getMax_range() {
		return max_range;
	}

	public void setMax_range(Float max_range) {
		this.max_range = max_range;
	}

	public Float getRange() {
		return range;
	}

	public void setRange(Float range) {
		this.range = range;
	}
	
	
	
	

}
