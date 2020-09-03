package com.donikrizky.kicau.itemservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ItemPostRequestDTO {

	@Builder.Default
	private Integer parentItemId = 0;
	private String comment;
}
