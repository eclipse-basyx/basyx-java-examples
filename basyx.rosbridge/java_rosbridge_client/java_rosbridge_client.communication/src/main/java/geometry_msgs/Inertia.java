package geometry_msgs;

import java_rosbridge_client.core.utility.RosMessage;

public class Inertia extends RosMessage{

	private Double m;
	private Vector3 com;
	private Double ixx;
	private Double ixy;
	private Double ixz;
	private Double iyy;
	private Double iyz;
	private Double izz;
	
	public Inertia() {
		
	}
	
	public Inertia(Double m, Vector3 com, Double ixx, Double ixy, Double ixz,
			Double iyy, Double iyz, Double izz) {
		this.setM(m);
		this.setCom(com);
		this.setIxx(ixx);
		this.setIxy(ixy);
		this.setIxz(ixz);
		this.setIyy(iyy);
		this.setIyz(iyz);
		this.setIzz(izz);
	}

	public Double getM() {
		return m;
	}

	public void setM(Double m) {
		this.m = m;
	}

	public Vector3 getCom() {
		return com;
	}

	public void setCom(Vector3 com) {
		this.com = com;
	}

	public Double getIxz() {
		return ixz;
	}

	public void setIxz(Double ixz) {
		this.ixz = ixz;
	}

	public Double getIxy() {
		return ixy;
	}

	public void setIxy(Double ixy) {
		this.ixy = ixy;
	}

	public Double getIxx() {
		return ixx;
	}

	public void setIxx(Double ixx) {
		this.ixx = ixx;
	}

	public Double getIyy() {
		return iyy;
	}

	public void setIyy(Double iyy) {
		this.iyy = iyy;
	}

	public Double getIyz() {
		return iyz;
	}

	public void setIyz(Double iyz) {
		this.iyz = iyz;
	}

	public Double getIzz() {
		return izz;
	}

	public void setIzz(Double izz) {
		this.izz = izz;
	}

}
