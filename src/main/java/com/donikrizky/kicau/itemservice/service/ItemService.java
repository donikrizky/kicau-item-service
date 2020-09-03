package com.donikrizky.kicau.itemservice.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.donikrizky.kicau.itemservice.dto.request.ItemPostRequestDTO;
import com.donikrizky.kicau.itemservice.dto.response.ItemResponseDTO;

public interface ItemService {

	public void post(Integer userId, ItemPostRequestDTO requestDTO);

	public void reply(Integer userId, ItemPostRequestDTO requestDTO);

	public Page<ItemResponseDTO> findByUserId(List<Integer> userId, Integer pageNumber, Integer pageSize, String sortBy,
			String direction);

	Page<ItemResponseDTO> findFollowedItem(Integer userId, Integer pageNumber, Integer pageSize, String sortBy,
			String direction);

}
