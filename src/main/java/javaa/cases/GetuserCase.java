package javaa.cases;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import javaa.config.TestConfig;
import java.io.IOException;
import javaa.model.Getuser;
import javaa.model.User;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javaa.utis.Databatisutils;

/**
 * Created by XULE on 2018/12/18.
 */
public class GetuserCase {
    @Test(dependsOnGroups = "loginTrue",description = "获取userid为1的用户信息")
    public void getuser() throws IOException, InterruptedException {
        SqlSession session = Databatisutils.getSqlSession();
        Getuser getuser=session.selectOne("getuser",1);
        System.out.println(getuser.toString());
        System.out.println(TestConfig.getUserUrl);

        Thread.sleep(4000);

        JSONArray resultJson = getJsonResult(getuser);

        /**
         * 下边三行可以先讲
         */
        User user = session.selectOne(getuser.getExpected(),getuser);
        System.out.println("自己数据获取用户信息:"+user.toString());
        List userList = new ArrayList();
        userList.add(user);
        JSONArray jsonArray = new JSONArray(userList);
        JSONArray jsonArray1=new JSONArray(resultJson.getString(0));
        //预期结果
        System.out.println("预期结果:"+jsonArray);
        System.out.println("实际结果："+jsonArray1);
        //实际结果
        System.out.println("调用接口获取用户信息:"+jsonArray1);
        Assert.assertEquals(jsonArray.toString(),jsonArray1.toString());
    }

    private JSONArray getJsonResult(Getuser getuser) throws IOException {
        HttpPost post = new HttpPost(TestConfig.getUserUrl);
        System.out.print(post);
        JSONObject param = new JSONObject();
        param.put("id",getuser.getUserid());
        //设置请求头信息 设置header
        post.setHeader("content-type","application/json");
        //将参数信息添加到方法中
        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);
        //设置cookies
        TestConfig.defaultHttpClient.setCookieStore(TestConfig.store);
        //声明一个对象来进行响应结果的存储
        String result;
        //执行post方法
        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
        //获取响应结果
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println("调用接口result:"+result);
        List resultList = Arrays.asList(result);
        JSONArray array = new JSONArray(resultList);
        System.out.println(array.toString());
        return array;
    }
}

