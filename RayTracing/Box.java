package RayTracing;

public class Box extends Surfaces{
    vector center;
    double edgeLength;

    public Box(vector center, double edgeLength, Material material) {
        this.center = center;
        this.edgeLength = edgeLength;
        this.material = material;
    }

    public double intersection(Ray ray){
        vector min = new vector(center.x - 0.5*edgeLength, center.y - 0.5*edgeLength,center.z - 0.5*edgeLength);
        vector max = new vector(center.x + 0.5*edgeLength, center.y + 0.5*edgeLength,center.z + 0.5*edgeLength);

        double tmin = (min.x - ray.freePart.x) / ray.tCoefficient.x;
        double tmax = (max.x - ray.freePart.x) / ray.tCoefficient.x;

        if (tmin > tmax){
            double tmp = tmin;
            tmin = tmax;
            tmax = tmp;
//            swap(tmin, tmax);
        }

        double tymin = (min.y - ray.freePart.y) / ray.tCoefficient.y;
        double tymax = (max.y - ray.freePart.y) / ray.tCoefficient.y;

        if (tymin > tymax){
            double tmp = tymin;
            tymin = tymax;
            tymax = tmp;
//            swap(tymin, tymax);
        }

        if ((tmin > tymax) || (tymin > tmax))
            return 0;

        if (tymin > tmin)
            tmin = tymin;

        if (tymax < tmax)
            tmax = tymax;

        double tzmin = (min.z - ray.freePart.z) / ray.tCoefficient.z;
        double tzmax = (max.z - ray.freePart.z) / ray.tCoefficient.z;

        if (tzmin > tzmax){
            double tmp = tzmin;
            tzmin = tzmax;
            tzmax = tmp;
//            swap(tzmin, tzmax);
        }

        if ((tmin > tzmax) || (tzmin > tmax))
            return 0;

        if (tzmin > tmin)
            tmin = tzmin;

        //needed?
        if (tzmax < tmax)
            tmax = tzmax;

        return tmin;
    }

    public vector computeNormal(vector point){
        double a, b, c;
        if(point.x > center.x){
            a = Math.abs(point.x - center.x);
        }
        else {
            a = Math.abs(center.x - point.x);
        }

        if(point.y > center.y){
            b = Math.abs(point.y - center.y);
        }
        else {
            b = Math.abs(center.y - point.y);
        }

        if(point.z > center.z){
            c = Math.abs(point.z - center.z);
        }
        else {
            c = Math.abs(center.z - point.z);
        }

//        double b = Math.abs(point.y - center.y);
//        double c = Math.abs(point.z - center.z);
//
        double max = Math.max(a,Math.max(b, c));

        if(max == a){
            if(point.x > center.x){
                return new vector(1,0,0);
            }

            return new vector(-1,0,0);
        }

        if(max == b){
            if(point.y > center.y){
                return new vector(0,1,0);
            }

            return new vector(0,-1,0);
        }

        if(max == c){
            if(point.z > center.z){
                return new vector(0,0,1);
            }

            return new vector(0,0,-1);
        }

        //shouldnt get her
        return new vector(0,0,0);
    }

    public vector getCenter() {
        return center;
    }

    public void setCenter(vector center) {
        this.center = center;
    }

    public double getEdgeLength() {
        return edgeLength;
    }

    public void setEdgeLength(int edgeLength) {
        this.edgeLength = edgeLength;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    @Override
    public String toString() {
        return "Box{" +
                "center=" + center.toString() +
                ", edgeLength=" + edgeLength +
                ", material=" + material +
                '}';
    }
}
