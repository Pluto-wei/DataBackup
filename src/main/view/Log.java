package main.view;
import main.Util.UserDoa;
import java.util.Scanner;

public class Log {
    public static void main(String[] args) {
        //注册
        registered();
        //   登录
        boolean bl = false;
        bl = login();
        if (bl) {
            System.out.println("登录成功");

        } else {
            System.out.println("登录失败，请修改密码");
            caangepsws();   //修改秘密
        }
        withdraw();      //注销
    }
    //修改密码
    public static boolean caangepsws()
    {    System.out.println("是否要修改密码，,输入0为否定，其他数字为肯定");
        Scanner input = new Scanner(System.in);//用于接收各种数据类型,需要导入.util包。
        boolean b=false;
        int j = input.nextInt();//接收不同类型的数据
        if(j!=0)
        {
            String oldpsw = null;
            String newpsw = null;
            UserDoa userDoa = new UserDoa();
            System.out.println("请输入原始密码");
            Scanner scnne = new Scanner(System.in);
            oldpsw = scnne.nextLine();
            System.out.println("请输入新密码");
            newpsw = scnne.nextLine();
            b = UserDoa.caangepsw(oldpsw, newpsw);
        }
        else{
            System.out.println("欢迎下次使用");
        }
        return  true;
    }
    //登录
    public static boolean login() {
        boolean e = false;
        String username = null;
        String psw = null;
        UserDoa userDoa = new UserDoa();
        Scanner scnner = new Scanner(System.in);
        System.out.println("请输入用户名");

        username = scnner.nextLine();
        System.out.println("请输入密码");
        psw = scnner.nextLine();
        e = UserDoa.userLogin(username, psw);
        // System.out.println(e);
        return e;
    }

    //创建
    public static boolean registered()
    {
        System.out.println("注册");
        boolean w=false;
        String username = null;
        String psw = null;
        UserDoa userDoa = new UserDoa();
        Scanner scnner = new Scanner(System.in);
        System.out.println("请输入用户名");
        username = scnner.nextLine();
        System.out.println("请输入密码");
        psw = scnner.nextLine();
        w = UserDoa.UserRegisrer(username, psw);
        return w;
    }
    //注销
    public static   boolean withdraw()
    {        UserDoa userDoa = new UserDoa();
        boolean f=false;
        System.out.println("true 或者false");
        Scanner input = new Scanner(System.in);//用于接收各种数据类型,需要导入.util包。
        boolean j = input.nextBoolean();//接收不同类型的数据
        if(j)
        {
            System.out.println("开始注销");
            f= userDoa.withdraws();

        }
        return  true;

    }
}




