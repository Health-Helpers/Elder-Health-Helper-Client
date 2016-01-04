package com.hh.ehh.utils.json;

import com.hh.ehh.model.MedicalCenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mpifa on 21/12/15.
 */
public class JSONParser {

    public static List<MedicalCenter> getAllMedicalCentersFromJSON(String bodyUnParsed) throws JSONException {
        List<MedicalCenter> medicalCenters = new ArrayList<>();
        JSONObject object = new JSONObject(bodyUnParsed);
        JSONArray array = object.getJSONArray(JSONConsts.MEDICAL_CENTERS);
        MedicalCenter medicalCenter;
        for (int i = 0; i < array.length(); i++) {
            object = array.getJSONObject(i);
            medicalCenter = new MedicalCenter(
                    object.getString(JSONConsts.ID),
                    object.getString(JSONConsts.NAME),
                    String.valueOf(object.getDouble(JSONConsts.LATITUDE)),
                    String.valueOf(object.getDouble(JSONConsts.LONGITUDE)),
                    object.getString(JSONConsts.ADDRESS),
                    object.getString(JSONConsts.PHONE));
            medicalCenters.add(medicalCenter);
        }
        return medicalCenters;
    }

    public static MedicalCenter getMedicalCenterFromJSON(String bodyUnParsed) throws JSONException {
        JSONArray array = new JSONArray(bodyUnParsed);
        JSONObject object;
        MedicalCenter medicalCenter;
        object = array.getJSONObject(0);
        medicalCenter = new MedicalCenter(
                object.getString(JSONConsts.ID),
                object.getString(JSONConsts.NAME),
                object.getString(JSONConsts.LATITUDE),
                object.getString(JSONConsts.LONGITUDE),
                object.getString(JSONConsts.ADDRESS),
                object.getString(JSONConsts.PHONE));
        return medicalCenter;
    }
}
