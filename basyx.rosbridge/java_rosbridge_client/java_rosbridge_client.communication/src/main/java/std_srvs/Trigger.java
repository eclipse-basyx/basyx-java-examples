package std_srvs;

import java_rosbridge_client.core.utility.RosService;

public class Trigger extends RosService<TriggerArgs, TriggerResp>{
	
	public Trigger() {
		this.setArgClass(TriggerArgs.class);
		this.setRespClass(TriggerResp.class);
	}

}
