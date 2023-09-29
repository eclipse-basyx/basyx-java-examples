package trajectory_msgs;

import java_rosbridge_client.core.utility.RosMessage;
import msg_elements.Duration;

public class JointTrajectoryPoint extends RosMessage{
	
	private Double[] positions;
	private Double[] velocities;
	private Double[] accelerations;
	private Double[] effort;
	private Duration time_from_start;
	
	public JointTrajectoryPoint() {
		
	}
	
	public JointTrajectoryPoint(Double[] positions, Double[] velocities, Double[] accelerations,
			Double[] effort, Duration time_from_start) {
		this.setPositions(positions);
		this.setVelocities(velocities);
		this.setAccelerations(accelerations);
		this.setEffort(effort);
		this.setTime_from_start(time_from_start);
	}

	public Double[] getPositions() {
		return positions;
	}

	public void setPositions(Double[] positions) {
		this.positions = positions;
	}

	public Double[] getVelocities() {
		return velocities;
	}

	public void setVelocities(Double[] velocities) {
		this.velocities = velocities;
	}

	public Double[] getAccelerations() {
		return accelerations;
	}

	public void setAccelerations(Double[] accelerations) {
		this.accelerations = accelerations;
	}

	public Double[] getEffort() {
		return effort;
	}

	public void setEffort(Double[] effort) {
		this.effort = effort;
	}

	public Duration getTime_from_start() {
		return time_from_start;
	}

	public void setTime_from_start(Duration time_from_start) {
		this.time_from_start = time_from_start;
	}

}
