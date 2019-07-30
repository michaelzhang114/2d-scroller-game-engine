package testing;

public class GameCreatorTester {
//
//    private GameCreator gc = null;
//
//    @BeforeEach
//    public void setUp () {
//        gc = new GameCreator();
//    }
//    // do not change this test
//
//    @Test
//    public void createAnEntity(){
//        gc.addEntity("Mario");
//        EntityManager myEM = gc.getEManager();
//        assertTrue(myEM.getEntityNameAsEntity("Mario") != null);
//    }
//
//    @Test
//    public void createAnEntityInstance(){
//        gc.addEntity("Mario");
//        gc.addEntityInstance("Mario", "1", "adsfasdfaf");
//        EntityManager myEM = gc.getEManager();
//        assertTrue(myEM.getEntityNameAsEntity("Mario").getEntityInstance("1") != null);
//    }
//
//
//    @Test
//    public void basicPropertyEvent(){
//        gc.addEntity("mario");
//        gc.addEntityInstance("mario", "1", "some path blah blah");
//        gc.createPropertyToEntity("mario", "velocity", "0");
//        gc.setPropertyToEntityInstance("mario", "1", "velocity", "68");
//        gc.createAction("action1","velocity", "", "22");
//        gc.createPropertyEvent("propEvent1", "mario","velocity", "==", "78");
//        gc.addActionToPropertyEvent("propEvent1", "action1");
//        gc.setPropertyToEntityInstance("mario","1", "velocity", "78");
//        assertTrue(gc.getMapOfEntityInstance("mario", "1").get("velocity") == 100);
//    }
//
//    @Test
//    public void complexPropertyEvent1(){
//        gc.addEntity("mario");
//        gc.addEntityInstance("mario", "1", "sdfadfasd");
//        gc.addEntity("peach");
//        gc.addEntityInstance("peach", "1", "adfasdfasdfs");
//        gc.createPropertyToEntity("mario", "velocity", "0");
//        gc.createPropertyToEntity("peach", "velocity", "0");
//        gc.setPropertyToEntityInstance("peach", "1", "velocity", "68");
//        gc.setPropertyToEntityInstance("mario", "1", "velocity", "32");
//        gc.createAction("action1","velocity", "+", "31");
//        gc.createPropertyEvent("propEvent1", "mario","velocity", "==", "69");
//        gc.addActionToPropertyEvent("propEvent1", "action1");
//        gc.setPropertyToEntityInstance("mario","1", "velocity", "69");
//        assertTrue(gc.getMapOfEntityInstance("mario","1").get("velocity") == 100 && gc.getMapOfEntityInstance("peach","1").get("velocity") == 68);
//    }
//
//    @Test
//    public void addTwoGoombas(){
//        gc.addEntity("goomba");
//        gc.addEntityInstance("goomba", "1", "asdfsdfdsfsdf");
//        gc.addEntityInstance("goomba", "2", "asdfsd");
//        gc.createPropertyEvent("prop1", "goomba", "xVelocity", "==", "18");
//        gc.createPropertyEvent("prop2", "goomba", "xVelocity", "<", "18");
//        gc.createAction("action1", "xVelocity", "+", "18");
//        gc.createAction("action2", "xVelocity", "+", "36");
//        gc.addActionToPropertyEvent("prop1", "action1");
//        gc.addActionToPropertyEvent("prop2", "action2");
//        gc.setPropertyToEntityInstance("goomba", "1", "xVelocity", "18");
//        gc.setPropertyToEntityInstance("goomba", "2", "xVelocity", "16");
//        System.out.println(gc.getMapOfEntityInstance("goomba","1").get("xVelocity"));
//        System.out.println(gc.getMapOfEntityInstance("goomba","2").get("xVelocity"));
//        assertTrue(gc.getMapOfEntityInstance("goomba","1").get("xVelocity") == 36
//        && gc.getMapOfEntityInstance("goomba","2").get("xVelocity") == 52);
//
//    }

    //TODO: TEST WITH 2 ACTIONS AFFECTING SAME ENTITY


}

/*        gc = new GameCreator();

                List<String> keyRight = new ArrayList<>();
        keyRight.add("D");

        List<String> keyLeft = new ArrayList<>();
        keyLeft.add("A");

        List<String> keyUp = new ArrayList<>();
        keyUp.add("W");

        List<String> keyDown = new ArrayList<>();
        keyDown.add("S");

        gc.createEntity("Mario");
        gc.createEntity("Floor");

        gc.createEntity("Goomba");

        gc.createEventPackage("moveRight");
        gc.createKeyInputCondition("right", keyRight);
        gc.createAction("xLocMove", "xPosition", "+", "10");
        gc.addKeyInputEventToPackage("moveRight", "right");
        gc.addActionToEventPackage("moveRight", "xLocMove");
        gc.addEventPackageToEntity("Goomba", "moveRight");


        gc.createEventPackage("moveLeft");
        gc.createKeyInputCondition("left", keyLeft);
        gc.createAction("xLocMoveL", "xPosition", "-", "10");
        gc.addKeyInputEventToPackage("moveLeft", "left");
        gc.addActionToEventPackage("moveLeft", "xLocMoveL");
        gc.addEventPackageToEntity("Goomba", "moveLeft");

        gc.createEventPackage("moveUp");
        gc.createKeyInputCondition("up", keyUp);
        gc.createAction("yLocMoveU", "yPosition", "-", "10");
        gc.addKeyInputEventToPackage("moveUp", "up");
        gc.addActionToEventPackage("moveUp", "yLocMoveU");
        gc.addEventPackageToEntity("Goomba", "moveUp");

        gc.createEventPackage("moveDown");
        gc.createKeyInputCondition("down", keyDown);
        gc.createAction("yLocMoveD", "yPosition", "+", "10");
        gc.addKeyInputEventToPackage("moveDown", "down");
        gc.addActionToEventPackage("moveDown", "yLocMoveD");
        gc.addEventPackageToEntity("Goomba", "moveDown");








        gc.createEntityInstance("Floor", "w1", "brick.png");
        gc.createEntityInstance("Floor", "w2", "brick.png");
        gc.createEntityInstance("Floor", "w3", "brick.png");
        gc.createEntityInstance("Floor", "w4", "brick.png");
        gc.createEntityInstance("Floor", "w5", "brick.png");
        gc.createEntityInstance("Floor", "w6", "brick.png");
        gc.createEntityInstance("Floor", "w7", "brick.png");
        gc.createEntityInstance("Floor", "w8", "brick.png");
        gc.createEntityInstance("Floor", "w9", "brick.png");
        gc.createEntityInstance("Floor", "w10", "brick.png");
        gc.createEntityInstance("Floor", "w11", "brick.png");
        gc.createEntityInstance("Floor", "w12", "brick.png");
        gc.createEntityInstance("Floor", "w13", "brick.png");
        gc.createEntityInstance("Floor", "w14", "brick.png");
        gc.createEntityInstance("Floor", "w15", "brick.png");
        gc.createEntityInstance("Floor", "w16", "brick.png");
        gc.createEntityInstance("Floor", "w17", "brick.png");
        gc.createEntityInstance("Floor", "w18", "brick.png");
        gc.createEntityInstance("Floor", "w19", "brick.png");
        gc.createEntityInstance("Floor", "w20", "brick.png");

        gc.setPropertyToEntityInstance("Floor","w1","xPosition",  "10");
        gc.setPropertyToEntityInstance("Floor","w1","yPosition",  "180");
        gc.setPropertyToEntityInstance("Floor","w2","xPosition",  "42");
        gc.setPropertyToEntityInstance("Floor","w2","yPosition",  "180");
        gc.setPropertyToEntityInstance("Floor","w3","xPosition",  "74");
        gc.setPropertyToEntityInstance("Floor","w3","yPosition",  "180");
        gc.setPropertyToEntityInstance("Floor","w4","xPosition",  "106");
        gc.setPropertyToEntityInstance("Floor","w4","yPosition",  "180");
        gc.setPropertyToEntityInstance("Floor","w5","xPosition",  "138");
        gc.setPropertyToEntityInstance("Floor","w5","yPosition",  "180");
        gc.setPropertyToEntityInstance("Floor","w6","xPosition",  "170");
        gc.setPropertyToEntityInstance("Floor","w6","yPosition",  "280");
        gc.setPropertyToEntityInstance("Floor","w7","xPosition",  "202");
        gc.setPropertyToEntityInstance("Floor","w7","yPosition",  "280");
        gc.setPropertyToEntityInstance("Floor","w8","xPosition",  "234");
        gc.setPropertyToEntityInstance("Floor","w8","yPosition",  "280");
        gc.setPropertyToEntityInstance("Floor","w9","xPosition",  "266");
        gc.setPropertyToEntityInstance("Floor","w9","yPosition",  "280");
        gc.setPropertyToEntityInstance("Floor","w10","xPosition",  "298");
        gc.setPropertyToEntityInstance("Floor","w10","yPosition",  "280");
        gc.setPropertyToEntityInstance("Floor","w11","xPosition",  "330");
        gc.setPropertyToEntityInstance("Floor","w11","yPosition",  "280");
        gc.setPropertyToEntityInstance("Floor","w12","xPosition",  "362");
        gc.setPropertyToEntityInstance("Floor","w12","yPosition",  "280");
        gc.setPropertyToEntityInstance("Floor","w13","xPosition",  "394");
        gc.setPropertyToEntityInstance("Floor","w13","yPosition",  "280");
        gc.setPropertyToEntityInstance("Floor","w14","xPosition",  "426");
        gc.setPropertyToEntityInstance("Floor","w14","yPosition",  "280");
        gc.setPropertyToEntityInstance("Floor","w15","xPosition",  "458");
        gc.setPropertyToEntityInstance("Floor","w15","yPosition",  "280");
        gc.setPropertyToEntityInstance("Floor","w16","xPosition",  "490");
        gc.setPropertyToEntityInstance("Floor","w16","yPosition",  "280");
        gc.setPropertyToEntityInstance("Floor","w17","xPosition",  "522");
        gc.setPropertyToEntityInstance("Floor","w17","yPosition",  "280");
        gc.setPropertyToEntityInstance("Floor","w18","xPosition",  "554");
        gc.setPropertyToEntityInstance("Floor","w18","yPosition",  "280");
        gc.setPropertyToEntityInstance("Floor","w19","xPosition",  "586");
        gc.setPropertyToEntityInstance("Floor","w19","yPosition",  "280");
        gc.setPropertyToEntityInstance("Floor","w20","xPosition",  "618");
        gc.setPropertyToEntityInstance("Floor","w20","yPosition",  "280");




        gc.createEntityInstance("Mario", "m0", "mario-head.png");
        gc.createEntityInstance("Goomba", "g1", "goomba.png");
        gc.createEntityInstance("Goomba", "g2", "goomba.png");
        gc.createEntityInstance("Goomba", "g3", "goomba.png");
        gc.createPropertyInEntity("Goomba", "name", "0");
        gc.createPropertyInEntity("Mario", "name", "0");


        gc.setPropertyToEntityInstance("Goomba","g2","xPosition",  "80");
        gc.setPropertyToEntityInstance("Goomba","g2","yPosition",  "140");

        gc.setPropertyToEntityInstance("Goomba","g3","xPosition",  "120");
        gc.setPropertyToEntityInstance("Goomba","g3","yPosition",  "140");




        gc.setPropertyToEntityInstance("Goomba","g1","name",  "1");
        gc.setPropertyToEntityInstance("Goomba","g2","name",  "2");
        gc.setPropertyToEntityInstance("Goomba","g3","name",  "3");
        gc.setPropertyToEntityInstance("Mario","m0","name",  "4");



        gc.setPropertyToEntityInstance("Mario","m0","xPosition",  "0");
        gc.setPropertyToEntityInstance("Mario","m0","yPosition",  "150");
        //gc.setPropertyToEntityInstance("Mario","m0","xVelocity",  "10");
        gc.setPropertyToEntityInstance("Goomba","g1","xPosition",  "60");
        gc.setPropertyToEntityInstance("Goomba","g1","yPosition",  "140");
        gc.createPropertyInEntity("Mario", "Health", "2");
        gc.createPropertyInEntity("Goomba", "Health", "2");
        gc.createAction("gainYVel", "yAcceleration", "=", "-4");
        gc.createAction("loseYVel", "xAcceleration", "=", "5");
        gc.createEventPackage("MarioCollideGoomba");
        gc.createCollisionCondition("goombaCollision", "Goomba");
        gc.addCollisionConditionToPackage("MarioCollideGoomba", "goombaCollision" );
        //gc.addActionToEventPackage("MarioCollideGoomba", "gainYVel" );
        gc.createEventPackage("GoombaCollideMario");
        gc.createCollisionCondition("marioCollision", "Mario");
        gc.addCollisionConditionToPackage("GoombaCollideMario", "marioCollision" );
        gc.addActionToEventPackage("GoombaCollideMario", "loseYVel" );
        gc.addEventPackageToEntity("Mario", "MarioCollideGoomba");
        gc.addEventPackageToEntity("Goomba", "GoombaCollideMario");*/

