package com.bear.controller;

import com.bear.model.BaseReturn;
import com.bear.model.Sign;
import com.bear.model.Users;
import com.bear.service.SignService;
import com.bear.util.DateFormatUtil;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/sign")
public class SignController {
    @Autowired
    private SignService signService;

    /**
     * 跳转个人签到大厅页面
     * @param model
     * @return
     */
    @RequestMapping(value="/jumpSignIn")
    public String jumpSignIn(Model model){
        // 获取当前用户的当天的签到信息
        Sign sign = getSign();
        model.addAttribute("sign",sign);
        // 检查当前用户的当天的签到信息中的签到签退状态
        Map map = checkSignInOut(sign);
        model.addAttribute("map",map);
        return "sign/signIn";
    }

    /**
     * 跳转班级签到信息页面
     * @param model
     * @return
     */
    @RequestMapping(value="/jumpSignList")
    public String jumpSignList(Model model){
        return "sign/signList";
    }

    /**
     * 获取班级签到信息列表
     * @return
     */
    @RequestMapping(value="/getSignList")
    @ResponseBody
    public Object getSignList(Sign sign,
                              @RequestParam(required = false, defaultValue = "1") int page,
                              @RequestParam(required = false, defaultValue = "10") int limit ) {
        // 注意：数据库格式为2019-03-13，这里不能传new Date()，需要将时间也写成yyyy-MM-dd格式
        // 获取当天的yyyy-MM-dd的时间格式
        sign.setSdate(getDate());

        // 没有pageHelper的处理方式：计算偏移量
        // page = limit * (page - 1);
        // 存在pageHelper的处理方式：不计算偏移量
        PageInfo<Sign> pageInfo = signService.selectAllSign(sign,page,limit);
        // 将Layui-table返回json数据封装为对象
        BaseReturn baseReturn = new BaseReturn();
        baseReturn.setCode("0");
        baseReturn.setMsg("");
        baseReturn.setCount(pageInfo.getTotal());
        baseReturn.setData(pageInfo.getList());
        return baseReturn;
    }


    /**
     * 保存签到签退信息
     * @param type
     * @return
     */
    @RequestMapping(value="/saveSignInfo")
    @ResponseBody
    public Object saveSignInfo(Integer type){
        // 从shiro的session中取出我们保存的对象，该对象在登录认证成功后保存的
        Users users = (Users) SecurityUtils.getSubject().getPrincipal();
        // 获取当前用户的当天的签到信息
        Sign sign = getSign();

        // 响应返回对象Map
        Map<String,Object> jsonMap = new HashMap<String, Object>();
        // 标志量：查看是否操作成功
        Integer res = null;
        // 签到
        // 签到之前先看签到没，签到了不能重复签到了
        if(type == 1){
            if(sign == null || sign.getInstatus() == null){
                Sign temp = new Sign();
                temp.setUid(users.getId());
                temp.setSignintime(new Date());
                temp.setInstatus(1);
                temp.setSdate(new Date());
                temp.setAvailable(1);
                res = signService.save(temp);
            }
            if(res == null){
                jsonMap.put("success",false);
                jsonMap.put("info","请不要重复签到");
            }else if(res > 0){
                jsonMap.put("success",true);
                jsonMap.put("info","操作成功");
            }else if(res == 0){
                jsonMap.put("success",false);
                jsonMap.put("info","操作失败");
            }
            // 签退
            // 签退之前先看有没有签到，如果没有签到就不能签退，必须先签到才能签退
            // 在检查有没有签到之前，先检查有没有签退，假如已经签退了，后面的判断就没有意义了
        }else{
            if(sign == null || sign.getOutstatus() == null){
                if(sign != null && sign.getInstatus() != null){
                    Sign temp = new Sign();
                    temp.setId(sign.getId());
                    temp.setUid(users.getId());
                    temp.setSignouttime(new Date());
                    temp.setOutstatus(1);
                    res = signService.updateNotNull(temp);
                }
                if(res == null){
                    jsonMap.put("success",false);
                    jsonMap.put("info","签退之前请先签到");
                }else if(res > 0){
                    jsonMap.put("success",true);
                    jsonMap.put("info","操作成功");
                }else if(res == 0){
                    jsonMap.put("success",false);
                    jsonMap.put("info","操作失败");
                }
            }
            if(res == null){
                // 必须二次判断,例子：假如未签到就签退，jsonMap也put了一次，所以这里不能覆盖put了
                if( !jsonMap.containsKey("success") ){
                    jsonMap.put("success",false);
                    jsonMap.put("info","你已签退，请不要重复签退");
                }
            }
        }
        return jsonMap;
    }






    /**
     * 私有重构方法：获取当天的yyyy-MM-dd的时间格式
     * @return
     */
    private Date getDate() {
        // 将短时间格式时间转换为字符串 yyyy-MM-dd
        String strTempDate = DateFormatUtil.dateToStr(new Date());
        // 将短时间格式字符串转换为时间 yyyy-MM-dd
        Date dateTempDate = DateFormatUtil.strToDate(strTempDate);
        return dateTempDate;
    }

    /**
     * 私有重构方法：获取当前用户的当天的签到信息
     * @return
     */
    private Sign getSign() {
        // 从shiro的session中取出我们保存的对象，该对象在登录认证成功后保存的
        Users users = (Users) SecurityUtils.getSubject().getPrincipal();
        // 根据user获取当前用户的当天签到对象
        Sign sign = new Sign();
        sign.setUid(users.getId());

        // 注意：数据库格式为2019-03-13，这里不能传new Date()，需要将时间也写成yyyy-MM-dd格式
        // 获取当天的yyyy-MM-dd的时间格式
        sign.setSdate(getDate());

        // 查询单个对象
        sign = signService.selectOne(sign);
        return sign;
    }



    /**
     * 私有重构方法：检查当前用户的当天的签到信息中的签到签退状态
     * @param sign
     */
    private Map checkSignInOut(Sign sign) {
        // 签到信息 (签到打卡 | 迟到打卡 | 您还未签到,请签到)
        String inMessage = "";
        // 签退信息 (签退打卡 | 早退打卡 | 您还未签退，请签退)
        String outMessage = "";
        // 如果当前用户当天已存在签到信息
        if(sign != null){
            if(sign.getSignintime()!=null){
                // 获取签到时间的时
                Integer in = Integer.parseInt( DateFormatUtil.getHour2( sign.getSignintime() ) );
                if(in<9){
                    inMessage = "签到打卡";
                }else{
                    inMessage = "迟到打卡";
                }
            }
            if(sign.getSignouttime()!=null){
                // 获取签退事件的时
                Integer out = Integer.parseInt( DateFormatUtil.getHour2( sign.getSignouttime() ) );
                if(out>=18){
                    outMessage = "签退打卡";
                }else{
                    outMessage = "早退打卡";
                }
            }
            // 如果当前用户当天已存在签到信息
        }else{
            inMessage = "您还未签到,请签到";
            outMessage = "您还未签退，请签退";
        }

        Map<String,String> map = new HashMap<String, String>();
        map.put("inMessage",inMessage);
        map.put("outMessage",outMessage);
        return map;
    }
}
