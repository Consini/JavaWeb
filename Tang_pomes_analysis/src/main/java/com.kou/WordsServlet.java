package com.kou;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

@WebServlet("/word.json")
public class WordsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
        Map<String,Integer> map = new TreeMap<>();

        JSONArray jsonArray = new JSONArray();
        try (Connection connection = DBConfig.getConnection()) {
            String sql = "SELECT words FROM tangshi";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                try (ResultSet rs = statement.executeQuery()) {
                    while (rs.next()) {
                        String words = rs.getString("words");
                        String[] wordList = words.split(",");
                        for(int i = 0;i < wordList.length;i++){
                            if(map.containsKey(wordList[i])){
                                map.put(wordList[i],map.get(wordList[i])+1);
                            }else{
                                map.put(wordList[i],1);
                            }
                        }
                    }

                    for(Map.Entry<String,Integer> entry : map.entrySet()){
                        JSONArray item = new JSONArray();
                        item.add(entry.getKey());
                        item.add(entry.getValue());
                        jsonArray.add(item);
                    }

                    resp.getWriter().println(jsonArray.toJSONString());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JSONObject object = new JSONObject();
            object.put("error", e.getMessage());
            resp.getWriter().println(object.toJSONString());
        }

    }
}
