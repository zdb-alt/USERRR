package javaa.model;

import lombok.Data;

/**
 * Created by Administrator on 2018/12/18.
 */
@Data
public class User {
    private  int  id;
    private  String name;
    private  String password;
    private  String age;
    private  String  sex;
    private  String permission;
    private  String isDelete;

    @Override
    public  String toString(){
        return ("{id:"+id+","+
                "name:"+name+","+
                "password:"+password+","+
                "age:"+age+","+
                "sex:"+sex+","+
                "permission:"+permission+","+
                "isDelete:"+isDelete+"}");
    }
}
