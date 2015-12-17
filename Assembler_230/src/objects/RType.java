package objects;
import function.Main;

import static function.Functions.*;

public class RType extends Instruction{
	private String opCode;
    private String cond;
    private String s;
    private String opx;
    private String regD;
    private String regS;
    private String regT;

    // Commands: jr
    public RType(String _opCode, String _regS) {
        opCode = "0001";
        cond = "0000";
        s = "1";
        opx = "000";
        regD = "0000";
        regS = registerConverter(_regS);
        regT = "0000";
        Main.pc++;
    }

    //Commands: cmp
    public RType(String _opCode, String _regS, String _regT) {
        opCode = "0010";
        cond = "0000";
        s = "1";
        opx = "000";
        regD = "0000";
        regS = registerConverter(_regS);
        regT = registerConverter(_regT);
        Main.pc++;
    }

    //Commands: add, sub, and, or, xor, sll
    public RType(String _opCode, String _regD, String _regS, String _regT) { // add, sub, and, or, xor, cmp
        opCode = "0000";
        cond = "0000";
        s = "1";
        switch(_opCode.toLowerCase()){ // Selecting the correct opx depending on command
        	case "sll": opx = "000";
        	break;
        	case "add": opx = "100";
            break;
            case "sub": opx = "011";
            break;
            case "and": opx = "111";
            break;
            case "or": opx = "110";
            break;
            case "xor": opx = "101";
            break;
            default: System.out.println("Error in RType");
        }
        regD = registerConverter(_regD);
        regS = registerConverter(_regS);
        regT = registerConverter(_regT);
        Main.pc++;
    }

    @Override
    public String getOutput(){
    	// Pretty huh? I don't know of a good way to concat a bunch of strings.
    	return opCode.concat(cond).concat(s).concat(opx).concat(regD).concat(regS).concat(regT);
    }
    @Override
    public int getPcState(){
        return pcState;
    }
}