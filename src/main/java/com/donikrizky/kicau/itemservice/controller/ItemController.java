package com.donikrizky.kicau.itemservice.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.donikrizky.kicau.itemservice.common.CommonResource;
import com.donikrizky.kicau.itemservice.common.ResponseBody;
import com.donikrizky.kicau.itemservice.dto.request.ItemPostRequestDTO;
import com.donikrizky.kicau.itemservice.dto.response.ItemResponseDTO;
import com.donikrizky.kicau.itemservice.service.ItemService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "Item Management System")
@RestController
@Validated
public class ItemController extends CommonResource {

	@Autowired
	private Environment env;

	@Autowired
	private ItemService itemService;

	@ApiOperation(value = "Hello", response = ResponseEntity.class)
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer <access_token>")
	@GetMapping("/hello")
	public String index(HttpServletRequest request) {
		return "Hello from Item Service running at port: " + env.getProperty("local.server.port");
	}

	@ApiOperation(value = "Post your item", response = ResponseEntity.class)
//	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer <access_token>")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully post item"),
//			@ApiResponse(code = 401, message = "Full authentication is required to access this resource"),
//			@ApiResponse(code = 400, message = "Error: "),
//			@ApiResponse(code = 404, message = "User not found with id") })
	@PostMapping(value = "/post")
	public ResponseEntity<ResponseBody> postItem(@RequestBody ItemPostRequestDTO requestDTO,
			HttpServletRequest request) {
		LOGGER.info("User post item");

		Integer userId = 1;
		itemService.post(userId, requestDTO);
		return ResponseEntity
				.ok(getResponseBody(HttpStatus.OK.value(), "Successfully post item", request.getRequestURI()));

	}

	@ApiOperation(value = "Reply another item", response = ResponseEntity.class)
//	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer <access_token>")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully post item"),
//			@ApiResponse(code = 401, message = "Full authentication is required to access this resource"),
//			@ApiResponse(code = 400, message = "Error: "),
//			@ApiResponse(code = 404, message = "User not found with id") })
	@PostMapping(value = "/reply")
	public ResponseEntity<ResponseBody> replyItem(@RequestBody ItemPostRequestDTO requestDTO,
			HttpServletRequest request) {
		LOGGER.info("User reply another item");

		Integer userId = 1;
		itemService.reply(userId, requestDTO);
		return ResponseEntity
				.ok(getResponseBody(HttpStatus.OK.value(), "Successfully reply item", request.getRequestURI()));

	}

	@ApiOperation(value = "Find your items", response = ResponseEntity.class)
//	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer <access_token>")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully post item"),
//			@ApiResponse(code = 401, message = "Full authentication is required to access this resource"),
//			@ApiResponse(code = 400, message = "Error: "),
//			@ApiResponse(code = 404, message = "User not found with id") })
	@GetMapping(value = "/find-my-items")
	public ResponseEntity<ResponseBody> findMyItems(@RequestParam(defaultValue = "0") Integer pageNumber,
			@RequestParam(defaultValue = "10") Integer pageSize,
			@RequestParam(defaultValue = "createdDate") String sortBy,
			@ApiParam(value = "input ASC or DESC") @RequestParam(defaultValue = "DESC") String direction,
			HttpServletRequest request) {
		LOGGER.info("User find their item");

		Integer userId = 1;
		List<ItemResponseDTO> responseDTO = itemService.findByUserId(Arrays.asList(userId), pageNumber, pageSize,
				sortBy, direction);
		return ResponseEntity.ok(getResponseBody(HttpStatus.OK.value(), responseDTO, request.getRequestURI()));

	}

	@ApiOperation(value = "Find your timeline items", response = ResponseEntity.class)
//	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer <access_token>")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully post item"),
//			@ApiResponse(code = 401, message = "Full authentication is required to access this resource"),
//			@ApiResponse(code = 400, message = "Error: "),
//			@ApiResponse(code = 404, message = "User not found with id") })
	@GetMapping(value = "/find-my-timeline")
	public ResponseEntity<ResponseBody> findMyTimeline(@RequestParam(defaultValue = "0") Integer pageNumber,
			@RequestParam(defaultValue = "10") Integer pageSize,
			@RequestParam(defaultValue = "createdDate") String sortBy,
			@ApiParam(value = "input ASC or DESC") @RequestParam(defaultValue = "DESC") String direction,
			HttpServletRequest request) {
		LOGGER.info("User find their timeline items");

		Integer userId = 1;
		List<ItemResponseDTO> responseDTO = itemService.findFollowedItem(userId, pageNumber, pageSize, sortBy,
				direction);
		return ResponseEntity.ok(getResponseBody(HttpStatus.OK.value(), responseDTO, request.getRequestURI()));

	}
}
