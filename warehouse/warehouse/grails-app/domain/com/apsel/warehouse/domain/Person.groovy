package com.apsel.warehouse.domain

class Person{
	String name;
	String surname;
	Address address;
	
    static constraints = {
		name blank:false;
		surname blank:false;
		address nullable:false;
    }
}
