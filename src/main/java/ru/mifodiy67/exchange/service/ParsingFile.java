package ru.mifodiy67.exchange.service;

import ru.mifodiy67.exchange.model.StatisticInfo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ParsingFile {

    public List<StatisticInfo> parsing(String fileName) throws IOException {

        File file = new File(getClass().getClassLoader().getResource(fileName).getFile());
        List<StatisticInfo> list;
        try (Stream<String> lines = Files.lines(file.toPath())) {
            list = lines
                    .filter(l -> l.contains("\t"))
                    .skip(1)
                    .map(e -> {
                        String[] str = e.split("\t");
                        String transactionName = str[1];
                        String callTimeStr = str[15];
                        int callTime = Integer.parseInt(callTimeStr);
                        return new StatisticInfo(transactionName, callTime);
                    })
                    .collect(Collectors.toList());
        }
        return list;
    }

    public Map<Integer, Long> getEventStatistics(List<StatisticInfo> list, String eventName) {
        Map<StatisticInfo, Long> result = list.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        Map<Integer, Long> finalMap = new TreeMap<>();
        result.entrySet().stream()
                .filter(e -> eventName.equals(e.getKey().getTransactionName()))
                .forEachOrdered(e -> finalMap.put(e.getKey().getCallTime(), e.getValue()));
        return finalMap;
    }
}
