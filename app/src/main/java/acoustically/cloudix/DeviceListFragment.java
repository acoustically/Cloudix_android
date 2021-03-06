package acoustically.cloudix;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import acoustically.cloudix.ConnectToServer.HttpRequestor;
import acoustically.cloudix.ConnectToServer.HttpResponseListener;
import acoustically.cloudix.ConnectToServer.JSONObjectWithToken;
import acoustically.cloudix.ConnectToServer.Server;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DeviceListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DeviceListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeviceListFragment extends Fragment {
  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";

  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;

  private OnFragmentInteractionListener mListener;

  public DeviceListFragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @param param1 Parameter 1.
   * @param param2 Parameter 2.
   * @return A new instance of fragment DeviceListFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static DeviceListFragment newInstance(String param1, String param2) {
    DeviceListFragment fragment = new DeviceListFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    args.putString(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mParam1 = getArguments().getString(ARG_PARAM1);
      mParam2 = getArguments().getString(ARG_PARAM2);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_device_list, container, false);
  }

  // TODO: Rename method, update argument and hook method into UI event
  public void onButtonPressed(Uri uri) {
    if (mListener != null) {
      mListener.onFragmentInteraction(uri);
    }
  }

  @Override
  public void onResume() {
    super.onResume();
    ListView listView = getView().findViewById(R.id.DeviceListView);
    DeviceListViewAdapter listAdapter = new DeviceListViewAdapter(this);
    getDeviceList(listView, listAdapter);
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof OnFragmentInteractionListener) {
      mListener = (OnFragmentInteractionListener) context;
    } else {
      throw new RuntimeException(context.toString()
        + " must implement OnFragmentInteractionListener");
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
  }

  /**
   * This interface must be implemented by activities that contain this
   * fragment to allow an interaction in this fragment to be communicated
   * to the activity and potentially other fragments contained in that
   * activity.
   * <p>
   * See the Android Training lesson <a href=
   * "http://developer.android.com/training/basics/fragments/communicating.html"
   * >Communicating with Other Fragments</a> for more information.
   */
  public interface OnFragmentInteractionListener {
    // TODO: Update argument type and name
    void onFragmentInteraction(Uri uri);
  }
  private void getDeviceList(final ListView listView, final DeviceListViewAdapter adapter) {
    try {
      HttpRequestor requestor = new HttpRequestor(Server.getUrl("switchs/all.json"));
      requestor.post(new JSONObjectWithToken(), new HttpResponseListener() {
        @Override
        protected void httpResponse(JSONObject json) {
          try {
            JSONArray jsonArray = json.getJSONArray("response");
            for (int i = 0; i < jsonArray.length(); i++) {
              JSONObject jsonObject = jsonArray.getJSONObject(i);
              String name = jsonObject.getString("name");
              String serial = jsonObject.getString("switch_serial");
              int position = jsonObject.getInt("position");
              boolean power;
              if(jsonObject.getInt("power") == 0) {
                power = false;
              } else {
                power = true;
              }
              DeviceListItem item = new DeviceListItem(serial, position, name, power);
              adapter.addDevice(item);
            }
            listView.setAdapter(adapter);
          } catch (Exception e) {
            Log.e("ERROR", "get device error");
          }
        }

        @Override
        protected void httpExcepted() {

        }
      });
    } catch (Exception e) {
      Log.e("get device", "error is occurred at http request");
    }
  }
}
