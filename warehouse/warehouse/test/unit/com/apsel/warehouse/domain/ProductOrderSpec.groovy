package com.apsel.warehouse.domain

import com.apsel.warehouse.vo.Price
import grails.plugin.spock.UnitSpec;
import grails.test.*


class ProductOrderSpec extends UnitSpec {
	def "should be created for person"(){
		setup:
		mockDomain(ProductOrder)
		mockDomain(Person)
		mockDomain(Address)
		
		when:
		def order = new ProductOrder(orderer:person).save(failOnError:true);
		
		then:
		ProductOrder.list().size()==1;
		and:
		def newOrder = ProductOrder.get(order.id);
		assert newOrder !=null
		assert newOrder.orderer.name == "marcin"
		
		where:
		address=new Address(street:"topolowa",city:"radziechowy",houseNumber:57);
		person = new Person(name:"marcin",surname:"czech", address:address);	
	}
	
	def "should be created for company"(){
		setup:
		mockDomain(ProductOrder)
		mockDomain(Company)
		mockDomain(Address)
		
		when:
		def order = new ProductOrder(orderer:company,quantity:1.0).save(failOnError:true);
		
		then:
		ProductOrder.list().size()==1;
		and:
		def newOrder = ProductOrder.get(order.id);
		assert newOrder !=null
		assert newOrder.orderer.name == "mtm"
		
		where:
		address=new Address(street:"topolowa",city:"radziechowy",houseNumber:57);
		company = new Company(name:"mtm", address:address);
	}
	
	def "should be created both person and company same time"(){
		setup:
		mockDomain(ProductOrder)
		mockDomain(Company)
		mockDomain(Address)
		mockDomain(Person)
		
		when:
		def companyOrder = new ProductOrder(orderer:company).save(failOnError:true);
		def personOrder = new ProductOrder(orderer:person).save(failOnError:true);
		
		then:
		ProductOrder.list().size()==2;
		
		and:
		def newCompanyOrder = ProductOrder.get(companyOrder.id);
		assert newCompanyOrder !=null
		assert newCompanyOrder.orderer.name == "mtm"
		
		and:
		def newPersonOrder = ProductOrder.get(personOrder.id);
		assert newPersonOrder !=null
		assert newPersonOrder.orderer.name == "marcin"
		
		where:
		address=new Address(street:"topolowa",city:"radziechowy",houseNumber:57);
		company = new Company(name:"mtm", address:address);
		person = new Person(name:"marcin",surname:"czech", address:address);
		
	}
	
	def "should be quantity two point zero"(){
		setup:
		mockDomain(ProductOrder)
		mockDomain(OrderItem)
		mockDomain(Company)
		mockDomain(Address)
		def companyOrder = new ProductOrder(orderer:company).save(failOnError:true);
		
		when:"add new order item"
		companyOrder.items+=orderItem
		and:"updated order"
		companyOrder.save(failOnError:true)
		
		then:
		assert companyOrder.quantity == 2.0;
		and:"after restore"
		def newCompanyOrder = ProductOrder.get(companyOrder.id);
		assert newCompanyOrder
		assert newCompanyOrder.quantity==2.0
		
		where:
		orderItem = new OrderItem(name:"węgiel", price:new Price(1.0), itemCount:2);
		address=new Address(street:"topolowa",city:"radziechowy",houseNumber:57);
		company = new Company(name:"mtm", address:address);
		
	}

}
