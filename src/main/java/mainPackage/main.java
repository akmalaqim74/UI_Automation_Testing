package mainPackage;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.FileUtils;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.*;
import java.util.Properties;

public class main {
    static int skipped,broken,failures,testsRun;
    static String allureReportPath;

    public static void main(String[] args) throws IOException{
        allureHandler();
        String[] mavenCommand = {"cmd.exe", "/c", "mvn", "clean", "test" ,"-Dtest=testSuite"};
        executeCommands(mavenCommand);
        String[] allureCommand = { "cmd.exe", "/c", "allure", "generate", "allure-results", "-o","allure-reports"};
        executeCommands(allureCommand);
        getTestResult();
        String[] allureCommandSingleReport = { "cmd.exe", "/c", "allure", "generate", "--single-file", "allure-results"};
        executeCommands(allureCommandSingleReport);
        renameAllureReport();
        if(failures > 0){
            sendEmailWithAttachment("akmalaqim74@gmail.com,qalead@senang.io","The test suite has finished executing with ERRORS. Please find the Allure report attached.");
        }
        if(broken > 0 || skipped > 0)
            sendEmailWithAttachment("akmalmustaqimsenang@gmail.com,qalead@senang.io","The test suite has finished executing with BROKEN || skipped || no issues test. Please find the Allure report attached.");
    }

    public static void getTestResult(){
        try {
            // Get the current working directory
            String currentDir = System.getProperty("user.dir");
            String filePath = currentDir + "\\allure-reports\\history\\history-trend.json";

            // Read the JSON file
            JsonElement jsonElement = JsonParser.parseReader(new FileReader(filePath));

            // Check if the root element is an array
            if (jsonElement.isJsonArray()) {
                JsonArray jsonArray = jsonElement.getAsJsonArray();
                // Assuming there's only one element in the array
                JsonObject jsonObject = jsonArray.get(0).getAsJsonObject();

                // Get the "data" object
                JsonObject dataObject = jsonObject.getAsJsonObject("data");

                // Get the values and assign them to the variables
                skipped = dataObject.get("skipped").getAsInt();
                broken = dataObject.get("broken").getAsInt();
                failures = dataObject.get("failed").getAsInt();
                testsRun = dataObject.get("passed").getAsInt();

                // Print the values for verification
                System.out.println("Skipped: " + skipped);
                System.out.println("Errors: " + broken);
                System.out.println("Failures: " + failures);
                System.out.println("Tests Run: " + testsRun);
            } else {
                System.out.println("Root element is not an array.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void allureHandler() throws IOException{

        String projectRoot = System.getProperty("user.dir");
        String allureResultsPath = projectRoot + "\\allure-results";
        System.out.println("Allure Results Path: " + allureResultsPath);
        File dirFile = new File(allureResultsPath);
        if(dirFile.exists()){
            FileUtils.deleteDirectory(dirFile);
        }else{
            System.out.println("allure-result not exist");
        }
        allureReportPath = projectRoot + "\\allure-report";
        System.out.println("Allure Report Path: " + allureReportPath);
        File reportDir = new File(allureReportPath);
        if(reportDir.exists()){
            FileUtils.deleteDirectory(reportDir);
        }else{
            System.out.println("allure report not exist");
        }

        String allureReportsPath = projectRoot + "\\allure-reports";
        System.out.println("Allure Results Path: " + allureResultsPath);
        File reportsDir = new File(allureReportsPath);
        if(reportsDir.exists()){
            FileUtils.deleteDirectory(reportsDir);
        }else{
            System.out.println("allure-reports not exist");
        }
    }
    private static void executeCommands(String[] commands) {
        // Initialize ProcessBuilder
        ProcessBuilder processBuilder = new ProcessBuilder(commands);
        processBuilder.redirectErrorStream(true);
        try {
            // Start the process
            Process process = processBuilder.start();
            String line;
            // Read output and error streams
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((line = reader.readLine()) != null){
                System.out.println(line);
            }
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((line = errorReader.readLine()) != null) {
                System.err.println(line);
            }
            // Wait for the process to complete
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Maven commands executed successfully.");
            } else {
                System.out.println("Failed to execute Maven commands. Exit code: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void renameAllureReport() throws IOException {

        String projectRoot = System.getProperty("user.dir");
        File indexFile = new File(projectRoot + "\\allure-report\\index.html");
        File renamedFile = new File(projectRoot + "\\allure-report\\allure_report.html");
        if (indexFile.exists()) {
            if (indexFile.renameTo(renamedFile)) {
                System.out.println("Renamed index.html to allureReport.html");
            } else {
                System.out.println("Failed to rename index.html to allureReport.html");
            }
        } else {
            System.out.println("index.html does not exist.");
        }
    }
    public static void sendEmailWithAttachment(String sendTo,String msg) {
        // Set up email properties
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587"); // Use port 587 for TLS
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // Use SSL for secure connection (optional with port 587)


        // Authenticate
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("akmalmustaqimsenang@gmail.com", "pnihiuoaiwfoecne");
            }
        });

        try {
            // Create a default MimeMessage object
            Message message = new MimeMessage(session);

            // Set From: header field of the header
            message.setFrom(new InternetAddress("akmalmustaqimsenang@gmail.com"));

            // Set To: header field of the header
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(sendTo));

            // Set Subject: header field
            message.setSubject("Test Report: Allure Report");

            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();

            // Now set the actual message
            messageBodyPart.setText(msg);

            // Create a multipart message
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            String projectRoot = System.getProperty("user.dir");
            allureReportPath = projectRoot + "\\allure-report";
            DataSource source = new FileDataSource(allureReportPath + "\\allure_report.html");
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName("allure_report.html");
            multipart.addBodyPart(messageBodyPart);

            // Send the complete message parts
            message.setContent(multipart);

            // Send message
            Transport.send(message);

            System.out.println("Sent message successfully.... to " + sendTo);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
