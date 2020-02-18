import dao.Image;
import dao.ImageDao;

/**
 * @Description TODO
 * @Author K
 * @Date 2020/2/18 18:16
 **/
public class TestImageDao {
    // 用于进行简单测试
    public static void main(String[] args) {
        // 1.测试插入数据
//        Image image = new Image();
//        image.setImageName("3.png");
//        image.setSize(100);
//        image.setUploadTime("003");
//        image.setContentType("image/png");
//        image.setPath("./data/1.png");
//        image.setMd5("11222");
//        ImageDao imageDao = new ImageDao();
//        imageDao.insert(image);

        // 2.测试查看所有图片
//        ImageDao imageDao = new ImageDao();
//        System.out.println(imageDao.selectAll());

        // 3.测试查看指定图片
//        ImageDao imageDao = new ImageDao();
//        System.out.println(imageDao.selectOne(3));

        // 4. 测试删除指定图片
        ImageDao imageDao = new ImageDao();
        imageDao.delete(2);
    }
}
