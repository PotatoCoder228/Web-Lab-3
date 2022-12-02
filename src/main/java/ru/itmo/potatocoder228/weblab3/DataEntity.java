package ru.itmo.potatocoder228.weblab3;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "shots_db")
public class DataEntity implements Serializable {
    @Id
    @SequenceGenerator(name = "jpaSequence", sequenceName = "JPA_SEQUENCE", allocationSize = 1)

    //Потому что PostgreSQL?
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Integer id;

    private Double x;
    private Double y;
    private Double r;
    private Boolean hitResult;

    //Обязательный пустой конструктор
    public DataEntity() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = Math.round(x * 1000.0) / 1000.0;
        ;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = Math.round(y * 1000.0) / 1000.0;
    }

    public Double getR() {
        return r;
    }

    public void setR(Double r) {
        this.r = Math.round(r * 1000.0) / 1000.0;
        ;
    }

    public Boolean getHitResult() {
        return hitResult;
    }

    public void setHitResult(Boolean hitResult) {
        this.hitResult = hitResult;
    }


    @Override
    public String toString() {
        return "Entry{" +
                "xValye=" + x +
                ", yValue=" + y +
                ", rValue=" + r +
                ", hitResult=" + hitResult +
                '}';
    }

    @Override
    public int hashCode() {
        return x.hashCode() + y.hashCode() +
                r.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof DataEntity) {
            DataEntity entryObj = (DataEntity) obj;
            return x.equals(entryObj.getX()) &&
                    y.equals(entryObj.getY()) &&
                    r.equals(entryObj.getR());
        }
        return false;
    }
}