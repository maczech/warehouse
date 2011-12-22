package com.apsel.warehouse.domain

import org.spockframework.compiler.model.SetupBlock;

import grails.plugin.spock.UnitSpec;
import grails.test.*
import grails.validation.ValidationException;

class AddressSpec extends UnitSpec {

	def  "should be persisted"(){
		setup:
		mockDomain(Address);
		
		when:
		new Address(street:street,city:city,houseNumber:houseNumber,apartmentNumber:apartmentNumber).save(failOnError: true);
		
		then:
		Address.list().size()==1;
		
		where:
		street = "jasna";
		city = "radziechowy";
		houseNumber = 876;
		apartmentNumber = 144;
		
		
	}
	
	def "should be persisted with empty apartment"(){
		setup:
		mockDomain(Address);
		
		when:
		new Address(street:street,city:city,houseNumber:houseNumber).save(failOnError: true);
		
		then:
		Address.list().size()==1;
		
		where:
		street = "jasna";
		city = "radziechowy";
		houseNumber = 876;
		apartmentNumber = 144;
	}
	
	def  "should be persisted with empty street"(){
		setup:
		mockDomain(Address);
		
		when:
		new Address(city:city,houseNumber:houseNumber,apartmentNumber:apartmentNumber).save(failOnError: true);
		
		then:
		Address.list().size()==1;
		
		where:
		city = "radziechowy";
		houseNumber = 876;
		apartmentNumber = 144;
		
		
	}
	
	def "should be deleted"(){
		setup:
		mockDomain(Address);
		def address = new Address(street:street,city:city,houseNumber:houseNumber);
		address.save(failOnError: true);
		
		when:
		address.delete();
		
		then:
		Address.list().size()==0;
		and:
		Address.get(address.id)==null;
		
		where:
		street = "jasna";
		city = "radziechowy";
		houseNumber = 876;
		apartmentNumber = 144;
			
		
	} 
	
	def "should be same street and id after modified"(){
		setup:
		mockDomain(Address);
		def address = new Address(street:street,city:city,houseNumber:houseNumber);
		address.save(failOnError: true);
		
		when:"new street set"
		address.street=newStreet;
		and:"address updated"
		address.save(failOnError:true);
		
		then:"still only one address"
		Address.list().size()==1;
		
		and:"new street stored"
		def newAddress = Address.findByStreet(newStreet);
		assert newAddress!=null;
		assert newAddress.street == newStreet
		
		and:"id not changed"
		assert address.id==newAddress.id;
		
		where:
		newStreet = "topolowa";
		street = "jasna";
		city = "radziechowy";
		houseNumber = 876;
		apartmentNumber = 144;
	}
	
	
	def "should throw exception when city is null"(){
		setup:
		mockDomain(Address);
		
		when:
		new Address(street:street,houseNumber:houseNumber).save(failOnError: true);
		
		then:
		def e = thrown(ValidationException);
		
		where:
		street = "jasna";

		houseNumber = 876;
		apartmentNumber = 144;
	}
	
	def "should throw exception when house number is null"(){
		setup:
		mockDomain(Address);
		
		when:
		new Address(street:street,city:city).save(failOnError: true);
		
		then:
		def e = thrown(ValidationException);
		
		where:
		street = "jasna";
		city = "radziechowy";
		
		apartmentNumber = 144;
	}
	
	def "should throw excetion when city is empty"(){
		setup:
		mockDomain(Address);
		
		when:
		new Address(street:street,city:city,houseNumber:houseNumber,apartmentNumber:apartmentNumber).save(failOnError: true);
		
		then:
		def e = thrown(ValidationException);
		
		where:
		street = "jasna";
		city = "";
		houseNumber = 876;
		apartmentNumber = 144;
	}
	def "should throw excetion when houseNumber is zero"(){
		setup:
		mockDomain(Address);
		
		when:
		new Address(street:street,city:city,houseNumber:houseNumber,apartmentNumber:apartmentNumber).save(failOnError: true);
		
		then:
		def e = thrown(ValidationException);
		
		where:
		street = "jasna";
		city = "radziechowy";
		houseNumber = 0;
		apartmentNumber = 144;
	}
	

}
