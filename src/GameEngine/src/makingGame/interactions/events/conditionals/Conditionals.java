package makingGame.interactions.events.conditionals;

import java.util.ArrayList;
import java.util.Arrays;

//maybe rename condition
//essentially a signature
public class Conditionals {

    //i am not sure if final stop external access
    public final ArrayList<String> Signatures;

    /**this class conditionals is a signature of a type of conditionals
     * there are limited number of conditional types
     */
    public Conditionals(){
        Signatures=new ArrayList<>();
    }
    public Conditionals(String type){
        this();
        Signatures.add(type);
    }

    /**
     * add signature segments
     * @param sig_seg
     */
    public void addSignature(String sig_seg){
        Signatures.add(sig_seg);
    }

    /**
     * @param o
     * @return if o is a conditional and the same as this conditional
     */
    public boolean equals(Object o){
        if(o instanceof Conditionals){
            return this.equals((Conditionals) o);
        }
//        ArrayList<String> sig_i=c_i.Signatures;
        return false;
    }

    /**assume ci not null since this will only be called when there's actual conditionals
     * not any more since arraylist .equal implement it
     * @param c_i
     * @return if the two conditionals are the same
     */
    public boolean equals(Conditionals c_i){
        ArrayList<String> sig_i=c_i.Signatures;
        return Signatures.equals(sig_i);
    }

    /**
     * a way to print this conditional in understandable english
     * @return text description of the class, essentially the signature
     */
    public String toString(){
        return Signatures.toString();
    }

    /**
     * a way to compare two conditionals in some of the java util data structures, especially hashmap
     *
     * @return hashcode
     */
    public int hashCode(){
        int hash=0;
        return Signatures.hashCode();

    }

}
