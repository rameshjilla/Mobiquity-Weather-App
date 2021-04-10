package com.mobiquity.utils


object Constants {

    object NetworkService {
        const val BASE_URL = "http://api.openweathermap.org"
        const val API_KEY_VALUE = "fae7190d7e6433ec3a45285ffcf55c86"
        const val RATE_LIMITER_TYPE = "data"
        const val API_KEY_QUERY = "appid"
    }

    object AlgoliaKeys {
        const val APPLICATION_ID = "plNW8IW0YOIN"
        const val SEARCH_API_KEY = "029766644cb160efa51f2a32284310eb"
    }

    object cities {
        const val samplelist = "[\n" +
                "  {\n" +
                "    name: \"Hyderabad\",lon:78.4744,lat:17.3753\n" +
                "  },\n" +
                "  {\n" +
                "    name: \"Chennai\",lon:80.2785,lat:13.0878\n" +
                "  },\n" +
                "  {\n" +
                "    name: \"Mumbai\",lon:72.8479,lat:19.0144\n" +
                "  },\n" +
                "  {\n" +
                "    name: \"Pune\",lon:73.8553,lat:18.5196\n" +
                "  },\n" +
                "  {\n" +
                "    name: \"Goa\",lon:74.0833,lat:15.3333\n" +
                "  },\n" +
                "{\n" +
                "    name: \"Delhi\",lon:77.2167,lat:28.6667\n" +
                "  },\n" +
                "{\n" +
                "    name: \"Kolkata\",lon:88.3697,lat:22.5697\n" +
                "  },\n" +
                "  \n" +
                "]"
    }

    object Coords {
        const val LAT = "lat"
        const val LON = "lon"
        const val METRIC = "metric"
    }
}
