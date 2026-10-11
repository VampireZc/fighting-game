package com.fighting.domain;

public class Enemy extends Character{
    private String skill;
    private Boolean defending; // 是否正在防御
    public Enemy() {
    }

    public Enemy(String name, int hp, int attack, int defense, String skill) {
        super(name, hp, attack, defense);
        this.skill = skill;
        this.defending = false;
    }

    public Boolean getDefending() {
        return defending;
    }

    public void setDefending(Boolean defending) {
        this.defending = defending;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    // 受到伤害减半
    @Override
    public void attack(int amount){
        if (this.defending){
            amount = Math.max(1, amount / 2);
            this.defending = false;
        }

        super.attack(amount); // 扣血

    }

}
