package de.sollder1.slage;

public final class SlageApi {

    private static SlageApi instance;

    private SlageApi(){
        //TODO: maybe some pre init stuff?
    }

    public synchronized static SlageApi getInstance() {
        if(instance == null) {
            instance = new SlageApi();
        }
        return instance;
    }


}
