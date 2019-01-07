package javaa.utis;
import javaa.model.Interfacename;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Administrator on 2018/12/18.
 */
public class Configfile {
    private static ResourceBundle bundle= ResourceBundle.getBundle("application", Locale.CHINA);;

    public static String getUrl(Interfacename name){
        String address = bundle.getString("test.url");
        String uri = "";
        String testUrl;
        if(name == Interfacename.ADDUSER){
            uri = bundle.getString("addUser.uri");

        }

        if(name == Interfacename.GETUSER){
            uri = bundle.getString("getUser.uri");
        }

        if(name == Interfacename.GETUSERLIST){
            uri = bundle.getString("getUserList.uri");
        }

        if(name == Interfacename.UPDATEUSER){
            uri = bundle.getString("updateUser.uri");
        }

        if(name == Interfacename.USERLOGIN){
            uri = bundle.getString("login.uri");
        }
        testUrl = address + uri;
        return testUrl;
    }
}
