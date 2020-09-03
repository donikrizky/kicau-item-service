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
		this.addDummyItem();
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
	public List<ItemResponseDTO> findByUserId(List<Integer> userId, Integer pageNumber, Integer pageSize, String sortBy,
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

		return itemRepository.findItemByUserId(userId, paging).toList();
	}

	@Override
	public List<ItemResponseDTO> findFollowedItem(Integer userId, Integer pageNumber, Integer pageSize, String sortBy,
			String direction) {
		List<Integer> followedId = userFeignClient.findFollowed(userId);

		return this.findByUserId(followedId, pageNumber, pageSize, sortBy, direction);
	}

	private void addDummyItem() {
		List<Item> items = new ArrayList<Item>();
		// userId 2
		items.add(Item.builder().comment("Hari Senin cerah sekali").userId(2).build());
		items.add(Item.builder().comment("Hari Selasa sedikit mendung").userId(2).build());
		items.add(Item.builder().comment("Hari Rabu hujan").userId(2).build());
		items.add(Item.builder().comment("Hari Kamis banjir").userId(2).build());
		items.add(Item.builder().comment("Hari Jumat longsor dimana mana").userId(2).build());

		// userId 3
		items.add(Item.builder().comment("Aku pengen makan ayam").userId(3).build());
		items.add(Item.builder().comment("Aku sudah makan ayam").userId(3).build());
		items.add(Item.builder().comment("Ayamnya jatuh").userId(3).build());
		items.add(Item.builder().comment("Ayamnya keinjek").userId(3).build());
		items.add(Item.builder().comment("Aku sudah tidak punya ayam").userId(3).build());
		
		items.add(Item.builder().comment("Ih wow").userId(1).build());
		items.add(Item.builder().comment("jangan sakit").userId(1).build());
		items.add(Item.builder().comment("punggul gatel").userId(1).build());
		
		itemRepository.saveAll(items);
	}
}
