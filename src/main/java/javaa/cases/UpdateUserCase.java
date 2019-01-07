package javaa.cases;
import javaa.config.TestConfig;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import javaa.model.Updateuser;
import javaa.model.User;
import javaa.utis.Databatisutils;

/**
 * Created by XULE on 2018/12/18.
 */
public class UpdateUserCase {
    @Test(dependsOnGroups = "loginTrue",description = "更改用户接口")
    public void updateUserCase() throws IOException, InterruptedException {
        SqlSession session= Databatisutils.getSqlSession();
        Updateuser updateuser =session.selectOne("updateuser",1);
        System.out.println(updateuser.toString());
        System.out.println(TestConfig.updateUserUrl);
        int result = getResult(updateuser);
        //获取更新后的结果
        Thread.sleep(3000);
        User user = session.selectOne(updateuser.getExpected(),updateuser);
        System.out.println(user.toString());
        Assert.assertNotNull(user);
        Assert.assertNotNull(result);
    }

    private int getResult(Updateuser updateuser) throws IOException {
        HttpPost post = new HttpPost(TestConfig.updateUserUrl);
        JSONObject param = new JSONObject();
        param.put("id",updateuser.getUserid());
        param.put("name",updateuser.getName());
        param.put("sex",updateuser.getSex());
        param.put("age",updateuser.getAge());
        param.put("permission",updateuser.getPermission());
        param.put("isDelete",updateuser.getIsDelete());
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
        return Integer.parseInt(result);

    }

    @Test(dependsOnGroups = "loginTrue",description = "删除用户信息")
    public void deleteUserCase() throws IOException, InterruptedException {
        SqlSession session= Databatisutils.getSqlSession();
        Updateuser updateuser =session.selectOne("updateuser",2);
        System.out.println(updateuser.toString());
        System.out.println(TestConfig.updateUserUrl);
        int result = getResult(updateuser);
        Thread.sleep(3000);
        User user = session.selectOne(updateuser.getExpected(),updateuser);
        System.out.println(user.toString());
        Assert.assertNotNull(user);
        Assert.assertNotNull(result);
    }

}
