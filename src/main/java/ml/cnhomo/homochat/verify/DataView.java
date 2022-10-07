package ml.cnhomo.homochat.verify;

public class DataView {
    private byte[] data;
    private int index = 0;

    public void move(int length){
        this.index += length;
    }
    public void setIndex(int index){
        this.index = index;
    }

    public byte[] getData(){
        return data;
    }

    public void addLong(long... l){
        for(long Long : l){
            data[index] = (byte) (Long & 0xFF);
            data[index + 1] = (byte) ((Long>>8) & 0xFF);
            data[index + 2] = (byte) ((Long>>16) & 0xFF);
            data[index + 3] = (byte) ((Long>>24) & 0xFF);
            data[index + 4] = (byte) ((Long>>32) & 0xFF);
            data[index + 5] = (byte) ((Long>>40) & 0xFF);
            data[index + 6] = (byte) ((Long>>48) & 0xFF);
            data[index + 7] = (byte) ((Long>>56) & 0xFF);
            index += 8;
        }
    }
    public void addInt(int... i){
        for(int Int : i){
            data[index] = (byte) (Int & 0xFF);
            data[index + 1] = (byte) ((Int>>8) & 0xFF);
            data[index + 2] = (byte) ((Int>>16) & 0xFF);
            data[index + 3] = (byte) ((Int>>24) & 0xFF);
            index += 4;
        }
    }
    public void addByte(byte... b){
        for(byte Byte : b){
            data[index] = Byte;
            index++;
        }
    }
    public void addString(String s){
        byte[] tmp = s.getBytes();
        for(byte b : tmp){
            addByte(b);
        }
    }
}
