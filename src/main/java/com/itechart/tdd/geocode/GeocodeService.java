package com.itechart.tdd.geocode;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeocodeService {

    private static final String GEOCODE_API_BASE = "https://maps.googleapis.com/maps/api/geocode/json?";
    private static final String API_KEY = "AIzaSyCoDDV2xaVpU2sZYCRJBbFJApUFeQIxRgY";

    private HttpURLConnectionHelper connection;

    public GeocodeService(HttpURLConnectionHelper connection) {
        this.connection = connection;
    }

    public Address geocode(String address) {
        if (StringUtils.isBlank(address)) {
            throw new GooglePlacesException("Address cannot be blank");
        }

        StringBuilder sb = new StringBuilder(GEOCODE_API_BASE);
        sb.append("key=").append(API_KEY);
        try {
            sb.append("&address=").append(URLEncoder.encode(address, "utf8"));
        } catch (UnsupportedEncodingException e) {
            throw new GooglePlacesException("Invalid address", e);
        }

        String response = connection.sendGet(sb.toString());
        return parseResponse(response);
    }

    private Address parseResponse(String response) {
        Map<String, Object> map;
        Address address = new Address();

        if (response == null || response.isEmpty()) {
            throw new GooglePlacesException("Bad request");
        }

        ObjectMapper mapper = new ObjectMapper();

        try {
            map = mapper.readValue(response, new TypeReference<HashMap<String, Object>>() {});
        } catch (IOException e) {
            throw new GooglePlacesException("Invalid response");
        }

        String status = (String)map.get("status");

        if (status == null || !status.equalsIgnoreCase("OK")) {
            throw new GooglePlacesException("Bad request");
        }

        if (((List)map.get("results")).isEmpty()) {
            throw new GooglePlacesException("Bad request");
        }

        Map<String,Object> result = (Map<String,Object>)((List)map.get("results")).get(0);

        if (result == null || result.isEmpty()) {
            throw new GooglePlacesException("Bad request");
        }

        List<Object> addressComponents = (List<Object>)result.get("address_components");

        if (addressComponents == null || addressComponents.isEmpty()) {
            throw new GooglePlacesException("Bad request");
        }

        for (Object o : addressComponents) {
            Map<String, Object> addressComponent = (Map<String, Object>)o;
            List<String> types = (List<String>)addressComponent.get("types");

            if (types.contains("country")) {
                address.setCountry((String) addressComponent.get("long_name"));
            } else if (types.contains("locality")) {
                address.setCity((String)addressComponent.get("long_name"));
            } else if (types.contains("street_number")) {
                address.setStreetNumber((String)addressComponent.get("long_name"));
            } else if (types.contains("route")) {
                address.setStreet((String) addressComponent.get("long_name"));
            }
        }

        Map<String, Object> latLng = (Map<String, Object>)((Map<String, Object>)result.get("geometry")).get("location");
        address.setLng((Double) latLng.get("lng"));
        address.setLat((Double) latLng.get("lat"));

        Object formattedAddress = result.get("formatted_address");
        if (formattedAddress != null) {
            address.setFullAddress(formattedAddress.toString());
        }

        Object placeId = result.get("place_id");
        if (placeId != null) {
            address.setPlaceId(placeId.toString());
        }

        return address;
    }

}
