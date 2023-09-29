package sensor_msgs;

import java_rosbridge_client.core.utility.RosMessage;
import std_msgs.Header;

public class BatteryState extends RosMessage{
	private Integer POWER_SUPPLY_STATUS_UNKNOWN = 0;
	private Integer POWER_SUPPLY_STATUS_CHARGING = 1;
	private Integer POWER_SUPPLY_STATUS_DISCHARGING = 2;
	private Integer POWER_SUPPLY_STATUS_NOT_CHARGING = 3;
	private Integer POWER_SUPPLY_STATUS_FULL = 4;
	
	private Integer POWER_SUPPLY_HEALTH_UNKNOWN = 0;
	private Integer POWER_SUPPLY_HEALTH_GOOD = 1;
	private Integer POWER_SUPPLY_HEALTH_OVERHEAT = 2;
	private Integer POWER_SUPPLY_HEALTH_DEAD = 3;
	private Integer POWER_SUPPLY_HEALTH_OVERVOLTAGE = 4;
	private Integer POWER_SUPPLY_HEALTH_UNSPEC_FAILURE = 5;
	private Integer POWER_SUPPLY_HEALTH_COLD = 6;
	private Integer POWER_SUPPLY_HEALTH_WATCHDOG_TIMER_EXPIRE = 7;
	private Integer POWER_SUPPLY_HEALTH_SAFETY_TIMER_EXPIRE = 8;
	
	private Integer POWER_SUPPLY_TECHNOLOGY_UNKNOWN = 0;
	private Integer POWER_SUPPLY_TECHNOLOGY_NIMH = 1;
	private Integer POWER_SUPPLY_TECHNOLOGY_LION = 2;
	private Integer POWER_SUPPLY_TECHNOLOGY_LIPO = 3;
	private Integer POWER_SUPPLY_TECHNOLOGY_LIFE = 4;
	private Integer POWER_SUPPLY_TECHNOLOGY_NICD = 5;
	private Integer POWER_SUPPLY_TECHNOLOGY_LIMN = 6;
	
	private Header header;
	private Float voltage;
	private Float temperature;
	private Float current;
	private Float charge;
	private Float capacity;
	private Float design_capacity;
	private Float percentage;
	private Integer power_supply_status;
	private Integer power_supply_health;
	private Integer power_supply_technology;
	private Boolean present;
	
	private Float[] cell_voltage;
	private Float[] cell_temperature;
	
	private String location;
	private String serial_number;
	
	public BatteryState() {
		
	}
	
	public BatteryState(Integer POWER_SUPPLY_STATUS_UNKNOWN, Integer POWER_SUPPLY_STATUS_CHARGING, Integer POWER_SUPPLY_STATUS_DISCHARGING,
			Integer POWER_SUPPLY_STATUS_NOT_CHARGING, Integer POWER_SUPPLY_STATUS_FULL, Integer POWER_SUPPLY_HEALTH_UNKNOWN,
			Integer POWER_SUPPLY_HEALTH_GOOD, Integer POWER_SUPPLY_HEALTH_OVERHEAT, Integer POWER_SUPPLY_HEALTH_DEAD, Integer POWER_SUPPLY_HEALTH_OVERVOLTAGE,
			Integer POWER_SUPPLY_HEALTH_UNSPEC_FAILURE, Integer POWER_SUPPLY_HEALTH_COLD, Integer POWER_SUPPLY_HEALTH_WATCHDOG_TIMER_EXPIRE, 
			Integer POWER_SUPPLY_HEALTH_SAFETY_TIMER_EXPIRE, Integer POWER_SUPPLY_TECHNOLOGY_UNKNOWN, Integer POWER_SUPPLY_TECHNOLOGY_NIMH,
			Integer POWER_SUPPLY_TECHNOLOGY_LION, Integer POWER_SUPPLY_TECHNOLOGY_LIPO, Integer POWER_SUPPLY_TECHNOLOGY_LIFE, Integer POWER_SUPPLY_TECHNOLOGY_NICD,
			Integer POWER_SUPPLY_TECHNOLOGY_LIMN, Header header, Float voltage, Float temperature, Float current, Float charge, Float capacity, Float design_capacity,
			Float percentage, Integer power_supply_status, Integer power_supply_health, Integer power_supply_technology, Boolean present, Float[] cell_voltage,
			Float[] cell_temperature, String location, String serial_number) {
		
		this.POWER_SUPPLY_STATUS_UNKNOWN = POWER_SUPPLY_STATUS_UNKNOWN;
		this.POWER_SUPPLY_STATUS_CHARGING = POWER_SUPPLY_STATUS_CHARGING;
		this.POWER_SUPPLY_STATUS_DISCHARGING = POWER_SUPPLY_STATUS_DISCHARGING;
		this.POWER_SUPPLY_STATUS_NOT_CHARGING = POWER_SUPPLY_STATUS_NOT_CHARGING;
		this.POWER_SUPPLY_STATUS_FULL = POWER_SUPPLY_STATUS_FULL;
		this.POWER_SUPPLY_HEALTH_UNKNOWN = POWER_SUPPLY_HEALTH_UNKNOWN;
		this.POWER_SUPPLY_HEALTH_GOOD = POWER_SUPPLY_HEALTH_GOOD;
		this.POWER_SUPPLY_HEALTH_OVERHEAT = POWER_SUPPLY_HEALTH_OVERHEAT;
		this.POWER_SUPPLY_HEALTH_DEAD = POWER_SUPPLY_HEALTH_DEAD;
		this.POWER_SUPPLY_HEALTH_OVERVOLTAGE = POWER_SUPPLY_HEALTH_OVERVOLTAGE;
		this.POWER_SUPPLY_HEALTH_UNSPEC_FAILURE = POWER_SUPPLY_HEALTH_UNSPEC_FAILURE;
		this.POWER_SUPPLY_HEALTH_COLD = POWER_SUPPLY_HEALTH_COLD;
		this.POWER_SUPPLY_HEALTH_WATCHDOG_TIMER_EXPIRE = POWER_SUPPLY_HEALTH_WATCHDOG_TIMER_EXPIRE;
		this.POWER_SUPPLY_HEALTH_SAFETY_TIMER_EXPIRE = POWER_SUPPLY_HEALTH_SAFETY_TIMER_EXPIRE;
		this.POWER_SUPPLY_TECHNOLOGY_UNKNOWN = POWER_SUPPLY_TECHNOLOGY_UNKNOWN;
		this.POWER_SUPPLY_TECHNOLOGY_NIMH = POWER_SUPPLY_TECHNOLOGY_NIMH;
		this.POWER_SUPPLY_TECHNOLOGY_LION = POWER_SUPPLY_TECHNOLOGY_LION;
		this.POWER_SUPPLY_TECHNOLOGY_LIPO = POWER_SUPPLY_TECHNOLOGY_LIPO;
		this.POWER_SUPPLY_TECHNOLOGY_LIFE = POWER_SUPPLY_TECHNOLOGY_LIFE;
		this.POWER_SUPPLY_TECHNOLOGY_NICD = POWER_SUPPLY_TECHNOLOGY_NICD;
		this.POWER_SUPPLY_TECHNOLOGY_LIMN = POWER_SUPPLY_TECHNOLOGY_LIMN;
		this.header = header;
		this.voltage = voltage;
		this.temperature = temperature;
		this.current = current;
		this.charge = charge;
		this.capacity = capacity;
		this.design_capacity = design_capacity;
		this.percentage = percentage;
		this.power_supply_status = power_supply_status;
		this.power_supply_health = power_supply_health;
		this.power_supply_technology = power_supply_technology;
		this.present = present;
		this.cell_voltage = cell_voltage;
		this.cell_temperature = cell_temperature;
		this.location = location;
		this.serial_number = serial_number;
		
	}
	
	public BatteryState(Header header, Float voltage, Float temperature, Float current, Float charge, Float capacity, Float design_capacity,
			Float percentage, Integer power_supply_status, Integer power_supply_health, Integer power_supply_technology, Boolean present, Float[] cell_voltage,
			Float[] cell_temperature, String location, String serial_number) {
		this.header = header;
		this.voltage = voltage;
		this.temperature = temperature;
		this.current = current;
		this.charge = charge;
		this.capacity = capacity;
		this.design_capacity = design_capacity;
		this.percentage = percentage;
		this.power_supply_status = power_supply_status;
		this.power_supply_health = power_supply_health;
		this.power_supply_technology = power_supply_technology;
		this.present = present;
		this.cell_voltage = cell_voltage;
		this.cell_temperature = cell_temperature;
		this.location = location;
		this.serial_number = serial_number;
	}


	public Integer getPOWER_SUPPLY_STATUS_UNKNOWN() {
		return POWER_SUPPLY_STATUS_UNKNOWN;
	}

	public void setPOWER_SUPPLY_STATUS_UNKNOWN(Integer pOWER_SUPPLY_STATUS_UNKNOWN) {
		POWER_SUPPLY_STATUS_UNKNOWN = pOWER_SUPPLY_STATUS_UNKNOWN;
	}

	public Integer getPOWER_SUPPLY_STATUS_CHARGING() {
		return POWER_SUPPLY_STATUS_CHARGING;
	}

	public void setPOWER_SUPPLY_STATUS_CHARGING(Integer pOWER_SUPPLY_STATUS_CHARGING) {
		POWER_SUPPLY_STATUS_CHARGING = pOWER_SUPPLY_STATUS_CHARGING;
	}

	public Integer getPOWER_SUPPLY_STATUS_DISCHARGING() {
		return POWER_SUPPLY_STATUS_DISCHARGING;
	}

	public void setPOWER_SUPPLY_STATUS_DISCHARGING(Integer pOWER_SUPPLY_STATUS_DISCHARGING) {
		POWER_SUPPLY_STATUS_DISCHARGING = pOWER_SUPPLY_STATUS_DISCHARGING;
	}

	public Integer getPOWER_SUPPLY_STATUS_NOT_CHARGING() {
		return POWER_SUPPLY_STATUS_NOT_CHARGING;
	}

	public void setPOWER_SUPPLY_STATUS_NOT_CHARGING(Integer pOWER_SUPPLY_STATUS_NOT_CHARGING) {
		POWER_SUPPLY_STATUS_NOT_CHARGING = pOWER_SUPPLY_STATUS_NOT_CHARGING;
	}

	public Integer getPOWER_SUPPLY_STATUS_FULL() {
		return POWER_SUPPLY_STATUS_FULL;
	}

	public void setPOWER_SUPPLY_STATUS_FULL(Integer pOWER_SUPPLY_STATUS_FULL) {
		POWER_SUPPLY_STATUS_FULL = pOWER_SUPPLY_STATUS_FULL;
	}

	public Integer getPOWER_SUPPLY_HEALTH_UNKNOWN() {
		return POWER_SUPPLY_HEALTH_UNKNOWN;
	}

	public void setPOWER_SUPPLY_HEALTH_UNKNOWN(Integer pOWER_SUPPLY_HEALTH_UNKNOWN) {
		POWER_SUPPLY_HEALTH_UNKNOWN = pOWER_SUPPLY_HEALTH_UNKNOWN;
	}

	public Integer getPOWER_SUPPLY_HEALTH_GOOD() {
		return POWER_SUPPLY_HEALTH_GOOD;
	}

	public void setPOWER_SUPPLY_HEALTH_GOOD(Integer pOWER_SUPPLY_HEALTH_GOOD) {
		POWER_SUPPLY_HEALTH_GOOD = pOWER_SUPPLY_HEALTH_GOOD;
	}

	public Integer getPOWER_SUPPLY_HEALTH_OVERHEAT() {
		return POWER_SUPPLY_HEALTH_OVERHEAT;
	}

	public void setPOWER_SUPPLY_HEALTH_OVERHEAT(Integer pOWER_SUPPLY_HEALTH_OVERHEAT) {
		POWER_SUPPLY_HEALTH_OVERHEAT = pOWER_SUPPLY_HEALTH_OVERHEAT;
	}

	public Integer getPOWER_SUPPLY_HEALTH_DEAD() {
		return POWER_SUPPLY_HEALTH_DEAD;
	}

	public void setPOWER_SUPPLY_HEALTH_DEAD(Integer pOWER_SUPPLY_HEALTH_DEAD) {
		POWER_SUPPLY_HEALTH_DEAD = pOWER_SUPPLY_HEALTH_DEAD;
	}

	public Integer getPOWER_SUPPLY_HEALTH_OVERVOLTAGE() {
		return POWER_SUPPLY_HEALTH_OVERVOLTAGE;
	}

	public void setPOWER_SUPPLY_HEALTH_OVERVOLTAGE(Integer pOWER_SUPPLY_HEALTH_OVERVOLTAGE) {
		POWER_SUPPLY_HEALTH_OVERVOLTAGE = pOWER_SUPPLY_HEALTH_OVERVOLTAGE;
	}

	public Integer getPOWER_SUPPLY_HEALTH_UNSPEC_FAILURE() {
		return POWER_SUPPLY_HEALTH_UNSPEC_FAILURE;
	}

	public void setPOWER_SUPPLY_HEALTH_UNSPEC_FAILURE(Integer pOWER_SUPPLY_HEALTH_UNSPEC_FAILURE) {
		POWER_SUPPLY_HEALTH_UNSPEC_FAILURE = pOWER_SUPPLY_HEALTH_UNSPEC_FAILURE;
	}

	public Integer getPOWER_SUPPLY_HEALTH_COLD() {
		return POWER_SUPPLY_HEALTH_COLD;
	}

	public void setPOWER_SUPPLY_HEALTH_COLD(Integer pOWER_SUPPLY_HEALTH_COLD) {
		POWER_SUPPLY_HEALTH_COLD = pOWER_SUPPLY_HEALTH_COLD;
	}

	public Integer getPOWER_SUPPLY_HEALTH_WATCHDOG_TIMER_EXPIRE() {
		return POWER_SUPPLY_HEALTH_WATCHDOG_TIMER_EXPIRE;
	}

	public void setPOWER_SUPPLY_HEALTH_WATCHDOG_TIMER_EXPIRE(Integer pOWER_SUPPLY_HEALTH_WATCHDOG_TIMER_EXPIRE) {
		POWER_SUPPLY_HEALTH_WATCHDOG_TIMER_EXPIRE = pOWER_SUPPLY_HEALTH_WATCHDOG_TIMER_EXPIRE;
	}

	public Integer getPOWER_SUPPLY_HEALTH_SAFETY_TIMER_EXPIRE() {
		return POWER_SUPPLY_HEALTH_SAFETY_TIMER_EXPIRE;
	}

	public void setPOWER_SUPPLY_HEALTH_SAFETY_TIMER_EXPIRE(Integer pOWER_SUPPLY_HEALTH_SAFETY_TIMER_EXPIRE) {
		POWER_SUPPLY_HEALTH_SAFETY_TIMER_EXPIRE = pOWER_SUPPLY_HEALTH_SAFETY_TIMER_EXPIRE;
	}

	public Integer getPOWER_SUPPLY_TECHNOLOGY_UNKNOWN() {
		return POWER_SUPPLY_TECHNOLOGY_UNKNOWN;
	}

	public void setPOWER_SUPPLY_TECHNOLOGY_UNKNOWN(Integer pOWER_SUPPLY_TECHNOLOGY_UNKNOWN) {
		POWER_SUPPLY_TECHNOLOGY_UNKNOWN = pOWER_SUPPLY_TECHNOLOGY_UNKNOWN;
	}

	public Integer getPOWER_SUPPLY_TECHNOLOGY_NIMH() {
		return POWER_SUPPLY_TECHNOLOGY_NIMH;
	}

	public void setPOWER_SUPPLY_TECHNOLOGY_NIMH(Integer pOWER_SUPPLY_TECHNOLOGY_NIMH) {
		POWER_SUPPLY_TECHNOLOGY_NIMH = pOWER_SUPPLY_TECHNOLOGY_NIMH;
	}

	public Integer getPOWER_SUPPLY_TECHNOLOGY_LION() {
		return POWER_SUPPLY_TECHNOLOGY_LION;
	}

	public void setPOWER_SUPPLY_TECHNOLOGY_LION(Integer pOWER_SUPPLY_TECHNOLOGY_LION) {
		POWER_SUPPLY_TECHNOLOGY_LION = pOWER_SUPPLY_TECHNOLOGY_LION;
	}

	public Integer getPOWER_SUPPLY_TECHNOLOGY_LIPO() {
		return POWER_SUPPLY_TECHNOLOGY_LIPO;
	}

	public void setPOWER_SUPPLY_TECHNOLOGY_LIPO(Integer pOWER_SUPPLY_TECHNOLOGY_LIPO) {
		POWER_SUPPLY_TECHNOLOGY_LIPO = pOWER_SUPPLY_TECHNOLOGY_LIPO;
	}

	public Integer getPOWER_SUPPLY_TECHNOLOGY_LIFE() {
		return POWER_SUPPLY_TECHNOLOGY_LIFE;
	}

	public void setPOWER_SUPPLY_TECHNOLOGY_LIFE(Integer pOWER_SUPPLY_TECHNOLOGY_LIFE) {
		POWER_SUPPLY_TECHNOLOGY_LIFE = pOWER_SUPPLY_TECHNOLOGY_LIFE;
	}

	public Integer getPOWER_SUPPLY_TECHNOLOGY_NICD() {
		return POWER_SUPPLY_TECHNOLOGY_NICD;
	}

	public void setPOWER_SUPPLY_TECHNOLOGY_NICD(Integer pOWER_SUPPLY_TECHNOLOGY_NICD) {
		POWER_SUPPLY_TECHNOLOGY_NICD = pOWER_SUPPLY_TECHNOLOGY_NICD;
	}

	public Integer getPOWER_SUPPLY_TECHNOLOGY_LIMN() {
		return POWER_SUPPLY_TECHNOLOGY_LIMN;
	}

	public void setPOWER_SUPPLY_TECHNOLOGY_LIMN(Integer pOWER_SUPPLY_TECHNOLOGY_LIMN) {
		POWER_SUPPLY_TECHNOLOGY_LIMN = pOWER_SUPPLY_TECHNOLOGY_LIMN;
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public Float getVoltage() {
		return voltage;
	}

	public void setVoltage(Float voltage) {
		this.voltage = voltage;
	}

	public Float getTemperature() {
		return temperature;
	}

	public void setTemperature(Float temperature) {
		this.temperature = temperature;
	}

	public Float getCurrent() {
		return current;
	}

	public void setCurrent(Float current) {
		this.current = current;
	}

	public Float getCharge() {
		return charge;
	}

	public void setCharge(Float charge) {
		this.charge = charge;
	}

	public Float getCapacity() {
		return capacity;
	}

	public void setCapacity(Float capacity) {
		this.capacity = capacity;
	}

	public Float getDesign_capacity() {
		return design_capacity;
	}

	public void setDesign_capacity(Float design_capacity) {
		this.design_capacity = design_capacity;
	}

	public Float getPercentage() {
		return percentage;
	}

	public void setPercentage(Float percentage) {
		this.percentage = percentage;
	}

	public Integer getPower_supply_status() {
		return power_supply_status;
	}

	public void setPower_supply_status(Integer power_supply_status) {
		this.power_supply_status = power_supply_status;
	}

	public Integer getPower_supply_health() {
		return power_supply_health;
	}

	public void setPower_supply_health(Integer power_supply_health) {
		this.power_supply_health = power_supply_health;
	}

	public Integer getPower_supply_technology() {
		return power_supply_technology;
	}

	public void setPower_supply_technology(Integer power_supply_technology) {
		this.power_supply_technology = power_supply_technology;
	}

	public Boolean getPresent() {
		return present;
	}

	public void setPresent(Boolean present) {
		this.present = present;
	}

	public Float[] getCell_voltage() {
		return cell_voltage;
	}

	public void setCell_voltage(Float[] cell_voltage) {
		this.cell_voltage = cell_voltage;
	}

	public Float[] getCell_temperature() {
		return cell_temperature;
	}

	public void setCell_temperature(Float[] cell_temperature) {
		this.cell_temperature = cell_temperature;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getSerial_number() {
		return serial_number;
	}

	public void setSerial_number(String serial_number) {
		this.serial_number = serial_number;
	}

	
	
}
