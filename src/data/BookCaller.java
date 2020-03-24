package data;

import domain.JsonResult;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Handles the API call
 */
public class BookCaller {

    /**
     * @param ISBN the ISBN of the book for which to get information
     * @return JsonResult object with the information from the API, can contain NULL objects
     */
    public static JsonResult getBookByISBN(String ISBN) {
        try {
            URL bookInfo = new URL("https://www.googleapis.com/books/v1/volumes?q=isbn:" + ISBN);

            InputStream input = bookInfo.openStream();
            Reader reader = new InputStreamReader(input, "UTF-8");
            return new Gson().fromJson(reader, JsonResult.class);

        } catch(MalformedURLException e) {
            System.out.println("A problem occurred creating the URK");
            e.printStackTrace();
        } catch(IOException e) {
            System.out.println("A problem occurred opening a stream");
            e.printStackTrace();
        }

        return null;
    }
}
