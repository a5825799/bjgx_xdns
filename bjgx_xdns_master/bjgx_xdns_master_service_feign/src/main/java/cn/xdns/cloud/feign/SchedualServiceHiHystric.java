package cn.xdns.cloud.feign;

import org.springframework.stereotype.Component;

import cn.xdns.cloud.service.SchedualServiceHi;

@Component
public class SchedualServiceHiHystric implements SchedualServiceHi{

	@Override
	public String sayHiFromClientOne(String name) {
		// TODO Auto-generated method stub
		return "sorry" + name;
	}

}
