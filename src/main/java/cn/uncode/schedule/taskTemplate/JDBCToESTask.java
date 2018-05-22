package cn.uncode.schedule.taskTemplate;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import org.junit.Test;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liulh
 * @decription JDBC获取数据如ES任务模板
 * @create 2018-05-22 9:53
 **/
@Component("JDBCToESTask")
public class JDBCToESTask {

    public static final String BEAN_NAME = "JDBCToESTask";
    public static final String METHOD_NAME = "JDBCSinkES";

    public void JDBCSinkES (String data) throws Exception {
        JSONObject config = JSON.parseObject(data);
        if ((config.get("map")) instanceof Map) {
            Map<String, String> map = (Map) config.get("map");
            for (String s : map.keySet()) {
                System.out.println(map.get(s));
            }
        }
        System.out.println(config);
    }

    @Test
    public void test() {
        Map<String, String> map = new HashMap<>();
        map.put("UserId", "LIULH");
        Map<String, Object> result = new HashMap<>();
        result.put("map", map);
        try {
            JDBCSinkES(new Gson().toJson(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
