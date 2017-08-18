package diploma.gyumri.theatre.model;

import android.app.Activity;
import android.os.Environment;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class JsonParser {

    private static String loadJSONFromAsset(Activity activity) {
        String json = null;
        File f = new File("src/MockData.json");
/////////////////////////////////////////////////////////////////////////////////

        String fileName = "MockData.xml";
        String myDirectory = "myDirectory";
        String externalStorage = Environment.getExternalStorageDirectory().getAbsolutePath();

        File outputFile = new File(externalStorage + File.separator + myDirectory + File.separator + fileName);

        Log.i("hah", outputFile.exists() + "");
///////////////////////////////////////////////////////////////////////////////////////
        Log.i("tag", "loadJSONFromAsset: " + f.exists());
        try {
            InputStream is = activity.getAssets().open("src/main/MockData.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static List<Event> getEventList(Activity activity) {
        try {
//            JSONObject obj = new JSONObject(loadJSONFromAsset(activity));
            JSONObject obj = new JSONObject("{\n" +
                    "  \"events\": [\n" +
                    "    {\n" +
                    "      \"name\": \" Lir Arqa\",\n" +
                    "      \"desc\": \" .......... \",\n" +
                    "      \"price\": \" 1000-2500\",\n" +
                    "      \"stage\": \" Big hall \",\n" +
                    "      \"date\": \" Sa Jul 29 18:00:00\",\n" +
                    "      \"imgUrl\": \"https://i.ytimg.com/vi/By6edE8t-Ao/maxresdefault.jpg \",\n" +
                    "      \"videoUrl\": \"BLPC0b5uZOI\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"name\": \" Ser Jazz Satana\",\n" +
                    "      \"desc\": \" .......... \",\n" +
                    "      \"price\": \" 1000-2500\",\n" +
                    "      \"stage\": \" Big hall \",\n" +
                    "      \"date\": \" Tue Jul 25 17:00:00\",\n" +
                    "      \"imgUrl\": \" https://scontent-frx5-1.xx.fbcdn.net/v/t1.0-9/18486449_1615883995097770_5915545111604871807_n.jpg?oh=154a5dfdc0bb4c050f0cd5c9eff4696e&oe=5A0AA331\",\n" +
                    "      \"videoUrl\": \" asdasdasd asd \"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"name\": \" ՍԻՐԱՅԻՆ ԽԱՂԵՐ Գյումրու դրամատիկականի փոքր բեմում\",\n" +
                    "      \"desc\": \" .......... \",\n" +
                    "      \"price\": \" 1000-2500\",\n" +
                    "      \"stage\": \" Small hall \",\n" +
                    "      \"date\": \" Wed Jul 26 19:00:00\",\n" +
                    "      \"imgUrl\": \" http://gyumritheatre.am/demo/frontend/web/uploads/anons/love-games-1492118439.jpg \",\n" +
                    "      \"videoUrl\": \" BNIcteBdAcQ\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"name\": \" Xelahex Patmutyun\",\n" +
                    "      \"desc\": \" \n" +
                    "Ներկայացման հիմքում հայոց ցեղասպանության պատճառով որբացած ու ԱՄՆ-ում հայտնված ամուսիններ Արամի և Սեդայի կյանքն է, որոնց թեև ամեն վայրկյան մտատանջում է անցյալը, թուրքերի կողմից դաժանաբար սպանված ծնողների, հարազատների մասին հիշողությունները, բայց փորձում են երջանիք լինել, զավակներ ունենալ ու սփոփվել, որ կորցրածի տեղը կլրացվի: Սակայն ցեղասպանությունից մազապուրծ փրկված Սեդան սովի, հալածանքների, հիվանդության և այլնի պատճառով զավակ ունենալ չի կարող: Ուստի երջանիկ կյանքի սպասումը մնում է անիրական երազանք: Արամի տանը կախված հարազատների «անգլուխ» լուսանկարներն հանգիստ չեն տալիս հերոսին, որոնց անտես հայացքները հիշեցնում են գենի շարունակականության մասին: Ուրեմն հերոսներին մնում է ելք գտնել, իսկ ելքը…\n" +
                    "Այս ներկայացումը ավանդույթի ուժ է ստանում ամեն տարի. ապրիլի 24-ին, թատրոնը ներկայացնելով այն, ևս մեկ անգամ, արվեստի միջոցով իր հարգանքն ու խոնարհումն է արտահայտում Սուրբ և անմեղ զոհերի հիշատակին:\",\n" +
                    "      \"price\": \" 1000-2500\",\n" +
                    "      \"stage\": \" Big hall \",\n" +
                    "      \"date\": \" Mon Jul 24 18:30:00\",\n" +
                    "      \"imgUrl\": \" https://scontent-frx5-1.xx.fbcdn.net/v/t1.0-9/17992360_1592638510755652_6474898530030643607_n.jpg?oh=125de4d1eca23b52f0e6342c25553181&oe=59F4966B\",\n" +
                    "      \"videoUrl\": \" BNIcteBdAcQ\"\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}");
            JSONArray m_jArry = obj.getJSONArray("events");
            Log.i("hhh", "getEventList:  " + m_jArry.length());
            List<Event> events = new ArrayList<>(m_jArry.length());


            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);

                Event event = new Event();
                event.setName(jo_inside.getString("name"));
                event.setDesc(jo_inside.getString("desc"));
                event.setPrice(jo_inside.getString("price"));
                event.setStage(jo_inside.getString("stage"));
                event.setDate(jo_inside.getString("date"));
                event.setImgUrl(jo_inside.getString("imgUrl").trim());
                event.setVideoUrl(jo_inside.getString("videoUrl").trim());
                events.add(event);

            }
            return events;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }


}
