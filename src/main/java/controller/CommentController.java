package controller;

import common.DataConfig;
import entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.CommentService;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by teacher ZHANG on 2020/2/28
 */
@Controller
@RequestMapping("comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @RequestMapping("get_comments")
    @ResponseBody
    public Map<String, Object> getAllComments(Integer museumId, Integer page, Model model) {
        Map<String, Object> map = new HashMap<>();
        Integer pageSize = DataConfig.pageSize;

        Integer pid = page == null ? 1 : page;
        Integer start = (pid - 1) * pageSize;

        List<Comment> comments = commentService.getAllComments(museumId, start, pageSize);
        Integer count = commentService.getCommentsCount(museumId);

        //计算总页数
        Integer total = (count % pageSize == 0) ? count / pageSize : count / pageSize + 1;

        map.put("comments", comments);
        map.put("pageNum", pid);
        map.put("pageCount", total);

        return map;
    }

    @RequestMapping("add_comment")
    @ResponseBody
    public Map<String, Object> addOneComment(Comment comment, HttpSession session) {
        Map<String, Object> map = new HashMap<>();

        String phone = (String) session.getAttribute("phone");
        if (phone == null) {
            map.put("success", 0); //未登录返回零
            return map;
        }

        //加入手机号
        comment.setPhoneNum(phone);

        //加入数据库
        if (commentService.addOneComment(comment) > 0) {
            map.put("success", 1);
        }
        else {
            map.put("success", -1);
            map.put("message", "数据库写入失败");
        }

        return map;
    }
}
