package RayTracing;

import java.awt.Transparency;
import java.awt.color.*;
import java.awt.image.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 *  Main class for ray tracing exercise.
 */
public class RayTracer {

	public int imageWidth;
	public int imageHeight;

	/**
	 * defining List to Material and for each surface type
	 */
	List<Material> MaterialsList = new ArrayList<Material>();
	List<Sphere> SpheresList = new ArrayList<Sphere>();
	List<InfinitePlane> InfinitePlanesList = new ArrayList<InfinitePlane>();
	List<Box> BoxsList = new ArrayList<Box>();
	List<Light> LightList = new ArrayList<Light>();

	/**
	 * defining variables that will describe the camera and general settings
	 */
	public Camera camera;
	public vector P0;

	public RGB backgroundColor;
	public int numOfShadowRays;
	public int maxRecLevel;
	Random random = new Random();

	//method to test the paring
	public void testParsing(){
		System.out.println("****Check camera***");
		System.out.println(camera.toString());
		System.out.println("end of camera");
		System.out.println(" ");
		System.out.println("***check settings***");
		System.out.println("background color="+ backgroundColor);
		System.out.println("numOfShadowRays=" + numOfShadowRays);
		System.out.println("maxRecLevel=" + maxRecLevel);
		System.out.println("****Check materials***");
		System.out.println(" ");
		for(int i=0; i<MaterialsList.size(); i++){
			System.out.println("material num=" + i);
			System.out.println(MaterialsList.get(i).toString());
		}
		System.out.println("end of materials");
		System.out.println(" ");
		System.out.println("****Check spheres***");
		for(int i=0; i<SpheresList.size(); i++){
			System.out.println("sphere num=" + i);
			System.out.println(SpheresList.get(i).toString());
		}
		System.out.println("end of spheres");
		System.out.println(" ");
		System.out.println("****Check infiniteplaes***");
		for(int i=0; i<InfinitePlanesList.size(); i++){
			System.out.println("InfPlane num=" + i);
			System.out.println(InfinitePlanesList.get(i).toString());
		}
		System.out.println("end of InfinitePlanes");
		System.out.println(" ");
		System.out.println("****Check Boxes***");
		for(int i=0; i<BoxsList.size(); i++){
			System.out.println("Box num=" + i);
			System.out.println(BoxsList.get(i).toString());
		}
		System.out.println("end of Boxes");
		System.out.println(" ");
		System.out.println("****Check Lights***");
		for(int i=0; i<LightList.size(); i++){
			System.out.println("Light num=" + i);
			System.out.println(LightList.get(i).toString());
		}
		System.out.println("end of Lights");
		System.out.println(" ");

		System.out.println("!!!!!!END!!!!!!");
	}


	/**
	 * Runs the ray tracer. Takes scene file, output image file and image size as input.
	 */
	public static void main(String[] args) {

		try {

			RayTracer tracer = new RayTracer();

                        // Default values:
			tracer.imageWidth = 500;
			tracer.imageHeight = 500;

			if (args.length < 2)
				throw new RayTracerException("Not enough arguments provided. Please specify an input scene file and an output image file for rendering.");

			String sceneFileName = args[0];
			String outputFileName = args[1];

			if (args.length > 3)
			{
				tracer.imageWidth = Integer.parseInt(args[2]);
				tracer.imageHeight = Integer.parseInt(args[3]);
			}


			// Parse scene file:
			tracer.parseScene(sceneFileName);

			tracer.camera.screenHeight = tracer.imageHeight * (tracer.camera.screenWidth/ tracer.imageWidth);

			//tracer.testParsing();

			// Render scene:
			tracer.renderScene(outputFileName);

//		} catch (IOException e) {
//			System.out.println(e.getMessage());
		} catch (RayTracerException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}


	}

	/**
	 * Parses the scene file and creates the scene. Change this function so it generates the required objects.
	 */
	public void parseScene(String sceneFileName) throws IOException, RayTracerException
	{
		FileReader fr = new FileReader(sceneFileName);

		BufferedReader r = new BufferedReader(fr);
		String line = null;
		int lineNum = 0;
		System.out.println("Started parsing scene file " + sceneFileName);



		while ((line = r.readLine()) != null)
		{
			line = line.trim();
			++lineNum;

			if (line.isEmpty() || (line.charAt(0) == '#'))
			{  // This line in the scene file is a comment
				continue;
			}
			else
			{
				String code = line.substring(0, 3).toLowerCase();
				// Split according to white space characters:
				String[] params = line.substring(3).trim().toLowerCase().split("\\s+");

				if (code.equals("cam"))
				{
					vector cameraPosition = new vector(Double.parseDouble(params[0]), Double.parseDouble(params[1]), Double.parseDouble(params[2]));
					vector lookAtPoint = new vector(Double.parseDouble(params[3]), Double.parseDouble(params[4]), Double.parseDouble(params[5]));
					vector upVector = new vector(Double.parseDouble(params[6]), Double.parseDouble(params[7]), Double.parseDouble(params[8]));
					double screenDistance = Double.parseDouble(params[9]);
					double screenWidth = Double.parseDouble(params[10]);
//					boolean fishEye = Boolean.parseBoolean(params[11]); //no fisheye were implemented
					boolean fishEye = false;

					camera = new Camera(cameraPosition, lookAtPoint, upVector, screenDistance, screenWidth, fishEye);

					System.out.println(String.format("Parsed camera parameters (line %d)", lineNum));
				}
				else if (code.equals("set"))
				{
					backgroundColor = new RGB(Double.parseDouble(params[0]), Double.parseDouble(params[1]), Double.parseDouble(params[2]));
					numOfShadowRays = Integer.parseInt(params[3]);
					maxRecLevel = Integer.parseInt(params[4]);

					System.out.println(String.format("Parsed general settings (line %d)", lineNum));
				}
				else if (code.equals("mtl"))
				{
					RGB diffuse = new RGB(Double.parseDouble(params[0]), Double.parseDouble(params[1]), Double.parseDouble(params[2]));
					RGB specular = new RGB(Double.parseDouble(params[3]), Double.parseDouble(params[4]), Double.parseDouble(params[5]));
					RGB reflection = new RGB(Double.parseDouble(params[6]), Double.parseDouble(params[7]), Double.parseDouble(params[8]));
					double phong = Double.parseDouble(params[9]);
					double transparency = Double.parseDouble(params[10]);

					Material material = new Material(diffuse, specular, reflection, phong, transparency);

					MaterialsList.add(material);


					System.out.println(String.format("Parsed material (line %d)", lineNum));
				}
				else if (code.equals("sph"))
				{
						vector center = new vector(Double.parseDouble(params[0]), Double.parseDouble(params[1]), Double.parseDouble(params[2]));
						double radius = Double.parseDouble(params[3]);
						Material material = MaterialsList.get(Integer.parseInt(params[4])-1);

						Sphere sphere = new Sphere(center, radius, material);
						SpheresList.add(sphere);

					System.out.println(String.format("Parsed sphere (line %d)", lineNum));
				}
				else if (code.equals("pln"))
				{
					vector normal = new vector(Double.parseDouble(params[0]), Double.parseDouble(params[1]), Double.parseDouble(params[2]));
					double c = Double.parseDouble(params[3]);
					Material material = MaterialsList.get(Integer.parseInt(params[4])-1);

					InfinitePlane ip = new InfinitePlane(normal, c, material);
					InfinitePlanesList.add(ip);

					System.out.println(String.format("Parsed plane (line %d)", lineNum));
				}
				else if (code.equals("box"))
				{
					vector center = new vector(Double.parseDouble(params[0]), Double.parseDouble(params[1]), Double.parseDouble(params[2]));
					double len = Double.parseDouble(params[3]);
					Material material = MaterialsList.get(Integer.parseInt(params[4])-1);

					Box box = new Box(center, len, material);
					BoxsList.add(box);

					System.out.println(String.format("Parsed box (line %d)", lineNum));
				}
				else if (code.equals("lgt"))
				{
					vector position = new vector(Double.parseDouble(params[0]), Double.parseDouble(params[1]), Double.parseDouble(params[2]));
					RGB lightColor = new RGB(Double.parseDouble(params[3]), Double.parseDouble(params[4]), Double.parseDouble(params[5]));
					double specular = Double.parseDouble(params[6]);
					double shadow = Double.parseDouble(params[7]);
					double rad = Double.parseDouble(params[8]);

					Light light = new Light(position, lightColor, specular, shadow, rad);
					LightList.add(light);

					System.out.println(String.format("Parsed light (line %d)", lineNum));
				}
				else
				{
					System.out.println(String.format("ERROR: Did not recognize object: %s (line %d)", code, lineNum));
				}
			}
		}

                // It is recommended that you check here that the scene is valid,
                // for example camera settings and all necessary materials were defined.

		System.out.println("Finished parsing scene file " + sceneFileName);

	}

	/**
	 * Renders the loaded scene and saves it to the specified file location.
	 */
	public void renderScene(String outputFileName)
	{
		long startTime = System.currentTimeMillis();

		// Create a byte array to hold the pixel data:
		byte[] rgbData = new byte[this.imageWidth * this.imageHeight * 3];

		//calculate P and P0
		vector P = camera.cameraPosition.add(camera.Vz.scale(camera.screenDistance));
		vector part1 = (P.subtract(camera.Vx.scale(0.5 * camera.screenWidth)));
		P0 = part1.subtract(camera.Vy.scale(0.5 * camera.screenHeight));

		//P=P0
		P.copy(P0);
		//the main loop, as in the last slide in Ray casting presentation
		for(int i=0; i<imageHeight; i++){
			P.copy(P0);

			for(int j=0; j < imageWidth; j++){
				//build the ray
				vector V = P.subtract(camera.cameraPosition);
				V.normalize();
				Ray ray = new Ray(V, camera.cameraPosition);

				//find the intersection
				findIntersection(ray);

				//calculating the color
				RGB colorAtPoint = calculateColor(ray);

				rgbData[(i * this.imageWidth + j) * 3] = (byte)(255*colorAtPoint.R);
				rgbData[(i * this.imageWidth + j) * 3 + 1] = (byte)(255*colorAtPoint.G);
				rgbData[(i * this.imageWidth + j) * 3 + 2] = (byte)(255*colorAtPoint.B);

				P = P.add(camera.Vx.scale(camera.screenWidth/imageWidth));
			}

			P0 = P0.add(camera.Vy.scale(camera.screenHeight/imageHeight));
		}

		long endTime = System.currentTimeMillis();
		Long renderTime = endTime - startTime;

		System.out.println("Finished rendering scene in " + renderTime.toString() + " milliseconds.");

                // This is already implemented, and should work without adding any code.
		saveImage(this.imageWidth, rgbData, outputFileName);

		System.out.println("Saved file " + outputFileName);

	}

	//find intersection with ray
	public void findIntersection(Ray ray){
		double final_t = Double.MAX_VALUE ;
		Surfaces surface = null;

		for(int i = 0; i < BoxsList.size(); i++){
			Box box = BoxsList.get(i);
			double t = box.intersection(ray);

			if(t>0 && t < final_t && box != ray.prevSurface){
				final_t = t;
				surface = box;
			}
		}

		for(int i = 0; i < SpheresList.size(); i++){
			Sphere sphere = SpheresList.get(i);
			double t = sphere.intersection(ray);

			if(t>0 && t < final_t && sphere != ray.prevSurface){
				final_t = t;
				surface = sphere;
			}
		}

		for(int i = 0; i < InfinitePlanesList.size(); i++){
			InfinitePlane ip = InfinitePlanesList.get(i);
			double t = ip.intersection(ray);

			if(t > 0 && t < final_t && ip != ray.prevSurface){
				final_t = t;
				surface = ip;
			}
		}

		ray.setSmallest_t(final_t);
		ray.setSur(surface);
	}

	//calculate color at specific point
	public RGB calculateColor(Ray ray){
		Surfaces surface = ray.surface;
		double t = ray.smallest_t;
		vector point = ray.getPoint();

		if(surface==null || t<=0){
			return new RGB(backgroundColor.R,backgroundColor.G, backgroundColor.B);
		}

		RGB rgbColor = new RGB(0,0,0);

		RGB diffuse = new RGB(0,0,0);
		RGB specular = new RGB(0,0,0);

		for(int i=0; i < LightList.size(); i++){
			Light light = LightList.get(i);
			vector t_coefficient = point.subtract(light.position);
			t_coefficient.normalize();
			Ray lightRay = new Ray(t_coefficient, light.position);

			findIntersection(lightRay);

			double lightIntensity = calculateSoftShadows(point, light, surface);

			vector L = light.position.subtract(point);
			L.normalize();
			vector N = surface.computeNormal(point);
			N.normalize();

			double NdotL = L.dotProduct(N);
			if(NdotL < 0){
				NdotL = 0;
			}

			//add diffuse part
			double c = lightIntensity*NdotL;
			RGB LightMultiplyMaterial1 = light.lightColor.multiplyElementWise(surface.material.diffuse);
			diffuse.addToSelf(LightMultiplyMaterial1.multiplyByScalar(c));

//			add specular part
			vector firstPartOfR = N.scale(2*NdotL);
			vector R = firstPartOfR.subtract(L);
			R.normalize();
			double RdotV = R.dotProduct(ray.tCoefficient.scale(-1));

			if(RdotV < 0){
				RdotV = 0;
			}

			c = lightIntensity*Math.pow(RdotV, surface.material.PhongSpecularityCoefficient)*light.specularIntensity;
			RGB LightMultiplyMaterial2 = light.lightColor.multiplyElementWise(surface.material.specular);
			specular.addToSelf(LightMultiplyMaterial2.multiplyByScalar(c));

		}


		rgbColor.R = (diffuse.R + specular.R)*(1-surface.material.Transparency);
		rgbColor.G = (diffuse.G + specular.G)*(1-surface.material.Transparency);
		rgbColor.B = (diffuse.B + specular.B)*(1-surface.material.Transparency);

		//calculating reflection
		if(ray.recLevel <= maxRecLevel){// is it < or <=?
			vector N = surface.computeNormal(point);
			double VN = ray.tCoefficient.dotProduct(N);//should multiply by -1?
			vector R = ray.tCoefficient.subtract(N.scale(2*VN));
			R.normalize();
			Ray reflectionRay = new Ray(R, point, ray.recLevel+1);
			findIntersection(reflectionRay);
			RGB reflectionColor1 = calculateColor(reflectionRay);
			RGB reflectionColor2 = reflectionColor1.multiplyElementWise(surface.material.reflection);

			rgbColor.addToSelf(reflectionColor2);
		}


		//calculate transparency-need to fix
		if(ray.recLevel <= maxRecLevel &&  surface.material.Transparency!= 0){
			Ray transparencyRay = new Ray(ray.tCoefficient, point, ray.recLevel+1,surface);
			findIntersection(transparencyRay);
			RGB transparencyColor1 = calculateColor(transparencyRay);
			RGB transparencyColor2 = transparencyColor1.multiplyByScalar(surface.material.Transparency);

			rgbColor.addToSelf(transparencyColor2);
		}


		rgbColor.R = Math.min(rgbColor.R, 1);
		rgbColor.G = Math.min(rgbColor.G, 1);
		rgbColor.B = Math.min(rgbColor.B, 1);

		return rgbColor;
	}

	public double calculateSoftShadows(vector point, Light light, Surfaces surface){
		vector V = point.subtract(light.position);
		V.normalize();
		double c = light.position.dotProduct(V);
		InfinitePlane plane = new InfinitePlane(V, c);

		double x = random.nextDouble();
		double y = random.nextDouble();

		//find new point on the plane
		vector point2 = new vector(x, y, (c - V.x*x - V.y*y) / V.z);
		vector Vx = point2.subtract(light.position);
		Vx.normalize();
		vector Vy = Vx.crossProduct(V);
		Vy.normalize();

		double r =light.radius;
		vector light_P0_1 = light.position.subtract(Vx.scale(0.5 * r));
		//the P0 of the rectangle
		vector light_P0 = light_P0_1.subtract(Vy.scale(0.5 * r));

		double count = 0;
		vector curr = new vector(light_P0.x,light_P0.y,light_P0.z);

		for(int i =0; i <numOfShadowRays; i++){
			curr.copy(light_P0);

			for(int j = 0; j < numOfShadowRays; j++){
				double mx = random.nextDouble();
				double my = random.nextDouble();

				vector currPoint_1 = curr.add(Vx.scale(mx * r/numOfShadowRays));
				vector currentPointFinal = currPoint_1.add(Vy.scale(my * r/numOfShadowRays));

				vector vec_V = point.subtract(currentPointFinal);
				vec_V.normalize();

				Ray softShadowRay = new Ray(vec_V, currentPointFinal);
				findIntersection(softShadowRay);

				if(softShadowRay.surface == surface){
					count++;
				}

				curr = curr.add(Vx.scale(r/numOfShadowRays));
			}

			light_P0 = light_P0.add(Vy.scale(r/numOfShadowRays));
		}

		double percent = count/Math.pow(numOfShadowRays, 2);
		double retValue = (1-light.shadowIntensity)*1 + light.shadowIntensity*percent;

		if(retValue < 0){
			retValue = 0;
		}

		if(retValue > 1){
			retValue = 1;
		}

		return retValue;
	}

	//////////////////////// FUNCTIONS TO SAVE IMAGES IN PNG FORMAT //////////////////////////////////////////

	/*
	 * Saves RGB data as an image in png format to the specified location.
	 */
	public static void saveImage(int width, byte[] rgbData, String fileName)
	{
		try {

			BufferedImage image = bytes2RGB(width, rgbData);
			ImageIO.write(image, "png", new File(fileName));

		} catch (IOException e) {
			System.out.println("ERROR SAVING FILE: " + e.getMessage());
		}

	}

	/*
	 * Producing a BufferedImage that can be saved as png from a byte array of RGB values.
	 */
	public static BufferedImage bytes2RGB(int width, byte[] buffer) {
	    int height = buffer.length / width / 3;
	    ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_sRGB);
	    ColorModel cm = new ComponentColorModel(cs, false, false,
	            Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
	    SampleModel sm = cm.createCompatibleSampleModel(width, height);
	    DataBufferByte db = new DataBufferByte(buffer, width * height);
	    WritableRaster raster = Raster.createWritableRaster(sm, db, null);
	    BufferedImage result = new BufferedImage(cm, raster, false, null);

	    return result;
	}

	public static class RayTracerException extends Exception {
		public RayTracerException(String msg) {  super(msg); }
	}


}
