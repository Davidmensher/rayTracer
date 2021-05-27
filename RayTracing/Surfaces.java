package RayTracing;

public abstract class Surfaces {

    public Material material;

    public abstract double intersection(Ray ray);
    public abstract vector computeNormal(vector point);

}
