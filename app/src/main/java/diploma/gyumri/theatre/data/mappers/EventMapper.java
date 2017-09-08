package diploma.gyumri.theatre.data.mappers;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import diploma.gyumri.theatre.data.dto.EventDTO;
import diploma.gyumri.theatre.model.Event;

/**
 * Created by root on 7/30/17.
 */

public class EventMapper {
    public static Event toEvent(EventDTO eventDTO) {
        Event event = new Event();
        event.setId(eventDTO.getId());
        event.setName(eventDTO.getName());
        event.setDesc(eventDTO.getDesc());
        event.setPrice(eventDTO.getPrice());
        event.setStage(eventDTO.getStage());

        event.setDate(getEventDate(eventDTO.getDate()));
        event.setImgUrl(eventDTO.getImgUrl());
        event.setVideoUrl(eventDTO.getVideoUrl());
        return event;
    }

    private static String getEventDate(long time) {
        StringBuilder stringBuilder = new StringBuilder();
        Date date = new Date(time);
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        switch (calendar.get(Calendar.MONTH)) {
            case Calendar.JANUARY:
                stringBuilder.append("Հունվար - ի ");
                break;
            case Calendar.FEBRUARY:
                stringBuilder.append("Փետրվար - ի ");
                break;
            case Calendar.MARCH:
                stringBuilder.append("Մարտ - ի ");
                break;
            case Calendar.APRIL:
                stringBuilder.append("Ապրիլ - ի ");
                break;
            case Calendar.MAY:
                stringBuilder.append("Մայիս - ի ");
                break;
            case Calendar.JUNE:
                stringBuilder.append("Հունիս - ի ");
                break;
            case Calendar.JULY:
                stringBuilder.append("Հուլիս - ի ");
                break;
            case Calendar.AUGUST:
                stringBuilder.append("Օգոստոս - ի ");
                break;
            case Calendar.SEPTEMBER:
                stringBuilder.append("Սեպտեմբեր - ի ");
                break;
            case Calendar.OCTOBER:
                stringBuilder.append("Հոկտեմբեր - ի ");
                break;
            case Calendar.NOVEMBER:
                stringBuilder.append("Նոյեմբեր - ի ");
                break;
            case Calendar.DECEMBER:
                stringBuilder.append("Դեկտեմբեր - ի ");
                break;
        }
        stringBuilder.append(calendar.get(Calendar.DAY_OF_MONTH));
        stringBuilder.append(" - ին Ժամը։ ");
        stringBuilder.append(calendar.get(Calendar.HOUR_OF_DAY));
        stringBuilder.append(":");
        stringBuilder.append(calendar.get(Calendar.MINUTE));
        stringBuilder.append(" - ին");
        return stringBuilder.toString();
    }

}
