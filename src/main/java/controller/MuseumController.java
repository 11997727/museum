package controller;

import common.DataConfig;
import common.DateUtils;
import common.TextUtils;
import entity.Comment;
import entity.District;
import entity.Museum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.CommentService;
import service.DistrictService;
import service.MuseumService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by teacher ZHANG on 2020/2/19
 */
@Controller
@RequestMapping("museum")
public class MuseumController {
    @Autowired
    private MuseumService museumService;

    @Autowired
    private DistrictService districtService;

    @RequestMapping("all_museums")
    public String getAllMuseums(String district, @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
                                Integer page, Model model) {
        Integer pageSize = DataConfig.pageSize;

        //预约日期
        Date visitDate = date == null? DateUtils.getNextDate() : date;

        Integer pid = page == null ? 1 : page;
        Integer start = (pid - 1) * pageSize;

        //将区县转换为整数列表
        List<Integer> list = TextUtils.textToList(district);

        //取某区县第二天博物馆的信息
        List<Museum> museums = museumService.getAllMuseums(list, visitDate, start, pageSize);
        Integer count = museumService.getMuseumsCount(list);

        //所有的区县，用于页面上方所有区县的展示
        List<District> districts = districtService.getAllDistricts();

        //计算总页数
        Integer total = (count % pageSize == 0) ? count / pageSize : count / pageSize + 1;

        //取得所有博物馆ID并转换成字符串
        StringBuilder builder = new StringBuilder();

        for (Museum museum : museums) {
            if (builder.length() > 0) {
                builder.append(",");
            }

            builder.append(museum.getMuseumId());
        }

        //下一周日期，用于下拉列表框的显示
        List<String> days = DateUtils.getNextWeek();

        model.addAttribute("museums", museums);
        model.addAttribute("pageNum", pid);
        model.addAttribute("pageCount", total);
        model.addAttribute("visitList", days);
        model.addAttribute("visitDate", DateUtils.getDateString(visitDate));
        model.addAttribute("museumIds", builder.toString());
        model.addAttribute("districts", districts);
        model.addAttribute("districtIds", list);
        model.addAttribute("districtStr", district);

        return "all_museums";
    }
/*
    @RequestMapping("get_museum")
    public String getMuseumById(Integer museumId, Model model) {
        Integer pageSize = DataConfig.pageSize;

        Museum museum = museumService.getMuseumById(museumId, null);

        List<Comment> comments = commentService.getAllComments(museumId, 0, pageSize);
        Integer count = commentService.getCommentsCount(museumId);

        //计算总页数
        Integer total = (count % pageSize == 0) ? count / pageSize : count / pageSize + 1;

        model.addAttribute("museum", museum);
        model.addAttribute("comments", comments);
        model.addAttribute("pageNum", 1);
        model.addAttribute("pageCount", total);

        return "get_museum";
    }
 */

    @RequestMapping("get_museum")
    public String getMuseumById(Integer museumId, Model model) {
        Museum museum = museumService.getMuseumById(museumId, null);

        model.addAttribute("museum", museum);

        return "get_museum";
    }
}
