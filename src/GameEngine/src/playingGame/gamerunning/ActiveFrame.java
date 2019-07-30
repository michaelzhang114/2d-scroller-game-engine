package playingGame.gamerunning;
/**
 * @author: yao yuan
 */
public class ActiveFrame {
    public double leftx, rightx;
    //public double margin;
    public double freeleftx,freerightx;

    /**
     * constructor
     * @param framewidth
     * @param freeleftx
     * @param freerightx
     */
    public ActiveFrame(double framewidth, double freeleftx,double freerightx){
        leftx=0;
        rightx=framewidth;
        this.freerightx=freerightx;
        this.freeleftx=freeleftx;
    }


    /**
     *
     * @param newx
     * updates to be able to scroll
     */
    public void updatefreerightx(double newx){
        double diff=newx-freerightx;
        freerightx=newx;
        freeleftx+=diff;
        leftx+=diff;
        rightx+=diff;

    }
}
