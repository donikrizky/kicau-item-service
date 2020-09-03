package com.donikrizky.kicau.itemservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "auth-service")
@RequestMapping(value = "/client")
public interface AuthFeignClient {

	@GetMapping(value = "/username-by-id/{userId}")
	String findUsernameById(@RequestHeader(HttpHeaders.AUTHORIZATION) String header, @PathVariable("userId") Integer userId);

}
