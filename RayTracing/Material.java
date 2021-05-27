package RayTracing;

public class Material {
    public RGB diffuse;
    public RGB specular;
    public RGB reflection;
    public double PhongSpecularityCoefficient;
    public double Transparency;

    public Material(RGB diffuse, RGB specular, RGB reflection, double phongSpecularityCoefficient, double transparency) {
        this.diffuse = diffuse;
        this.specular = specular;
        this.reflection = reflection;
        PhongSpecularityCoefficient = phongSpecularityCoefficient;
        Transparency = transparency;
    }

    @Override
    public String toString() {
        return "Material{" +
                "diffuse=" + diffuse +
                ", specular=" + specular +
                ", reflection=" + reflection +
                ", PhongSpecularityCoefficient=" + PhongSpecularityCoefficient +
                ", Transparency=" + Transparency +
                '}';
    }
}
