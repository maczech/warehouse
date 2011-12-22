package com.apsel.warehouse.domain

import com.apsel.warehouse.vo.Price;

class OrderItem {
	String name;
	Price price;
	Integer amount;
	
    static constraints = {
		name blank:false;
		price blank:false;
		amount blank:false, validator:{
				it>0;
			};
    }
	static embedded = ['price'];
}
