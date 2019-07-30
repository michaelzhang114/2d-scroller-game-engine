# README

## VoogaSalad: MightyDux

### Contributors
* Michael Zhang
* Diego Chamorro
* Justin Kim
* Yao Yuan
* Alejandro Meza
* Irene Qiao
* Eric Werbel
* Connor Ghazaleh

### Timeline
* Date started: March 19, 2019 
* Date ended: April 29, 2019
* Time spent:
    * Michael Zhang: 80 hrs
    * Diego Chamorro: 80 hrs
    * Eric Werbel: 65 hrs
    * Connor Ghazaleh: 60 hrs
    * Justin Kim: 65 hrs

### Role
*Game Authoring:*
* Alejandro-
* Irene-

*Game Engine:*
* Diego-- Worked on all aspects of the Engine: Actions, EventPackages, API, Conditions, and running the game. Refactored actions, conditions, conditionChecks, and ActionEngines into inheritance hierarchies. Also refactored GameEnvironment to facilitate the further implementation of checks, conditions, and engines. 
* Justin--worked on file management and data and integrated these components into `GameCreator` and `GamePlaying`. Designed the distinction between entities and instances of entities which was necessary for implementing XML serialization. Built physics engine and gave ideas for handling all actions within engines. 
* Michael--worked on `AuthoringAPI`, which handles communication with `GameAuthoring`. Majority of level transitioning, collisions and bits of event package handling, actions, and conditions.
* Yao

*Game Player:*
* Connor- set up database and wrote classes to write to and read from the database for messaging, friends, account info, and game stats. Completed content for main menu screen, game lobby screen, and set the framework for messages. Also worked on file structure for accessing game names, descriptions, icons, etc.
* Eric- set up screen flow control/transitions using screen manager class and created the basic skeleton for each Screen. Wrote most content on the screens/formatting and wrote an EmailBot that sends you your password when forgotten. Also set up friends, info, and most of messaging using methods from the databases. Also helped with integration of GamePlayer and GameAuthoring. 

### Files used to start the project
* Player Main Class: RunPlayer (inside Running package and can be run as a jar)

### Files used and errors caught
* `GameEngine` throws the following exceptions:
    - `ActionNotFoundException`
    - `ConditionNotFoundException`
    - `DuplicateEntityInstanceIdException`
    - `DuplicateLevelControllerException`
    - `IllegalEntityException`
    - `IllegalEventPackageException`
    - `IllegalInstanceException`
    - `IllegalLevelException`
    - `IllegalPropertyException`
    - `IllegalScreenSizeException`
    - `MainCharacterNotFoundException`
* `GamePlayer` mostly throws exceptions regarding Files that were not found. When attemtping to load files from the games directory, exceptions are caught if the file is not found and an error message is displayed. We have incorporated a lot of built in error checking to prevent exceptions (i.e. not allowing a null account to be created). 

### Data or resource files
* Game Player has its own resources folder in its module that includes CSS Stylesheets and properties files containing information about what error message to display. Additionally, there is a games folder within the data directory that Player reads from to get the list of games along with their instructions, description, and image. 


### Information about using the program
* Game Player: run RunPlayer which pulls up a main menu where you can browse available games or login/create an account. If logged in, you can then view your account info by clicking on the My Account button. This will take you to a screen that has three tabs, Account Info, Friends, and Messages. If you want to play a game, you simply select it from the main menu and go to the game lobby. Here, instructions on how to play the game will be displayed. Once you click play, the game engine will render the game and you can begin playing. You can choose to pause the game at any time by clicking the pause button. Once the game is paused, you can continue playing, save the current state, or quit. 

### Decisions, assumptions, simplifications
- `GameEngine` made a lot of decisions and assumptions regarding the possibilities of game creation. `Entity` is defined by a set of `EventPackage`, and `Instance` is defined by a map of its properties.
- Properties give `Instance` a lot of power to affect other `Instance`
- `GamePlayer` simplified the friends process by not sending friend requests, if one user adds the other, they automatically become friends
- There are two separate mains, one for Player and one for Authoring, you cannot access both by running one program- simplified communication and module dependencies

### Known bugs and crashes
- Some error checking is not refined in `GameAuthoring`
- Screensizes and button sizes are not ideal
- Account Info tabs do not refresh automatically, need to click off and return to get updated info
- Click "go to lobby" button before selecting a game gives a null pointer exception

### Extra features
Game Player:
* Accounts System (stored in Database)
    * Friends (add/remove and can search for other users)
    * Game Stats (# times played, high score, avg score, etc.)
    * Messaging (send and receive messages to/from other users)
    * Login/Create Account (and authentication)
* Email Bot
    * Sends an email containing the password linked to the given account username to the email linked with the account
* Pause (pause the game while running)
    * Can continue playing
    * Can save the current state of the game (done through the engine)
    * Can quit and return to the Game Lobby

### Impressions
* Michael--challenging project that required a lot of planning and communication. I learnt heaps and improved my programming skills.
* Eric -- It was a tough project to wrap my head around at the beginning because of how big it was, but as we planned, it seemed to become more manageable. There were some very frusterating times when one thing just wasn't working, but it was fun to focus on adding cool features like messaging and a friends system. Definitely a good experience and I learned a lot. 
* Justin -- Difficult project that required lots of design planning. It was really cool to finally see our skills improve throughout the class to the point where we could make a game engine. 
* Diego Chamorro -- As a member of the engine team, this project really drove the importance and power of abstraction.  
