package com.fighting.domain;

import java.util.ArrayList;

public class Player extends Character{
    public ArrayList<String> skills;
    public ArrayList<Consumable> packageList;

    private int mp; // 添加mp属性
    private int mpMax;

    public Player() {
        super();
        skills = new ArrayList<>();
        packageList = new ArrayList<>();
    }

    public int getMp() {
        return mp;
    }

    public void setMp(int mp) {
        this.mp = mp;
    }

    public int getMpMax() {
        return mpMax;
    }

    public void setMpMax(int mpMax) {
        this.mpMax = mpMax;
    }

    public Player(String name, int hp, int attack, int defense, int mp) {
        super(name, hp, attack, defense);
        this.mp = mp;
        this.mpMax = mp;
        skills = new ArrayList<>();
        packageList = new ArrayList<>();
    }

    @Override
    public String showInfo(){
        return "[角色名称：" + super.getName() + "，血量：" + this.getHp() + "，攻击力：" + this.getAttack() + "，防御力：" + this.getDefense() + "，蓝条：" + this.getMp() + "]";
    }

    // 显示技能
    public String showSkills() {
        return skills.toString();
    }

    // 显示包内物品
    public String showPackage() {
        return packageList.toString();
    }
}
