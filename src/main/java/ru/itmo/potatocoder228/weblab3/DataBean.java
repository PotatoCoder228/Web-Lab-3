package ru.itmo.potatocoder228.weblab3;

import javax.annotation.ManagedBean;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ManagedBean
@ApplicationScoped
public class DataBean implements Serializable {
    public static final String persistenceUnit = "shots_db";

    private Shot shot;
    private List<Shot> shots;
    private final DataValidator validator = new DataValidator();

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private EntityTransaction transaction;

    public DataBean(){
        shot = new Shot();
        shots = new ArrayList<>();

        connection();
    }

    private void connection() {
        entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnit);
        entityManager = entityManagerFactory.createEntityManager();
        transaction = entityManager.getTransaction();
    }

    private void loadEntries() {
        try {
            transaction.begin();
            Query query = entityManager.createQuery("SELECT e FROM Shot e");
            shots = query.getResultList();
            transaction.commit();
        } catch (RuntimeException exception) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw exception;
        }
    }
    public void addEntry() {
        try {
            transaction.begin();
            if(validator.validateData(shot)) {
                shot.checkHit();
                entityManager.persist(shot);
                shots.add(shot);
                shot = new Shot();
                transaction.commit();
            }else{
                throw new RuntimeException();
            }
        } catch (RuntimeException exception) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw exception;
        }
    }

    public void addEntryWithParameters(){
        System.out.println("Данные получены!");
        if(shot==null) shot=new Shot();
        try {
            Map<String, String> paramMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            transaction.begin();
            shot.setX(Double.parseDouble(paramMap.get("x")));
            shot.setY(Double.parseDouble(paramMap.get("y")));
            shot.setR(Double.parseDouble(paramMap.get("r")));
            System.out.println(paramMap.get("x"));
            System.out.println(paramMap.get("y"));
            System.out.println(paramMap.get("r"));
            if(validator.validateData(shot)) {
                shot.checkHit();
                entityManager.persist(shot);
                shots.add(shot);
                shot = new Shot();
                transaction.commit();
            }else{
                throw new RuntimeException();
            }
        } catch (RuntimeException exception) {
            System.out.println("Ошибка:" + exception.getMessage());
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw exception;
        }
    }
    public String clearEntries() {
        try {
            transaction.begin();
            Query query = entityManager.createQuery("DELETE FROM Shot ");
            query.executeUpdate();
            shots.clear();
            transaction.commit();
        } catch (RuntimeException exception) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw exception;
        }
        return "redirect";
    }

    public Shot getShot() {
        return shot;
    }

    public void setEntry(Shot shot) {
        this.shot = shot;
    }

    public List<Shot> getEntries() {
        return shots;
    }

    public void setEntries(List<Shot> shots) {
        this.shots = shots;
    }
}
