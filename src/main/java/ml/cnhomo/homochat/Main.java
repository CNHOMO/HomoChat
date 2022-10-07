package ml.cnhomo.homochat;

import ml.cnhomo.homochat.socket.ChatWebSocket;
import ml.cnhomo.homochat.verify.DataView;
import ml.cnhomo.homochat.verify.StringHash;

import java.io.Console;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

public class Main {
    static ChatWebSocket ws;

    static final long majorVersion = 10001;

    static final long minorVersion = 0;

    public static void main(String[] args) throws URISyntaxException {
        chooseServer();
    }
    private static void chooseServer() throws URISyntaxException {
        //选择服务器
        System.out.println("选择连接服务器：（使用honochat.cmhomo.ml）");
        Scanner scanner = new Scanner(System.in);
        String URI = "ws://" + scanner.nextLine();
        ws = new ChatWebSocket(new URI(URI));
        //登录
        System.out.println("登录你的账户：");
        System.out.print("用户名：");
        String username = scanner.nextLine();
        System.out.print("密码：");
        String password = scanner.nextLine();
        login(StringHash.getHash(username,"SHA-512"),StringHash.getHash(password,"SHA-512"));
    }

    private static void login(String usernameHash, String passwordHash){
        DataView dv = new DataView();

        String loginData = usernameHash + "=" + passwordHash;

        //数据头(34字节): 时间(long)+操作类型(byte)+客户端版本(string)+数据长度(int)+数据开始标识(byte)
        dv.addLong(System.currentTimeMillis());//时间 8
        dv.addByte((byte) 0, (byte) 2);//登录操作 2
        dv.addLong(majorVersion, minorVersion);//客户端版本 16
        dv.addInt(loginData.length());//数据长度 4
        dv.addByte((byte) 114, (byte) 51, (byte) 4, (byte) 191);//数据开始标识 4

        //数据(114514字节)
        dv.addString(loginData);

        //数据尾(260字节)：数据结束标识(byte)+数据hash(string)
        dv.addByte((byte) 81, (byte) 0, (byte) 11, (byte) 45);
        dv.addString(StringHash.getHash(loginData,"SHA-256"));

        //发送数据
        ws.send(dv.getData());
    }
}