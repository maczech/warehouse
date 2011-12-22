package com.apsel.warehouse.domain

import com.apsel.warehouse.vo.Price
import grails.plugin.spock.UnitSpec;
import grails.test.*
import grails.validation.ValidationException;


class OrderItemSpec extends UnitSpec {
	def "should be persisted"(){
		setup:
		mockDomain(OrderItem);
		
		when:
		new OrderItem(name:name, price:price, itemCount:count).save(failOnError:true);
		
		then:
		OrderItem.list().size()==1;
		
		where:
		name="węgiel";
		price = new Price(100);
		count = 1;	
	}
	
	def "should be deleted"(){
		setup:
		mockDomain(OrderItem);
		def orderItem = new OrderItem(name:name, price:price,itemCount:count).save(failOnError:true);
		
		when:
		orderItem.delete();
		
		then:
		OrderItem.list().size()==0;
		and:
		OrderItem.get(orderItem.id)==null
		
		where:
		name="węgiel";
		price = new Price(100);
		count = 1;
			
	}
	def "should be same name and id after modified"(){
		setup:
		mockDomain(OrderItem);
		def orderItem = new OrderItem(name:name, price:price,itemCount:count).save(failOnError:true);
		
		when:
		orderItem.name=newName;
		orderItem.save();
		
		then:
		OrderItem.list().size()==1
		and:
		def newOrderItem = OrderItem.findByName(newName)
		assert newOrderItem.id==orderItem.id;
		assert newOrderItem.name==orderItem.name
		
		where:
		name="węgiel";
		newName = "muł"
		price = new Price(100);
		count = 1;
		
	}
	
	def "should throw exception when name is null"(){
		setup:
		mockDomain(OrderItem)
		
		when:
		new OrderItem(price:price).save(failOnError:true);
		
		then:
		def e = thrown(ValidationException)
		
		where:
		price = new Price(100);
		
	}
	
	def "should throw exception when name is empty"(){
		setup:
		mockDomain(OrderItem)
		
		when:
		new OrderItem(name:name,price:price).save(failOnError:true);
		
		then:
		def e = thrown(ValidationException)
		
		where:
		name=""
		price = new Price(100);
		
	}
	
	def "should throw exception when price is null"(){
		setup:
		mockDomain(OrderItem)
		
		when:
		new OrderItem(name:name,itemCount:count).save(failOnError:true);
		
		then:
		def e = thrown(ValidationException)
		
		where:
		name="węgiel"
		count=1
		
		
	}
	
	def "should throw exception when count is null"(){
		setup:
		mockDomain(OrderItem);
		
		when:
		new OrderItem(name:name, price:price).save(failOnError:true);
		
		then:
		def e = thrown(ValidationException)
		
		where:
		where:
		name="węgiel";
		price = new Price(100);
	}
	
	def "should throw exception when count is 0"(){
		setup:
		mockDomain(OrderItem);
		
		when:
		new OrderItem(name:name, price:price,itemCount:count).save(failOnError:true);
		
		then:
		def e = thrown(ValidationException)
		
		where:
		where:
		name="węgiel";
		price = new Price(100);
		count=0;
	}
   
}
