package uv.er.joseph.gpsdriver.location;

/**
 * Created by Wesker on 04/01/2017.
 */

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface VehicleService {
    @GET("vehicles")
    Call<List<Vehicle>> all();

    @GET("vehicles/{vehicleno}")
    Call<Vehicle> get(@Path("vehicleno") int vehicleno);

    @POST("vehicles/new")
    Call<Vehicle> create(@Body Vehicle vehicle);

    @PUT("vehicles/{vehicleno}")
    Call<Vehicle> update(@Path("vehicleno") int vehicleno, @Body Vehicle vehicle);

    /*@DELETE("books/{id}")
    void delete(@Path("id") Long id);*/
}