package Helper;

import android.util.Log;

/**
 * Created by admin on 19.09.2016.
 */
public class LogSystem {
    public static final String TAG = "TAG";
    public static void LogThis(String object)
    {
        Log.e(TAG, " -> " + object.toString());
    }
}
