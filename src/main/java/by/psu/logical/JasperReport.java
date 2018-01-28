package by.psu.logical;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.application.Platform;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.exception.DRException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JasperReport {


    private JasperReportBuilder report = null;
    private File file = null;

    public JasperReport(final File file) {
        this.file = file;
        report = DynamicReports.report();
    }

    private Connection getConnection(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/CompanyFX",
                    "postgres",
                    "Raidmax123"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void getReport() {
        try {
            Connection connection = getConnection();

            report.columns(
                    Columns.column("Имя сотрудника", "name", DataTypes.stringType()),
                    Columns.column("Фамилия сотрудника", "last_name", DataTypes.stringType()),
                    Columns.column("Отчество сотрудника", "patronymic", DataTypes.stringType()),
                    Columns.column("Мобильный номер", "private_number_phone", DataTypes.stringType()),
                    Columns.column("Email", "email", DataTypes.stringType())
            )
                    .title(
                            Components.text("Сотрудники компании по организации выездов на детские праздники")
                                    .setHorizontalAlignment(HorizontalAlignment.CENTER))
                    .pageFooter(Components.pageXofY())//show page number on the page footer
                    .setDataSource("SELECT e.name, last_name, patronymic, pc.data_birth, pc.private_number_phone, pc.email " +
                            "FROM employees e LEFT JOIN private_cards pc ON pc.id = e.id", connection);


            report.toHtml(new FileOutputStream(file.getAbsolutePath()));

            connection.close();
        } catch (SQLException | DRException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}