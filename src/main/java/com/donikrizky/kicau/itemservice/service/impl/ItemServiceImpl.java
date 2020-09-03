package com.donikrizky.kicau.itemservice.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.donikrizky.kicau.itemservice.dto.request.ItemPostRequestDTO;
import com.donikrizky.kicau.itemservice.dto.response.ItemResponseDTO;
import com.donikrizky.kicau.itemservice.entity.Item;
import com.donikrizky.kicau.itemservice.exception.BadRequestException;
import com.donikrizky.kicau.itemservice.feign.AuthFeignClient;
import com.donikrizky.kicau.itemservice.feign.MutualFeignClient;
import com.donikrizky.kicau.itemservice.repository.ItemRepository;
import com.donikrizky.kicau.itemservice.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	private ItemRepository itemRepository;
	private MutualFeignClient mutualFeignClient;
	private AuthFeignClient authFeignClient;

	@Autowired
	ItemServiceImpl(ItemRepository itemRepository, AuthFeignClient authFeignClient,
			MutualFeignClient mutualFeignClient) {
		this.itemRepository = itemRepository;
		this.authFeignClient = authFeignClient;
		this.mutualFeignClient = mutualFeignClient;
	}

	private static final String ERROR_SORT_DIRECTION = "Error: Can only input ASC or DESC for direction!";
	private static final String ERROR_SORTBY = "Error: Can only input createdDate for sortBy!";

	Predicate<String> sortByCreatedDate = s -> s.equals("createdDate");

	@Override
	public void post(Integer userId, ItemPostRequestDTO requestDTO) {
		Item item = Item.builder().userId(userId).comment(requestDTO.getComment()).build();
		itemRepository.save(item);

	}

	@Override
	public void reply(Integer userId, ItemPostRequestDTO requestDTO) {
		Item item = Item.builder().userId(userId).parentItemId(requestDTO.getParentItemId())
				.comment(requestDTO.getComment()).build();
		itemRepository.save(item);

	}

	@Override
	public List<ItemResponseDTO> findByUserId(String header, List<Integer> userId, String username, Integer pageNumber,
			Integer pageSize, String sortBy, String direction) {

		Pageable paging = this.itemValidation(pageNumber, pageSize, sortBy, direction);
		List<ItemResponseDTO> response = new ArrayList<ItemResponseDTO>();
		itemRepository.findItemByUserId(userId, paging).toList().forEach(item -> {
			item.setUsername(username);
			item.setFavoriteCount(mutualFeignClient.findFavoriteCount(header, item.getItemId()));
			response.add(item);
		});
		return response;
	}

	@Override
	public List<ItemResponseDTO> findFollowedItem(String header, Integer userId, Integer pageNumber, Integer pageSize,
			String sortBy, String direction) {
		List<Integer> followedId = mutualFeignClient.findFollowed(header, userId);

		Pageable paging = this.itemValidation(pageNumber, pageSize, sortBy, direction);
		List<ItemResponseDTO> response = new ArrayList<ItemResponseDTO>();
		itemRepository.findItemByUserId(followedId, paging).toList().forEach(item -> {
			item.setUsername(authFeignClient.findUsernameById(header, userId));
			item.setFavoriteCount(mutualFeignClient.findFavoriteCount(header, item.getItemId()));
			response.add(item);
		});
		return response;
	}

	private Pageable itemValidation(Integer pageNumber, Integer pageSize, String sortBy, String direction) {
		if (!sortByCreatedDate.test(sortBy)) {
			throw new BadRequestException(ERROR_SORTBY);
		}

		Pageable paging;
		if (direction.equalsIgnoreCase("DESC")) {
			paging = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());

		} else if (direction.equalsIgnoreCase("ASC")) {
			paging = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());

		} else {
			throw new BadRequestException(ERROR_SORT_DIRECTION);
		}
		return paging;
	}

}
