package objects;

import static function.Functions.registerConverter;

public class JType extends Instruction {
	private String opCode;
    private String constant;

    //Commands: j, jal
    public JType(String _opCode, String _constant) {
        switch(_opCode){
        case "j":
        	opCode = "1100";
        	break;
        case "jal":
        	opCode = "1101";
        	break;
        default:
        	System.out.println("Error in JType");
        	break;
        }
        constant = String.format("%020d", Integer.parseInt(Integer.toString(Integer.parseInt(_constant),2)));
    }

    //Command: li
    public JType(String _opCode, String _liRegister, String _constant) {
        opCode = "1110";
        //constant = String.format("%020d", Integer.parseInt(_liRegister.concat(_constant)));// Think that's right, not entirely sure how to combine them.
        // I'm so sorry.
        String liRegisterTemp = String.format("%04d", Integer.parseInt(registerConverter(_liRegister)));
        String constantTemp = String.format("%016d",Integer.parseInt(Integer.toString(Integer.parseInt(_constant),2)));
        constant = liRegisterTemp.concat(constantTemp);
    }

    @Override
    public String getOutput(){
        return opCode.concat(constant);
    }
}