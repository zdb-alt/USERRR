package javaa.cases;
import javaa.config.TestConfig;
import javaa.model.Adduser;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

import javaa.model.User;
import javaa.utis.Databatisutils;

/**
 * Created by XULE on 2018/12/18.
 */

public class Addusercase {
    @Test(dependsOnGroups = "loginTrue",description = "添加用户接口")
    public void adduser() throws IOException, InterruptedException {
        Thread.sleep(5000);
        SqlSession session= Databatisutils.getSqlSession();
        Adduser adduser=session.selectOne("adduser",2);
        System.out.println(adduser.toString());
        System.out.println(TestConfig.addUserUrl);


        //发请求
        String result = getResult(adduser);


        //验证用户看是否添加成功
        Thread.sleep(3000);
        User user = session.selectOne("addUsers",adduser);
        System.out.println(user.toString());
        //处理结果，就是判断返回结果是否符合预期
        Assert.assertEquals(adduser.getExpected(),result);


    }

    private String getResult(Adduser adduser) throws IOException {

        HttpPost post = new HttpPost(TestConfig.addUserUrl);
        JSONObject param = new JSONObject();
        param.put("name",adduser.getName());
        param.put("password",adduser.getPassword());
        param.put("sex",adduser.getSex());
        param.put("age",adduser.getAge());
       param.put("permission",adduser.getPermission());
        param.put("isDelete",adduser.getIsDelete());
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
        System.out.println(result);
        return result;
    }
    }




