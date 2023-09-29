package nav_msgs;

import java_rosbridge_client.core.utility.RosServiceResp;

public class GetPlanResp extends RosServiceResp{
	private Path plan;
	
	public GetPlanResp() {
		
	}
	
    public GetPlanResp(Path plan) {
		this.setPlan(plan);
	}

	public Path getPlan() {
		return plan;
	}

	public void setPlan(Path plan) {
		this.plan = plan;
	}

}
