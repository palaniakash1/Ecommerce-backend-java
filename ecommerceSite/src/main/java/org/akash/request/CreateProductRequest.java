package org.akash.request;

import java.util.HashSet;
import java.util.Set;

import org.akash.model.Size;

import lombok.Data;


@Data
public class CreateProductRequest {

	
	private String title;
	
	private String description;
	
	private int price;
	
	private int discountedPrice;
	
	private int discountedPercent;
	
	private int quanitiy;
	
	private String brand;
	
	private String color;
	
	private Set<Size> size = new HashSet<>();
	
	private String imageUrl;
	
	private String firstLevelCategory;
	
	private String secondLevelCategory;
	
	private String thirdLevelCategory;
	
}
