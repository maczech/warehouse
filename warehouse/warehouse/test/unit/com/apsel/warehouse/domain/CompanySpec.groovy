package com.apsel.warehouse.domain

import grails.plugin.spock.UnitSpec;
import grails.test.*
import grails.validation.ValidationException;

class CompanySpec extends UnitSpec{
	def "should be persisted"(){
		setup:
		mockDomain(Company);
		mockDomain(Address);
		
		when:
		new Company(name:name, address:address).save(failOnError:true);
		
		then:
		Company.list().size()==1;
		
		where:
		name="mtm";
		address=new Address(street:"topolowa",city:"radziechowy",houseNumber:57);
		
	}
	def "should be deleted"(){
		setup:
		mockDomain(Company);
		mockDomain(Address);
		def company = new Company(name:name, address:address).save(failOnError:true);
		
		when:
		company.delete();
		
		then:
		Company.list().size()==0;
		and:
		Company.get(company.id)==null;	
		
		where:
		name="mtm";
		address=new Address(street:"topolowa",city:"radziechowy",houseNumber:57);
		
	}  
	def "should be same name and id after modified"(){
		setup:
		mockDomain(Company);
		mockDomain(Address);
		def company = new Company(name:name, address:address).save(failOnError:true);
		
		when:"new name set"
		company.name=newName;
		and:"company updated"
		company.save(failOnError:true);
		
		then:"still only one company"
		Company.list().size()==1;
		
		and:"new name stored"
		def newCompany = Company.findByName(newName);
		assert newCompany!=null;
		
		and:"id not changed"
		assert company.id==newCompany.id;
		
		where:
		newName="mtm2"
		name="mtm";
		address=new Address(street:"topolowa",city:"radziechowy",houseNumber:57);
		
	}
	
	def "should throw exception when name is null"(){
		setup:
		mockDomain(Company);
		mockDomain(Address);
		
		when:
		new Company(address:address).save(failOnError:true);
		
		then:
		def e = thrown(ValidationException);
		
		where:
		address=new Address(street:"topolowa",city:"radziechowy",houseNumber:57);
		
	}
	def "should throw exception when name is empty"(){
		setup:
		mockDomain(Company);
		mockDomain(Address);
		
		when:
		new Company(name:name, address:address).save(failOnError:true);
		
		then:
		def e = thrown(ValidationException);
		
		where:
		name="";
		address=new Address(street:"topolowa",city:"radziechowy",houseNumber:57);
	}
	
}
