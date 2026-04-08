package ru.yandex.practicum.sleeptracker;

public enum Chronotype {
    OWL ("СОВА"),
    LARK ("ЖАВОРОНОК"),
    PIGEON ("ГОЛУБЬ");

    private final String ruTitle;

    Chronotype(String ruTitle) {
        this.ruTitle = ruTitle;
    }

    public String getTitle() {
        return ruTitle;
    }
}
