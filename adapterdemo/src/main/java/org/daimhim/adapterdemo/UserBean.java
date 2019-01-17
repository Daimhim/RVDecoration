package org.daimhim.adapterdemo;

/**
 * 项目名称：org.daimhim.adapterdemo
 * 项目版本：RVDecoration
 * 创建时间：2019/1/17 16:18  星期四
 * 创建人：Administrator
 * 修改时间：2019/1/17 16:18  星期四
 * 类描述：Administrator 太懒了，什么都没有留下
 * 修改备注：Administrator 太懒了，什么都没有留下
 *
 * @author：Administrator
 */
public class UserBean {
    private String name;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String pId) {
        id = pId;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String pName) {
        name = pName;
    }
}
