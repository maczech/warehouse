package com.apsel.warehouse.domain

class Person extends Customer{
	
	String surname;
	
	
    static constraints = {
		
		surname blank:false;
		
    }
}
