package org.akash.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddItemRequest {

	
	private long productId;
	
	private String size;
	
	private int quantity;
	
	private Integer price;
	
	
}
