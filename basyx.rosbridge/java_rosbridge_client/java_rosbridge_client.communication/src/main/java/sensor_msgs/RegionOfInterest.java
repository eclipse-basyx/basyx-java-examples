package sensor_msgs;

public class RegionOfInterest {
	private Long x_offset;
	private Long y_offset;
	private Long height;
	private Long width;
	private Boolean rectify;
	
	public RegionOfInterest() {
		
	}

	public RegionOfInterest(Long x_offset, Long y_offset, Long height, Long width, Boolean rectify) {
		this.x_offset = x_offset;
		this.y_offset = y_offset;
		this.height = height;
		this.width = width;
		this.rectify = rectify;
	}

	public Long getX_offset() {
		return x_offset;
	}

	public void setX_offset(Long x_offset) {
		this.x_offset = x_offset;
	}

	public Long getY_offset() {
		return y_offset;
	}

	public void setY_offset(Long y_offset) {
		this.y_offset = y_offset;
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

	public Boolean getRectify() {
		return rectify;
	}

	public void setRectify(Boolean rectify) {
		this.rectify = rectify;
	}
	
	
}
