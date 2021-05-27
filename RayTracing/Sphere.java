package RayTracing;

public class Sphere extends Surfaces{
    vector center;
    double rad;


    public Sphere(vector center,double rad,Material material) {
        this.center = center;
        this.rad = rad;
        this.material = material;

    }

    public double intersection(Ray ray){
        vector L = center.subtract(ray.freePart);
        double Tca = L.dotProduct(ray.tCoefficient);

        if(Tca < 0){
            return 0;
        }

        double dSquare = L.dotProduct(L) - Tca*Tca;

        if(dSquare > rad*rad){
            return 0;
        }

        double Thc = Math.sqrt(rad*rad - dSquare);

        return Math.min(Tca-Thc, Tca+Thc);
    }

    public vector computeNormal(vector point){
        vector v = point.subtract(center);
        v.normalize();

        return v;
    }

    public vector getCenter() {
        return center;
    }

    public void setCenter(vector center) {
        this.center = center;
    }

    public double getRad() {
        return rad;
    }

    public void setRad(double rad) {
        this.rad = rad;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "center=" + center.toString() +
                ", rad=" + rad +
                ", material=" + material +
                '}';
    }
}
