package RayTracing;

/**
There's a little bit of abuse here... this class refers to either vector and point
 **/

public class Ray { // Ray := freePart + t*tCoefficient
    public vector tCoefficient;
    public vector freePart;

    public Surfaces surface;
    public double smallest_t;
    public int recLevel;
    public Surfaces prevSurface;

    public Ray(vector tCoefficient, vector freePart) {
        this.tCoefficient = tCoefficient;
        this.freePart = freePart;
        this.recLevel = 1;
        this.prevSurface = null;
    }

    public Ray(vector tCoefficient, vector freePart, int recLevel) {
        this.tCoefficient = tCoefficient;
        this.freePart = freePart;
        this.recLevel = recLevel;
        this.prevSurface = null;
    }

    public Ray(vector tCoefficient, vector freePart, int recLevel, Surfaces prevSurface) {
        this.tCoefficient = tCoefficient;
        this.freePart = freePart;
        this.recLevel = recLevel;
        this.prevSurface = prevSurface;
    }

    public Surfaces getSur() {
        return surface;
    }

    public void setSur(Surfaces sur) {
        this.surface = sur;
    }

    //returns the first intersection point of the ray
    public vector getPoint(){
        return freePart.add(tCoefficient.scale(smallest_t));
    }

    public double getSmallest_t() {
        return smallest_t;
    }

    public void setSmallest_t(double smallest_t) {
        this.smallest_t = smallest_t;
    }

    public vector gettCoefficient() {
        return tCoefficient;
    }

    public void settCoefficient(vector tCoefficient) {
        this.tCoefficient = tCoefficient;
    }

    public vector getFreePart() {
        return freePart;
    }

    public void setFreePart(vector freePart) {
        this.freePart = freePart;
    }
}
