package javaa.model;

import lombok.Data;

/**
 * Created by Administrator on 2018/12/18.
 */
@Data
public class Updateuser {
    private int id;
    private int userid;
    private String name;
    private String sex;
    private String age;
    private String permission;
    private String isDelete;
    private String expected;
}
