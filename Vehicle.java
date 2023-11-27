/**
 * TODO: Write a comment describing your class here.
 *This abstract class represents a common Vehicle model in the parking lot system.
 *It contains fields and methods shared across vehicle types like Car, Bike etc.
 *Concrete subclasses must override the abstract getVehicleType() method.
 * @author TODO: Soma Hayasaka,Hayasakas@student.unimelb.edu.au,1396301.
 */
public abstract class Vehicle {

  protected String regnId;
  protected String entryTime;
  protected String entryDate;
  protected int numberOfHits;
  protected int x;
  protected int y;
  /*public abstract double calculateParkingFee(Date timeOfExit);*/

  public Vehicle(String id, String time, String date, int x, int y){
    regnId = id;
    entryTime = time;
    entryDate = date;
    this.x = x;
    this.y = y;
  }

    public void moveX(int x){
    this.x += x;
    }

  public void moveY(int y){
    this.y += y;
  }

  public void hit(){
    this.numberOfHits ++;
  }

  public int getX(){
    return x;
  }

  public int getY(){
    return y;
  }

  public String getRegnId() {
    return regnId;
  }

  public String getEntryDate() {
    return entryDate;
  }

  public String getEntryTime() {
    return entryTime;
  }

  public int getNumberOfHits() {
    return numberOfHits;
  }

  public abstract String getVehicleType();

}



