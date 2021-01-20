package com.example.weathertest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private TextView edittext;
    private TextView tem;
    private TextView tem1;
    private TextView tem2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edittext = (TextView) findViewById(R.id.editText1);
        Button button = (Button) findViewById(R.id.button1);
        final TextView temp = findViewById(R.id.tem);
        final TextView temp1 = findViewById(R.id.tem1);
        final TextView temp2 = findViewById(R.id.tem2);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String my_string = edittext.getText().toString();
                if (TextUtils.isEmpty(my_string)) {
                    Toast.makeText(MainActivity.this, "没有数据输入", Toast.LENGTH_LONG).show();
                } else {
                    Map<String, String> map2 = new HashMap<String, String>();
                    map2.put("北京", "101010100");
                    map2.put("beijing", "101010100");
                    map2.put("天津", "101030100");
                    map2.put("上海", "101020100");
                    map2.put("西安", "101110101");
                    map2.put("成都", "101270101");
                    map2.put("兰州", "101160101");
                    map2.put("昆明", "101290101");
                    map2.put("双流区", "101270106");
                    map2.put("成都市双流区", "101270106");
                    map2.put("双流", "101270106");
                    map2.put("广州", "101280101");
                    map2.put("武汉", "101200101");
                    map2.put("雁塔区", "101110113");
                    map2.put("雁塔", "101110113");
                    map2.put("兴平", "101110211");
                    map2.put("兴平市", "101110211");
                    String str_city = map2.get(my_string);
                    StringBuilder sb = new StringBuilder();
                    InputStream is = null;
                    BufferedReader br = null;
                    PrintWriter out = null;
                    try {
                        String url = "http://gfapi.mlogcn.com/weather/v001/now?areacode=" + str_city;
                        URL uri = new URL(url);
                        HttpURLConnection connection = (HttpURLConnection) uri.openConnection();
                        connection.setRequestMethod("GET");
                        connection.setReadTimeout(5000);
                        connection.setConnectTimeout(10000);
                        connection.setRequestProperty("key", "3gB6JNhQfJIH69q13eh9fdBdD8KFohAg");
                        connection.connect();
                        is = connection.getInputStream();
                        br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                        String line;
                        //缓冲逐行读取
                        while ((line = br.readLine()) != null) {
                            sb.append(line);
                        }
                        System.out.println(sb.toString());

                        int i1 = sb.indexOf("text");
                        int j = sb.indexOf("last_update");
                        char[] c = new char[100000];
                        sb.getChars(i1 - 1, j - 2, c, 0);
                        System.out.println(c);

                        String s = String.valueOf(c);
                        System.out.println(s);
                        String str2 = s.replace("\"", "").replace("}", "");
                        System.out.println(str2);

                        String[] str3 = str2.split(",");
                        //创建Map对象
                        Map<String, Object> map = new HashMap<String, Object>();
                        //循环加入map集合
                        for (int i = 0; i < str3.length; i++) {
                            //根据":"截取字符串数组
                            String[] str4 = str3[i].split(":");
                            //str2[0]为KEY,str2[1]为值
                            map.put(str4[0], str4[1]);
                        }
                        System.out.println(map.get("text"));
                        System.out.println(map.get("temp"));
                        System.out.println(map.get("wind_dir"));
                        temp.setText("天气:" + map.get("text"));
                        temp1.setText("温度:" + map.get("temp"));
                        temp2.setText("风向:" + map.get("wind_dir"));
                    } catch (Exception ignored) {
                        System.out.println(ignored);
                    } finally {
                        //关闭流
                        try {
                            if (is != null) {
                                is.close();
                            }
                            if (br != null) {
                                br.close();
                            }
                            if (out != null) {
                                out.close();
                            }
                        } catch (Exception ignored) {
                            System.out.println(ignored);
                        }
                    }
                }
            }
        });

    }


}
