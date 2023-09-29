package nav_msgs;

import geometry_msgs.PoseWithCovarianceStamped;
import java_rosbridge_client.core.utility.RosServiceArgs;

public class SetMapArgs extends RosServiceArgs{
	
	private OccupancyGrid map;
	private PoseWithCovarianceStamped initial_pose;
	
	public SetMapArgs() {
		
	}
	
    public SetMapArgs(OccupancyGrid map, PoseWithCovarianceStamped initial_pose) {
		this.setMap(map);
    	this.setInitial_pose(initial_pose);
	}

	public OccupancyGrid getMap() {
		return map;
	}

	public void setMap(OccupancyGrid map) {
		this.map = map;
	}

	public PoseWithCovarianceStamped getInitial_pose() {
		return initial_pose;
	}

	public void setInitial_pose(PoseWithCovarianceStamped initial_pose) {
		this.initial_pose = initial_pose;
	}

}
