package com.donikrizky.kicau.itemservice.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Api(value = "Query Management System")
@RestController
@Validated
public class QueryController {

	@Autowired
	private Environment env;
	
	@ApiOperation(value = "Hello", response = ResponseEntity.class)
//    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer <access_token>")
	@GetMapping("/hello")
	public String index(HttpServletRequest request) {
		return "Hello from Item Service running at port: " + env.getProperty("local.server.port");
	}
}
