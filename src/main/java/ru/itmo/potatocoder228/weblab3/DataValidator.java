package ru.itmo.potatocoder228.weblab3;

public class DataValidator implements ValidateData {
    public boolean validateX(double x) {
        return !(x < -4) && !(x > 4);
    }

    public boolean validateY(double y) {
        return !(y < -3) && !(y > 5);
    }

    public boolean validateR(double r) {
        return !(r < 1) && !(r > 4);
    }

    public boolean validateData(Shot shot){
        return validateX(shot.getX())&&validateY(shot.getY())&&validateR(shot.getR());
    }
}
