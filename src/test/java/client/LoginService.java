package client;

import com.google.gson.JsonObject;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;


/**
 * Created by a.oreshnikova on 03.08.2018.
 */

public class LoginService {

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://auth.iqoption.com/api/v1.0/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private LoginInterface api = retrofit.create(LoginInterface.class);

    public Response<JsonObject> doLogin(String email, String password, String googleClientId) {
        Response<JsonObject> response = null;
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("email", email).addFormDataPart("password", password).addFormDataPart("google_client_id", googleClientId);
        Call<JsonObject> json = api.doLogin(builder.build());
        try {
            response = json.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
