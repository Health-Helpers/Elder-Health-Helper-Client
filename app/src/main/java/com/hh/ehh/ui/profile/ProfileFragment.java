package com.hh.ehh.ui.profile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hh.ehh.R;
import com.hh.ehh.model.Profile;


public class ProfileFragment extends Fragment {
    private static final String PROFILE_KEY = "PROFILE";
    private Profile dbProfile;
    private ImageView avatar;
    private TextView name, mail, city;

    public static ProfileFragment newInstance(Profile profile) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(PROFILE_KEY, profile);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);
        avatar = (ImageView) view.findViewById(R.id.profile_image);
        name = (TextView) view.findViewById(R.id.name);
        mail = (TextView) view.findViewById(R.id.mail);
        city = (TextView) view.findViewById(R.id.city);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(dbProfile==null){
            dbProfile = new Profile("1","Juan","Perez","juanperez@gmail.com","Lleida",null,"973234323");
        }
        //avatar.setImageDrawable(profile.getImageAsDrawable());
        name.setText(dbProfile.getName());
        mail.setText(dbProfile.getEmail());
        city.setText(dbProfile.getLocation());
       setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.profile_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.refresh:
//                logout();
                break;
            default:
                break;
        }
        return true;
    }
    //UNCOMENT WHEN DB IS WORKING
//    private void logout() {
//        final AlertDialog.Builder licenceDialog = new AlertDialog.Builder(getActivity());
//        licenceDialog.setTitle(getActivity().getResources().getString(R.string.warning));
//        licenceDialog.setMessage(getActivity().getResources().getString(R.string.delete_warning)).setCancelable(false)
//                .setPositiveButton(R.string.Accept, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                        DataBaseSQLiteHelper dbHelper = DataBaseSQLiteHelper.newInstance(getActivity());
//                        getActivity().deleteDatabase(dbHelper.getDatabaseName());
//                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SharedPrefsConstants.PREFS, Context.MODE_PRIVATE);
//                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                        editor.clear().apply();
//                        Intent intent = new Intent(getActivity(), SplashActivity.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(intent);
//                        getActivity().finish();
//                    }
//                })
//                .setNegativeButton(R.string.decline, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//        licenceDialog.show();
//    }

}

