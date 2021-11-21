package com.nowcoder.community.controller;
import com.nowcoder.community.service.AlphaService;
import org.apache.catalina.filters.ExpiresFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
//在控制器的方法中，我们可以直接使用Request、Response对象处理请求与响应
@Controller
@RequestMapping("/alpha")  //声明类或方法的访问路径，还可以声明请求的方式
public class AlphaController {
    @Autowired
    private AlphaService alphaService;
    /**
    @RequestMapping("/hello")
    @ResponseBody
    public String sayhello(){
        return "hello spring boot!";
    }
    //run CommunityApplication.java后输入网址http://localhost:8080/community/alpha/hello
    */
    @RequestMapping("/data")
    @ResponseBody
    public  String getData(){ //处理查询请求
        return alphaService.find();
    }

    //SpringMVC
    @RequestMapping("/http")
    public void http(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //通过request对象，获取请求数据
        System.out.println(request.getMethod());  //默认GET请求
        System.out.println(request.getServletPath());   //请求路径，/alpha/http/
        //获取请求的消息头
        Enumeration<String>  enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()){
            String name = enumeration.nextElement();
            String value = request.getHeader(name);
            System.out.println(name + ": " + value);
        }
        //获取请求体，包含业务数据（传入的参数）
        System.out.println(request.getParameter("code")); // 传入code参数，如http://localhost:8080/community/alpha/http?code=123

        // response对象， 返回响应数据
        response.setContentType("text/html;charset=utf-8");   //数据类型
        try {
            PrintWriter writer = response.getWriter();  //通过writer向浏览器打印一个网页
            writer.write("<hl>牛客网</hl>");
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    //一、
    //GET请求（向服务器获取数据）的两种传参方式：
    //  方法一：  /students?current=1&limit=20    //分页，当前为第一页，每页20条
    //@RequestParam：可以将请求对象中的参数，绑定到控制器中的方法参数
    @RequestMapping(path = "/students", method = RequestMethod.GET)
    @ResponseBody
    public String getStudents(
            @RequestParam(name = "current", required = false, defaultValue = "1") int current,
            @RequestParam(name = "limit", required = false, defaultValue = "10") int limit
            ){
        System.out.println("current = " + current + ", limit = " + limit);
        return "some students";
        //在浏览器中输入http://localhost:8080/community/alpha/students?current=3&limit=50， 控制台显示current = 3, limit = 50，网页显示"some students"
    }

    // 方法二：   /students/101   //访问某个学生
    @RequestMapping(path = "/students/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getStudents(
            @PathVariable("id" ) int id         //@PathVariable：将请求路径中的参数，绑定到控制器中方法的参数，即从浏览器输入的网址中得到id变量并赋值给相应参数
            ){
        System.out.println("id = " + id);
        return "a student";
    }

    //二、
    //POST请求（浏览器向服务器提交数据）
    //首先创建一个带有表单的静态网页 /main /resources/static/html/student.html

    @RequestMapping(path = "/student", method = RequestMethod.POST)
    @ResponseBody
    public String saveStudent(String name, int age){  //参数与html表单一致，才能传递
        System.out.println("name = " + name + ", age = " + age);
        return "success";
        //访问http://localhost:8080/community/alpha/student，输入数据，点击保存，网页跳转到success，同时控制台显示name = xx, age = 11
    }

    //三、
    //响应HTML数据，返回一个HTML，不加@ResponseBody
    //响应方式一：更直观，将model和view都装到一个对象中
    //例：浏览器查询一个老师
    //ModelAndView对象，既可以存储模型数据，又可以存储模板路径
    /**
     * Spriongboot创建的项目，在resources  -> templates下的资源是不能直接访问的，没有开放访问权限。有两种方式访问templates模板下的资源文件：
     * 方式一：在application.yml或者application.properties配置文件中将访问权限开放（这种方式不推荐）
     * 方式二：通过内部controller跳转访问的资源（推荐）
     */
    @RequestMapping(path = "/teacher", method = RequestMethod.GET)
    public ModelAndView getTeacher(){   //返回model和视图，再传给模板引擎
        ModelAndView mav = new ModelAndView();
        mav.addObject("name", "张三");
        mav.addObject("age", 30);
        mav.setViewName("/demo/view");//设置模板的路径和名字，不用写后缀
        return mav;
    }

    //响应方式二：简化方式一，将Model数据装到一个参数中，并返回View视图，传给dispatcher sevlet
    //例：查询一个学校
    @RequestMapping(path = "/school", method = RequestMethod.GET)
    public String getSchool(Model model){    //model是一个bean对象，dispatcher sevlet会自动实例化一个Model对象传递进来
        model.addAttribute("name", "北京大学");
        model.addAttribute("age", 123);
        return "/demo/view";  //返回模板view的路径
    }

    //四、
    //响应JSON数据（异步请求：当前网页不刷新的情况下，后台访问服务器，并返回一个结果，如网站注册账号，显示昵称被占用）
    //  Java对象 -> JSON字符串 -> JS对象
    //一个员工数据，使用Map类进行封装，相当于自定义一个Emp的类
     //dispatcher servlet调用此方法时，根据注解和返回类型，会自动将map转换成JSON字符串，转回给浏览器
    @RequestMapping(path = "/emp", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getEmp(){
        Map<String, Object> emp = new HashMap<>();
        emp.put("name", "张三");
        emp.put("age", 23);
        emp.put("salary", 8000.00);
        return emp;
    }

    //查询所有员工  ArrayList
    @RequestMapping(path = "/emps", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, Object>> getEmps(){
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> emp = new HashMap<>();
        emp.put("name", "张三");
        emp.put("age", 23);
        emp.put("salary", 8000.00);
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name", "李四");
        emp.put("age", 24);
        emp.put("salary", 9000.00);
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name", "王五");
        emp.put("age", 25);
        emp.put("salary", 10000.00);
        list.add(emp);
        return list;
    }

}
