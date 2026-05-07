package edu.hitsz.prop;

import edu.hitsz.application.ImageManager;
import edu.hitsz.observer.Observer;
import edu.hitsz.observer.Subject;
import java.util.ArrayList;
import java.util.List;

public class BombProp extends AbstractProp implements Subject {

    private List<Observer> observerList = new ArrayList<>();

    public BombProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
        this.image = ImageManager.PROP_BOMB_IMAGE;
    }

    @Override
    public void registerObserver(Observer observer) {
        observerList.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observerList.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observerList) {
            observer.updateBomb();
        }
    }

    @Override
    public void active() {
        System.out.println("炸弹生效！");
        notifyObservers();
    }
}


