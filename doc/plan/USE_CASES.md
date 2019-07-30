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