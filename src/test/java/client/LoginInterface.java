package client;

import com.google.gson.JsonObject;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by a.oreshnikova on 03.08.2018.
 */

public interface LoginInterface {

    @POST("login")
    Call<JsonObject> doLogin(@Body RequestBody body);
}
