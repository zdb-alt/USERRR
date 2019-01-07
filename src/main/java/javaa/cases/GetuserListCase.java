package javaa.cases;
import javaa.config.TestConfig;
import javaa.model.User;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import javaa.model.GetuserList;

import java.util.List;
import javaa.utis.Databatisutils;

/**
 * Created by XULE on 2018/12/18.
 */
public class GetuserListCase {
    @Test(dependsOnGroups = "loginTrue",description = "获取性别为男的用户")
    public void getuserList() throws IOException, InterruptedException {
        SqlSession session = Databatisutils.getSqlSession();
        GetuserList getuserList=session.selectOne("getuserList",1);
        System.out.println(getuserList.toString());
        System.out.println(TestConfig.getUserListUrl);
        //发送请求
        JSONArray resultJson = getJsonResult(getuserList);
        /**
         * 验证结果
         */
        Thread.sleep(3000);
        List<User> userList = session.selectList(getuserList.getExpected(),getuserList);
        for(User u : userList){
            System.out.println("获取的user:"+u.toString());
        }
        JSONArray userListJson = new JSONArray(userList);
        Thread.sleep(3000);
        Assert.assertEquals(userListJson.length(),resultJson.length());

        for(int i = 0;i<resultJson.length();i++){
            JSONObject expect = (JSONObject) resultJson.get(i);
            JSONObject actual = (JSONObject) userListJson.get(i);
            Assert.assertEquals(expect.toString(),actual.toString());
        }

    }

    private JSONArray getJsonResult(GetuserList getuserList) throws IOException {
        HttpPost post = new HttpPost(TestConfig.getUserListUrl);
        JSONObject param = new JSONObject();
        param.put("name",getuserList.getName());
        param.put("sex",getuserList.getSex());
        param.put("age",getuserList.getAge());
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
        JSONArray jsonArray = new JSONArray(result);

        System.out.println("调用接口list result:"+result);

        return jsonArray;

    }

}

