package com.example.cmo_lms.services;

import com.example.cmo_lms.model.LoginResponseModel;
import com.example.cmo_lms.model.Cmo_Lms_Summary_ResponseModel;
import com.example.cmo_lms.model.RepDetailsResponse;
import com.example.cmo_lms.model.SearchResponseModel;
import com.example.cmo_lms.model.Searchref_noResponseModel;
import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface ApiService {
    @GET("/Api/cmolmssummary")
    Call<List<Cmo_Lms_Summary_ResponseModel>> getData();

    @POST("/Api/CCLogin")
    Call<LoginResponseModel> loginUser(@Query("user") String username, @Query("pwd") String password);

    @POST("/Api/SearchRepGrievMob")
    Call<List<SearchResponseModel>> searchByName(@Query("refname") String ref_name);

    @POST("/Api/GetRepresentativeDetails")
    Call<Searchref_noResponseModel> searchByRef_no(@Query("ref_no") String refNo);

    @POST("/Api/GetRepDetailsIdToName")
    Call<RepDetailsResponse> searchByIds(@Body JsonObject requestBody);

    @Streaming
    @GET("Grievance/download")
    Call<ResponseBody> downloadFile(@Query("path") String filePath);
}
