package acoustically.cloudix;

/**
 * Created by acoustically on 17. 10. 11.
 */

public class DeviceListItem {
  String name;
  boolean power;
  String serial;
  int position;

  public DeviceListItem(String serial, int position, String name, boolean power) {
    this.name = name;
    this.power = power;
    this.serial = serial;
    this.position = position;
  }

  public String getName() {
    return name;
  }

  public boolean isPowerOn() {
    return power;
  }

  public String getSerial() {
    return serial;
  }

  public int getPosition() {
    return position;
  }
}
