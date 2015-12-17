package objects;

public class Instruction {
    private String opCode;
    public String output;

    public Instruction(String opCode) {
        this.opCode = opCode;
    }

    public Instruction() {

    }

    public String getOpCode() {
        return opCode;
    }
    public String getOutput() {
        return output;
    }
}
