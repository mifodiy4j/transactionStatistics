package ru.mifodiy67.exchange.dao;

import ru.mifodiy67.exchange.model.TotalInfo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Set;

import static ru.mifodiy67.exchange.util.ExchangeConst.DB_URL;
import static ru.mifodiy67.exchange.util.ExchangeConst.JDBC_DRIVER;
import static ru.mifodiy67.exchange.util.ExchangeConst.PASS;
import static ru.mifodiy67.exchange.util.ExchangeConst.USER;

public class OrderDao {

    private static final String SAVE_ORDERS =
            "INSERT INTO INFO(EXEC_TIME, TRANS_NO, WEIGHT, PERCENT) VALUES (?, ?, ?, ?)";

    public void saveAll(Set<TotalInfo> totalInfoSet) throws ClassNotFoundException {
        Class.forName(JDBC_DRIVER);
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = connection.prepareStatement(SAVE_ORDERS)) {
            for (TotalInfo totalInfo : totalInfoSet) {
                stmt.setInt(1, totalInfo.getExecTime());
                stmt.setInt(2, totalInfo.getTransNo());
                stmt.setFloat(3, totalInfo.getWeight());
                stmt.setFloat(4, totalInfo.getPercent());

                stmt.execute();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
