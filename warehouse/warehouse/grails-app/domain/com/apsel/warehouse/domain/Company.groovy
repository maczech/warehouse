package com.apsel.warehouse.domain

class Company {
	String name;
	Address address;
	
    static constraints = {
		name blank:false;
		address nullable:false;
    }
}
