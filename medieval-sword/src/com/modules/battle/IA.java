package com.modules.battle;

import com.game.Stack;

public class IA {
	public IA() {

	}

	public void processTurn( Board board, int battle_side, Stack stack_selected ) {
		System.out.println(
			board.getNextSquare( battle_side, stack_selected.getSquare() ) );
	}
}
