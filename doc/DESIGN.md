DESIGN.md
This file can be completed when the analysis is submitted and any part of it can be used within your analysis as appropriate.

## High-Level Design Goals

Our program will allow the user to create any game of the genre '2D Scrolling Platformer'. 

At a high level there are three parts of the project: Game Player, Game Engine, and the Game Authoring Environment. 

The Game Authoring Environment will be used by the user to create a game, and then this game will be run in the Game Player. Engine will render the game. 

We intend to create 3 modules to subdivide the project. These modules will include: Game Player, Game Engine, and Authoring Environment. Functionally, these represent: a home environment where users can navigate to the games they'd like to play or settings they'd like to configure, the component that controls game play, and the environment where users can create games using pre-constructed tools.

All modules will need to communicate with each other, but methods for this will vary. The Authoring Environment will need to communicate via and API with Game Engine to present the functionality of the application to the user, and will also store game configurations in XML files that are loaded by both the Game Engine and Game Player. The Game Player will communicate with the Game Engine via an API so that it can have control over important in-game options such as pausing, playing, saving, but most importantly receiving the state of the game to display on the screen. The state will include any information relevant to the Heads Up Display, in addition to positions of all entities on the screen. The Engine's API consists of all of the functions mentioned above. It's primary goal is to share functionality with the other modules, in addition to sharing some details about the state of the game with the Game Player.

## How to Add New Features

#### Adding new screens/popups to the Authoring Environment
New screens can be added by creating a class that inherits from the abstract Screen class, which contains basic formatting information for Screens in the GAE.
New popups can also inherit from the GamePopup or LibraryPopup class that contains formatting information for popups. To display the popups/screens they must either be referenced in a lambda in either the ScreenManager or some Controller class. 

### Game Features
#### Adding new default properties to entities
Each entity in the game is packaged with several properties when created. These properties can be modified  and they include x and y position, velocity, acceleration. The game authoring environment reads these properties from the game engine so that GAE code can remain closed while the game engine implements these properties. Examples of additional properties could include a sound property or image property. 

#### Add new conditions and actions
All logics in our game are driven by conditions and actions. That is if certain conditions are met, certain actions want to be taken on some entities in the game. This is pretty powerful. Should we want expansion, such as checking different types of conditions or carry out more complex actions, we would just add those conditions or actions. However, to support these additions, it might take some structural changes with entities or tweaks on the subengine components, such as conditionchecker or action engine. Of course there might be some additional dependencies, so we would also have to declare and set up dependencies in the game environment. But that's it. All conditions and actions inherit an abstract class so there's really not too much more changes to make outside these components given our encapsulation.

## Major Design choices

### Game Player
The Game Player module is rooted around the navigation screens. Those are the top level entities that use methods defined in other classes to provide functionality for playing the game, displaying instructions or descriptions of the game in addition to other features such as accounts, messaging, and friends. The screens use a hierarchy of inheritance where a super class called SceneDisplay extends out to each one of the navigation screens that contains methods common to each one of them. Diving deeper into the how the screens are managed, all of the navigation is controlled by a ScreenManager class which contains all of the methods that define how to switch between screens. This process occurs by passing lambdas between the screen classes so that each one has a link to the other screen classes it has to switch to when the user interacts with navigation buttons. This allowed the design to remain encapsulated by keeping each screen contained within its own class and by limiting the dependencies between any of the
screen classes. Outside of the screen management, there are some extra features such as the implementation of accounts and a database to manage account data and data for other features that are separated into their own packages and use their own inheritance hierarchy. The database classes, for instance, have a simple hierarchy where database functionality was broken down into classes that define database access specifically depending on the subject of the data (for instance there is one class that specifically manages access to the database whenever it pertains to game stats). Each one of these classes inherits from a super class that defines important methods necessary for each of the sub classes to work. The two main methods are used for sending basic data retrieval queries and basic data modification queries. 


### Game Engine

On a high level, the engine code is split into the `makingGame` and `playingGame` packages, handling the code concerning authoring and playing respectively. In `makingGame`, we create `Entity`s, `EventPackage`s, `Condition`s, and `Action`s. This is all held in `EntityManager`, which is what we serialize. In `playingGame`, the `EntityManager` is loaded, and then the `Instance` are derived and their `EventPackage`s evaluated. The respective checkers (engines) will evaluate these conditions and then tell its associated actions to execute.

The power vs simplicity trade-off was a key concern for us from the beginning. We were initially focused on power, since a lot of game engines have capabilities of creating complex games. Therefore we designed our engine in a way in which we can support a wide variety of games.

After implementing our API, we realized that our API was not as friendly as we would have liked it to be. We then added presets. These give the character default moving and jumping capabilities. By adding presets, we make the API more simple and easier to use when creating a game.

### Game Authoring Environment
The `GameAuthoring` module consists of 3 main screens,  the `GameViewScreen`, `LevelViewScreen`, and the `MapViewScreen`. Each of these screens extend the abstract Screen class, which contains basic javafx formatting information and methods for setting and getting Screen components. Each of these Screens also have a Controller - the `GameViewContoller`, `LevelViewController`, and `MapViewController`, which contain lambdas that link the respective Screens to their associated popups. Each Controller also contains calls to the `GameCreator`, which implements the `GameEngine` API,  separating the frontend-end implementation in the Screens from the backend.

The switching between 3 Screens is controlled by the `ScreenManager`, which has access to the `Controller` for each of the Screens, and calls on the associated Controller to display the desired Screen. The use of the `ScreenManager` provides a higher-level organization of the GameAuthoring content.

The `Utilities` package contains templates for visual components, such as the `LibraryPopup`, `GamePopup`, and `GameTab` abstract superclasses. These superclasses have formatting information, methods for creating components, and may pass in a Controller that allows these visual components to link to other popups or make backend changes.


## Assumptions or Decisions Made to Simplify or Resolve Ambiguities in Project Functionality

### Game features
We are only making games that can be modelled with our existent conditions and actions, which is already very powerful given the number of combinations we can have. But we can also addition conditions and actions to make more advanced games.

### Scrolling
Our game only does horizontal scrolling. This allows the buffer structure of active instances to be easier. There's a right buffer and left buffer that are implemented as priority queues and hold entities that are a little far from the current frame. Only entity instances that are within certain ranges of the current frame will be updated. This is pretty scalable, and allows us to have a large map. 

It's not impossible to implement up and down buffer and do vertical scrolling. But the 2D structure of the array might be hard to manage and definitely will have higher overhead.

### Entity vs entity instance
Having a distinction between `Entity` and `Instance` makes game creation simpler. As a game author, it makes sense to see your entities and then drag the one you want on screen (then it becomes an instance). So this design aligned with our authoring environment. It also simplifies `EventPackage` creation. For example, all of the bricks on screen share the same `EventPackage`s--nothing can go through it. It would be tedious to give these `EventPackage`s to every single brick on screen.

The case for only having `Instance` is that we know that in essence, everything on screen is different and that they will all have slightly different properties. `GameAuthoring` could easily assign the same `EventPackage`s to a group of `Instance`s. This meant that `GameEngine` treats every individual `Instance` differently, which is what happens anyways.

### Unchanging Screen Size
The Player module made some assumptions such as the screen size when locating objects on the screen. This made it simple to declare constants up at the top of the screen to decide where things would be placed rather than placing them dynamically. This does mean that the screen looks odd and titles and buttons become misplaced when the screen is resized but the functionality did not change.

### Database existence
The Player module made another assumption when implementing the database as part of the code, which was simply that one exists and is set up to interface with the JDBC driver. Without this the code will still run because of how our error handling is set up, but no information will be retrieved and the screens will often look blank when using any of the features that employ a database.