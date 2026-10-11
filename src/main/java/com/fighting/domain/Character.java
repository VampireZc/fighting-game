package com.fighting.domain;

public class Character {
    private String name;
    private int Hp;
    private int MaxHp;
    private int Attack;
    private int Defense;
    public int level;

    public Character() {
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Character(String name, int hp, int attack, int defense) {
        this.name = name;
        Hp = hp;
        MaxHp = hp;
        Attack = attack;
        Defense = defense;
        level = 1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHp() {
        return Hp;
    }

    public void setHp(int hp) {
        Hp = hp;
    }

    public int getMaxHp() {
        return MaxHp;
    }

    public void setMaxHp(int maxHp) {
        MaxHp = maxHp;
    }

    public int getAttack() {
        return Attack;
    }

    public void setAttack(int attack) {
        Attack = attack;
    }

    public int getDefense() {
        return Defense;
    }

    public void setDefense(int defense) {
        Defense = defense;
    }

    // 判断是否死亡
    public Boolean isAlive() {
        return this.Hp > 0;
    }

    // 恢复血量
    public void recover(int amount){
        this.Hp = this.Hp + amount;
        if (this.Hp > this.MaxHp) {
            this.Hp = this.MaxHp;
        }
    }

    //受到的伤害
    public void attack(int amount){
        this.Hp = this.Hp - amount;
        if (this.Hp < 0) {
            this.Hp = 0;
        }
    }

    //显示角色信息
    public String showInfo(){
        return "[角色名称：" + this.name + "，血量：" + this.Hp + "，攻击力：" + this.Attack + "，防御力：" + this.Defense + "]";
    }

}
