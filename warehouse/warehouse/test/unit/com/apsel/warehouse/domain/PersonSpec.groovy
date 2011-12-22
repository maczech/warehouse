package com.apsel.warehouse.domain

import grails.plugin.spock.UnitSpec;
import grails.test.*
import grails.validation.ValidationException;

class PersonSpec extends UnitSpec {
	def "should be persisted"(){
		setup:
		mockDomain(Person);
		mockDomain(Address);
		
		when:
		new Person(name:name,surname:surname, address:address).save(failOnError:true);
		
		then:
		Person.list().size()==1;
		
		where:
		name="marcin";
		surname="czech";
		address=new Address(street:"topolowa",city:"radziechowy",houseNumber:57);
		
	}
	def "should be deleted"(){
		setup:
		mockDomain(Person);
		mockDomain(Address);
		def person = new Person(name:name,surname:surname, address:address).save(failOnError:true);
		
		when:
		person.delete();
		
		then:
		Person.list().size()==0;
		and:
		Person.get(person.id)==null;
		
		where:
		name="marcin";
		surname="czech";
		address=new Address(street:"topolowa",city:"radziechowy",houseNumber:57);
	}
	
	def "should be same name and id after modified"(){
		setup:
		mockDomain(Person);
		mockDomain(Address);
		def person = new Person(name:name,surname:surname, address:address).save(failOnError:true);
		
		when:"new name set"
		person.name=newName;
		and:"person updated"
		person.save(failOnError:true);
		
		then:"still only one person"
		Person.list().size()==1;
		
		and:"new name stored"
		def newPerson = Person.findByName(newName);
		assert newPerson!=null;
		
		and:"id not changed"
		assert person.id==newPerson.id;
		
		where:
		newName="anna"
		name="marcin";
		surname="czech";
		address=new Address(street:"topolowa",city:"radziechowy",houseNumber:57);
		
	}
	
	def "should throw exception when name is null"(){
		setup:
		mockDomain(Person);
		mockDomain(Address);
		
		when:
		new Person(address:address,surname:surname).save(failOnError:true);
		
		then:
		def e = thrown(ValidationException);
		
		where:
		surname="Czech";
		address=new Address(street:"topolowa",city:"radziechowy",houseNumber:57);
		
	}
	def "should throw exception when name is empty"(){
		setup:
		mockDomain(Person);
		mockDomain(Address);
		
		when:
		new Person(name:name,surname:surname, address:address).save(failOnError:true);
		
		then:
		def e = thrown(ValidationException);
		
		where:
		name="";
		surname="Czech";
		address=new Address(street:"topolowa",city:"radziechowy",houseNumber:57);
	}
	
	def "should throw exception when surname is null"(){
		setup:
		mockDomain(Person);
		mockDomain(Address);
		
		when:
		new Person(name:name,address:address).save(failOnError:true);
		
		then:
		def e = thrown(ValidationException);
		
		where:
		name="marcin"
		
		address=new Address(street:"topolowa",city:"radziechowy",houseNumber:57);
		
	}
	def "should throw exception when surname is empty"(){
		setup:
		mockDomain(Person);
		mockDomain(Address);
		
		when:
		new Person(name:name,surname:surname, address:address).save(failOnError:true);
		
		then:
		def e = thrown(ValidationException);
		
		where:
		name="marcin";
		surname="";
		address=new Address(street:"topolowa",city:"radziechowy",houseNumber:57);
	}
	
	def "should throw exception when adress is null"(){
		setup:
		mockDomain(Person);
		mockDomain(Address);
		
		when:
		new Person(name:name,surname:surname).save(failOnError:true);
		
		then:
		def e = thrown(ValidationException);
		
		where:
		name="marcin"
		surname="Czech";
		
		
	}
	
	def "should return orders by person"(){
		setup:
		mockDomain(Address);
		mockDomain(ProductOrder)
		mockDomain(Person)
		mockDomain(Customer)
		
		when:"new person created"
		def person = new Person(name:name,surname:surname, address:address).save(failOnError:true);
		and:"new order for person created"
		def order = new ProductOrder(orderer:person).save(failOnError:true);
		
		then:"get person by name"
		def newPerson = Customer.get(person.id);
		newPerson?.orders?.size()==1
		def newOrder = ProductOrder.findByOrderer(newPerson);
		
		
		
		where:
		name="marcin";
		surname="czech";
		address=new Address(street:"topolowa",city:"radziechowy",houseNumber:57);
		
	}
}
