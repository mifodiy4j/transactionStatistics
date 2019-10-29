package ru.mifodiy67.exchange.util;

import ru.mifodiy67.exchange.dao.OrderDao;
import ru.mifodiy67.exchange.model.StatisticInfo;
import ru.mifodiy67.exchange.model.TotalInfo;
import ru.mifodiy67.exchange.service.ParsingFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import static ru.mifodiy67.exchange.util.ExchangeConst.FIRST_BORDER_VALUE;
import static ru.mifodiy67.exchange.util.ExchangeConst.SECOND_BORDER_VALUE;
import static ru.mifodiy67.exchange.util.ExchangeConst.THIRD_BORDER_VALUE;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        String fileName = "";
        String eventName = "";
        if (args.length > 0) {
            fileName = args[0];
            eventName = args[1];
        }

        ParsingFile service = new ParsingFile();
        List<StatisticInfo> list = service.parsing(fileName);
        Map<Integer, Long> samplesMap = service.getEventStatistics(list, eventName);

        int minBorder = (int) samplesMap.keySet().toArray()[0];
        int median = getMedian(samplesMap);
        int endIndex = samplesMap.size() - 1;
        int maxBorder = (int) samplesMap.keySet().toArray()[endIndex];
        int firstPercentile = median + Math.round((maxBorder - median) * FIRST_BORDER_VALUE);
        int secondPercentile = median + Math.round((maxBorder - median) * SECOND_BORDER_VALUE);
        int thirdPercentile = median + Math.round((maxBorder - median) * THIRD_BORDER_VALUE);

        String result = String.format("%s min=%d 50%%=%d 90%%=%d 99%%=%d 99.9%%=%d",
                eventName, minBorder, median, firstPercentile, secondPercentile, thirdPercentile);
        System.out.println(result);

        int recordsCount = list.size();
        int count = 0;
        int intervalCount = 0;
        Set<TotalInfo> totalInfoSet = new TreeSet<>();
        int endTime = (int) (Math.ceil((float)maxBorder / 10) * 10);
        for (int time = minBorder; time <= endTime; time++) {
            long value = samplesMap.getOrDefault(time, 0L);
            count += value;
            intervalCount += value;
            if (intervalCount != 0 && time % 5 == 0) {
                float weight = (float) intervalCount / recordsCount * 100;
                float percent = (float) count / recordsCount * 100;
                TotalInfo totalInfo = new TotalInfo(time, intervalCount, weight, percent);
                totalInfoSet.add(totalInfo);
                intervalCount = 0;
            }
        }
        OrderDao orderDao = new OrderDao();
        orderDao.createTable();
        orderDao.saveAll(totalInfoSet);
    }

    private static int getMedian(Map<Integer, Long> map) {
        int median;
        int size = map.size();
        if (size % 2 == 0) {
            int index = size / 2;
            int firstPeak = (int) map.keySet().toArray()[index - 1];
            int secondPeak = (int) map.keySet().toArray()[index];
            median = (firstPeak + secondPeak) / 2;
        } else {
            int index = (size - 1) / 2;
            median = (int) map.keySet().toArray()[index];
        }
        return median;
    }
}
