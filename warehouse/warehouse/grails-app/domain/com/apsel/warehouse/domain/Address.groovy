package com.apsel.warehouse.domain

class Address {
	
	String street;
	String city;
	Integer houseNumber;
	Integer apartmentNumber;
	
    static constraints = {
		
		city blank:false;
		houseNumber blank:false, min:1;

		street nullable:true;
		apartmentNumber nullable:true;
    }
}
