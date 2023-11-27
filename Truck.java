/**
 * TODO: Write a comment describing your class here.
 *This class represents a Truck vehicle.
 *It extends the base Vehicle class and overrides the getVehicleType()
 *method to return "truck".
 *The vehicle details like registration ID, model, color etc are inherited
 *from the parent Vehicle class.
 * @author TODO: Soma Hayasaka,Hayasakas@student.unimelb.edu.au,1396301.
 */
public class Truck extends Vehicle{
  protected String vehicleType="truck";

  public Truck(String regnId, String model, String colour, String dateOfEntry,String timeOfEntry, int x, int y) {
    super(regnId, timeOfEntry, dateOfEntry, x, y);
  }
  @Override
    public String getVehicleType(){
        return vehicleType;
    }
}