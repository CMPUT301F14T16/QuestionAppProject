package ca.ualberta.cmput301f14t16.easya.Model.Data;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Responsible for dealing with the Json files at a more lower level
 * @author Cauani
 *
 */
public class PMDataParser {
	public final static void saveJson(PMFilesEnum filename, String json){
        FileOutputStream outputStream;
        try
        {
        	ContextProvider.get().deleteFile(filename.getArchiveName());
            outputStream = ContextProvider.get().openFileOutput(filename.getArchiveName(), Context.MODE_PRIVATE);
            outputStream.write(json.getBytes());
            outputStream.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public final static String loadJson(PMFilesEnum filename){
        StringBuffer sBuffer = new StringBuffer("");
        try
        {
            FileInputStream inputStream = null;
            try
            {
                inputStream = ContextProvider.get().openFileInput(filename.getArchiveName());
            }
            catch (IOException e)
            {
                FileOutputStream outputStream;
                outputStream = ContextProvider.get().openFileOutput(filename.getArchiveName(), Context.MODE_PRIVATE);                
                outputStream.close();

                inputStream = ContextProvider.get().openFileInput(filename.getArchiveName());
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
    
    public final static void saveUserPreference(String key, String content){
        SharedPreferences sharedPref = ContextProvider.get().getSharedPreferences(
                key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, content);
        editor.commit();
    }

    public final static String recoverUserPreference(String key){
        SharedPreferences sharedPref = ContextProvider.get().getSharedPreferences(
                key, Context.MODE_PRIVATE);
        return sharedPref.getString(key, "");
    }
}
