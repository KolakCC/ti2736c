package project.omdb;

import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.net.URLCodec;

/**
 * Created by Dereck on 3/16/2015.
 */
public class API {
    public static OMDBMovie searchMovie(ti2736c.Movie m) throws Exception {
        return searchMovie(getSearchTitle(m));
    }

    public static String doCall(String url) throws Exception {
        InputStream in = null;
        try {
            in = new URL(url).openStream();
            String json = IOUtils.toString(in);
            return json;
        } finally {
            IOUtils.closeQuietly(in);
        }
    }

    public static OMDBMovie searchMovie(String title) throws Exception {
        System.out.println("OMDB API: Search " + title);
        Gson gson = new Gson();

        URLCodec codec = new URLCodec();
        String result = doCall("http://www.omdbapi.com/?t=" + codec.encode(title) + "&y=&plot=short&r=json");

        System.out.println(result);
        OMDBMovie movie = gson.fromJson(result, OMDBMovie.class);

        return movie;
    }

    public static String getSearchTitle(ti2736c.Movie m) throws EncoderException {
        String[] splitted = m.getTitle().replaceAll("_", " ").split(" ");
        String[] dropYear = Arrays.copyOf(splitted, splitted.length - 1);
        StringBuilder join = new StringBuilder();
        for (String s : dropYear) {
            join.append(s);
            join.append(" ");
        }
        URLCodec codec = new URLCodec();
        return codec.encode(join.toString());
    }

    public static String getSearchTitle(String t) throws EncoderException {
        String withoutYear = t.replaceAll("_\\(.+?\\)$", "");
        int commaPosition = withoutYear.lastIndexOf(",");
        if (commaPosition != -1) {
            withoutYear = withoutYear.substring(commaPosition) + withoutYear.substring(0,commaPosition);
        }
        /*if (t.contains(",_The_")) {
            name = "The " + t.replaceAll(",_The", "");
        }

        if (t.contains(",_A_")) {
            name = "A " + t.replaceAll(",_A_", "");
        }*/

        URLCodec codec = new URLCodec();
        return codec.encode(withoutYear);
    }
}

class MovieList extends ArrayList<OMDBMovie> {
}