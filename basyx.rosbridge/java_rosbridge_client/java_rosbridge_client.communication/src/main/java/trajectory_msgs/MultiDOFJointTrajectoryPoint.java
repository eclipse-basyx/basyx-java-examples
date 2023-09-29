package trajectory_msgs;

import geometry_msgs.Transform;
import geometry_msgs.Twist;
import java_rosbridge_client.core.utility.RosMessage;
import msg_elements.Duration;

public class MultiDOFJointTrajectoryPoint extends RosMessage{
	
	private Transform[] transforms;
	private Twist[] velocities;
	private Twist[] accelerations;
	private Duration time_from_start;
	
	public MultiDOFJointTrajectoryPoint() {
		
	}
	
	public MultiDOFJointTrajectoryPoint(Transform[] transforms, Twist[] velocities, Twist[] accelerations, Duration time_from_start) {
		this.setTransforms(transforms);
		this.setVelocities(velocities);
		this.setAccelerations(accelerations);
		this.setTime_from_start(time_from_start);
	}

	public Transform[] getTransforms() {
		return transforms;
	}

	public void setTransforms(Transform[] transforms) {
		this.transforms = transforms;
	}

	public Twist[] getVelocities() {
		return velocities;
	}

	public void setVelocities(Twist[] velocities) {
		this.velocities = velocities;
	}

	public Twist[] getAccelerations() {
		return accelerations;
	}

	public void setAccelerations(Twist[] accelerations) {
		this.accelerations = accelerations;
	}

	public Duration getTime_from_start() {
		return time_from_start;
	}

	public void setTime_from_start(Duration time_from_start) {
		this.time_from_start = time_from_start;
	}

}
