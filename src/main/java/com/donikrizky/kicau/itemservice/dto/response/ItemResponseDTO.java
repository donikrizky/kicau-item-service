package com.donikrizky.kicau.itemservice.dto.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponseDTO {

	private Integer itemId;
	private String username;
	private String comment;
	private Integer parentItemId;
	private Date createdDate;
	private Long favoriteCount;

	public ItemResponseDTO(Integer itemId, String comment, Integer parentItemId, Date createdDate) {
		this.itemId = itemId;
		this.comment = comment;
		this.parentItemId = parentItemId;
		this.createdDate = createdDate;
	}

}
