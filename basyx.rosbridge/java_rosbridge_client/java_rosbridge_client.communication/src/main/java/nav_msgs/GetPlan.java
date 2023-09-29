package nav_msgs;

import java_rosbridge_client.core.utility.RosService;

public class GetPlan extends RosService<GetPlanArgs, GetPlanResp>{

	public GetPlan() {
		this.setArgClass(GetPlanArgs.class);
		this.setRespClass(GetPlanResp.class);
	}
}
