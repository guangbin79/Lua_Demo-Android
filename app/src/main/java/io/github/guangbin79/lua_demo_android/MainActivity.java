package io.github.guangbin79.lua_demo_android;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.keplerproject.luajava.LuaState;
import org.keplerproject.luajava.LuaStateFactory;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testWebServer();
    }

    private void initSystem(LuaState luaState) {
        Context context = getApplicationContext();

        luaState.pushString(context.getCacheDir().getAbsolutePath());
        luaState.setGlobal("CachesDirectory");

        luaState.pushString(context.getFilesDir().getAbsolutePath());
        luaState.setGlobal("DocumentsDirectory");

        luaState.pushString(context.getPackageResourcePath());
        luaState.setGlobal("ResourceDirectory");

        luaState.pushString(context.getDatabasePath("temp").getAbsolutePath());
        luaState.setGlobal("TemporaryDirectory");

        luaState.LdoFile(String.format("%s$assets/lua-script/system.lua", context.getPackageResourcePath()));
    }

    private void testWebServer() {
        Log.v("Lua_Demo", "*** wsapi-xavante\r\n");

        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);

        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }

        String ip = getWifiApIpAddress();
        TextView ipView = (TextView)findViewById(R.id.URLView);

        if (ip != null) {
            ipView.setText(String.format("http://%s:8080/hello.html", ip));
        } else {
            ipView.setText(String.format("Please connect to Wi-Fi for testing.", ip));
        }

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run()
            {
                LuaState luaState = LuaStateFactory.newLuaState();
                luaState.openLibs();
                initSystem(luaState);
                luaState.LdoString("require 'test.wsapi-xavante.xavante-example'");
                luaState.close();
            }
        });
        thread.start();
    }

    private String getWifiApIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en
                    .hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                if (intf.getName().contains("wlan")) {
                    for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr
                            .hasMoreElements();) {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress()
                                && (inetAddress.getAddress().length == 4)) {
                            return inetAddress.getHostAddress();
                        }
                    }
                }
            }
        } catch (SocketException ex) {
        }
        return null;
    }
}

