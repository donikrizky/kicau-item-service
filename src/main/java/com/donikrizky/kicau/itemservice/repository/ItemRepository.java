package com.donikrizky.kicau.itemservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.donikrizky.kicau.itemservice.dto.response.ItemResponseDTO;
import com.donikrizky.kicau.itemservice.entity.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

	@Query("SELECT new com.donikrizky.kicau.itemservice.dto.response.ItemResponseDTO "
			+ "(i.comment, i.parentItemId) from Item i WHERE i.userId = :userId AND deleted = false")
	public Page<ItemResponseDTO> findItemByUserId(@Param("userId") Integer userId, Pageable paging);
}
