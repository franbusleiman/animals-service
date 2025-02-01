package com.liro.animals.dto.mappers;

public enum Liveness {
    ALIVE(false),
    DEATH(true),
    ALL(null);

    private final Boolean liveness;

     Liveness(Boolean liveness){
        this.liveness = liveness;
    }

    public Boolean liveness()
    {
        return this.liveness;
    }
}
