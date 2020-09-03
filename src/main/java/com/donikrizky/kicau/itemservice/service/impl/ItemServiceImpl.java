package com.donikrizky.kicau.itemservice.service.impl;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.donikrizky.kicau.itemservice.dto.request.ItemPostRequestDTO;
import com.donikrizky.kicau.itemservice.dto.response.ItemResponseDTO;
import com.donikrizky.kicau.itemservice.entity.Item;
import com.donikrizky.kicau.itemservice.exception.BadRequestException;
import com.donikrizky.kicau.itemservice.feign.UserFeignClient;
import com.donikrizky.kicau.itemservice.repository.ItemRepository;
import com.donikrizky.kicau.itemservice.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	private ItemRepository itemRepository;
	private UserFeignClient userFeignClient;

	@Autowired
	ItemServiceImpl(ItemRepository itemRepository, UserFeignClient userFeignClient) {
		this.itemRepository = itemRepository;
		this.userFeignClient = userFeignClient;
	}

	private static final String ERROR_SORT_DIRECTION = "Error: Can only input ASC or DESC for direction!";
	private static final String ERROR_SORTBY = "Error: Can only input createdDate for sortBy!";

	Predicate<String> sortByCreatedDate = s -> s.equals("createdDate");

	@Override
	public void post(Integer userId, ItemPostRequestDTO requestDTO) {
		Item item = Item.builder().comment(requestDTO.getComment()).build();
		itemRepository.save(item);

	}

	@Override
	public void reply(Integer userId, ItemPostRequestDTO requestDTO) {
		Item item = Item.builder().parentItemId(requestDTO.getParentItemId()).comment(requestDTO.getComment()).build();
		itemRepository.save(item);

	}

	@Override
	public Page<ItemResponseDTO> findByUserId(List<Integer> userId, Integer pageNumber, Integer pageSize, String sortBy,
			String direction) {
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

		return itemRepository.findItemByUserId(userId, paging);
	}

	@Override
	public Page<ItemResponseDTO> findFollowedItem(Integer userId, Integer pageNumber, Integer pageSize, String sortBy,
			String direction) {
		List<Integer> followedId = userFeignClient.findFollowed(userId)
				.orElseThrow(() -> new BadRequestException("Error: You haven't followed anyone"));
		
		return this.findByUserId(followedId, pageNumber, pageSize, sortBy, direction);
	}
}
