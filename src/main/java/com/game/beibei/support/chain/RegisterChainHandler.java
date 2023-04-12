package com.game.beibei.support.chain;

import com.game.beibei.model.SpecialModel;

public interface RegisterChainHandler extends StandardChainHandler {

	boolean doHandler(SpecialModel sm);
	
}
