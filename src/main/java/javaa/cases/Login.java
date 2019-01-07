package javaa.cases;
import javaa.config.TestConfig;
import javaa.model.Interfacename;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

import javaa.model.Userlogin;
import javaa.utis.Configfile;
import javaa.utis.Databatisutils;

/**
 * Created by Administrator on 2018/12/18.
 */
public class Login {
    @BeforeTest(groups = "LoginTrue",description = "测试前准备httpclient对象..")
    public void beforetest(){
        TestConfig.getUserUrl= Configfile.getUrl(Interfacename.GETUSER);
        TestConfig.getUserListUrl=Configfile.getUrl(Interfacename.GETUSERLIST);
        TestConfig.addUserUrl=Configfile.getUrl(Interfacename.ADDUSER);
        TestConfig.loginUrl=Configfile.getUrl(Interfacename.USERLOGIN);
        TestConfig.updateUserUrl=Configfile.getUrl(Interfacename.UPDATEUSER);
        TestConfig.defaultHttpClient=new DefaultHttpClient();

    }
    @Test(groups = "loginTrue",description = "用户登录成功接口")
    public  void login() throws IOException, InterruptedException {
        SqlSession session= Databatisutils.getSqlSession();
        Userlogin userlogin=session.selectOne("userlogin",1);
        System.out.println(userlogin.toString());
        System.out.println(TestConfig.loginUrl);

        String result = getResult(userlogin);
        //处理结果，就是判断返回结果是否符合预期
        Thread.sleep(3000);
        Assert.assertEquals(userlogin.getExpected(),result);
    }

    private String getResult(Userlogin userlogin) throws IOException {
        //下边的代码为写完接口的测试代码
        HttpPost post = new HttpPost(TestConfig.loginUrl);
        JSONObject param = new JSONObject();
        param.put("name",userlogin.getName());
        param.put("password",userlogin.getPassword());
        //设置请求头信息 设置header
        post.setHeader("content-type","application/json");
        //将参数信息添加到方法中
        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);
        //声明一个对象来进行响应结果的存储
        String result;
        //执行post方法
        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
        //获取响应结果
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);
        TestConfig.store = TestConfig.defaultHttpClient.getCookieStore();
        return result;
    }


    @Test(groups = "loginfalse",description = "登录失败接口测试")
    public  void loginfalse() throws IOException {
        SqlSession session= Databatisutils.getSqlSession();
        Userlogin userlogin=session.selectOne("userlogin",2);
        System.out.println(userlogin.toString());
        System.out.println(TestConfig.loginUrl);

        String result = getResult(userlogin);
        //处理结果，就是判断返回结果是否符合预期
        Assert.assertEquals(userlogin.getExpected(),result);
    }


}
