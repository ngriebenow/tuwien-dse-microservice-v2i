package dse.grp20.actorcontrol.utils;

import dse.grp20.common.dto.GeoDTO;

/**
 * @Package: dse.grp20.actorcontrol.utils
 * @Class: Utils
 * @Author: Guenter Windsperger {01302775}
 * @Date: 24.05.2020
 */
public class Utils {

    public static GeoDTO minus(GeoDTO geo1, GeoDTO geo2) {
        return new GeoDTO(geo1.getLatitude() - geo2.getLatitude(), geo1.getLongitude() - geo2.getLongitude());
    }

    public static GeoDTO plus(GeoDTO geo1, GeoDTO geo2) {
        return new GeoDTO(geo1.getLatitude() + geo2.getLatitude(), geo1.getLongitude() + geo2.getLongitude());
    }


    public static double distance(GeoDTO start, GeoDTO end) {
        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(end.getLatitude() - start.getLatitude());
        double lonDistance = Math.toRadians(end.getLongitude() - start.getLongitude());
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(start.getLatitude())) * Math.cos(Math.toRadians(end.getLatitude()))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c * 1000; // convert to meters
    }

}
