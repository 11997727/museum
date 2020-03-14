package controller;

import common.TextUtils;
import entity.Museum;
import entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.MuseumService;
import service.OrderService;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by teacher ZHANG on 2020/2/20
 */
@Controller
@RequestMapping("order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private MuseumService museumService;

    @RequestMapping("goto_login")
    public String gotoLogin(Integer page, String district, String date, Integer museum, Model model) {
        model.addAttribute("page", page);
        model.addAttribute("date", date);
        model.addAttribute("district", district);
        model.addAttribute("museum", museum);

        return "user_login";
    }

    @RequestMapping("user_login")
    public String gotoLogin(Integer page, String district, String date, String phone, Integer museum, HttpSession session) {
        String str = district == null? "" : district;
        String url = "";

        if (museum != null) {
            url = "redirect:/museum/get_museum.action?museumId=" + museum; //转向博物馆详情页面
        }
        else if (page == null) {
            url = "redirect:/order/get_orders.action"; //转向订单查看页面
        }
        else {
            //转向预约页面
            url = "redirect:/museum/all_museums.action?page="+page+"&district="+str+"&date="+date;
        }

        if (phone != null && phone.length() == 11) {
            session.setAttribute("phone", phone);
            return url;
        }
        else {
            return "error";
        }
    }

    @RequestMapping("add_order")
    @ResponseBody
    public Map<String, Object> addOneOrder(Integer museumId,
                                           @DateTimeFormat(pattern = "yyyy-MM-dd") Date visitDate,
                                           HttpSession session) {
        Map<String, Object> map = new HashMap<>();

        String phone = (String) session.getAttribute("phone");
        if (phone == null) {
            map.put("success", 0); //未登录返回零
            return map;
        }

        //取museum内的容量和订单数
        Museum museum = museumService.getMuseumById(museumId, visitDate);

        //是否当天休息
        if (museum.getDayOff() > 0) {
            map.put("success", -1);
            map.put("message", "今日闭馆，请明日再试");

            return map;
        }

        //剩余票数
        Integer remain = museum.getCapacity() - museum.getOrderNum();
        if (remain <= 0) {
            map.put("success", -1);
            map.put("message", "门票预约完毕，请明日再试");

            return map;
        }

        //有无预约记录
        Integer count = orderService.getOrdersCount(museumId, phone, visitDate);
        System.out.println(phone+":"+count);
        if (count > 0) {
            map.put("success", -1);
            map.put("message", "已有预约，不能重复预约");

            return map;
        }

        //加入数据库
        if (orderService.addOneOrder(museumId, phone, visitDate) > 0) {
            map.put("success", 1);
            //返回博物馆id和余票
            map.put("museumId", museumId);
            map.put("remainNum", remain - 1);
        }
        else {
            map.put("success", -1);
            map.put("message", "数据库写入失败");
        }

        return map;
    }

    //当前页所有博物馆的余票
    //museumIds:所有博物馆的id号
    @RequestMapping("all_remains")
    @ResponseBody
    public Map<Integer, Integer> getAllRemains(String museumIds, //本页所有博物馆的Id
                                               @DateTimeFormat(pattern = "yyyy-MM-dd") Date visitDate) {
        Map<Integer, Integer> map = new HashMap<>();

        //将博物馆的id号转为整数并放入List
        List<Integer> list = TextUtils.textToList(museumIds);

        //根据id取得博物馆信息
        if (list == null) { return map; }

        //初始化返回的map，数据库若没有某天的订单则没有数据返回
        for (Integer i : list) { map.put(i, 0); }

        //取某一天多个博物馆的订单数量
        List<Map> result = orderService.selectCountsByGroup(list, visitDate);

        //转换结果格式
        for (Map m : result) {
            Integer id = (Integer) m.get("museumId");
            Long num = (Long) m.get("count");

            map.put(id, num.intValue());
        }

        //取当前页博物馆信息
        List<Museum> museums = museumService.selectMuseumsByList(list, visitDate);

        //计算每一个博物馆的余票
        for (Museum museum : museums) {
            Integer mid = museum.getMuseumId();
            Integer num = map.get(mid); //订单数

            if (museum.getDayOff() > 0) {
                map.put(mid, -1); //若当天闭馆则余票显示为-1
            }
            else {
                map.put(mid, museum.getCapacity()-num); //余票
            }
        }

        //余票数
        for (Integer i : map.keySet()) {
            System.out.println(i+":"+map.get(i));
        }

        return map;
    }

    @RequestMapping("get_orders")
    public String getOrdersByPhone(Model model, HttpSession session) {
        String phone = (String) session.getAttribute("phone");
        if (phone == null) {
            return "redirect:/order/goto_login.action";
        }

        List<Order> list = orderService.getOrdersByPhone(phone);

        model.addAttribute("orders", list);

        return "get_orders";
    }

    @RequestMapping("del_orders")
    public String delAllOrders(String orders) {
        if (orders != null) {
            List<Integer> list = TextUtils.textToList(orders);

            orderService.delAllOrders(list);
        }

        return "redirect:get_orders.action";
    }
}
