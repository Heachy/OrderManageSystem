package works.indentManage;

import works.utils.JdbcUtils;

import java.sql.*;
import java.util.Date;
import java.util.Scanner;

public class test {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        int select;
        while(true)
        {
            ShowMenu();//显示菜单界面
            select=sc.nextInt();
            if(select==1)
            {
                AddIndent();//添加订单
            }
            else if(select==2)
            {
                AddProduct();//添加商品
            }
            else if(select==3)
            {
                Query();//查询
            }
            else if(select==4)
            {
                Update();//修改
            }
            else if(select==5)
            {
                Delete();//删除
            }
            else if(select==0)//退出
            {
                System.out.println("欢迎下次使用");
                break;
            }
        }
    }
    //操作菜单
    public static void ShowMenu()
    {
        System.out.println( );
        System.out.println("     +---------------------------+");
        System.out.println("     |      欢迎使用订单管理系统     |");
        System.out.println("     |                           |");
        System.out.println("     |         1、添加订单         |");
        System.out.println("     |         2、添加商品         |");
        System.out.println("     |         3、查询订单         |");
        System.out.println("     |         4、修改订单         |");
        System.out.println("     |         5、删除订单         |");
        System.out.println("     |         0、退出系统         |");
        System.out.println("     |                           |");
        System.out.println("     +---------------------------+");
        System.out.println( );
        System.out.println("请输入要操作内容：");
    }
    //添加订单
    public  static  void AddIndent()
    {//输入订单信息
        Scanner sc=new Scanner(System.in);
        System.out.println("请输入订单编号：");
        String indent_id=sc.next();
        System.out.println("请输入商品编号：");
        String product_id=sc.next();
        System.out.println("请输入购买数量：");
        int amount=sc.nextInt();
        //SQL部分
        Connection conn=null;
        PreparedStatement st=null;
        try {
            conn= JdbcUtils.getConnection();
            String sql="INSERT INTO `indent`(`order_number`,`product_number`,`amount`,`order_time`) VALUE(?,?,?,?)";
            st=conn.prepareStatement(sql);
            st.setString(1,indent_id);
            st.setString(2,product_id);
            st.setInt(3,amount);
            st.setDate(4,new java.sql.Date(new Date().getTime()));
            int i= st.executeUpdate();
            if(i>0)
            {
                System.out.println("添加成功");
            }
            else
            {
                System.out.println("添加失败，编号重复");
            }
            System.out.println("请输入1并按回车继续");
            int x=sc.nextInt();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JdbcUtils.release(conn,st, null);
    }

    //添加商品
    public  static  void AddProduct()
    {//输入商品信息
        Scanner sc=new Scanner(System.in);
        System.out.println("请输入商品编号：");
        String id=sc.next();
        System.out.println("请输入商品名称：");
        String name=sc.next();
        System.out.println("请输入商品价格：");
        int price=sc.nextInt();
        //SQL部分
        Connection conn=null;
        PreparedStatement st=null;
        try {
            conn= JdbcUtils.getConnection();
            String sql="INSERT INTO `product`(`product_number`,`name`,`price`) VALUE(?,?,?)";
            st=conn.prepareStatement(sql);
            st.setString(1,id);
            st.setString(2,name);
            st.setInt(3,price);
            int i= st.executeUpdate();
            if(i>0)
            {
                System.out.println("添加成功");
            }
            else
            {
                System.out.println("添加失败，编号重复");
            }
            System.out.println("请输入1并按回车继续");
            int x=sc.nextInt();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JdbcUtils.release(conn,st, null);
    }
    //信息更新
    public static void Update()
    {
        Scanner sc=new Scanner(System.in);
        System.out.println("请输入要修改的订单编号：");
        String id=sc.next();
        //SQL部分
        Connection conn=null;
        PreparedStatement st=null;
        ResultSet rs=null;
        try {
            conn=JdbcUtils.getConnection();
            String sql="SELECT `order_number`,`product_number`,`amount`,`order_time` FROM `indent` WHERE `order_number`=?";
            st=conn.prepareStatement(sql);
            st.setString(1,id);
            rs=st.executeQuery();
            if(rs.next())
            {
                System.out.println("该订单为：");
                System.out.println("订单编号："+rs.getString("order_number"));
                System.out.println("商品编号："+rs.getString("product_number"));
                System.out.println("购买数量："+rs.getInt("amount"));
                System.out.println("订单日期："+rs.getDate("order_time"));
                System.out.println("请选择修改内容：");
                System.out.println("1、订单编号  2、商品编号  3、购买数量");
                int select=sc.nextInt();
                int i=0;
                if(select==1)
                {
                    System.out.println("请输入新的订单编号：");
                    String newId=sc.next();
                    sql="UPDATE `indent` SET `order_number`=? WHERE `order_number`=?";
                    st=conn.prepareStatement(sql);
                    st.setString(1,newId);
                    st.setString(2,id);
                    i= st.executeUpdate();
                }
                else if(select==2)
                {
                    System.out.println("请输入新的商品编号：");
                    String newId=sc.next();
                    sql="UPDATE `indent` SET `product_number`=? WHERE `order_number`=?";
                    st=conn.prepareStatement(sql);
                    st.setString(1,newId);
                    st.setString(2,id);
                    i= st.executeUpdate();
                }
                else if(select==3)
                {
                    System.out.println("请输入新的购买数量：");
                    int newAmount=sc.nextInt();
                    sql="UPDATE `indent` SET `amount`=? WHERE `order_number`=?";
                    st=conn.prepareStatement(sql);
                    st.setInt(1,newAmount);
                    st.setString(2,id);
                    i= st.executeUpdate();
                }
                if(i>0)
                {
                    System.out.println("修改成功");
                }
            }
            else{
                System.out.println("未查找到该编号的订单");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JdbcUtils.release(conn,st,rs);
        System.out.println("请输入1并按回车继续");
        int x=sc.nextInt();
    }
    //查询订单
    public static void Query()
    {//确定查询方式
        Scanner sc=new Scanner(System.in);
        System.out.println("请选择查询方式：");
        System.out.println("1、全部查询  2、按照订单编号查询");
        int select=sc.nextInt();
        //SQL部分
        Connection conn=null;
        PreparedStatement st=null;
        ResultSet rs=null;
        if(select==1) {
            try {
                conn = JdbcUtils.getConnection();
                String sql = "SELECT `order_number`,i.`product_number`,`name` ,`price`,`amount`,`order_time`FROM `indent` AS i INNER JOIN `product`AS p WHERE i.`product_number`=p.`product_number`";
                st = conn.prepareStatement(sql);
                rs = st.executeQuery();
                while (rs.next()) {
                    System.out.println("订单编号：" + rs.getString("order_number"));
                    System.out.println("商品编号：" + rs.getString("product_number"));
                    System.out.println("商品名称：" + rs.getString("name"));
                    System.out.println("商品价格：" + rs.getInt("price"));
                    System.out.println("购买数量：" + rs.getInt("amount"));
                    System.out.println("订单日期：" + rs.getDate("order_time"));
                    System.out.println("----------------------------");
                }
                System.out.println("请输入1并按回车继续");
                int x = sc.nextInt();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else if(select==2){
            System.out.println("请输入订单编号：");
            String id=sc.next();
            try {
                conn=JdbcUtils.getConnection();
                String sql="SELECT `order_number`,i.`product_number`,`name` ,`price`,`amount`,`order_time`FROM `indent` AS i INNER JOIN `product`AS p WHERE i.`product_number`=p.`product_number`AND `order_number`=?";
                st=conn.prepareStatement(sql);
                st.setString(1,id);
                rs=st.executeQuery();
                if(rs.next())
                {
                    System.out.println("找到该订单：");
                    System.out.println("订单编号：" + rs.getString("order_number"));
                    System.out.println("商品编号：" + rs.getString("product_number"));
                    System.out.println("商品名称：" + rs.getString("name"));
                    System.out.println("商品价格：" + rs.getInt("price"));
                    System.out.println("购买数量：" + rs.getInt("amount"));
                    System.out.println("订单日期：" + rs.getDate("order_time"));

                }
                else
                {
                    System.out.println("未找到订单");
                }
                System.out.println("请输入1并按回车继续");
                int x = sc.nextInt();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        JdbcUtils.release(conn,st,rs);
    }
    //删除订单
    public static void Delete()
    {//选择删除的订单
        Scanner sc=new Scanner(System.in);
        System.out.println("请输入要删除的订单编号：");
        String id=sc.next();
        //SQL部分
        Connection conn=null;
        PreparedStatement st=null;
        try {
            conn=JdbcUtils.getConnection();
            String sql="DELETE FROM `indent` WHERE `order_number`=?";
            st=conn.prepareStatement(sql);
            st.setString(1,id);
            int i=st.executeUpdate();
            if(i>0)
            {
                System.out.println("删除成功");
            }
            else
            {
                System.out.println("未查找到该订单，删除失败");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JdbcUtils.release(conn,st, null);
        System.out.println("请输入1并按回车继续");
        int x=sc.nextInt();
    }
}
