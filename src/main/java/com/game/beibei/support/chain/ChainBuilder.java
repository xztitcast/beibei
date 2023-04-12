package com.game.beibei.support.chain;

public class ChainBuilder<T extends StandardChainHandler> {

	private T head;
	
	private T tail;
	
	public ChainBuilder<T> addHandler(T handler) {
		if(this.head == null) {
			this.head = this.tail = handler;
			return this;
		}
		this.tail.setNextHandler(handler);
		this.tail = handler;
		return this;
	}
	
	public T build() {
		return this.head;
	}
}
