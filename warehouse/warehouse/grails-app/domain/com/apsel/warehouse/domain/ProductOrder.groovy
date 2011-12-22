package com.apsel.warehouse.domain

class ProductOrder {
	
	
	BigDecimal quantity=0.0;
	def orderer;
	def items=[]

	static hasMany = [items : OrderItem]
    
	
	def addItem={product,amount->
		def item = new OrderItem()
		item.params = product.params
		item.amount = amount
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
			quantity += (item.price.value * item.itemCount);	
			
		}
		
		return quantity;
		
			
	}
}
