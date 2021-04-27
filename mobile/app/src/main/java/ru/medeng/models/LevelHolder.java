package ru.medeng.models;

import ru.medeng.models.user.AccessLevel;

public class LevelHolder {
    private AccessLevel level;

    public AccessLevel getLevel() {
        return level;
    }

    public void setLevel(AccessLevel level) {
        this.level = level;
    }
}
