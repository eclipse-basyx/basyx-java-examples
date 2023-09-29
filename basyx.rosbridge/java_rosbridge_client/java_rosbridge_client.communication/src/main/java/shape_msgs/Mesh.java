package shape_msgs;

import geometry_msgs.Point;
import java_rosbridge_client.core.utility.RosMessage;

public class Mesh extends RosMessage{
	
	private MeshTriangle[] triangles;
	private Point[] vertices;
	
	public Mesh() {
		
	}
	
	public Mesh(MeshTriangle[] triangles, Point[] vertices) {
		this.setTriangles(triangles);
		this.setVertices(vertices);
	}

	public MeshTriangle[] getTriangles() {
		return triangles;
	}

	public void setTriangles(MeshTriangle[] triangles) {
		this.triangles = triangles;
	}

	public Point[] getVertices() {
		return vertices;
	}

	public void setVertices(Point[] vertices) {
		this.vertices = vertices;
	}

}
