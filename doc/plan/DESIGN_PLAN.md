# VOOGASalad Plan

## Introduction


Our program will allow the user to create any game of the genre '2D Scrolling Platformer'. 

At a high level there are three parts of the project: Game Player, Game Engine, and the Game Authoring Environment. 

The Game Authoring Environment will be used by the user to create a game, and then this game will be run in the Game Player. Engine will render the game. 

## Overview

We intend to create 3 modules to subdivide the project. These modules will include: Game Player, Game Engine, and Authoring Environment. Functionally, these represent: a home environment where users can navigate to the games they'd like to play or settings they'd like to configure, the component that controls game play, and the environment where users can create games using pre-constructed tools. 

All modules will need to communicate with each other, but methods for this will vary. The Authoring Environment will need to communicate via and API with Game Engine to present the functionality of the application to the user, and will also store game configurations in XML files that are loaded by both the Game Engine and Game Player. The Game Player will communicate with the Game Engine via an API so that it can have control over important in-game options such as pausing, playing, saving, but most importantly receiving the state of the game to display on the screen. The state will include any information relevant to the Heads Up Display, in addition to positions of all entities on the screen. The Engine's API consists of all of the functions mentioned above. It's primary goal is to share functionality with the other modules, in addition to sharing some details about the state of the game with the Game Player.


## User Interface

### Game Player
The Game Player will spawn a window where the user can select between a multitude of games that have been created. There will be additional buttons to click on to interact with different features of the Game Player such as account management. After choosing a game a user will be presented with the option to start a new game, load a new one, or view high scores and statistics about the game. 

We plan to implement a hierarchy of classes using polymorphism to manage these menu options. For instance we may implement a game manager class that contains all of the options and methods relevant for interacting with  the selection of available games. We will establish a heirarchy of classes that contain all of the entities in the UI that allow the user to interact with the information about the games. This could include a game-selector, a stats-viewer, or a preferences-manager. All of these may be able to inherit from superclasses or implement interfaces to define core functionality and share relevant information. 

We will also need class heirarchies that manage features like account creation and management. Those can be further broken down into components such as data management for account identification, data management for account statistics (game stats accumulated by a user), and authentication mechanisms. The first two options will need to interact with a database in some way which (prior to significant research) seems it may require its own class to manage the connection to the database and the data transfer.

### Game Authoring Environment
The Game Authoring Environment (GAE) will have a start screen with options to edit a recent project, create a new project, or upload a project. There will also be a help section containing demo authored projects and information on how to use the GAE. 

The GAE will allow the user to view the project from a variety of scopes. In the broadest scope, the user can edit properties of the overall game and view all the created levels (with the option to delete or add new levels). All user-defined properties in the game will be created by selecting a template, such as <String name, double value> for a property with a numerical value, or <String name, boolean value>, for example. This should allow great flexibility for the user to define a variety of properties. 

At the next broadest scope, the user can view level-specific information, such as the scenes within a level and edit level-specific properties. 

In the narrowest scope, the user can view one scene and edit the scene's environment information, which contains properties relating to the physics of the particular scene. 

To add new entities to the scene, there will be a separate window containing a library of all created entities and an option to create new entities. There will be an entity editor for entity name, image, physics properties, and user-defined properties if the user wants to create a new entity, or if the user wants to create a new entity that inherits from an entity superclass. The user can drag a created entity from the library into the desired location in the scene.

There will be an events library containing events relating to game logic, level logic, and entity interactions. There will also be an events editor containing templates of possible event types that will be obtained flexibly from the game engine.

Each event will also be associated with at least one action. User-defined actions are available in the actions library and can be dragged into the actions section in the editor for an event.

There will also be an actions editor that will allow the user to specify the name of the action, choose an abstract action type (implemented in the game engine), assign values to the specific parameter values of the action type (also read in from the game engine), and if desired, add additional actions to form a more complex action. 

## Design Details

### Game Player
The Game Player module will handle the overall control of the program for an individual wanting to play one of the created games. This includes setting up a splash screen where the user can view and choose games, allowing the user to start, stop, and pause the game, giving the user the ability to save games, and allowing users to create and sign in to accounts that can save their personalized stats. Our module will be broken into three modules: general UI, data, and accounts (with potentially more if additional features are added). All classes that handle the display and gneral flow of the program will be located in the general UI package. We can implement an interface with common methods (i.e. makeButton() and loadNewScene()) that can be passed into multiple different classes to reduce dupliicated code. We will also create subclasses for each different component that needs to be displayed n the screen (HUD, game play, etc.). Classes that handle loading and saving game data (saved games, high scores, etc.) will be located in the data package. Lastly, all classes relating to accounts will be held in the account package. We will use a database to store account data so that users can log in on multiple machines after creating an account. By using a database, we do not have to pass files between computers/make sure all computers have updated files for accounts to work. We will also use a database to store global high scores. 

### Game Engine
The Engine consists of components that handle the physics, XML, managing the game-play (collision-detection), rendering (update graphic locations and image choice), and game logics (how game progress to different levels and how entities' states transit triggered by event). The Engine would implement the `Action`s that are given to the `Authoring` environment, parse the XML

### Game Authoring Environment
The Game Authoring Environment front-end will be able to take in user input and translate the information into backend models.

A Game consists of Levels and has game properties.

Levels consist of Scenes and have level properties.

Scenes consist of Entities and have environment properties.

Entities have names and properties and can be associated with Events and Actions.

Events can be interactions between Entites or when a certain property reaches a specified value or state. Events are associated with Actions.

Actions are changes - the most fundamental physics or math calculations are implemented in Game Engine, but the user can define compositions of Actions and specify which properties or Entities are modified, and how.

## User Interface Wireframe
### Start Screen with Recent Projects, Start, and Help sections
![](https://i.imgur.com/MUFi1ze.png)

### Game Properties Overview of Levels
![](https://i.imgur.com/qB85jUU.png)

### Level Overview
![](https://i.imgur.com/6zGAmJE.png)

### Level Properties Editor
![](https://i.imgur.com/c381AvG.png)

### Scene View
![](https://i.imgur.com/tYUiyqt.png)

### Scene Environment Editor
![](https://i.imgur.com/aWY0NdN.png)

### Entity Library
![](https://i.imgur.com/Q0xEb6q.png)

### Entity Editor
![](https://i.imgur.com/Xg1ugl6.png)

### Event Library
![](https://i.imgur.com/cbMJWNX.png)

### Event Editor
![](https://i.imgur.com/ELXUOaP.png)

### Action Library
![](https://i.imgur.com/62RPvTL.png)


### Action Editor
![](https://i.imgur.com/5ErgXDq.png)


## Example Games

#### Super Mario
Super Mario Bros is a platformer with an objective of reaching the end of the level with as many coins as possible. There are obstacles and threats that hurt the main character as well as moving platforms and gaps that require the main character to navigate the map.

#### Doodle jump
Doodle jump is an infinite platformer that moves a character upwards by bouncing on blocks. This requires the game authoring to support random obstacle generation and make the lose codition be the character falling off the screen. We plan to have the authoring environment support random obstacle generation after the midpoint demo.

#### Space Invaders
Space Invaders is a shooting, infinite platform game that moves the player through levels by shooting the threats. This also requires random obstacle generation from game authoring.

#### Abstraction
From the three games above, we have developed a good sense of how we ought to create abstractions in our game engine. We will use `Entity`, which defines a square in our grid, and `Action`, which defines the actions that each `Entity` take on once an event happens.


## Design Considerations
* Animation of actions: 
    * We considered many designs to accomplish this task because the difficulty lies in the Game Engine having to share game state information with the Game Player. Animating becomes difficult when all that can be shared is snapshots in time. To solve this problem, we will create a queue that Game Engine will add to and that Game Player will pop from, so that Game Player can always have a sequence of game states to draw from that progress through time. This allows for animations (like jumping).
* On-screen movement
    * The main thing our group considered was how to define global constants that define movement in the game. If individual entities have movement constants specified uniquely, there will be no commonalities with how objects on the screen navigate through the game. To mitigate this, we decided to specify some global constants as environment properties (such as gravity) that will affect all objects on the screen, and we can also define other constants separately (such as height of a jump).


## Terminology
* Entity - any object you can see and physically interact with
* GameMap - the game map consisting of entities
* Event - a certain condition that occurs such as time passing, entities colliding, and meeting certain property conditions such as score
* Action - what occurs due to an event

## Use Cases

### GamePlayer
1. Start game
    * Display splash screen with game selections
    * Click on desired game
    * Load game config XML
    * Click on "New Game"
    * Load game variables XML (with initial values)
    * Render game with engine
2. Load game (from saved state)
    * Display splash screen with game selections
    * Click on desired game
    * Load game config XML
    * Click on "Load Game"
    * Navigate to directory with saved game XMLs
    * Load game variables XML (with saved values)
    * Render game with engine
3. Save game
    * Save relevant game variables (i.e. health, points, lives, ...) in new XML
4. Change game preferences
    * Display splash screen with game selections
    * Click on preferences button
    * Spawn new window
    * In new window, specify preference values with drop down menus
5. View high scores
    * Display splash screen with game selections
    * Click on desired game
    * Click on stats
    * Display high score and other game stats
6. Replay game 
    * After game over, display "Play Again?" button
    * If user clicks yes: reload game config XML
7. Update HUD
    * During game play, call information from Game Engine
    * Display information as text in corners of screen
8. Clear game data
    * Display splash screen with game selections
    * Click on desired game
    * Click on clear game stats
    * Reset values in file containing stats
9. Log into account
    * Display splash screen with game selections
    * Click on "Accounts"
    * Click on "Log In"
    * Type in user and password
    * Navigate to authentication file and check for existing user, then check for matching password
    * Display "Welcome \<user>"
10. Create account
    * Display splash screen with game selections
    * Click on "Accounts"
    * Click on "New Account"
    * Fill out required information
    * Store user and password in authentication file
    * Display message confirming account creation
    * Send player back out to splash screen
11. Run Game
    * The method getRender from the Engine is called at a certain frame rate. 
12. View high score
    * Game Player should display high score of current game. 

### Authoring
1. Set rules for collision between entities
    * Select from a list of possible actions that follow from a collision
3. Set speed of entity movement
    * In Authoring, adjust from a slider to change the speed of each entity 
5. Add different entities to the game
    * Select from a list of possible entities and add to the game grid
7. Add a background
    * Select the background color from a set color palette or an image file
9. Set a time-limit
    * Add a game condition that is time-dependent
10. Set an Event on an Entity
    * Use a combination of the fundamentalActions moveUp and moveDown to create a function (which is a combination of fundamentalActions) that behaves as jump
11. Create an action as a combination of fundamentalActions
    * Actions can be a fundamentalAction which are basic building blocks of action that are defined in the Engine, or a function defined in the authoring environment that is a combination of fundamentalActions. 
12. Create an aribitrary property (for example 'health'). 
    * The author will be able to create properties to the entities and set a conditional to the property.
    * Health hits zero -> action: delee character
13. Add new Level [Scene]
    * The User will be able to add a new Level and link it to the previous level
14. Add new Scene 
    * User will be able to link multiple scenes together and link them with conditionals. 
15. Add new Event
    * User can pick what causes the event and define what the event does
16. Load entity
    * USer will be able to load images (.jpeg) to the game if he or she wants to use their own
17. Add entities to Scene
    * User will be able to drag and drop entities to the map where he or she wants it to be (initial position)

    
 

### Engine
1. Detect Collision in the Map
    * The engine is going to have a canvas/virtual map of all the nodes on the screen and going to detect when they intersect. 
2. User presses the right arrow
    * If right arrow maps to an action in a node, then act on that action. 
    * Update map
3. Load game from an XML file
    * The Game Player will pass us the file path to the XML. 
    * We will read the XML and create the virutal map and entities.
4. Read Event
    * Game engine will read what type of event and what it does and display on screen
5. Jump action via state transition
    * When the player character is on the ground (in rest state) and jump key is pressed to make the main character to jump, the state of the player character is changed into jumping up. 
    * The player character keeps ascending frame by frame until the max jump is reached. Then the player's state become descending/falling down.
    * The player character keeps descending frame by frame until the player is detected to have collided with something below it. Then the player's state becomes rest. It doesn't matter if the jump key is pressed during the jumping up and falling down state since not state transition can be triggered.
6. Jump action via velocity and acceleration, i.e. physics
    * When the player character is on the ground (in rest state) and jump key is pressed to make the main character to jump, the state of the player character is changed into jumping. The vertical velocity is set as a upward speed at this transition.
    * Physics engine keeps updating the location and speed according to character's velocity and acceleration, so the character would go up until the vertical velocity becomes zero and it would fall down until it hits the ground and acceleration is set to zero.
7. Jump and teleport when teleport has higher priority to execute
    * When the player character is on the ground (in rest state) and jump key is pressed to make the main character to jump, the state of the player character is changed into jumping up. 
    * The player character is detected to be beyond the threshold position in the teleporter entity. 
    * When this exact frame is being updated, say teleportation event has higher priority, the player character's location is first updated to the exit location determined by the teleporter. Then the user's position would be updated by physics. It continues until the character is standing on something.
8. Jump and teleport when teleport has lower priority to execute
    * When the player character is on the ground (in rest state) and jump key is pressed to make the main character to jump, the state of the player character is changed into jumping up. 
    * The player character is detected to be beyond the threshold position in the teleporter entity. 
    * Alternatively, the physics engine update could have higher priority for execution. After the player character reaches the threshold position, its position would first be updated according to the physics and then updated to a fix location by teleportation. 
    * Essentially, the difference with previous user case is that there's one jump update omitted (executed but no impact). If jumping is implemented by physics, the max height will be lower in this case. If jumping is implemented by state transition, the player could still reach the target height but it takes one more frame.
9. Bouncing off wall by physics engine
    * When the player character runs at a wall at a speed, the collision would be detected by physics enginer. The player character's speed and acceleration would be set to mimic the bouncing off effect. The wall will remains unmoved.
10. Bouncing off an enemy or friendly character by physics engine
    * When the player character runs at an enemy or friendly character at a speed, the collision would be detected by physics enginer. The player character's speed and acceleration would be set to mimic the bouncing off effect. The other character can be also bounced off at same or different speed and acceleration.
11. Time event
    * Many actions occur continuously throughout the game without any specific events occuring other than the passage of time. For example, entity movement occurs without any interaction with other entities on the screen and only depends on time.
12. State event
    * Actions can also occur due to a certain state of an entity. For example, in most scrolling platformers, the user "wins" when they reach the end of the level (which can just simply be reaching an X coordinate). This is purely triggered by the state of a certain entity.


### Utilites
1. Exporting a game to an XML
    * When a user is complete building the entities, events, and actions in a game, they can use the data utility to save these configurations as an XML file.
2. Exporting a level to an XML
    * A user can export a level configuration when they are done building the layout of a level.
3. Loading in a game XML
    * The game engine must be able to load in an XML file and recognize the entities, actions, and events it defined.
4. Loading in a default level XML
    * Based on a game XML file, the level XML file must set the default states of the level including entities and entity states.
5. Saving level states
    * A user can pause the game, and save the state of the level. Each game can define what a save state can be. For example, in the Mario game, if the user tries to save the game while Mario is in midair, then the game will save his state as touching the ground while preserving his x coordinate.
6. Loading in a level state
    * This is similar to loading in a level XML file except this file may contain more specific states about each object. Basically, loading a default level state is a subset of loading in a level state.

## Authoring
### Internal API
-getEntityList()
-getPropertyList()
-getEventList()
-getLevel()
-getEnvironmentList()
-display()
-update()
-addToCanvas()
-deleteFromCanvas()
-addProperty()
-addEvent()
-addLevel()
-addEntity()


## Engine
### Internal API

### External API (Authoring)
- getListOfActions()

### External API (GamePlay)
- play()
- pause()
- quit()
- updateScene()
- loadGame()
- getStats()


## GamePlayer

### Internal API
- makeButton()
- makeComboBox()
- makeScrollPane()
- loadScene()
- connectToDatabase()
- retrieveFromDatabase()
- getHighScore()
- connectToNetwork()


