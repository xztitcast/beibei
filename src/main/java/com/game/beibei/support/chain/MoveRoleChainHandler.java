package com.game.beibei.support.chain;

import com.game.beibei.model.MoveModel;

public interface MoveRoleChainHandler extends StandardChainHandler {

	boolean doHandler(MoveModel mm);
}
