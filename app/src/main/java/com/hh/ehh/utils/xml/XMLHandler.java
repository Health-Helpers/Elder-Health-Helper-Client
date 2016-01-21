package com.hh.ehh.utils.xml;

import android.util.Xml;

import com.hh.ehh.model.Geofence;
import com.hh.ehh.model.Patient;
import com.hh.ehh.model.User;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mpifa on 20/12/15.
 */
public class XMLHandler {
    private static final String ID_PATIENT = "patientId";
    private static final String ID_DOC = "idDoc";
    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String BIRTHDATE = "birthdate";
    private static final String ADDRESS = "address";
    private static final String PHONE = "phone";
    private static final String DISEASE = "disease";
    private static final String DEPGRADE = "depGrade";
    private static final String PATIENT = "patient";
    private static final String PATIENTS = "patients";
    private static final String PATIENT_HEAD = "getResponsiblePatientsResponse";
    private static final String ENCODING = "UTF-8";


    private static final String GEOFENCE_RESPONSE_HEAD = "getPatientGeofenceResponse";
    private static final String GEOFENCES = "geofences";
    private static final String GEOFENCE = "geofence";
    private static final String GEOFENCE_ID = "id";
    private static final String GEOFENCE_LATITUDE = "latitude";
    private static final String GEOFENCE_LONGITUDE = "longitude";
    private static final String GEOFENCE_RADIUS = "radius";

    private static XmlPullParserFactory xmlFactoryObject;

    public static Patient getPatientFromXML(String stringXML) throws XmlPullParserException, IOException {
        InputStream stream = new ByteArrayInputStream(stringXML.getBytes(ENCODING));
        xmlFactoryObject = XmlPullParserFactory.newInstance();
        XmlPullParser myParser = xmlFactoryObject.newPullParser();
        myParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        myParser.setInput(stream, null);
        Patient patient = parsePatient(myParser);
        stream.close();
        return patient;
    }

    public static List<Patient> getResponsiblePatients(String stringXML) throws XmlPullParserException, IOException {
        InputStream stream = new ByteArrayInputStream(stringXML.getBytes(ENCODING));
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true);
            parser.setInput(stream, null);
            parser.nextTag();
            return parsePatients(parser);
        } finally {
            stream.close();
        }
    }

    private static List<Patient> parsePatients(XmlPullParser parser)
            throws XmlPullParserException, IOException {
        final String ns = "";
        List<Patient> patientList = new ArrayList<Patient>();
        parser.require(XmlPullParser.START_TAG, ns, PATIENT_HEAD);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String tagName = parser.getName();
            if (tagName.equals(PATIENTS)) {
                readPatients(parser, patientList);
//                patientList.add(readPatient(parser));

            } else {
                skipTag(parser);
            }
        }
        return patientList;
    }

    private static void readPatients(XmlPullParser parser, List<Patient> patientList) throws IOException, XmlPullParserException {
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String tagName = parser.getName();
            if (tagName.equals(PATIENT)) {
                patientList.add(readPatient(parser));

            } else {
                skipTag(parser);
            }
        }
    }

    private static Patient readPatient(XmlPullParser parser) throws IOException, XmlPullParserException {
        User.UserBuilder builder = new User.UserBuilder();
        String disease = null;
        String dependencyGrade = null;
        final String ns = "";

        parser.require(XmlPullParser.START_TAG, ns, PATIENT);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            switch (name) {
                case ID_PATIENT:
                    builder.setId(readString(parser, ID_PATIENT));
                    break;
                case ID_DOC:
                    builder.setIdDoc(readString(parser, ID_DOC));
                    break;
                case NAME:
                    builder.setName(readString(parser, NAME));
                    break;
                case SURNAME:
                    builder.setSurname(readString(parser, SURNAME));
                    break;
                case BIRTHDATE:
                    builder.setBirthdate(readString(parser, BIRTHDATE));
                    break;
                case ADDRESS:
                    builder.setAddress(readString(parser, ADDRESS));
                    break;
                case PHONE:
                    builder.setPhone(readString(parser, PHONE));
                    break;
                case DISEASE:
                    disease = readString(parser, DISEASE);
                    break;
                case DEPGRADE:
                    dependencyGrade = readString(parser, DEPGRADE);
                    break;
                default:
                    skipTag(parser);
                    break;
            }
        }
        Patient patient = new Patient(builder.build());
        patient.setDiseases(disease);
        patient.setDependencyGrade(dependencyGrade);
        return patient;
    }

    private static String readString(XmlPullParser parser, String TAG) throws IOException, XmlPullParserException {
        final String ns = "";
        parser.require(XmlPullParser.START_TAG, ns, TAG);
        String nombre = getText(parser);
        parser.require(XmlPullParser.END_TAG, ns, TAG);
        return nombre;
    }

    private static String getText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String resultado = "";
        if (parser.next() == XmlPullParser.TEXT) {
            resultado = parser.getText();
            parser.nextTag();
        }
        return resultado;
    }

    private static void skipTag(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

    private static Patient parsePatient(XmlPullParser myParser) {
        int event;
        Patient patient;
        String text = null;
        User.UserBuilder builder = new User.UserBuilder();
        String disease = null;
        String dependencyGrade = null;
        try {
            event = myParser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                String name = myParser.getName();
                switch (event) {
                    case XmlPullParser.START_TAG:
                        break;
                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        switch (name) {
                            case ID_PATIENT:
                                builder.setId(text);
                                break;
                            case ID_DOC:
                                builder.setIdDoc(text);
                                break;
                            case NAME:
                                builder.setName(text);
                                break;
                            case SURNAME:
                                builder.setSurname(text);
                                break;
                            case BIRTHDATE:
                                builder.setBirthdate(text);
                                break;
                            case ADDRESS:
                                builder.setAddress(text);
                                break;
                            case PHONE:
                                builder.setPhone(text);
                                break;
                            case DISEASE:
                                disease = text;
                                break;
                            case DEPGRADE:
                                dependencyGrade = text;
                                break;
                        }
                        break;
                }
                event = myParser.next();
            }
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }
        patient = new Patient(builder.build());
        patient.setDiseases(disease);
        patient.setDependencyGrade(dependencyGrade);
        return patient;
    }

    public static List<Geofence> getPatientGeofences(String stringXML) throws XmlPullParserException, IOException {
        InputStream stream = new ByteArrayInputStream(stringXML.getBytes(ENCODING));
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true);
            parser.setInput(stream, null);
            parser.nextTag();
            return parseGeofences(parser);
        } finally {
            stream.close();
        }
    }

    private static List<Geofence> parseGeofences(XmlPullParser parser)
            throws XmlPullParserException, IOException {

        final String ns = "";
        List<Geofence> geofenceList = new ArrayList<>();

        parser.require(XmlPullParser.START_TAG, ns, GEOFENCE_RESPONSE_HEAD);

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String tagName = parser.getName();
            if (tagName.equals(GEOFENCES)) {
                readGeofences(parser,geofenceList);
            } else {
                skipTag(parser);
            }
        }

        return geofenceList;
    }

    private static void readGeofences(XmlPullParser parser, List<Geofence> geofenceList) throws IOException, XmlPullParserException {
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String tagName = parser.getName();
            if (tagName.equals(GEOFENCE)) {
                geofenceList.add(readGeofence(parser));
            } else {
                skipTag(parser);
            }
        }
    }

    private static Geofence readGeofence(XmlPullParser parser) throws IOException, XmlPullParserException {


        final String ns = "";
        Geofence geofence = new Geofence();

        parser.require(XmlPullParser.START_TAG, ns, GEOFENCE);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            switch (name) {
                case GEOFENCE_ID:
                    geofence.setGeofenceId(readString(parser, GEOFENCE_ID));
                    break;
                case GEOFENCE_RADIUS:
                    geofence.setRadius(readString(parser, GEOFENCE_RADIUS));
                    break;
                case GEOFENCE_LATITUDE:
                    geofence.setLatitude(readString(parser, GEOFENCE_LATITUDE));
                    break;
                case GEOFENCE_LONGITUDE:
                    geofence.setLongitude(readString(parser, GEOFENCE_LONGITUDE));
                    break;
                default:
                    skipTag(parser);
                    break;
            }
        }

        return geofence;
    }
}

