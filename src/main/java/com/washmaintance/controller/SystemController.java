package com.washmaintance.controller;

import com.washmaintance.pojo.Result;
import com.washmaintance.websocket.WebSocketServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class SystemController {
    @GetMapping("/socket/push/{cid}")
    public Result pushMessage(@PathVariable("cid") String cid, String message) {
//        Map<String, Object> result = new HashMap<>();
//        try {
//            HashSet<String> sids = new HashSet<>();
//            sids.add(cid);
//            WebSocketServer.sendMessage("服务端推送消息：" + message, sids);
//            result.put("code", cid);
//            result.put("msg", message);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return Result.success();
    }
}
