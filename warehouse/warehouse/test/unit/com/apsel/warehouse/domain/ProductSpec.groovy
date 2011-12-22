package com.apsel.warehouse.domain

import com.apsel.warehouse.vo.Price
import grails.plugin.spock.UnitSpec;
import grails.test.*
import grails.validation.ValidationException;


class ProductSpec extends UnitSpec {
  def "should be persisted"(){
	setup:
	mockDomain(Product)
	
	when:
	new Product(name:name,price:price).save(failOnError:true);
	
	then:
	Product.list().size()==1
	
	where:
	name = "wegiel"
	price = new Price(100);
	  
  }
  
 def "should be deleted"(){
		setup:
		mockDomain(Product);
		def product = new Product(name:name, price:price).save(failOnError:true);
		
		when:
		product.delete();
		
		then:
		product.list().size()==0;
		and:
		Product.get(product.id)==null
		
		where:
		name="węgiel";
		price = new Price(100);
		
			
	}
 
 def "should be same name and id after modified"(){
	 setup:
	 mockDomain(Product);
	 def product = new Product(name:name, price:price).save(failOnError:true);
	 
	 when:
	 product.name=newName;
	 product.save();
	 
	 then:
	 Product.list().size()==1
	 and:
	 def newProduct = Product.findByName(newName)
	 assert newProduct.id==product.id;
	 assert newProduct.name==product.name
	 
	 where:
	 name="węgiel";
	 newName = "muł"
	 price = new Price(100);
 
 }
 
 def "should throw exception when name is null"(){
	 setup:
	 mockDomain(Product)
	 
	 when:
	 new Product(price:price).save(failOnError:true);
	 
	 then:
	 def e = thrown(ValidationException)
	 
	 where:
	 price = new Price(100);
	 
 }
 
 def "should throw exception when name is empty"(){
	 setup:
	 mockDomain(Product)
	 
	 when:
	 new Product(name:name,price:price).save(failOnError:true);
	 
	 then:
	 def e = thrown(ValidationException)
	 
	 where:
	 name=""
	 price = new Price(100);
	 
 }
 
 def "should throw exception when price is null"(){
	 setup:
	 mockDomain(Product)
	 
	 when:
	 new Product(name:name).save(failOnError:true);
	 
	 then:
	 def e = thrown(ValidationException)
	 
	 where:
	 name="wegiel"

	 
 }
}
