import com.google.protobuf.InvalidProtocolBufferException;
import com.kcode.protobuf.domain.Entity;
import com.kcode.protobuf.domain.GpsData;
import org.junit.Test;

/**
 * @Date: 2018/8/22 上午11:19
 */
public class TestGpsProtobuf {

    @Test
    public void test() {
        System.out.println("===== 构建一个GPS模型开始 =====");

        GpsData.gps_data.Builder gps_builder = GpsData.gps_data.newBuilder();
        gps_builder.setAltitude(1);
        gps_builder.setDataTime("2017-12-17 16:21:44");
        gps_builder.setGpsStatus(1);
        gps_builder.setLat(39.123);
        gps_builder.setLon(120.112);
        gps_builder.setDirection(30.2F);
        gps_builder.setId(100L);

        GpsData.gps_data gps_data = gps_builder.build();
        System.out.println(gps_data.toString());

        System.out.println("===== 构建GPS模型结束 =====");

        System.out.println("===== gps Byte 开始=====");
        for (byte b : gps_data.toByteArray()) {
            System.out.print(b);
        }
        System.out.println("\n" + "bytes长度" + gps_data.toByteString().size());
        System.out.println("===== gps Byte 结束 =====");

        System.out.println("===== 使用gps 反序列化生成对象开始 =====");
        GpsData.gps_data gd = null;
        try {
            gd = GpsData.gps_data.parseFrom(gps_data.toByteArray());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
        System.out.print(gd.toString());
        System.out.println("===== 使用gps 反序列化生成对象结束 =====");
    }

    @Test
    public void testForPerson() {
        System.out.println("===== 构建一个person模型开始 =====");
        Entity.Person.Builder personBuilder = Entity.Person.newBuilder();
        personBuilder.addPhone(Entity.Person.Phone.newBuilder().setNumber("111").setType(Entity.Person.PhoneType.HOME).build());
        personBuilder.setId(1);
        personBuilder.setName("酒不空");

        Entity.Person person = personBuilder.build();
        System.out.println(person.toString());

        System.out.println("===== 构建person模型结束 =====");

        System.out.println("===== person Byte 开始=====");
        for (byte b : person.toByteArray()) {
            System.out.print(b);
        }
        System.out.println("\n" + "bytes长度" + person.toByteString().size());
        System.out.println("===== person Byte 结束 =====");

        System.out.println("===== 使用person 反序列化生成对象开始 =====");
        Entity.Person p = null;
        try {
            p = Entity.Person.parseFrom(person.toByteArray());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
        System.out.print(p.toString());
        System.out.println("===== 使用person 反序列化生成对象结束 =====");

    }



    @Test
    public void test1() {
        Entity.UnionInfoValue value = Entity.UnionInfoValue.newBuilder().setVersion(1)
                .setUid(123456)
                .setPassword1("abcdefg")
                .setSeq(0).build();

        System.out.println(value.toByteString().toString());
        for (byte b : value.toByteArray()) {
            System.out.print(b);
        }
    }
}
