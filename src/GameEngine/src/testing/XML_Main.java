package testing;

import javafx.scene.Scene;
import external.GameCreator;
import javafx.application.Application;
import javafx.stage.Stage;
import external.GamePlaying;
import makingGame.entity.BasicProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class XML_Main extends Application {

    GameCreator gc;

    public void makeGame(){
        gc = new GameCreator();

        gc.setCurrentLevel("0");

        gc.setCurrentLevel("0");

        List<String> keyRight = new ArrayList<>();
        keyRight.add("D");

        List<String> keyLeft = new ArrayList<>();
        keyLeft.add("A");

        List<String> keyUp = new ArrayList<>();
        keyUp.add("W");

        List<String> keyDown = new ArrayList<>();
        keyDown.add("S");

        List<String> keyUpI = new ArrayList<>();
        keyUpI.add("I");

        List<String> keyDownK = new ArrayList<>();
        keyDownK.add("K");

        gc.setGameScreenSize("1280", "640");

        gc.setBackgroundImage("space.png");
        gc.setGameName("sticky");
        gc.setIcon("/Users/justinkim/Duke/sp19/CS308/voogasalad_mightydux/src/GameEngine/resources/goomba.png");
        gc.setDescription("play stickyman made by michael");
        gc.setInstructions("hi there");

        gc.createEntity("Man");
        gc.createEntity("Block");
        gc.createEntity("PowerUp");
        gc.createEntity("Bomb");


        gc.createPropertyInEntity("Man", "yAcceleration", "0");
        gc.createPropertyInEntity("Block", "yAcceleration", "0");
        gc.createPropertyInEntity("PowerUp", "yAcceleration", "0");
        gc.createPropertyInEntity("Bomb", "yAcceleration", "0");
        gc.createPropertyInEntity("Man", "manState", "0.0");
        gc.createPropertyInEntity("Man", "death", "0.0");




        gc.createEntityInstance("Man", "m0", "running.gif");
        gc.setPropertyToEntityInstance("Man", "m0", "xPosition", "100");
        gc.setPropertyToEntityInstance("Man", "m0", "yPosition", "200");
        gc.setPropertyToEntityInstance("Man", "m0", "yAcceleration", "20");
        gc.setPropertyToEntityInstance("Man", "m0", "manState", "0.0");







        gc.setMainCharacter("Man", "m0");



        // bottom blocks
        for (int i=1;i<1000;i++) {
            String id = "blk-btm" + i;
            String xloc = Double.toString((i-1) * 31);
            String yloc = Integer.toString(500);
            gc.createEntityInstance("Block", id, "block2.png");
            gc.setPropertyToEntityInstance("Block", id, "xPosition", xloc);
            gc.setPropertyToEntityInstance("Block", id, "yPosition", yloc);
        }

        // top blocks
        for (int i=1;i<1000;i++) {
            String id = "blk-top" + i;
            String xloc = Double.toString((i-1) * 31);
            String yloc = Integer.toString(100);
            gc.createEntityInstance("Block", id, "block2.png");
            gc.setPropertyToEntityInstance("Block", id, "xPosition", xloc);
            gc.setPropertyToEntityInstance("Block", id, "yPosition", yloc);
        }

        // speed power ups
        for (int i = 1; i < 100; i++) {
            String id = "spd" + i;

            String xLoc = Double.toString(i* 900);

            //250-350
            Random ran = new Random();
            int x = ran.nextInt(100) + 250;
            String yLoc = Integer.toString(x);

            String xVel = "-50";
            //String xAcc = "20";

            gc.createEntityInstance("PowerUp", id, "block1.png");
            gc.setPropertyToEntityInstance("PowerUp", id, "xPosition", xLoc);
            gc.setPropertyToEntityInstance("PowerUp", id, "yPosition", yLoc);
            gc.setPropertyToEntityInstance("PowerUp", id, "xVelocity", xVel);
            //gc.setPropertyToEntityInstance("PowerUp", id, "xAcceleration", xAcc);
        }

        // speed power ups
        for (int i = 1; i < 100; i++) {
            String id = "bmb" + i;

            String xLoc = Double.toString(i * 400);


            Random ran = new Random();
            int x = ran.nextInt(300) + 150;
            String yLoc = Integer.toString(x);

            String xVel = "-100";
            //String xAcc = "20";

            gc.createEntityInstance("Bomb", id, "bomb.png");
            gc.setPropertyToEntityInstance("Bomb", id, "xPosition", xLoc);
            gc.setPropertyToEntityInstance("Bomb", id, "yPosition", yLoc);
            gc.setPropertyToEntityInstance("Bomb", id, "xVelocity", xVel);
            //gc.setPropertyToEntityInstance("PowerUp", id, "xAcceleration", xAcc);
        }


        //~~~~~~~~~~~~~~~~~~~~~~~~~~


        gc.createEntity("Isaac");
//        gc.addFSMToEntityInstance("Isaac", "i1","FasterLeftRightFSM");
//        gc.addFSMToEntityInstance("Isaac", "i1","UpDownFSM");
        gc.createEntityInstance("Isaac", "i1", "Isaac.png");
        gc.setMainCharacter("Isaac", "i1");
        gc.createPropertyInEntity("Isaac","state", "notattacking");




        // man initial pos
        gc.setPropertyToEntityInstance("Man","m0","xPosition",  "0");
        gc.setPropertyToEntityInstance("Man","m0","yPosition",  "245");
        gc.setPropertyToEntityInstance("Man", "m0", "xVelocity", "100");


        gc.createAction("SameY", "yVelocity", "=", "0");
        gc.createAction("HardTopY", "yPosition", "=", "134");
        gc.createAction("HardBottomY", "yPosition", "=", "466");
        gc.createAction("YAccZero", "yAcceleration", "=", "0");
        gc.createAction("YVelZero", "yVelocity", "=", "0");
        gc.createAction("AtTop", "manState", "=", "1.0");
        gc.createAction("AtBottom", "manState", "=", "0.0");
        gc.createAction("SpeedUp", "xVelocity", "+", "70");
        gc.createActionString("ImageUpsideDown", "Man", "imagePath", "running2.gif");
        gc.createActionString("ImageUpside", "Man", "imagePath", "running.gif");


        // top collision with block
        gc.createEventPackage("TopCollisionWithBlock");
        gc.createCollisionCondition("TopWithBlock", "Block", "top");
        gc.addConditionToPackage("TopCollisionWithBlock", "TopWithBlock");
        gc.addActionToEventPackage("TopCollisionWithBlock", "HardTopY");
        gc.addActionToEventPackage("TopCollisionWithBlock", "YVelZero");
        gc.addActionToEventPackage("TopCollisionWithBlock", "AtTop");
        gc.addEventPackageToEntity("Man", "TopCollisionWithBlock");

        // bottom collision with block
        gc.createEventPackage("BottomCollisionWithBlock");
        gc.createCollisionCondition("BottomWithBlock", "Block", "bottom");
        gc.addConditionToPackage("BottomCollisionWithBlock", "BottomWithBlock");
        gc.addActionToEventPackage("BottomCollisionWithBlock", "HardBottomY");
        gc.addActionToEventPackage("BottomCollisionWithBlock", "YVelZero");
        gc.addActionToEventPackage("BottomCollisionWithBlock", "AtBottom");
        gc.addEventPackageToEntity("Man", "BottomCollisionWithBlock");


        // up key
        gc.createEventPackage("AccelerateUp");
        gc.createKeyInputCondition("up", keyUp);
        gc.createAction("AccUpwards", "yAcceleration", "=", "-20");
        gc.createPropertyCondition("IfAtBottom", "manState", "==", "0.0");
        gc.addConditionToPackage("AccelerateUp", "up");
        gc.addConditionToPackage("AccelerateUp", "IfAtBottom");
        gc.addActionToEventPackage("AccelerateUp", "AccUpwards");
        gc.addActionToEventPackage("AccelerateUp", "ImageUpsideDown");
        gc.addEventPackageToEntity("Man", "AccelerateUp");

        // down key
        gc.createEventPackage("AccelerateDown");
        gc.createKeyInputCondition("down", keyDown);
        gc.createAction("AccDownwards", "yAcceleration", "=", "20");
        gc.createPropertyCondition("IfAtTop", "manState", "==", "1.0");
        gc.addConditionToPackage("AccelerateDown", "down");
        gc.addConditionToPackage("AccelerateDown", "IfAtTop");
        gc.addActionToEventPackage("AccelerateDown", "AccDownwards");
        gc.addActionToEventPackage("AccelerateDown", "ImageUpside");
        gc.addEventPackageToEntity("Man", "AccelerateDown");

        // collide with powerup
        gc.createEventPackage("PowerUpSpeed");
        gc.createCollisionCondition("CollidePowerUp", "PowerUp", "any");
        gc.addConditionToPackage("PowerUpSpeed", "CollidePowerUp");
        gc.addActionToEventPackage("PowerUpSpeed", "SpeedUp");
        gc.addEventPackageToEntity("Man", "PowerUpSpeed");

        // collide with bomb
        gc.createEventPackage("DeathState");
        gc.createCollisionCondition("CollideBomb", "Bomb", "any");
        gc.createAction("ChangeDeathState", "death", "=", "1.0");
        gc.addConditionToPackage("DeathState", "CollideBomb");
        gc.addActionToEventPackage("DeathState", "ChangeDeathState");
        gc.addEventPackageToEntity("Man", "DeathState");

        gc.createLevelChangeEventPackage("die", "1");
        gc.createPropertyCondition("CheckStateDeath", "Man","death", "==", "1.0");
        gc.addConditionToLevelChangeEventPackage("die", "CheckStateDeath");

        for (int i=1;i<100;i++) {
            String id = "brick" + i;
            String xloc = Double.toString((i-1) * 31);
            String yloc = Integer.toString(280);
            if (5 == i || i == 15) {
                yloc = Integer.toString(280-32);
            }
            if (!(30 <= i && i <= 36)) {
                gc.createEntityInstance("Brick", id, "brick.png");
                gc.setPropertyToEntityInstance("Brick", id, "xPosition", xloc);
                gc.setPropertyToEntityInstance("Brick", id, "yPosition", yloc);
            }

        }

//        gc.setPropertyToEntityInstance("Mario","m0", BasicProperties.YACC,  "0");
//        gc.setPropertyToEntityInstance("Mario","m0", BasicProperties.YVEL,  "0");
//
//        for (int i = 1; i <= 6; i++) {
//            String id = "g" + i;
//            String x = Integer.toString(140 + i * 50);
//            gc.createEntityInstance("Goomba", id, "goomba.png");
//            gc.setPropertyToEntityInstance("Goomba",id,"xPosition",  x);
//            gc.setPropertyToEntityInstance("Goomba",id,"yPosition",  "2.00");
//            gc.setPropertyToEntityInstance("Goomba",id,"xVelocity",  "100");
//        }
//
//        // hit left
//        gc.createAction("setPositiveXVel", "xVelocity", "=", "30");
//        gc.createEventPackage("GoombaCollideBrickLeft");
//        gc.createCollisionCondition("goombaBrickLeft", "Brick", "left");
//        gc.addConditionToPackage("GoombaCollideBrickLeft", "goombaBrickLeft");
//        gc.addActionToEventPackage("GoombaCollideBrickLeft", "setPositiveXVel");
//        gc.addEventPackageToEntity("Goomba", "GoombaCollideBrickLeft");
//
//        // hit right
//        gc.createAction("setNegativeXVel", "xVelocity", "=", "-30");
//        gc.createEventPackage("GoombaCollideBrickRight");
//        gc.createCollisionCondition("goombaBrickRight", "Brick", "right");
//        gc.addConditionToPackage("GoombaCollideBrickRight", "goombaBrickRight");
//        gc.addActionToEventPackage("GoombaCollideBrickRight", "setNegativeXVel");
//        gc.addEventPackageToEntity("Goomba", "GoombaCollideBrickRight");
//
//
//
//
//
//
//        gc.createEventPackage("GoombaCollideMario");
//        gc.createAction("getPunted", "yVelocity", "-", "200");
//        gc.createAction("getPuntedX", "xVelocity", "+", "200");
//        gc.createCollisionCondition("marioCollision", "Mario","any");
//        gc.addConditionToPackage("GoombaCollideMario", "marioCollision" );
//        gc.addActionToEventPackage("GoombaCollideMario", "getPunted" );
//        gc.addActionToEventPackage("GoombaCollideMario", "getPuntedX" );
//
//        gc.addEventPackageToEntity("Goomba", "GoombaCollideMario");
//
//
//
        gc.setCurrentLevel("1");
        gc.createEntityInstance("Man", "m1", "running.gif");
        gc.setMainCharacter("Man", "m1");
        gc.setBackgroundImage("go1.png");
//
//        gc.createLevelChangeEventPackage("levelChangeSecond", "0");
//        gc.createPropertyCondition("goombaLevel", "Mario", "xPosition", ">", "500");
//        gc.addConditionToLevelChangeEventPackage("levelChangeSecond","goombaLevel");
//        gc.addActionToEventPackage("levelChangeSecond", "resetMario");
//
//
//
//
//        gc.setBackgroundImage("background.png");
//        gc.setGameScreenSize("1280", "640");
//        gc.createEntityInstance("Mario", "1000", "goomba.png");
//        gc.setMainCharacter("Mario", "1000");
//        gc.createEntityInstance("Bird", "bird69", "bird.png");
//        gc.setPropertyToEntityInstance("Bird", "bird69", "xPosition", "600");
//        gc.setPropertyToEntityInstance("Bird", "bird69", "yPosition", "100");
//        gc.setPropertyToEntityInstance("Bird", "bird69", "xVelocity", "-100");
//
//        for (int i=1;i<100;i++) {
//            String id = "brick" + i + "level2";
//            String xloc = Double.toString((i-1) * 31);
//            String yloc = Integer.toString(280);
//            if (5 == i || i == 15) {
//                yloc = Integer.toString(280+32);
//            }
//            if (!(30 <= i && i <= 36)) {
//                gc.createEntityInstance("Brick", id, "brick.png");
//                gc.setPropertyToEntityInstance("Brick", id, "xPosition", xloc);
//                gc.setPropertyToEntityInstance("Brick", id, "yPosition", yloc);
//            }
//
//        }




        gc.setPropertyToEntityInstance("Mario","m0","xPosition",  "0");
        gc.setPropertyToEntityInstance("Mario","m0","yPosition",  "245");
        gc.setPropertyToEntityInstance("Mario","m0", BasicProperties.YACC,  "0");
        gc.setPropertyToEntityInstance("Mario","m0", BasicProperties.YVEL,  "0");

        for (int i = 1; i <= 6; i++) {
            String id = "g" + i;
            String x = Integer.toString(140 + i * 50);
            gc.createEntityInstance("Goomba", id, "goomba.png");
            gc.setPropertyToEntityInstance("Goomba",id,"xPosition",  x);
            gc.setPropertyToEntityInstance("Goomba",id,"yPosition",  "2.00");
            gc.setPropertyToEntityInstance("Goomba",id,"xVelocity",  "100");
        }

        // hit left
        gc.createAction("setPositiveXVel", "xVelocity", "=", "30");
        gc.createEventPackage("GoombaCollideBrickLeft");
        gc.createCollisionCondition("goombaBrickLeft", "Brick", "left");
        gc.addConditionToPackage("GoombaCollideBrickLeft", "goombaBrickLeft");
        gc.addActionToEventPackage("GoombaCollideBrickLeft", "setPositiveXVel");
        gc.addEventPackageToEntity("Goomba", "GoombaCollideBrickLeft");

        // hit right
        gc.createAction("setNegativeXVel", "xVelocity", "=", "-30");
        gc.createEventPackage("GoombaCollideBrickRight");
        gc.createCollisionCondition("goombaBrickRight", "Brick", "right");
        gc.addConditionToPackage("GoombaCollideBrickRight", "goombaBrickRight");
        gc.addActionToEventPackage("GoombaCollideBrickRight", "setNegativeXVel");
        gc.addEventPackageToEntity("Goomba", "GoombaCollideBrickRight");






        gc.createEventPackage("GoombaCollideMario");
        gc.createAction("getPunted", "yVelocity", "-", "200");
        gc.createAction("getPuntedX", "xVelocity", "+", "200");
        gc.createCollisionCondition("marioCollision", "Mario","any");
        gc.addConditionToPackage("GoombaCollideMario", "marioCollision" );
        gc.addActionToEventPackage("GoombaCollideMario", "getPunted" );
        gc.addActionToEventPackage("GoombaCollideMario", "getPuntedX" );

        gc.addEventPackageToEntity("Goomba", "GoombaCollideMario");



        gc.setCurrentLevel("1");

        gc.createLevelChangeEventPackage("levelChangeSecond", "0");
        gc.createPropertyCondition("goombaLevel", "Mario", "xPosition", ">", "500");
        gc.addConditionToLevelChangeEventPackage("levelChangeSecond","goombaLevel");
        gc.addActionToEventPackage("levelChangeSecond", "resetMario");




        gc.setBackgroundImage("background.png");
        gc.setGameScreenSize("1280", "640");
        gc.createEntityInstance("Mario", "1000", "goomba.png");
        gc.setMainCharacter("Mario", "1000");
        gc.createEntityInstance("Bird", "bird69", "bird.png");
        gc.setPropertyToEntityInstance("Bird", "bird69", "xPosition", "600");
        gc.setPropertyToEntityInstance("Bird", "bird69", "yPosition", "100");
        gc.setPropertyToEntityInstance("Bird", "bird69", "xVelocity", "-100");

        for (int i=1;i<100;i++) {
            String id = "brick" + i + "level2";
            String xloc = Double.toString((i-1) * 31);
            String yloc = Integer.toString(280);
            if (5 == i || i == 15) {
                yloc = Integer.toString(280+32);
            }
            if (!(30 <= i && i <= 36)) {
                gc.createEntityInstance("Brick", id, "brick.png");
                gc.setPropertyToEntityInstance("Brick", id, "xPosition", xloc);
                gc.setPropertyToEntityInstance("Brick", id, "yPosition", yloc);
            }

        }


    }




    @Override
    public void start(Stage stage) throws Exception {

//        makeGame();
//        gc.saveGame();
        GamePlaying playGames = new GamePlaying("super_mario_yao");

        playGames.loadGame();
        Scene scene = playGames.getScene();
        stage.setScene(scene);
        playGames.runGame();
        stage.show();

        //GameCreator gcFromXML = xmlParser.xmlToGameCreator(gcAsXML);
        //runGame(gcFromXML.getEManager(), stage);
    }

    /**
     * Start the program.
     */
    public static void main (String[] args) {
        launch(args);
    }

}


//package testing;
//
//import javafx.scene.Scene;
//import external.GameCreator;
//import javafx.application.Application;
//import javafx.stage.Stage;
//import external.GamePlaying;
//import makingGame.entity.BasicProperties;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//public class XML_Main extends Application {
//
//    GameCreator gc;
//    public void makeGame(){
//        gc = new GameCreator();
//
//        gc.setCurrentLevel("0");
//
//        List<String> keyRight = new ArrayList<>();
//        keyRight.add("D");
//
//        List<String> keyLeft = new ArrayList<>();
//        keyLeft.add("A");
//
//        List<String> keyUp = new ArrayList<>();
//        keyUp.add("W");
//
//        List<String> keyDown = new ArrayList<>();
//        keyDown.add("S");
//
//        List<String> keyRightL = new ArrayList<>();
//        keyRightL.add("L");
//
//        List<String> keyLeftJ = new ArrayList<>();
//        keyLeftJ.add("J");
//
//        List<String> keyUpI = new ArrayList<>();
//        keyUpI.add("I");
//
//        List<String> keyDownK = new ArrayList<>();
//        keyDownK.add("K");
//
//        gc.setGameScreenSize("1280", "640");
//        gc.setBackgroundImage("LineRunnerBG.png");
//        gc.setGameName("LineRunner");
//        gc.setIcon("C:\\Users\\enw12\\Documents\\CS308\\LineRunnerIcon.png");
//        gc.setDescription("Avoid obstacles to see how far you can go!");
//        gc.setInstructions("Press W to jump and press S to slide!");
//
//        gc.createEntity("Man");
//        gc.createEntity("Block");
//        gc.createEntity("Obstacle");
//
//
//        gc.createPropertyInEntity("Man", "yAcceleration", "0");
//        gc.createPropertyInEntity("Block", "yAcceleration", "0");
//        gc.createPropertyInEntity("Obstacle", "yAcceleration", "0");
//
//
//        gc.createEntityInstance("Man", "m0", "RunningMan2.png");
//        gc.setPropertyToEntityInstance("Man", "m0", "xPosition", "100");
//        gc.setPropertyToEntityInstance("Man", "m0", "yPosition", "200");
//        gc.setPropertyToEntityInstance("Man", "m0", "yAcceleration", "6");
//
//
//        gc.setMainCharacter("Man", "m0");
//
//
//
//
//        // bottom blocks
//        for (int i=1;i<1000;i++) {
//            String idBlock = "blk-btm" + i;
//            String idObstacle = "obst" + i;
//            String xloc = Double.toString((i-1) * 31);
//            String yloc = Integer.toString(500);
//            String bottomLoc = Integer.toString(470);
//            String topLoc = Integer.toString(440);
//            gc.createEntityInstance("Block", idBlock, "brick.png");
//            gc.setPropertyToEntityInstance("Block", idBlock, "xPosition", xloc);
//            gc.setPropertyToEntityInstance("Block", idBlock, "yPosition", yloc);
//            Random r = new Random();
//            int n = r.nextInt(100);
//            if (n < 7  && i > 15 && i % 3 == 0) {
//                gc.createEntityInstance("Obstacle", idObstacle, "wheel.png");
//                gc.setPropertyToEntityInstance("Obstacle", idObstacle, "xPosition", xloc);
//                gc.setPropertyToEntityInstance("Obstacle", idObstacle, "yPosition", bottomLoc);
//            } else if (n > 93 && i > 15 && i % 3 == 0) {
//                gc.createEntityInstance("Obstacle", idObstacle, "fireball.png");
//                gc.setPropertyToEntityInstance("Obstacle", idObstacle, "xPosition", xloc);
//                gc.setPropertyToEntityInstance("Obstacle", idObstacle, "yPosition", topLoc);
//            }
//        }
//
//
//        //~~~~~~~~~~~~~~~~~~~~~~~~~~
//
//
//
//
//
//
//
//        // man initial pos
//        gc.setPropertyToEntityInstance("Man","m0","xPosition",  "0");
//        gc.setPropertyToEntityInstance("Man","m0","yPosition",  "220");
//        gc.setPropertyToEntityInstance("Man", "m0", "xVelocity", "125");
//
//        gc.createPropertyInEntity("Man", "state", "0.0");
//        gc.createPropertyInEntity("Man", "isHit", "0.0");
//
//        gc.createAction("SameY", "yVelocity", "=", "0");
//        gc.createAction("HardTopY", "yPosition", "=", "134");
//        gc.createAction("HardBottomY", "yPosition", "=", "440");
//        gc.createAction("HardBottomY2", "yPosition", "=", "475");
//        gc.createAction("YAccZero", "yAcceleration", "=", "0");
//        gc.createAction("YVelZero", "yVelocity", "=", "0");
//        gc.createAction("AtTop", "manState", "=", "1.0");
//        gc.createAction("AtBottom", "manState", "=", "0.0");
//        gc.createAction("MoveUp", "yVelocity", "=", "-250");
//
//
//
//
//        // bottom collision with block
//        gc.createEventPackage("BottomCollisionWithBlock");
//        gc.createCollisionCondition("BottomWithBlock", "Block", "bottom");
//        gc.addConditionToPackage("BottomCollisionWithBlock", "BottomWithBlock");
//        gc.createPropertyCondition("checkSlide", "state", "==", "1.0");
//        gc.addConditionToPackage("BottomCollisionWithBlock", "checkSlide");
//        gc.addActionToEventPackage("BottomCollisionWithBlock", "HardBottomY2");
//        gc.addActionToEventPackage("BottomCollisionWithBlock", "YVelZero");
//        gc.addEventPackageToEntity("Man", "BottomCollisionWithBlock");
//
//        gc.createEventPackage("BottomCollisionWithBlock2");
//        gc.createCollisionCondition("BottomWithBlock2", "Block", "bottom");
//        gc.addConditionToPackage("BottomCollisionWithBlock2", "BottomWithBlock2");
//        gc.createPropertyCondition("checkRun", "state", "==", "0.0");
//        gc.addConditionToPackage("BottomCollisionWithBlock2", "checkRun");
//        gc.addActionToEventPackage("BottomCollisionWithBlock2", "HardBottomY");
//        gc.addActionToEventPackage("BottomCollisionWithBlock2", "YVelZero");
//        gc.addEventPackageToEntity("Man", "BottomCollisionWithBlock2");
//
//        // up key
//        gc.createEventPackage("AccelerateUp");
//        gc.createKeyInputCondition("up", keyUp);
//        gc.addConditionToPackage("AccelerateUp", "up");
//        gc.addActionToEventPackage("AccelerateUp", "MoveUp");
//        gc.createAction("runState", "state", "=", "0.0");
//        gc.addActionToEventPackage("AccelerateUp", "runState");
//        gc.createActionString("running", "imagePath", "RunningMan2.png");
//        gc.addActionToEventPackage("AccelerateUp", "running");
//        gc.addEventPackageToEntity("Man", "AccelerateUp");
//
//        // down key
//        gc.createEventPackage("Slide");
//        gc.createKeyInputCondition("down", keyDown);
//        gc.addConditionToPackage("Slide", "down");
//        gc.createActionString("sliding", "imagePath", "SlidingMan2.png");
//        gc.createAction("slideState", "state", "=", "1.0");
//        gc.addActionToEventPackage("Slide", "sliding");
//        gc.addActionToEventPackage("Slide", "slideState");
//        gc.addEventPackageToEntity("Man", "Slide");
//
//        // obstacle collision
//        gc.createEventPackage("obstCollision");
//        gc.createCollisionCondition("obstC", "Obstacle", "any");
//        gc.addConditionToPackage("obstCollision", "obstC");
//        gc.createAction("isDead", "isHit", "=", "1.0");
//        gc.addActionToEventPackage("obstCollision", "isDead");
//        gc.addEventPackageToEntity("Man", "obstCollision");
//
//        gc.createLevelChangeEventPackage("levelChangeSecond", "1");
//        gc.createPropertyCondition("dead", "Man", "isHit", "==", "1.0");
//        gc.addConditionToLevelChangeEventPackage("levelChangeSecond","dead");
//
//
//
//
//        gc.setCurrentLevel("1");
//        gc.setBackgroundImage("LROver.png");
//        gc.setGameScreenSize("1280", "640");
//        gc.setMainCharacter("Man", "m1");
//
////        gc.setPropertyToEntityInstance("Mario","m0", BasicProperties.YACC,  "0");
////        gc.setPropertyToEntityInstance("Mario","m0", BasicProperties.YVEL,  "0");
////
////        for (int i = 1; i <= 6; i++) {
////            String id = "g" + i;
////            String x = Integer.toString(140 + i * 50);
////            gc.createEntityInstance("Goomba", id, "goomba.png");
////            gc.setPropertyToEntityInstance("Goomba",id,"xPosition",  x);
////            gc.setPropertyToEntityInstance("Goomba",id,"yPosition",  "2.00");
////            gc.setPropertyToEntityInstance("Goomba",id,"xVelocity",  "100");
////        }
////
////        // hit left
////        gc.createAction("setPositiveXVel", "xVelocity", "=", "30");
////        gc.createEventPackage("GoombaCollideBrickLeft");
////        gc.createCollisionCondition("goombaBrickLeft", "Brick", "left");
////        gc.addConditionToPackage("GoombaCollideBrickLeft", "goombaBrickLeft");
////        gc.addActionToEventPackage("GoombaCollideBrickLeft", "setPositiveXVel");
////        gc.addEventPackageToEntity("Goomba", "GoombaCollideBrickLeft");
////
////        // hit right
////        gc.createAction("setNegativeXVel", "xVelocity", "=", "-30");
////        gc.createEventPackage("GoombaCollideBrickRight");
////        gc.createCollisionCondition("goombaBrickRight", "Brick", "right");
////        gc.addConditionToPackage("GoombaCollideBrickRight", "goombaBrickRight");
////        gc.addActionToEventPackage("GoombaCollideBrickRight", "setNegativeXVel");
////        gc.addEventPackageToEntity("Goomba", "GoombaCollideBrickRight");
////
////
////
////
////
////
////        gc.createEventPackage("GoombaCollideMario");
////        gc.createAction("getPunted", "yVelocity", "-", "200");
////        gc.createAction("getPuntedX", "xVelocity", "+", "200");
////        gc.createCollisionCondition("marioCollision", "Mario","any");
////        gc.addConditionToPackage("GoombaCollideMario", "marioCollision" );
////        gc.addActionToEventPackage("GoombaCollideMario", "getPunted" );
////        gc.addActionToEventPackage("GoombaCollideMario", "getPuntedX" );
////
////        gc.addEventPackageToEntity("Goomba", "GoombaCollideMario");
////
////
////
////        gc.setCurrentLevel("1");
////
////        gc.createLevelChangeEventPackage("levelChangeSecond", "0");
////        gc.createPropertyCondition("goombaLevel", "Mario", "xPosition", ">", "500");
////        gc.addConditionToLevelChangeEventPackage("levelChangeSecond","goombaLevel");
////        gc.addActionToEventPackage("levelChangeSecond", "resetMario");
////
////
////
////
////        gc.setBackgroundImage("background.png");
////        gc.setGameScreenSize("1280", "640");
////        gc.createEntityInstance("Mario", "1000", "goomba.png");
////        gc.setMainCharacter("Mario", "1000");
////        gc.createEntityInstance("Bird", "bird69", "bird.png");
////        gc.setPropertyToEntityInstance("Bird", "bird69", "xPosition", "600");
////        gc.setPropertyToEntityInstance("Bird", "bird69", "yPosition", "100");
////        gc.setPropertyToEntityInstance("Bird", "bird69", "xVelocity", "-100");
////
////        for (int i=1;i<100;i++) {
////            String id = "brick" + i + "level2";
////            String xloc = Double.toString((i-1) * 31);
////            String yloc = Integer.toString(280);
////            if (5 == i || i == 15) {
////                yloc = Integer.toString(280+32);
////            }
////            if (!(30 <= i && i <= 36)) {
////                gc.createEntityInstance("Brick", id, "brick.png");
////                gc.setPropertyToEntityInstance("Brick", id, "xPosition", xloc);
////                gc.setPropertyToEntityInstance("Brick", id, "yPosition", yloc);
////            }
////
////        }
//
//
//    }
//
//
//
//
//    @Override
//    public void start(Stage stage) throws Exception {
//        makeGame();
//        gc.saveGame();
//
//        GamePlaying playGames = new GamePlaying("LineRunner");
//        //String gcAsXML = xmlParser.gameCreatorToXML(gc);
//        //ActiveFrame hotdog = new ActiveFrame(10, 69, 420);
//        //String hotdogXML = xmlParser.objectToXML(hotdog);
//
//        playGames.loadGame();
//        Scene scene = playGames.getScene();
//        stage.setScene(scene);
//        playGames.runGame();
//        stage.show();
//
//        //GameCreator gcFromXML = xmlParser.xmlToGameCreator(gcAsXML);
//        //runGame(gcFromXML.getEManager(), stage);
//    }
//
//    /**
//     * Start the program.
//     */
//    public static void main (String[] args) {
//        launch(args);
//    }
//
//}


