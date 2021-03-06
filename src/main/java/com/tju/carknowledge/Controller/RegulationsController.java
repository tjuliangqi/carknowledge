package com.tju.carknowledge.Controller;

import com.tju.carknowledge.domain.*;
import com.tju.carknowledge.service.RegService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @ClassName RegulationsController
 * @Description TODO
 * @Author Yuan Yunxin
 * @Data 2020/7/1 15:23
 * @Version 1.0
 **/

@RestController
@RequestMapping(path = "/paper")
public class RegulationsController {
    /**
     * @Description 搜索框
     * 1.0:搜索关键词返回查询的标准行业数据
     **/
    @RequestMapping(path = "/search/standard",  method = RequestMethod.POST)
    // application/x-www-form-urlencoded
    public RetResult<List<EsStandardBean>> standardInfoSearchList(UserBean userBean) throws Exception {
        // 获取文章列表
        System.out.println("搜索框 standardInfoSearchList is ok");
        // 获取请求体
        String value = userBean.getValue();
        int page = userBean.getPage();
        //查询es获取数据
        RegService regService = new RegService();
        List<EsStandardBean> regulationsInfo;
        String title = new String();

        List<EsStandardBean> esStandardInfoList = regService.StandardInfoSearch(value, page);
        if (esStandardInfoList.isEmpty()){
            regulationsInfo = regService.StandardInfoSearch(value, page);
        }else{
            for (EsStandardBean esStandardInfo1 : esStandardInfoList){
                title = esStandardInfo1.getTitle();
                break;
            }
//        str.indexOf("ABC") != -1
            if (title.contains(value)){
                regulationsInfo = regService.StandardInfoSearch(value, page);
            }else{
                regulationsInfo = regService.StandardInfoSearch(title, page);
            }
        }
        return RetResponse.makeOKRsp(regulationsInfo);
    }

    /**
     * @Description 导航框（示例展示）
     * 1.0固定搜索词，返回固定标准图谱
     **/
    @RequestMapping(path = "/regulations",  method = RequestMethod.POST)
    // application/x-www-form-urlencoded
    public RetResult<Map<String, List>> GuidRegulationsGraph() throws Exception {
        // 获取文章列表
        System.out.println("导航框 GuidRegulationsGraph is ok");
        // 获取请求体
        String value = "机动车";
        //查询es获取数据
        RegService regService = new RegService();
//        Map<String, List> regulationsInfo = regService.guidGraph(value);
        Map<String, List> regulationsInfo = regService.graphSearch(value, 2);
        return RetResponse.makeOKRsp(regulationsInfo);
    }


    /**
     * @Description 搜索框（可删除）
     * 1.0:搜索关键词返回标准图谱
     **/
    @RequestMapping(path = "/search/regulationgraph",  method = RequestMethod.POST)
    // application/x-www-form-urlencoded
    public RetResult<Map<String, List>> RegulationsGraphSearch(UserBean userBean) throws Exception {
        System.out.println("搜索框 RegulationsSearchGraph is ok");
        // 获取请求体
        String value = userBean.getValue();

        //查询es获取数据
        RegService regService = new RegService();
        Map<String, List> regulationsInfo = regService.graphSearch(value, 2);
        return RetResponse.makeOKRsp(regulationsInfo);
    }
}
