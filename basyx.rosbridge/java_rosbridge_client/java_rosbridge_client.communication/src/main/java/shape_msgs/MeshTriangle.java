package shape_msgs;

import java_rosbridge_client.core.utility.RosMessage;

public class MeshTriangle extends RosMessage{
	private Long[] vertex_indices;
	
	public MeshTriangle() {
		
	}
	
    public MeshTriangle(Long[] vertex_indices) {
		this.setVertex_indices(vertex_indices);
	}

	public Long[] getVertex_indices() {
		return vertex_indices;
	}

	public void setVertex_indices(Long[] vertex_indices) {
		this.vertex_indices = vertex_indices;
	}
    
    
}
