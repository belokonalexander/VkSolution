package Helper;

import android.content.Context;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by alexander on 20.09.2016.
 */
public class GlobalShell {

    //internal save
    public static boolean saveFileFromUrl(String imageUrl, String filename, Context context){

        try {

            URL url = new URL(imageUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");

            urlConnection.connect();

            int status = urlConnection.getResponseCode();
            InputStream inputStream = urlConnection.getInputStream();
            int totalSize = urlConnection.getContentLength();
            int downloadedSize = 0;
            byte[] buffer = new byte[1024];
            int bufferLength = 0;

            BufferedOutputStream bw = new BufferedOutputStream(
                    context.openFileOutput(filename, context.MODE_PRIVATE));

            while ( (bufferLength = inputStream.read(buffer)) > 0 )
            {
                bw.write(buffer, 0, bufferLength);
                downloadedSize += bufferLength;

            }
            bw.close();
            inputStream.close();

            if(downloadedSize==totalSize) {
                return true;
            }
        } catch (Exception e){
            LogSystem.LogThis("Ошибка при загрузке файла");
        }
        return false;
    }

}
