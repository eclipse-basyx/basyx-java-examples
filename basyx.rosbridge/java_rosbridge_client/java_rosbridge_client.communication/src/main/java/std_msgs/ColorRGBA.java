package std_msgs;

import java_rosbridge_client.core.utility.RosMessage;

public class ColorRGBA extends RosMessage{
	private Float r;
	private Float g;
	private Float b;
	private Float a;
	
	public ColorRGBA() {
		
	}
	
    public ColorRGBA(Float r, Float g, Float b, Float a) {
		this.setR(r);
		this.setG(g);
		this.setB(b);
		this.setA(a);
	}

	public Float getR() {
		return r;
	}

	public void setR(Float r) {
		this.r = r;
	}

	public Float getG() {
		return g;
	}

	public void setG(Float g) {
		this.g = g;
	}

	public Float getB() {
		return b;
	}

	public void setB(Float b) {
		this.b = b;
	}

	public Float getA() {
		return a;
	}

	public void setA(Float a) {
		this.a = a;
	}

}
