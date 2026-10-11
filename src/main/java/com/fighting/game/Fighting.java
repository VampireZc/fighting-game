package com.fighting.game;

import com.fighting.domain.Consumable;
import com.fighting.domain.Enemy;
import com.fighting.domain.Player;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Fighting {
    String username;
    // 整个游戏共用一个 Scanner，避免每次 new 都会预读缓冲、丢弃未消费的输入导致错位
    private final Scanner sc = new Scanner(System.in);

    public Fighting() {
    }

    public Fighting(String username) {
        this.username = username;
    }

    //启动游戏
    public void start(){
        System.out.println("╔════════════════════════════════╗");
        System.out.println("🎮"+ username +" 欢迎来到文字格斗游戏 🎮   ");
        System.out.println("╚════════════════════════════════╝");
        createRole();

    }

    // 创建角色
    public void createRole(){
        System.out.println("创建你的角色：");
        String name = sc.nextLine();
        System.out.println("创建成功，你的角色名是：" + name);
        System.out.println("请分配你的属性点（共25点）");

        int points = 25;
        String[] attributePoints = {"生命值","攻击力","防御力", "蓝条"};
        int[] val = new int[4];
        for (int i = 0; i < attributePoints.length; i++) {
            String attribute = attributePoints[i];
            System.out.println("请输入" + attribute + "的值（剩余" + points + "点）：");
            int input = sc.nextInt();
            sc.nextLine(); // 消费掉回车换行，避免后续 nextLine 读到空串

            if (input < 0){
                System.out.println("输入的属性点不能为负数，分配0点给" + attribute + "。");
                input = 0;
            }

            if (input > points){
                System.out.println("输入的属性点超过剩余点数，全部点数分配给:" + attribute + "。");
                input = points;
            }

            points -= input;
            val[i] = input;
        }

        Player player = new Player(
                name,  // 角色名字
                100 + val[0] * 10,  // 生命值
                10 + val[1] * 2,  // 攻击力
                0 + val[2] * 1,  // 防御力
                10 + val[3] * 10 // 蓝条
        );

        // 添加技能
        player.skills.add("普通攻击");
        player.skills.add("强力一击");
        player.skills.add("生命涉取");

        System.out.println("创建角色成功!");
        System.out.println("\uD83C\uDF1F 初始属性:" + player.showInfo());
        System.out.println("\uD83C\uDF1F 初始技能:" + player.showSkills());


        // 创建敌人列表
        ArrayList<Enemy> enemieList = new ArrayList<>();
        enemieList.add(new Enemy("初级战士", 80, 15, 10, "猛击"));
        enemieList.add(new Enemy("敏捷刺客", 60, 20, 5, "快速攻击"));
        enemieList.add(new Enemy("重装坦克", 120, 10, 20, "防御姿态"));
        enemieList.add(new Enemy("神秘法师", 70, 25, 8, "火球术"));

        battle(player, enemieList); // 开始战斗
    }

    // 战斗
    public void battle(Player player, ArrayList<Enemy> enemieList){
        int count = 1;
        int wins = 0;

        while (player.isAlive()){

            if (wins != 0){ // 每胜一局，敌人属性提升
                for (int i = 0; i < enemieList.size(); i++) {
                    Enemy c = enemieList.get(i);
                    c.setMaxHp(c.getMaxHp() + 10);
                    c.setHp(c.getMaxHp());
                    c.setAttack(c.getAttack() + 5);
                    c.setDefense(c.getDefense() + 2);
                    c.setDefending(false); // 设置敌人不处于防御状态
                }
            }

            // 随机一个敌人进行战斗
            Random random = new Random();
            int randomIndex = random.nextInt(enemieList.size());
            Enemy enemy = enemieList.get(randomIndex);
            System.out.println(enemy.showInfo());

            System.out.println("═══════════════════════════════════════\n");
            System.out.println(" \uFE0F 第" + count + "轮战斗, [" + enemy.getName() + "]出现!");

            // 战斗循环
            int round = 1;
            while (player.isAlive()){
                System.out.println("---------------------------------------");
                System.out.println(" \uFE0F 第" + round + "回合开始");
                System.out.println(showHpBar(player.getName(), player.getHp(), player.getMaxHp()));
                System.out.println(showHpBar(enemy.getName(), enemy.getHp(), enemy.getMaxHp()));
                // 玩家回合
                playerTurn(player, enemy);

                if (!enemy.isAlive()){
                    System.out.println(enemy.getName() + "倒下了！");
                    wins++;
                    // 每打败一个敌人恢复蓝条30%
                    player.setMp((int)(player.getMpMax() * 1.3));
                    System.out.println("\uD83C\uDF89 恭喜你，赢得了第" + count + "轮战斗！");
                    System.out.println("当前属性：" + player.showInfo());
                    //获取奖励
                    getReward(player);
                    break;
                }

                // 敌人回合
                enemyTurn(enemy, player);

                if (!player.isAlive()){
                    System.out.println(player.getName() + "倒下了！");
                    System.out.println("\u2764\uFE0F 游戏结束，你输了！");
                    break;
                }
                round++;

            }

            //玩家胜利增加属性
            if (player.isAlive()){
                int healHP = random.nextInt(21) + 20;
                player.recover(healHP);
                System.out.println("\uD83D\uDC9A 你恢复了" + healHP + "点血量。");
                System.out.println("当前顺利" + wins + "场！");
            }

            // 每顺利三局提升角色属性
            if (player.isAlive() && wins % 3 == 0){
                player.setLevel(player.getLevel() + 1);
                player.setMaxHp(player.getMaxHp() + 20);
                player.setAttack(player.getAttack() + 5);
                player.setDefense(player.getDefense() + 3);
                System.out.println("\uD83D\uDCAA 你成功通过了三局，角色等级提升！当前等级：" + player.getLevel());
                System.out.println("当前属性：" + player.showInfo());
            }

            if (player.isAlive()){
                System.out.println("是否继续战斗？(y/n)");
                String choice = sc.nextLine();
                if ("y".equalsIgnoreCase(choice)){
                    count++;
                    continue; // 继续下一轮战斗
                }else if ("n".equalsIgnoreCase(choice)){
                    System.out.println("游戏结束，感谢游玩！");
                    break;
                }else {
                    System.out.println("无效的选择，默认继续战斗。");
                    count++;
                    continue; // 继续下一轮战斗
                }
            }

        }
    }

    private void getReward(Player player) {
        String[] rewards = {"\uD83C\uDFC6 桃子（1）", "\uD83D\uDCB0 煎蛋（2）", "\uD83D\uDCBB 花酿鸡（3）", "\uD83D\uDE0D 黑鲈鱼（4）", "\uD83D\uDE0E 烤乳猪（5）"};
        int[] numList = {10, 20, 30, 40, 50};
        // 有30%的概率可以获得上面奖励，并恢复对应的血量
        Random random = new Random();
        int num = random.nextInt(10);
        if (num < 10){
            int rewardIndex = random.nextInt(rewards.length);
            String reward = rewards[rewardIndex];
            int numValue = numList[rewardIndex];
            player.packageList.add(new Consumable(reward, numValue));
            System.out.println("\uD83C\uDF89 恭喜你获得了" + reward + "，可以恢复" + numValue + "点血量。");
        }
    }

    private void enemyTurn(Enemy enemy, Player player) {
        System.out.println("===== "+ enemy.getName() +"的回合 =====");
        String action = "普通攻击";
        Random r = new Random();
        int num = r.nextInt(11);

        if (num >= 5){
            action = enemy.getSkill();
        }

        switch (action){
            case "普通攻击":
                int damage = calculateDamage(enemy.getAttack(), player.getDefense());
                System.out.println(enemy.getName() + "对你使用了[普通攻击]" + "造成了" + damage + "点伤害。");
                player.attack(damage);//扣血
                break;
            case "猛击":
                int damage2 = calculateDamage((int)(enemy.getAttack() * 1.5), player.getDefense());
                System.out.println(enemy.getName() + "对你使用了[猛击]" + "造成了" + damage2 + "点伤害。");
                player.attack(damage2);//扣血
                break;
            case "快速攻击":
                int damage3 = 0;
                for (int i = 0; i < 2; i++) {
                 int temp = calculateDamage(enemy.getAttack() / 2, player.getDefense());
                 damage3 += temp;
                }
                System.out.println(enemy.getName() + "对你使用了[快速攻击]" + "造成了" + damage3 + "点伤害。");
                player.attack(damage3);//扣血
                break;
            case "防御姿态":
                enemy.setDefending(true);
                System.out.println(enemy.getName() + "进入了[防御姿态]。");
                break;
            case "火球术":
                int damage4 = calculateDamage((int)(enemy.getAttack() * 1.8), player.getDefense());
                System.out.println(enemy.getName() + "对你使用了[火球术]" + "造成了" + damage4 + "点伤害。");
                player.attack(damage4);//扣血
                break;
        }

    }

    // 显示血条
    public String showHpBar(String name, int hp, int MaxHp){
        int field = 20;
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(": [");
        int bars = (int) (hp * 1.0 / MaxHp * field); // 需要的进度条块数
        for (int i = 0; i < field; i++) {
            if(i < bars)
                sb.append("█");
            else
                sb.append(" ");
        }
        sb.append("] " + hp + "/" + MaxHp + "HP");
        return sb.toString();
    }

    // 玩家回合
    public void playerTurn(Player player, Enemy enemy){
        System.out.println("===== 你的回合 =====");
        System.out.println("1. 普通攻击");
        System.out.println("2. 强力一击");
        System.out.println("3. 生命涉取");
        System.out.println("4. 使用道具");
        int choice;
        while (true) {
            choice = sc.nextInt();
            sc.nextLine(); // 消费掉回车换行
            if (choice >= 1 && choice <= 4) {
                break;
            }
            System.out.println("无效的选择，请输入 1-4：");
        }

        switch (choice){
            case 1:
                player.setMp(player.getMp() - 10);
                if (player.getMp() < 10) System.out.println("没有足够的蓝条，无法使用技能。");
                int damage1 = calculateDamage(player.getAttack(), enemy.getDefense());
                System.out.println(player.getName() + "对" + enemy.getName() + "使用了[普通攻击]" + "造成了" + damage1 + "点伤害。");
                enemy.attack(damage1);//扣血
                break;
            case 2:
                player.setMp(player.getMp() - 10);
                if (player.getMp() < 10) System.out.println("没有足够的蓝条，无法使用技能。");
                if (player.getHp() > 10){
                    player.attack(10); // 扣血
                    int damage2 = calculateDamage((int)(player.getAttack() * 1.8), enemy.getDefense());
                    System.out.println("\uD83D\uDCA5 消耗10HP,你对" + enemy.getName() + "使用了[强力一击]" + "造成了" + damage2 + "点伤害。");
                    enemy.attack(damage2);//扣血
                }else {
                    System.out.println("没有足够的HP，无法使用强力一击。");
                }
                break;
            case 3:
                player.setMp(player.getMp() - 10);
                if (player.getMp() < 10) System.out.println("没有足够的蓝条，无法使用技能。");
                if (player.getHp() > 10){
                    player.attack(10); // 扣血
                    Random r = new Random();
                    int heal = r.nextInt(21);
                    player.recover(heal);// 恢复血量
                    System.out.println("\uD83D\uDC9A 消耗10HP,你使用了[生命涉取]" + "恢复了" + heal + "点血量。");
                }else {
                    System.out.println("没有足够的HP，无法使用生命涉取。");
                }
                break;
            case 4:
                if (player.packageList.isEmpty()) {
                    System.out.println("你的背包是空的，无法使用道具。");
                    break;
                }
                System.out.println("你的背包中有：");
                for (int i = 0; i < player.packageList.size(); i++) {
                    Consumable item = player.packageList.get(i);
                    System.out.println((i + 1) + ". " + item.getName() + "（恢复" + item.getNum() + "点血量）");
                }
                System.out.println("请选择要使用的道具(按序号即可)：");
                int itemIndex = sc.nextInt();
                sc.nextLine(); // 消费掉回车换行
                if (itemIndex < 1 || itemIndex > player.packageList.size()) {
                    System.out.println("无效的序号，道具使用失败。");
                    break;
                }
                Consumable used = player.packageList.remove(itemIndex - 1);
                player.recover(used.getNum());
                System.out.println("\uD83C\uDF81 你使用了" + used.getName() + "，恢复了" + used.getNum() + "点血量。");
                break;
        }
    }

    //计算伤害
    public int calculateDamage(int attack, int defense){
        int damage = attack - defense;
        if (damage < 0) damage = 1;
        return damage;
    }
}
