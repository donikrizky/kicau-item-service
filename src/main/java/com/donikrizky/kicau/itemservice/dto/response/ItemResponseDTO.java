package com.donikrizky.kicau.itemservice.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class ItemResponseDTO {

	private String comment;
	private Integer parentItemId;

	public ItemResponseDTO(String comment, Integer parentItemId) {
		this.comment = comment;
		this.parentItemId = parentItemId;
	}

}
