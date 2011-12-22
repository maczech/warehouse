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
		companyOrder.quantity == 2.0;
		and:"after restore"
		def newCompanyOrder = ProductOrder.get(companyOrder.id);
		newCompanyOrder!=null
		newCompanyOrder.quantity==2.0
		
		where:
		orderItem = new OrderItem(name:"węgiel", price:new Price(1.0), amount:2);
		address=new Address(street:"topolowa",city:"radziechowy",houseNumber:57);
		company = new Company(name:"mtm", address:address);
		
	}
	
	def "should be two orders after add"(){
		setup:
		mockDomain(ProductOrder)
		mockDomain(Product)
		ProductOrder order = new ProductOrder()
		Product coalProduct = new Product(name:"węgiel",price:new Price(100))
		Product coalProduct2 = new Product(name:"węgiel2",price:new Price(150));
		
		when:
		order.addItem(coalProduct,1);
		order.addItem(coalProduct2,2);
		
		then:
		order.items.size()==2

	}

}
