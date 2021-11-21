package com.nowcoder.community.controller;

import com.nowcoder.community.dao.DiscussPostMapper;
import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.Page;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.DiscussPostService;
import com.nowcoder.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {
    @Autowired
    private DiscussPostService discussPostService;
    @Autowired
    private UserService userService;

    //处理请求的方法
    //响应HTML数据，返回一个HTML
    // 将Model数据装到一个参数中，并返回View视图，传给dispatcher sevlet
    @RequestMapping(path = "/index", method = RequestMethod.GET)
    public String getIndexPage(Model model, Page page){ //model是一个bean对象
        /**Spring MVC框架中，方法调用前，dispatcher sevlet会自动实例化Model和Page，并将Page注入Model.
         * 所以，在thymeleaf中可以直接访问Page对象中的数据。
         */
        page.setRows(discussPostService.findDiscussPostRows(0));
        page.setPath("index");

        List<DiscussPost> list = discussPostService.findDiscussPosts(0,page.getOffset(),page.getLimit());

        //封装discusspost和user相应数据
        List<Map<String, Object>> discussPosts = new ArrayList<>();

        if (list != null){
            for (DiscussPost post : list){  //从list中迭代取出post
                Map<String, Object> map = new HashMap<>();
                map.put("post", post);
                User user = userService.findUserById(post.getUserId());
                map.put("user", user);
                discussPosts.add(map);
            }
        }
        model.addAttribute("discussPosts", discussPosts);
        return "index";   //返回模板view的路径，即templates/index.html

    }
}
