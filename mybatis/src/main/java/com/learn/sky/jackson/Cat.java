package com.learn.sky.jackson;

/**
 * @Date: 2018/5/14 下午9:10
 */
public class Cat extends Animal {
    boolean likesCream;
    public int lives;

    public Cat() {
    }

    public boolean isLikesCream() {
        return likesCream;
    }

    public void setLikesCream(boolean likesCream) {
        this.likesCream = likesCream;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }
}