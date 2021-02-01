package GraphicInterface;

import Domain.*;
import Service.ClientService;
import Service.RonAccountService;
import Service.TransactionService;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenerateClientReport {

    Client client;
    ClientService clientService;
    RonAccountService ronAccountService;
    TransactionService transactionService;

    public Button btnGenerate;
    public DatePicker txtStartDate;
    public DatePicker txtEndDate;

    List<BankReport> listItems = new ArrayList<BankReport>();

    public void setServices(Client client, ClientService clientService, RonAccountService ronAccountService, TransactionService transactionService) {
        this.client = client;
        this.clientService = clientService;
        this.ronAccountService = ronAccountService;
        this.transactionService = transactionService;
    }

    public void initialize() {
    }

    public void handleGenerateReport(ActionEvent actionEvent) throws FileNotFoundException, JRException {
      try {
          LocalDate start = txtStartDate.valueProperty().getValue();
          LocalDate end = txtEndDate.valueProperty().getValue();

          DateTime startDate = new DateTime();
          startDate.setDate(start);
          DateTime endDate = new DateTime();
          endDate.setDate(end);

          int startYear = startDate.getDate().getYear();
          int startMonth = startDate.getDate().getMonthValue();
          int startDay = startDate.getDate().getDayOfMonth();
          int endYear = endDate.getDate().getYear();
          int endMonth = endDate.getDate().getMonthValue();
          int endDay = endDate.getDate().getDayOfMonth();

          for (Transaction t : transactionService.readAllTransactions()) {
              int transactionYear = t.getTimestamp().getDate().getYear();
              int transactionMonth = t.getTimestamp().getDate().getMonthValue();
              int transactionDay = t.getTimestamp().getDate().getDayOfMonth();


              if (t.getClientId() == client.getId()) {
                  if (
                          startDay <= transactionDay && transactionDay <= endDay &&
                                  startMonth <= transactionMonth && transactionMonth <= endMonth &&
                                  startYear <= transactionYear && transactionYear <= endYear
                  ) {
                      listItems.add(new BankReport(t.getId(), t.getTimestamp().toString(), t.getAmountTransaction(), t.getType()));
                  }

              }

          }//end for

          String userName = "";
          double balance = 0.0;
          String iban = "";
          String startDateExport = startDate.getDate().toString();
          String endDateExport = endDate.getDate().toString();

          for (Transaction transaction : transactionService.readAllTransactions()) {
              if (transaction.getClientId() == client.getId()) {

                  userName = client.getName() + " " + client.getSurname();

                  for (RonAccount r : ronAccountService.readAllRonAccount()) {
                      if (r.getClientId() == client.getId()) {
                          balance = r.getSold();
                      }
                  }
                  String afterRo = client.getCnp().substring(0, 1);
                  String afterId = client.getCnp().substring(4, 11);

                  iban = "RO" + afterRo + "DFB" + client.getId() + afterId;

              }
          }//end for


          String outputFile = "C:\\exports\\" + userName.trim() + ".pdf";
          /* Convert List to JRBeanCollectionDataSource */
          JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(listItems);

          /* Map to hold Jasper report Parameters */
          Map<String, Object> parameters = new HashMap<String, Object>();
          parameters.put("CollectionBeanParam", itemsJRBean);
          parameters.put("ClientName", userName);
          parameters.put("Balance", balance);
          parameters.put("StartDate", startDateExport);
          parameters.put("EndDate", endDateExport);
          parameters.put("IBAN", iban);


          InputStream input = new FileInputStream(new File("C:\\Program Files (x86)\\DonFrateliBank\\app\\Cherry.jrxml"));
          JasperDesign jasperDesign = JRXmlLoader.load(input);
          JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
          JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

          //  JasperViewer.viewReport(jasperPrint);

          /* create PDF */
          OutputStream outputStream = new FileOutputStream(new File(outputFile));

          /* Write content to PDF file */
          JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

          String pdfName = userName.trim();
          System.out.println("PDF File Generated: " + pdfName + ".pdf");

          Alert alert = new Alert(Alert.AlertType.INFORMATION);
          alert.setTitle("Information");
          alert.setHeaderText("Your pdf file was exported");
          alert.setContentText("PDF File Generated: " + pdfName + ".pdf");
          alert.show();

          Stage stage = (Stage) btnGenerate.getScene().getWindow();
          stage.close();
      }catch (Exception e){
        //  System.out.println(e.getMessage());
      }
    }
}
