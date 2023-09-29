package main;
import std_srvs.SetBoolArgs;
import std_srvs.SetBoolResp;

public class TestSrvProcessor {
	public TestSrvProcessor() {
		
	}
	
	public SetBoolResp processBool(SetBoolArgs args) {
		System.out.println(args.isData());
		return new SetBoolResp(true, "Everything is fine");
	}
}
