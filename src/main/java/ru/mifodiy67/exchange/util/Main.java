package ru.mifodiy67.exchange.util;

import ru.mifodiy67.exchange.dao.OrderDao;
import ru.mifodiy67.exchange.model.StatisticInfo;
import ru.mifodiy67.exchange.model.TotalInfo;
import ru.mifodiy67.exchange.service.ParsingFile;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import static ru.mifodiy67.exchange.util.ExchangeConst.FIRST_BORDER_VALUE;
import static ru.mifodiy67.exchange.util.ExchangeConst.FOURTH_BORDER_VALUE;
import static ru.mifodiy67.exchange.util.ExchangeConst.SECOND_BORDER_VALUE;
import static ru.mifodiy67.exchange.util.ExchangeConst.THIRD_BORDER_VALUE;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        String fileName = "1.txt";
        String eventName = "ORDER";
        /*String fileName = "";
        if (args.length > 0) {
            fileName = args[0];
        }*/

        ParsingFile service = new ParsingFile();
        List<StatisticInfo> list = service.parsing(fileName);
        Map<Integer, Long> eventStatistics = service.getEventStatistics(list, eventName);

        Iterator<Map.Entry<Integer, Long>> iterator = eventStatistics.entrySet().iterator();
//        Map.Entry<Integer, Long> next = iterator.next();
        int minBorder = 0;
        int maxBorder;
        int numberOfRecords = list.size();
        long count = 0;
        int firstBorder = 0;
        int secondBorder = 0;
        int thirdBorder = 0;
        int fourthBorder = 0;
        float firstBorderCount = numberOfRecords * FIRST_BORDER_VALUE;
        float secondBorderCount = numberOfRecords * SECOND_BORDER_VALUE;
        float thirdBorderCount = numberOfRecords * THIRD_BORDER_VALUE;
        float fourthBorderCount = numberOfRecords * FOURTH_BORDER_VALUE;
        Set<TotalInfo> totalInfoSet = new TreeSet<>();
        while (iterator.hasNext()) {
            Map.Entry<Integer, Long> next = iterator.next();
            count += next.getValue();
            maxBorder = next.getKey();
            if (minBorder == 0) {
                minBorder = maxBorder;
            }
            if (count >= firstBorderCount && firstBorder == 0) {
                firstBorder = maxBorder;
            } else if (count >= secondBorderCount && secondBorder == 0) {
                secondBorder = maxBorder;
            } else if (count >= thirdBorderCount && thirdBorder == 0) {
                thirdBorder = maxBorder;
            } else if (count >= fourthBorderCount && fourthBorder == 0) {
                fourthBorder = maxBorder;
            }

//            if () {
            TotalInfo totalInfo = new TotalInfo(1, 111, 22.22f, 33.33f);
            totalInfoSet.add(totalInfo);
            totalInfo = new TotalInfo(2, 222, 33.33f, 44.44f);
            totalInfoSet.add(totalInfo);
//            }
        }
        String result = String.format("%s min=%d 50%%=%d 90%%=%d 99%%=%d 99.9%%=%d",
                eventName, minBorder, firstBorder, secondBorder, thirdBorder, fourthBorder);
        System.out.println(result);


        OrderDao orderDao = new OrderDao();
        orderDao.saveAll(totalInfoSet);
    }
}
