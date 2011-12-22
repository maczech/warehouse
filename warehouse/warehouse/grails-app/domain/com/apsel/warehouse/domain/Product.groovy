package com.apsel.warehouse.domain

import com.apsel.warehouse.vo.Price

class Product {
	String name
	Price price

    static constraints = {
		name blank:false;
		price blank:false;
    }
}
