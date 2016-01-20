package com.itechart.tdd.geocode;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GeocodeServiceTest {

    private static final String GET_DETAILS_URL = "https://maps.googleapis.com/maps/api/geocode/json" +
            "?key=AIzaSyCoDDV2xaVpU2sZYCRJBbFJApUFeQIxRgY" +
            "&address=%D0%9C%D0%B8%D0%BD%D1%81%D0%BA%2C+%D0%A2%D0%BE%D0%BB%D1%81%D1%82%D0%BE%D0%B3%D0%BE+10";

    private static final String GET_DETAILS_WITH_BLANK_ADDRESS_URL = "https://maps.googleapis.com/maps/api/geocode/json" +
            "?key=AIzaSyCoDDV2xaVpU2sZYCRJBbFJApUFeQIxRgY" +
            "&address=";

    private static final String GET_DETAILS_WITH_WRONG_ADDRESS_URL = "https://maps.googleapis.com/maps/api/geocode/json" +
            "?key=AIzaSyCoDDV2xaVpU2sZYCRJBbFJApUFeQIxRgY" +
            "&address=qwerty";

    private static final String GET_DETAILS_RESPONSE;
    private static final String GET_RESPONSE_WITH_ZERRO_REZULT;
    static {
        GET_DETAILS_RESPONSE = "{\n" +
                "   \"results\" : [\n" +
                "      {\n" +
                "         \"address_components\" : [\n" +
                "            {\n" +
                "               \"long_name\" : \"10\",\n" +
                "               \"short_name\" : \"10\",\n" +
                "               \"types\" : [ \"street_number\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"vulica Talstoha\",\n" +
                "               \"short_name\" : \"vulica Talstoha\",\n" +
                "               \"types\" : [ \"route\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"Kastryčnicki rajon\",\n" +
                "               \"short_name\" : \"Kastryčnicki rajon\",\n" +
                "               \"types\" : [ \"sublocality_level_1\", \"sublocality\", \"political\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"Minsk\",\n" +
                "               \"short_name\" : \"Minsk\",\n" +
                "               \"types\" : [ \"locality\", \"political\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"Belarus\",\n" +
                "               \"short_name\" : \"BY\",\n" +
                "               \"types\" : [ \"country\", \"political\" ]\n" +
                "            }\n" +
                "         ],\n" +
                "         \"formatted_address\" : \"vulica Talstoha 10, Minsk, Belarus\",\n" +
                "         \"geometry\" : {\n" +
                "            \"bounds\" : {\n" +
                "               \"northeast\" : {\n" +
                "                  \"lat\" : 53.8886614,\n" +
                "                  \"lng\" : 27.5445995\n" +
                "               },\n" +
                "               \"southwest\" : {\n" +
                "                  \"lat\" : 53.8880033,\n" +
                "                  \"lng\" : 27.5438164\n" +
                "               }\n" +
                "            },\n" +
                "            \"location\" : {\n" +
                "               \"lat\" : 53.88833229999999,\n" +
                "               \"lng\" : 27.544208\n" +
                "            },\n" +
                "            \"location_type\" : \"ROOFTOP\",\n" +
                "            \"viewport\" : {\n" +
                "               \"northeast\" : {\n" +
                "                  \"lat\" : 53.8896813302915,\n" +
                "                  \"lng\" : 27.5455569302915\n" +
                "               },\n" +
                "               \"southwest\" : {\n" +
                "                  \"lat\" : 53.8869833697085,\n" +
                "                  \"lng\" : 27.5428589697085\n" +
                "               }\n" +
                "            }\n" +
                "         },\n" +
                "         \"place_id\" : \"ChIJYfWwzd_P20YRBa2EqPK2Bhg\",\n" +
                "         \"types\" : [ \"premise\" ]\n" +
                "      }\n" +
                "   ],\n" +
                "   \"status\" : \"OK\"\n" +
                "}";
        GET_RESPONSE_WITH_ZERRO_REZULT = "{\n" +
                "   \"results\" : [],\n" +
                "   \"status\" : \"ZERO_RESULTS\"\n" +
                "}";
    }

    private GeocodeService geocodeService;

    @Before
    public void init() {
        HttpURLConnectionHelper connection = mock(HttpURLConnectionHelper.class);
        when(connection.sendGet(GET_DETAILS_URL)).thenReturn(GET_DETAILS_RESPONSE);
        when(connection.sendGet(GET_DETAILS_WITH_WRONG_ADDRESS_URL)).thenReturn(GET_RESPONSE_WITH_ZERRO_REZULT);
        when(connection.sendGet(GET_DETAILS_WITH_BLANK_ADDRESS_URL)).thenThrow(new GooglePlacesException("Bad request"));
        geocodeService = new GeocodeService(connection);
    }

    @Test
    public void testDetails() {
        String address = "Минск, Толстого 10";
        Address result = geocodeService.geocode(address);
        assertEquals("Minsk", result.getCity());
        assertEquals("Belarus", result.getCountry());
        assertEquals("vulica Talstoha", result.getStreet());
        assertEquals("10", result.getStreetNumber());
        assertNotNull(result.getPlaceId());
        assertNotNull(result.getLat());
        assertNotNull(result.getLng());
    }

    @Test(expected = GooglePlacesException.class)
    public void testDetailsWithBlankAddress() {
        geocodeService.geocode("");
    }

    @Test(expected = GooglePlacesException.class)
    public void testDetailsIfAddressIsNull() {
        geocodeService.geocode(null);
    }

    @Test(expected = GooglePlacesException.class)
    public void testDetailsIfAddressIsWrong() {
        String address = "qwerty";
        geocodeService.geocode(address);
    }
}