
/**
 * TODO: Write a comment describing your class here.
 *This class represents a Date containing both date and time.
 *It is used to capture check-in and check-out datetimes for vehicles.
 *The date and time can be set via constructor or setter methods.
 *Getter methods are provided to retrieve the stored values.
 * @author TODO: Soma Hayasaka,Hayasakas@student.unimelb.edu.au,1396301.
 */
public class  Date{
    String date;
    String time;
public Date (String date, String time){
    this.date=date;
    this.time=time;
}
public Date (){

}
public void setDate(String date){
    this.date=date;
}
public void setTime(String time){
    this.time=time;
}

public String getDate(){
    return date;
}
public String getTime(){
    return time;
}

}
