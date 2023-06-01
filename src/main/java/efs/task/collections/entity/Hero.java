package efs.task.collections.entity;

import java.util.Objects;

public class Hero {
    private String name;
    private String heroClass;

    public Hero(String name, String heroClass) {
        this.name = name;
        this.heroClass = heroClass;
    }

    public String getName() {
        return name;
    }

    public String getHeroClass() {
        return heroClass;
    }

    //TODO implementacja metody equal porównująca obiekty Hero na podstawie pól name i heroClass.


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hero hero)) return false;

        if (!Objects.equals(name, hero.name)) return false;
        return Objects.equals(heroClass, hero.heroClass);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (heroClass != null ? heroClass.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "My name is " + name + "and I am " + heroClass;
    }
}
