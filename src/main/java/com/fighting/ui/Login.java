package com.fighting.ui;

import com.fighting.Tool.Tool;
import com.fighting.domain.User;

import java.util.Scanner;

public class Login {

    // 启动登录界面
    public void start(){
        System.out.println("游戏登录注册页面打开了~");

        while (true) {
            System.out.println("╔════════════════════════════════╗");
            System.out.println("    🎮 欢迎来到文字格斗游戏 🎮   ");
            System.out.println("╚════════════════════════════════╝");
            System.out.println("请选择操作：1登录 2注册 3退出");

            Scanner sc = new Scanner(System.in);
            int choice = sc.nextInt();

            switch (choice){
                case 1 -> login();
                case 2 -> register();
                case 3 -> {
                    System.out.println("选择了退出操作");
                    System.exit(0);
                    return;
                }
                default -> System.out.println("输入有误，请重新选择！");
            }
        }
    }

    // 登录
    public void login(){
        System.out.println("选择了登录操作");
        System.out.println("请输入用户名：");
        Scanner sc = new Scanner(System.in);
        String username = sc.nextLine();
        System.out.println("请输入密码：");
        String password = sc.nextLine();
        User user = Tool.findUser(username);

        if (user == null){
            System.out.println("用户不存在，请先注册~");
            return;
        }

        if (!user.getState()){
            System.out.println("用户已经锁定，不允许登录~");
            return;
        }

        // 校验
        if (!Tool.checkUser(username, password)){
            Tool.loginTime--;
            System.out.println("账户或密码错误，还剩"+ (Tool.loginTime) +"次机会！");
            if (Tool.loginTime <=0){
                System.out.println("登录次数过多，账号已锁定！");
                user.setState(false); // 锁定用户
            }
            return;
        }

        // 校验验证码
        Tool.generateCode();
        String code = sc.nextLine();
        if (!Tool.checkCode(code)){
            System.out.println("验证码错误，请重新登录！");
            Tool.code = new StringBuilder();
            return;
        }

        System.out.println("登录成功！");

        Tool.loginTime = 3;
    }
    // 注册
    public void register(){
        System.out.println("选择了注册操作");
        System.out.println("请输入3-16位用户名：");
        Scanner sc = new Scanner(System.in);
        String username = sc.nextLine();
        // 校验用户名
        if(Tool.checkUsername(username)) {
            System.out.println("请输入3-8位密码：");
            String password = sc.nextLine();
            // 校验密码
            if(Tool.checkPassword(password)) {
                User user = new User(username, password);
                Tool.users.add(user);
                System.out.println("注册成功,可以去登录了~");
            }
        }
    }

}
