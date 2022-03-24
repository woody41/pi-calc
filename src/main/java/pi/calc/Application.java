package pi.calc;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Application {

    public static void main(String[] args) {
        Application app = new Application();
    }

    private static final BigDecimal _r = BigDecimal.ONE; // Static radius = 1 
    private static final Long _interval = 10L; //Set timeout between calculactions in ms
    private static final MathContext _round = new MathContext(10000); //Number of decimals 
    private BigInteger recursiveLevel = BigInteger.ZERO; //BigInteger -> If someone wants to tryhard Pi calculations
    
    public Application() {
        BigDecimal initP = BigDecimal.valueOf(1.0).pow(2, this._round).add(BigDecimal.valueOf(1.0).pow(2, this._round)).sqrt(this._round); //sqrt(1^2+1^2)
        CalcPI(initP, BigInteger.valueOf(4L)); //Initial values sqrt(1^2+1^2) and 4 lines (square)
    }

    public void CalcPI(BigDecimal P, BigInteger countRec) {
        this.recursiveLevel = this.recursiveLevel.add(BigInteger.ONE); //We are running this function
        
        BigDecimal Rx = this._r.pow(2, this._round)
                .subtract(P.divide(BigDecimal.valueOf(2.0), this._round).pow(2, this._round), this._round)
                .sqrt(this._round);//sqrt(_r^2-(P/2)^2)
        BigDecimal Ry = this._r.subtract(Rx, this._round);
        BigDecimal b = Ry.pow(2, this._round)
                .add(P.divide(BigDecimal.valueOf(2.0), this._round).pow(2, this._round), this._round)
                .sqrt(this._round); //sqrt(Ry^2+(P/2)^2)

        BigDecimal pi = b.multiply(new BigDecimal(countRec), this._round)
                .divide(_r, this._round); //b * countRec / _r
        System.err.println("¦ Recursive level count: " + recursiveLevel.toString() + " Lines: " + countRec + " Pi: " + pi.toString());

        countRec = countRec.multiply(BigInteger.TWO);
        try {
            Thread.sleep(this._interval);
        } catch (InterruptedException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }
        CalcPI(b, countRec);
    }

}
