package com.donikrizky.kicau.itemservice.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "user-service")
@RequestMapping(value = "/client")
public interface UserFeignClient {

	@GetMapping(value = "/mutual/{userId}")
	List<Integer> findFollowed(@PathVariable("userId") Integer userId);

}
