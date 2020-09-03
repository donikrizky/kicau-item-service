package com.donikrizky.kicau.itemservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "user-service")
@RequestMapping(value = "/user")
public interface UserFeignClient {
	
	//fetch legacy profile data
//	@GetMapping(value = "/db/allprofiles")
//    Optional<List<LegacyProfileRequestDTO>> fetchProfileDataFromLegacy(@RequestHeader(HttpHeaders.AUTHORIZATION) String header);
//    
//	// user service
//    @GetMapping(value = "/user/{id}")
//    Optional<LegacyUserResponseDTO> fetchUserbyId(@RequestHeader(HttpHeaders.AUTHORIZATION) String header, @PathVariable("id") Long id);
//    
//    @GetMapping(value = "/user/{email}")
//    Optional<LegacyUserResponseDTO> fetchUserbyEmail(@RequestHeader(HttpHeaders.AUTHORIZATION) String header, @PathVariable("email") String email);
//    
//    // image service
//    @PostMapping(value = "/image/url")
//	String getImageUrl(@RequestHeader(HttpHeaders.AUTHORIZATION) String header, @RequestBody Profile profile);
//    
//    // rating service
//    @GetMapping(value = "/rating/{userId}")
//    HashMap<String, Double> getUserRating(@RequestHeader(HttpHeaders.AUTHORIZATION) String header, @PathVariable("userId") Long userId);
    
}
