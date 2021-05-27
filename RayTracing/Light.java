package RayTracing;

public class Light {
    public vector position;
    public RGB lightColor;
    public double specularIntensity;
    public double shadowIntensity;
    public double radius;

    public Light(vector position, RGB light, double specularIntensity, double shadowIntensity, double radius) {
        this.position = position;
        this.lightColor = light;
        this.specularIntensity = specularIntensity;
        this.shadowIntensity = shadowIntensity;
        this.radius = radius;
    }

    @Override
    public String toString() {
        return "Light{" +
                "position=" + position +
                ", lightColor=" + lightColor +
                ", specularIntensity=" + specularIntensity +
                ", shadowIntensity=" + shadowIntensity +
                ", radius=" + radius +
                '}';
    }
}
