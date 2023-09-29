package stereo_msgs;

import java_rosbridge_client.core.utility.RosMessage;
import sensor_msgs.Image;
import sensor_msgs.RegionOfInterest;
import std_msgs.Header;

public class DisparityImage extends RosMessage{
	
	private Header header;
	private Image image;
	private Float f;
	private Float T;
	private RegionOfInterest valid_window;
	private Float min_disparity;
	private Float max_disparity;
	private Float delta_d;
	
	public DisparityImage() {
		
	}
	
	public DisparityImage(Header header, Image image, Float f, Float T, RegionOfInterest valid_window,
			Float min_disparity, Float max_disparity, Float delta_d) {
		this.setHeader(header);
		this.setImage(image);
		this.setF(f);
		this.setT(T);
		this.setValid_window(valid_window);
		this.setMin_disparity(min_disparity);
		this.setMax_disparity(max_disparity);
		this.setDelta_d(delta_d);
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Float getF() {
		return f;
	}

	public void setF(Float f) {
		this.f = f;
	}

	public Float getT() {
		return T;
	}

	public void setT(Float t) {
		T = t;
	}

	public RegionOfInterest getValid_window() {
		return valid_window;
	}

	public void setValid_window(RegionOfInterest valid_window) {
		this.valid_window = valid_window;
	}

	public Float getMin_disparity() {
		return min_disparity;
	}

	public void setMin_disparity(Float min_disparity) {
		this.min_disparity = min_disparity;
	}

	public Float getMax_disparity() {
		return max_disparity;
	}

	public void setMax_disparity(Float max_disparity) {
		this.max_disparity = max_disparity;
	}

	public Float getDelta_d() {
		return delta_d;
	}

	public void setDelta_d(Float delta_d) {
		this.delta_d = delta_d;
	}

}
