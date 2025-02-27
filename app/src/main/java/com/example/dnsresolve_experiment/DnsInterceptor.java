package com.example.dnsresolve_experiment;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Dns;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.dnsoverhttps.DnsOverHttps;

public class DnsInterceptor {
    private Dns dnsResolver = new DnsOverHttps.Builder()
            .client(new OkHttpClient()).url(HttpUrl.get("https://low.kahfguard.com/dns-query")).build();

    private OkHttpClient client = new OkHttpClient.Builder().dns(this.dnsResolver).build();

    public void makeGetRequest(String urlString, Callback callback) {
        Request request = new Request.Builder().url(urlString).build();

        try {
            client.newCall(request).enqueue(callback);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
//        catch (e) {
//            System.out.println("Printing Error");
//            System.out.println(e.toString());
//        }

    }
}
