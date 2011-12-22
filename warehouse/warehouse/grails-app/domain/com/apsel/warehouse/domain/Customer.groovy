package com.apsel.warehouse.domain

class Customer {
	String name;
	Address address;
	
	static hasMany = [orders:ProductOrder]
	
    static constraints = {
		name blank:false;
		address nullable:false;
    }	
}
