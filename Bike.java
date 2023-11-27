
/**
 * TODO: Write a comment describing your class here.
 * This class represents a Bike vehicle.
 *It extends the base Vehicle class and overrides the getVehicleType()
 *method to return "bike".
 *The vehicle details like registration ID, model, color etc are inherited
 *from the parent Vehicle class.
 * @author TODO: Soma Hayasaka,Hayasakas@student.unimelb.edu.au,1396301.
 */
public class Bike extends Vehicle{
  protected String vehicleType="bike";

  public Bike(String regnId, String model, String colour, String dateOfEntry,String timeOfEntry, int x, int y) {
    super(regnId, timeOfEntry, dateOfEntry, x, y);
  }

  @Override
    public String getVehicleType(){
        return vehicleType;
    }
}
