package sensor_msgs;

import java_rosbridge_client.core.utility.RosMessage;
import std_msgs.Header;

public class CameraInfo extends RosMessage{
	private Header header;
	private Long height;
	private Long width;
	private String distortion_model;
	private Double[] D;
	private Double[] K;
	private Double[] R;
	
	private Double[] P;
	Long binning_x;
	Long binning_y;
	
	RegionOfInterest roi;
	
	public CameraInfo() {
		
	}

	public CameraInfo(Header header, Long height, Long width, String distortion_model, Double[] d, Double[] k,
			Double[] r, Double[] p, Long binning_x, Long binning_y, RegionOfInterest roi) {
		this.header = header;
		this.height = height;
		this.width = width;
		this.distortion_model = distortion_model;
		D = d;
		K = k;
		R = r;
		P = p;
		this.binning_x = binning_x;
		this.binning_y = binning_y;
		this.roi = roi;
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

	public String getDistortion_model() {
		return distortion_model;
	}

	public void setDistortion_model(String distortion_model) {
		this.distortion_model = distortion_model;
	}

	public Double[] getD() {
		return D;
	}

	public void setD(Double[] d) {
		D = d;
	}

	public Double[] getK() {
		return K;
	}

	public void setK(Double[] k) {
		K = k;
	}

	public Double[] getR() {
		return R;
	}

	public void setR(Double[] r) {
		R = r;
	}

	public Double[] getP() {
		return P;
	}

	public void setP(Double[] p) {
		P = p;
	}

	public Long getBinning_x() {
		return binning_x;
	}

	public void setBinning_x(Long binning_x) {
		this.binning_x = binning_x;
	}

	public Long getBinning_y() {
		return binning_y;
	}

	public void setBinning_y(Long binning_y) {
		this.binning_y = binning_y;
	}

	public RegionOfInterest getRoi() {
		return roi;
	}

	public void setRoi(RegionOfInterest roi) {
		this.roi = roi;
	}
	
	
	

}
