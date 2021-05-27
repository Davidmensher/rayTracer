package RayTracing;

public class InfinitePlane extends Surfaces{
    public vector normal;
    double c;

    public InfinitePlane(vector normal, double c, Material material) {
        this.normal = normal;
        this.c = c;
        this.material = material;
    }

    public InfinitePlane(vector normal, double c) {
        this.normal = normal;
        this.c = c;
        this.material = null;
    }

    public double intersection(Ray ray){
        double top = c - normal.dotProduct(ray.freePart);
        double bottom = normal.dotProduct(ray.tCoefficient);

        //need to multiply by -1?
        return Math.max(top/bottom, 0);
    }

    public vector computeNormal(vector point){
        return normal;
    }

    public vector getNormal() {
        return normal;
    }

    public void setNormal(vector normal) {
        this.normal = normal;
    }

    public double getC() {
        return c;
    }

    public void setC(double c) {
        this.c = c;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    @Override
    public String toString() {
        return "InfinitePlane{" +
                "normal=" + normal.toString() +
                ", c=" + c +
                ", material=" + material +
                '}';
    }
}
