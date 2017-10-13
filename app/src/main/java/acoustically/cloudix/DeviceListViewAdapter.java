package acoustically.cloudix;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import acoustically.cloudix.Sign.DeviceListItem;

import static acoustically.cloudix.R.id.parent;

/**
 * Created by acoustically on 17. 10. 11.
 */

public class DeviceListViewAdapter extends BaseAdapter implements View.OnClickListener {
  List<DeviceListItem> deviceList = new LinkedList<>();
  @Override
  public int getCount() {
    return deviceList.size();
  }

  @Override
  public Object getItem(int i) {
    return deviceList.get(i);
  }

  @Override
  public long getItemId(int i) {
    return i;
  }

  @Override
  public View getView(int i, View view, ViewGroup viewGroup) {
    Context context = viewGroup.getContext();
    DeviceListItem device = deviceList.get(i);

    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    view = inflater.inflate(R.layout.device_list_item, viewGroup, false);

    TextView deviceNameView = (TextView) view.findViewById(R.id.DeviceName);
    deviceNameView.setText(deviceList.get(i).getName());
    Button devicePowerButton = (Button) view.findViewById(R.id.DevicePower);
    if(device.isPowerOn()) {
      devicePowerButton.setBackground(context.getDrawable(R.drawable.on));
    } else {
      devicePowerButton.setBackground(context.getDrawable(R.drawable.off));
    }
    return view;
  }

  @Override
  public void onClick(View view) {
    
  }
}
