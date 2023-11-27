/**
 * TODO: Write a comment describing your class here.
 *This class represents a log record of a vehicle checkout from the parking lot.
 *It captures details like vehicle type, registration ID, entry/exit datetimes
 *and the calculated parking fee.
 *These log objects are stored in a list and printed to view checkout history.
 * @author TODO: Soma Hayasaka,Hayasakas@student.unimelb.edu.au,1396301.
 */
public class ParkingLog {

  String vehicleType;
  String regId;
  String entryTime;
  String exitTime;
  String exitDate;
  String entryDate;
  double parkingFee;

  public ParkingLog(String vehicleType, String regId,String entryDate,String entryTime,String exitDate,String exitTime,double parkingFee) {
    this.vehicleType = vehicleType;
    this.regId = regId;
    this.entryTime = entryTime;
    this.entryDate = entryDate;
    this.exitTime = exitTime;
    this.exitDate = exitDate;
    this. parkingFee =  parkingFee;
}
}