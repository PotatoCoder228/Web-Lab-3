package ru.itmo.potatocoder228.weblab3;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "shots_db")
public class Shot implements Serializable {
    @Id
    @SequenceGenerator(name = "jpaSequence", sequenceName = "JPA_SEQUENCE", allocationSize = 1)

    //Потому что PostgreSQL?
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "x", nullable = false)
    private Double x;
    @Column(name = "y", nullable = false)
    private Double y;
    @Column(name = "r", nullable = false)
    private Double r;
    @Column(name = "hitResult", nullable = false)
    private Boolean hitResult;

    //Обязательный пустой конструктор
    public Shot() {
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

    public void checkHit(){
        final double X = Math.abs(x);
        final double XR = r;
        final double YR = r / 2;
        hitResult =  (x * x) / (XR * XR) + (y * y) / (YR * YR) <= 1 && !((x - XR / 4) * (x - XR / 4) / (XR * 0.15 * XR * 0.15) + (y - YR) * (y - YR) / (YR * 0.8 * YR * 0.8) <= 1 ||
                (x) * (x) / (XR * 0.1 * XR * 0.1) + (y - YR) * (y - YR) / (YR * 0.3 * YR * 0.3) <= 1 ||
                (x - XR / 9) * (x - XR / 9) / (XR / 9 * XR / 9) + (y + YR) * (y + YR) / (YR * 0.6 * YR * 0.6) <= 1 ||
                (x - XR / 3.2) * (x - XR / 3.2) / (XR / 9 * XR / 9) + (y + YR) * (y + YR) / (YR * 0.8 * YR * 0.8) <= 1);
    }

    @Override
    public String toString() {
        return "Shot{" +
                "X=" + x +
                ", Y=" + y +
                ", R=" + r +
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
        if (obj instanceof Shot) {
            Shot shotObj = (Shot) obj;
            return x.equals(shotObj.getX()) &&
                    y.equals(shotObj.getY()) &&
                    r.equals(shotObj.getR());
        }
        return false;
    }
}