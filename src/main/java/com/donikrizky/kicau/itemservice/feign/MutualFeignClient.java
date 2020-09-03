package com.donikrizky.kicau.itemservice.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "mutual-service")
@RequestMapping(value = "/client")
public interface MutualFeignClient {

	@GetMapping(value = "/mutual/{userId}")
	List<Integer> findFollowed(@RequestHeader(HttpHeaders.AUTHORIZATION) String header, @PathVariable("userId") Integer userId);

	@GetMapping(value = "/favorite/{itemId}")
	Long findFavoriteCount(@RequestHeader(HttpHeaders.AUTHORIZATION) String header, @PathVariable("itemId") Integer itemId);
}
