package RayTracing;

public class RGB {
    public double R;
    public double G;
    public double B;

    public RGB(double r, double g, double b) {
        R = r;
        G = g;
        B = b;
    }

    //add to self RGB data another RGB color
    public void addToSelf(RGB color){
        this.R += color.R;
        this.G += color.G;
        this.B += color.B;
    }

    public RGB multiplyElementWise(RGB color){
        return new RGB(this.R*color.R,this.G*color.G,this.B*color.B);
    }

    public RGB multiplyByScalar(double c){
        return new RGB(this.R*c, this.G*c, this.B*c);
    }

    @Override
    public String toString() {
        return "RGB{" +
                "R=" + R +
                ", G=" + G +
                ", B=" + B +
                '}';
    }
}
