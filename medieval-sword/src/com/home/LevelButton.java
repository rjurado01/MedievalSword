package com.home;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.game.Assets;
import com.game.Constants;
import com.utils.Vector2i;

/**
 * This class represents a button of levels window
 */
public class LevelButton extends Group {
  int level;
  Button title_back;
  Label title;

  String titles [] = { "Level", "Nivel" };

  public LevelButton(int lvl, Vector2i position, final HomeScreen screen, int n, boolean active) {
    this.level = lvl;

    title_back = new Button(
        Assets.getTextureRegion("rect"),
        Assets.getTextureRegion("rectBlack"));

    if( active ) {
      title_back = new Button(
          Assets.getTextureRegion("rect"),
          Assets.getTextureRegion("rectBlack"));

      title = new Label(titles[Constants.LANGUAGE_CODE] + " " + n, Assets.font2);
    }
    else {
      title_back = new Button(
          Assets.getTextureRegion("rect"),
          Assets.getTextureRegion("rect"));

      title = new Label("-", Assets.font2);
    }

    title_back.x = ( Constants.SIZE_W - title_back.width ) / 2;
    title_back.y = position.y;

    title.x = title_back.x + (title_back.width - title.width) / 2;
    title.y = title_back.y + 5;

    title_back.setClickListener(new ClickListener() {
      public void click(Actor actor, float x, float y) {
        screen.game.loadNewLevel(level);
      }
    });

    addActor( title_back );
    addActor( title );
  }
}
