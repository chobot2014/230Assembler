package objects;
import static function.Functions.*;

public class DType extends Instruction{
    private String opCode;
	private String cond;
    private String s;
    private String immediate;
    private String regS;
    private String regT;

    //Commands: si
    public DType(String _opCode, String _s) {
        opCode = "0111";
        cond = "0000";
        s = _s;
        immediate = "0000000";
        regS = "0000";
        regT = "0000";
    }

    //Commands: lw, sw
    public DType(String _opCode, String _regS, String _regT) {
    	switch(_opCode){
	    	case "lw":
	    		opCode = "0100";
	    		break;
	    	case "sw":
	    		opCode = "0101";
	    		break;
	    	default:
	    		System.out.println("Error in DType");
	    		break;
    	}
        cond = "0000";
        s = "0";
        immediate = "0000000";
        regS = registerConverter(_regS);
        regT = registerConverter(_regT);
    }

    //Commands: addi, lw, sw
     public DType(String _opCode, String _regS, String _regT, String _constantVal) {

        String destReg = _regS;
        String wRegt = _constantVal;

        if(_opCode.equalsIgnoreCase("addi")) {
            opCode = "0110";
            cond = "0000";
            s = "0";
            immediate = String.format("%07d", Integer.parseInt(Integer.toBinaryString(Integer.parseInt(_constantVal))));
            regS = registerConverter(_regS);
            regT = registerConverter(_regT);
        }else if(_opCode.equalsIgnoreCase("lw")){
            String offset = String.format("%07d", Integer.parseInt(Integer.toBinaryString(Integer.parseInt(_regT))));
            opCode = "0100";
            cond = "0000";
            s = "0";
            immediate = String.format("%07d",Integer.parseInt(offset));
            regS = registerConverter(destReg);
            regT = registerConverter(wRegt);
        }else if(_opCode.equalsIgnoreCase("sw")){
            String offset = String.format("%07d", Integer.parseInt(Integer.toBinaryString(Integer.parseInt(_regT))));
            opCode = "0101";
            cond = "0000";
            s = "0";
            immediate = String.format("%07d",Integer.parseInt(offset));
            regS = registerConverter(destReg);
            regT = registerConverter(wRegt);
        }
    }

    @Override
    public String getOutput(){
        return opCode.concat(cond).concat(s).concat(immediate).concat(regS).concat(regT);
    }
}