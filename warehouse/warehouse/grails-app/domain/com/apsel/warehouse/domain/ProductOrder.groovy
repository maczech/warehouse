package com.apsel.warehouse.domain

import com.apsel.warehouse.vo.Price;

class ProductOrder {
	
	
	BigDecimal quantity=0.0;
	def orderer;
	def items=[]

	static hasMany = [items : OrderItem]
    
	
	def addItem={product,amount->
		def item = new OrderItem()

		item.name  = product.name;
		item.price = product.price;
		item.amount = amount;
		items+=item;

	}
	
	
	static constraints = {
		
    }
	
	def beforeInsert={
		quantity = calculateQuantity();	
		
	}
	
	def beforeUpdate={
		quantity = calculateQuantity();
	}
	
	def calculateQuantity(){
		def quantity=0.0;
		
		items.each { item->
			quantity += (item.price.value * item.amount);	
			
		}
		
		return quantity;
		
			
	}
}
