package RayTracing;

public class Camera {
    public vector cameraPosition;
    public vector lookAtPoint;
    public vector upVector;
    public double screenDistance;
    public double screenWidth;
    public boolean fishEye;

    //ungiven parameters
    public double screenHeight;
    public vector towards;
    public vector Vx;
    public vector Vy;
    public vector Vz;


    public Camera(vector cameraPosition, vector lookAtPoint, vector upVector, double screenDistance, double screenWidth, boolean fishEye) {
        this.cameraPosition = cameraPosition;
        this.lookAtPoint = lookAtPoint;
        this.upVector = upVector;
        this.screenDistance = screenDistance;
        this.screenWidth = screenWidth;
        this.fishEye = fishEye;

        this.towards = (lookAtPoint.subtract(cameraPosition));
        this.towards.normalize();

        double sinx = -towards.y;
        double cosx = Math.sqrt(1-sinx*sinx);
        double siny = -towards.x/cosx;
        double cosy = towards.z/cosx;

        double[][] Matrix = {{cosy, 0, siny}, {-1*sinx*siny, cosx, sinx*cosy},{-1*cosx*siny, -1*sinx, cosx*cosy}};

        this.Vx = (new vector(1,0,0)).multiplyByMatrix(Matrix);
        this.Vy = (new vector(0,1,0)).multiplyByMatrix(Matrix).scale(-1);
        this.Vz = (new vector(0,0,1)).multiplyByMatrix(Matrix);
    }

    @Override
    public String toString() {
        return "Camera{" +
                "cameraPosition=" + cameraPosition +
                ", lookAtPoint=" + lookAtPoint +
                ", upVector=" + upVector +
                ", screenDistance=" + screenDistance +
                ", screenWidth=" + screenWidth +
                ", fishEye=" + fishEye +
                ", towards=" + towards +
                ", Vx=" + Vx +
                ", Vy=" + Vy +
                ", Vz=" + Vz +
                '}';
    }
}
