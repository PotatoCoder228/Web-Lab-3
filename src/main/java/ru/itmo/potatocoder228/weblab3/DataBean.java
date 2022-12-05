package ru.itmo.potatocoder228.weblab3;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.System.out;

@Model
@ApplicationScoped
public class DataBean implements Serializable {
    public static final String persistenceUnit = "shots_db";
    private final DataValidator validator = new DataValidator();
    private Shot shot;
    private List<Shot> shots;
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private EntityTransaction transaction;
    private double rMin = 0.25;
    private double rMax = 4;

    public DataBean() {
        shot = new Shot();
        shots = new ArrayList<>();

        connection();
    }

    public double getrMin() {
        return rMin;
    }

    public void setrMin(double rMin) {
        this.rMin = rMin;
    }

    public double getrMax() {
        return rMax;
    }

    public void setrMax(double rMax) {
        this.rMax = rMax;
    }

    private void connection() {
        entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnit);
        entityManager = entityManagerFactory.createEntityManager();
        transaction = entityManager.getTransaction();
    }

    private void loadShots() {
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

    public void addShot() {
        try {
            transaction.begin();
            shot.checkHit();
            entityManager.persist(shot);
            shots.add(shot);
            shot = new Shot();
            transaction.commit();
        } catch (RuntimeException exception) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw exception;
        }
    }

    public void addShotWithParameters() {
        out.println("Данные получены!");
        if (shot == null) shot = new Shot();
        try {
            Map<String, String> paramMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            transaction.begin();
            shot.setX(Double.parseDouble(paramMap.get("x")));
            shot.setY(Double.parseDouble(paramMap.get("y")));
            shot.setR(Double.parseDouble(paramMap.get("r")));
            out.println(paramMap.get("x"));
            out.println(paramMap.get("y"));
            out.println(paramMap.get("r"));
            shot.checkHit();
            entityManager.persist(shot);
            shots.add(shot);
            shot = new Shot();
            transaction.commit();
        } catch (RuntimeException exception) {
            out.println("Ошибка:" + exception.getMessage());
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw exception;
        }
    }

    public String clearShots() {
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

    public void setShot(Shot shot) {
        this.shot = shot;
    }

    public List<Shot> getShots() {
        return shots;
    }

    public void setShots(List<Shot> shots) {
        this.shots = shots;
    }
}
