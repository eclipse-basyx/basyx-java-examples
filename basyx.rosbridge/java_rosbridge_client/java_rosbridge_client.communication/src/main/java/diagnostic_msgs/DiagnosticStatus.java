package diagnostic_msgs;

import java_rosbridge_client.core.utility.RosMessage;

public class DiagnosticStatus extends RosMessage{
	private Integer OK=0;
	private Integer WARN=1;
	private Integer ERROR=2;
	private Integer STALE=3;
	
	private Integer level;
	private String name;
	private String message;
	private String hardware_id;
	private KeyValue[] values;
	
	public DiagnosticStatus() {
		
	}
	
	public DiagnosticStatus(Integer OK, Integer WARN, Integer ERROR, Integer STALE, Integer level,
			String name, String message, String hardware_id, KeyValue[] values) {
		this.setOK(OK);
		this.setWARN(WARN);
		this.setERROR(ERROR);
		this.setSTALE(STALE);
		this.setLevel(level);
		this.setName(name);
		this.setMessage(message);
		this.setHardware_id(hardware_id);
		this.setValues(values);
		
	}
	
	public DiagnosticStatus(Integer level, String name, String message, String hardware_id, KeyValue[] values) {
		this.setLevel(level);
		this.setName(name);
		this.setMessage(message);
		this.setHardware_id(hardware_id);
		this.setValues(values);
	}

	public Integer getOK() {
		return OK;
	}

	public void setOK(Integer oK) {
		OK = oK;
	}

	public Integer getWARN() {
		return WARN;
	}

	public void setWARN(Integer wARN) {
		WARN = wARN;
	}

	public Integer getERROR() {
		return ERROR;
	}

	public void setERROR(Integer eRROR) {
		ERROR = eRROR;
	}

	public Integer getSTALE() {
		return STALE;
	}

	public void setSTALE(Integer sTALE) {
		STALE = sTALE;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getHardware_id() {
		return hardware_id;
	}

	public void setHardware_id(String hardware_id) {
		this.hardware_id = hardware_id;
	}

	public KeyValue[] getValues() {
		return values;
	}

	public void setValues(KeyValue[] values) {
		this.values = values;
	}

}
