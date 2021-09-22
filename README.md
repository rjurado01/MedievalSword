MedievalSword
=============

Turn-based strategy game for Final University Project.

The gameplay is divided into two parts, tactical overland exploration and a turn based combat system.
The player creates an army by spending resources at one of the eight town types in the game.
The hero will progress in experience by engaging in combat with enemy heroes and monsters.
The conditions for victory vary depending on the map, including conquest of all enemies and towns,
collection of a certain amount of a resource, or finding the grail artifact.

Blog (spanish): http://medievalswordgame.wordpress.com

## Intallation

Download the repository and import the projects from Eclipse.

Right click the desktop project, Run As -> Java Application. Select the desktop starter class (e.g. Main.java).

## Requirements

* Eclipse
* OpenJDK

### Libreries

* [libgdx](https://github.com/libgdx/libgdx/)
* [tween-engine](https://github.com/dorkbox/TweenEngine)
* [gson](https://code.google.com/p/google-gson/)


## Directories

### my-gdx-game

Includes all the project code. It is divided into multiple packages:

* **com.level**: load levels and load/save the game.
* **com.modules.battle**: load and controller the battle screen.
* **com.modules.map**: load and controller the map screen.
* **com.mygdxgame**: initialize the game and contains general classes.
* **com.races.humands.heroes**: humands heroes specifications.
* **com.races.humands.units**: humands units specifications.
* **com.resources**: contains the map builds resources especification.
* **com.utils**: different utilities.

### my-gdx-game-desktop

Includes all the assets and the starter class to run the application on the desktop.

## License

Medieval Sword is released under the [GPL3 License](http://www.gnu.org/licenses/gpl-3.0.html).
