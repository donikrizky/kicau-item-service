package com.donikrizky.kicau.itemservice.service;

import java.util.List;

import com.donikrizky.kicau.itemservice.dto.request.ItemPostRequestDTO;
import com.donikrizky.kicau.itemservice.dto.response.ItemResponseDTO;

public interface ItemService {

	public void post(Integer userId, ItemPostRequestDTO requestDTO);

	public void reply(Integer userId, ItemPostRequestDTO requestDTO);

	public List<ItemResponseDTO> findByUserId(String header, List<Integer> userId, String username, Integer pageNumber,
			Integer pageSize, String sortBy, String direction);

	public List<ItemResponseDTO> findFollowedItem(String header, Integer userId, Integer pageNumber, Integer pageSize,
			String sortBy, String direction);

}
