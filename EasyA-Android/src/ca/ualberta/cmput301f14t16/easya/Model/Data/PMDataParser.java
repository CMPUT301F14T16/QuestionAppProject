package ca.ualberta.cmput301f14t16.easya.Model.Data;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

import com.google.gson.Gson;

import ca.ualberta.cmput301f14t16.easya.Model.Content;
import ca.ualberta.cmput301f14t16.easya.Model.Question;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Responsible for dealing with the Json files at a more lower level
 * @author Cauani
 *
 */
public class PMDataParser {
	public final static void saveJson(Context c, PMFilesEnum filename, String json)
    {
        FileOutputStream outputStream;
        try
        {
            outputStream = c.openFileOutput(filename.getArchiveName(), Context.MODE_PRIVATE);
            outputStream.write(json.getBytes());
            outputStream.flush();
            outputStream.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public final static String loadJson(Context c, PMFilesEnum filename)
    {
        StringBuffer sBuffer = new StringBuffer("");
        try
        {
            FileInputStream inputStream = null;
            try
            {
                inputStream = c.openFileInput(filename.getArchiveName());
            }
            catch (IOException e)
            {
                FileOutputStream outputStream;
                outputStream = c.openFileOutput(filename.getArchiveName(), Context.MODE_PRIVATE);
                outputStream.flush();
                outputStream.close();

                inputStream = c.openFileInput(filename.getArchiveName());
            }
            InputStreamReader inputReader = new InputStreamReader(inputStream);
            BufferedReader bufferReader = new BufferedReader(inputReader);

            String readString = bufferReader.readLine();
            while (readString != null)
            {
                sBuffer.append(readString);
                readString = bufferReader.readLine();
            }
            inputReader.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return sBuffer.toString();
    }
    
    public final static void saveUserPreference(Context c, String key, String content)
    {
    	Gson gson = new Gson();
        SharedPreferences sharedPref = c.getSharedPreferences(
                key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, content);
        editor.commit();
    }

    public final static String recoverUserPreference(Context c, String key)
    {
        SharedPreferences sharedPref = c.getSharedPreferences(
                key, Context.MODE_PRIVATE);
        return sharedPref.getString(key, "");
    }
}
