import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
class Hero{
    public String name;
    public String skill1;
    public String skill2;
    public String skill3;
}

public class TestGson {
    public static void main(String[] args) {
//        HashMap<String,Object> hashMap = new HashMap<String, Object>();
//        hashMap.put("name","曹操");
//        hashMap.put("skill1","剑气");
//        hashMap.put("skill2","吸血");
//        hashMap.put("skill3","加攻速");

        Hero hero = new Hero();
        hero.name = "曹操";
        hero.skill1 = "剑气";
        hero.skill2 = "吸血";
        hero.skill3 = "加攻速" ;

        // 通过 map 转成 JSON 结构的字符串
        // 1.创建一个 Gson 对象
        Gson gson = new GsonBuilder().create();
        // 2.使用 toJSON() 方法把键值对结构转成 JSON 字符串
        //String str = gson.toJson(hashMap);
        String str = gson.toJson(hero);
        System.out.println(str);

    }
}
