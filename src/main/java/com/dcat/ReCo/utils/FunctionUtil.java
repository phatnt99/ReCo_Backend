package com.dcat.ReCo.utils;

import java.time.LocalDate;

import com.dcat.ReCo.dtos.NearByRestaurantDTO;
import com.dcat.ReCo.dtos.RestaurantDistanceDTO;

public class FunctionUtil {
	public static Double avg(Integer... args) {
		Double result = 0.0;

		for (Integer i : args) {
			result += i;
		}

		return result / args.length;
	}
	
	public static double calDistance(double lng1, double lat1, double lng2, double lat2) {
		
		double userLat = lat1;
		double userLng = lng1;
		double venueLat = lat2;
		double venueLng = lng2;
		
		final int R = 6371; // Radius of the earth

		double latDistance = Math.toRadians(userLat - venueLat);
	    double lngDistance = Math.toRadians(userLng - venueLng);

	    double a = (Math.sin(latDistance / 2) * Math.sin(latDistance / 2)) +
	                    (Math.cos(Math.toRadians(userLat))) *
	                    (Math.cos(Math.toRadians(venueLat))) *
	                    (Math.sin(lngDistance / 2)) *
	                    (Math.sin(lngDistance / 2));

	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

	    
	    return (double)(Math.round(R * c));
	}

	public static RestaurantDistanceDTO distance(RestaurantDistanceDTO r, NearByRestaurantDTO dto) {
		if(r.getLatitude() == null || r.getLongtitude() == null) {
			r.setDistance((-1d));
			return r;
		}
		double userLat = dto.getLatitude();
		double userLng = dto.getLongtitude();
		double venueLat = r.getLatitude();
		double venueLng = r.getLongtitude();
		
		final int R = 6371; // Radius of the earth

		double latDistance = Math.toRadians(userLat - venueLat);
	    double lngDistance = Math.toRadians(userLng - venueLng);

	    double a = (Math.sin(latDistance / 2) * Math.sin(latDistance / 2)) +
	                    (Math.cos(Math.toRadians(userLat))) *
	                    (Math.cos(Math.toRadians(venueLat))) *
	                    (Math.sin(lngDistance / 2)) *
	                    (Math.sin(lngDistance / 2));

	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

	    r.setDistance((double)(Math.round(R * c)));
	    
	    return r;
	}
	
	public static boolean isRunning(LocalDate fromTime, LocalDate toTime) {
		LocalDate now = LocalDate.now();
		if(now.isEqual(fromTime) || now.isEqual(toTime))
			return true;
		return now.isAfter(fromTime) && now.isBefore(toTime);
	}
}
