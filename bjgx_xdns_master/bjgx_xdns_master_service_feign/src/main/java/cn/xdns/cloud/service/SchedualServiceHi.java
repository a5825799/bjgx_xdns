package cn.xdns.cloud.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.xdns.cloud.feign.SchedualServiceHiHystric;

@FeignClient(value = "service-hi",fallback=SchedualServiceHiHystric.class)
public interface SchedualServiceHi {

	@RequestMapping(value = "/hi",method = RequestMethod.GET)
    String sayHiFromClientOne(@RequestParam(value = "name") String name);

}
