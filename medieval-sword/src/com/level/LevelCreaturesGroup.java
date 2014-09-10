package com.level;

import java.util.List;

import com.utils.Vector2i;

/**
 * This class is used to save/load creatures group information in JSON format
 */
public class LevelCreaturesGroup {
  List<LevelStack> stacks;
  Vector2i square_number;
  int type;
}
