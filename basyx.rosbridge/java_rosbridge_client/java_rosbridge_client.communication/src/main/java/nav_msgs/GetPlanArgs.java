package nav_msgs;

import geometry_msgs.PoseStamped;
import java_rosbridge_client.core.utility.RosServiceArgs;

public class GetPlanArgs extends RosServiceArgs{
	private PoseStamped start;
	private PoseStamped goal;
	private Float tolerance;
	
	public GetPlanArgs() {
		
	}
	
    public GetPlanArgs(PoseStamped start, PoseStamped goal, Float tolerance) {
		this.setStart(start);
		this.setGoal(goal);
		this.setTolerance(tolerance);
	}

	public PoseStamped getStart() {
		return start;
	}

	public void setStart(PoseStamped start) {
		this.start = start;
	}

	public PoseStamped getGoal() {
		return goal;
	}

	public void setGoal(PoseStamped goal) {
		this.goal = goal;
	}

	public Float getTolerance() {
		return tolerance;
	}

	public void setTolerance(Float tolerance) {
		this.tolerance = tolerance;
	}
}
