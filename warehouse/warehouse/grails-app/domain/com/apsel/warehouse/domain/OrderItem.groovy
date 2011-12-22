package com.apsel.warehouse.domain

import com.apsel.warehouse.vo.Price;

class OrderItem {
	String name;
	Price price;
	Integer itemCount;
	
    static constraints = {
		name blank:false;
		price blank:false;
		itemCount blank:false, min:1;
    }
	static embedded = ['price'];
}
