package RayTracing;

public class vector {
    public double x;
    public double y;
    public double z;

    public vector(double x,double y,double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }


    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }


    //adding two vectors
    public vector add(vector v) {
        return new vector(this.getX()+v.getX(),this.getY()+v.getY(),this.getZ()+v.getZ());
    }


    public vector scale(double a) {
        return new vector(this.getX()*a,this.getY()*a,this.getZ()*a);
    }


    public vector subtract(vector v) {

        return this.add(v.scale(-1));

    }

    public void normalize() {
        double n = Math.sqrt(this.dotProduct(this));
        this.x = getX()/n;
        this.y = getY()/n;
        this.z = getZ()/n;
    }


    public double dotProduct(vector v) {
        return getX() * v.getX() + getY() * v.getY() + getZ() * v.getZ();
    }

    public vector crossProduct(vector B){
        double x = this.y * B.z - this.z * B.y;
        double y = this.z * B.x - this.x * B.z;
        double z = this.x * B.y - this.y * B.x;
        vector v = new vector(x, y, z);
        v.normalize();
        return v;
    }

    public void copy(vector v){
        this.x = v.getX();
        this.y = v.getY();
        this.z = v.getZ();
    }

    public boolean equals(vector v) {
        if (getX()==v.getX() && getY()==v.getY() && getZ()==v.getZ()) {
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + "," + z +")";
    }

     public vector multiplyByMatrix(double[][] mat){
        double x_cordinate = x*mat[0][0] + y*mat[1][0] + z*mat[2][0];
        double y_cordinate = x*mat[0][1] + y*mat[1][1] + z*mat[2][1];
        double z_cordinate = x*mat[0][2] + y*mat[1][2] + z*mat[2][2];

        return new vector(x_cordinate, y_cordinate, z_cordinate);
    }

    //write methods to find Vx, Vy, Vz




//    public double magnitude() {
//        return Math.sqrt(getX()*getX()+getY()*getY()+getZ()*getZ());
//
//    }




//    public vector translate(double x, double y) {
//        return this.translate(x, y,0.0);
//    }
//
//    public vector translate(double x, double y, double z) {
//        return new vector(getX()+x,getY()+y,getZ()+z);
//    }

//    public vector average(vector v) {
//        return new vector((getX()+v.getX())*0.5,(getY()+v.getY())*0.5,(getZ()+v.getZ())*0.5);
//    }


//    public double dot2D(vector v) {
//        return getX()*v.getX() + getY()*v.getY();
//    }

}
