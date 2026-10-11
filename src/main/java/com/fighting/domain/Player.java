package com.fighting.domain;

import java.util.ArrayList;

public class Player extends Character{
    public ArrayList<String> skills;
    public Player() {
        super();
        skills = new ArrayList<>();
    }

    public Player(String name, int hp, int attack, int defense) {
        super(name, hp, attack, defense);
        skills = new ArrayList<>();
    }

    // 显示技能
    public String showSkills() {
        return skills.toString();
    }
}
