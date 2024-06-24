package mainPackage;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.FileUtils;
import org.zeroturnaround.zip.ZipUtil;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.bouncycastle.util.Arrays.concatenate;

public class main {
    static int skipped,broken,failures,testsRun;
    static String allureReportPath;
    static String[] mavenCommand = {"mvn", "clean", "test", "-Dtest=testSuite"};
    static String[] allureCommand = {"allure", "generate", "allure-results", "-o", "allure-reports"};
    static String[] allureCommandSingleReport = {"allure", "generate", "--single-file", "allure-results"};
    static String errorReceiver = "akmalaqim74@gmail.com";
    static String errorMessage = "The test suite has finished executing with ERRORS. Please find the Allure report attached." ;
    static String defaultReceiver = "akmalmustaqimsenang@gmail.com";
    static String defaultMsg = "The test suite has finished executing with BROKEN || skipped || no issues test. Please find the Allure report attached.";
    static String fileSeparator = System.getProperty("file.separator");
    public static void main(String[] args) throws IOException{
        runAll();

    }

    public static void runAll() throws IOException{
        allureHandler();
        failedTestCases();
        executeCommands(mavenCommand);
        executeCommands(allureCommand);
        getTestResult();
        executeCommands(allureCommandSingleReport);
        renameAllureReport();
        if(failures > 0){
            //you can send multiple receiver, just example "email,email"
            sendEmailWithAttachment(errorReceiver,errorMessage);
        }
        if(broken > 0 || skipped > 0)
            sendEmailWithAttachment(defaultReceiver,defaultMsg);
    }

    public static void failedTestCases() throws IOException {
        //FYI inside allure-reports\\data\\attachments we also have the list of error screenshot, so if you want to use that, up to u
        String currentDir = System.getProperty("user.dir");
        String filePath = currentDir + fileSeparator + "failed-testcases";
        File directory = new File(filePath);
        if (!directory.exists()) {
            System.out.println("Creating new Directory for failed testcases");
            directory.mkdirs();
        }else{
            System.out.println("Delete existing failed testcases");
            FileUtils.deleteDirectory(directory);
            System.out.println("Creating new Directory for failed testcases");
            directory.mkdirs();
        }
    }

    public static void getTestResult(){
        try {
            // Get the current working directory
            String currentDir = System.getProperty("user.dir");
            String filePath = currentDir + fileSeparator+ "allure-reports"+ fileSeparator + "history" + fileSeparator + "history-trend.json";

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
        String allureResultsPath = projectRoot + fileSeparator + "allure-results";
        System.out.println("Allure Results Path: " + allureResultsPath);
        File dirFile = new File(allureResultsPath);
        if(dirFile.exists()){
            FileUtils.deleteDirectory(dirFile);
        }else{
            System.out.println("allure-result not exist");
        }
        allureReportPath = projectRoot + fileSeparator + "allure-report";
        System.out.println("Allure Report Path: " + allureReportPath);
        File reportDir = new File(allureReportPath);
        if(reportDir.exists()){
            FileUtils.deleteDirectory(reportDir);
        }else{
            System.out.println("allure report not exist");
        }

        String allureReportsPath = projectRoot + fileSeparator + "allure-reports";
        System.out.println("Allure Results Path: " + allureResultsPath);
        File reportsDir = new File(allureReportsPath);
        if(reportsDir.exists()){
            FileUtils.deleteDirectory(reportsDir);
        }else{
            System.out.println("allure-reports not exist");
        }
    }
    private static void executeCommands(String[] commands) {
        // Determine the command interpreter based on OS
        String osName = System.getProperty("os.name").toLowerCase();
        boolean isWindows = osName.contains("win");

        // Adjust commands for Windows or Linux
        List<String> commandList = new ArrayList<>();
        // Add interpreter commands for Windows
        if (isWindows) {
            commandList.add("cmd.exe");
            commandList.add("/c");
        }

        // Add all commands
        for (String command : commands) {
            commandList.add(command);
        }
        ProcessBuilder processBuilder = new ProcessBuilder(commandList);
        // Initialize ProcessBuilder
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
        File indexFile = new File(projectRoot + fileSeparator +"allure-report" + fileSeparator + "index.html");
        File renamedFile = new File(projectRoot + fileSeparator + "allure-report"+fileSeparator + "allure_report.html");
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
    public static void sendEmailWithAttachment  (String sendTo,String msg) throws IOException{
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
            BodyPart messageBodyPart1 = new MimeBodyPart();

            // Now set the actual message
            messageBodyPart1.setText("Folder of failed screenshot testcases\n For further Details PFA of the report");

            // Create a multipart message
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart1);

            // Part two is attachment
            messageBodyPart1 = new MimeBodyPart();
            String projectRoot = System.getProperty("user.dir");
            String failedTestPath = projectRoot + fileSeparator + "failed-testcases";
            File directory = new File(failedTestPath);
            if (directory.exists() && directory.isDirectory()) {
                File[] files = directory.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.isFile()) {
                            MimeBodyPart attachmentPart = new MimeBodyPart();
                            DataSource fileDataSource = new FileDataSource(file);
                            attachmentPart.setDataHandler(new DataHandler(fileDataSource));
                            attachmentPart.setFileName(file.getName());
                            multipart.addBodyPart(attachmentPart);
                        }
                    }
                }
            } else {
                System.err.println("Failed test cases directory does not exist or is not a directory.");
            }
            // send attachment using folder
            /*String zipFilePath = projectRoot + "\\failed-testcases.zip";
            File tempZip = new File(zipFilePath);
            if(tempZip.exists()){
                tempZip.delete();
            }else{
                System.out.println("allure-result not exist");
            }
            // Compress the directory
            ZipUtil.pack(new File(failedTestPath), new File(zipFilePath));
            DataSource source = new FileDataSource(zipFilePath);
            messageBodyPart1.setDataHandler(new DataHandler(source));
            messageBodyPart1.setFileName("Failed_testcases.zip");
            multipart.addBodyPart(messageBodyPart1);
            */
            // Add another body part for text content
            MimeBodyPart messageBodyPart2 = new MimeBodyPart();
            messageBodyPart2.setText(msg);
            allureReportPath = projectRoot + fileSeparator + "allure-report";
            DataSource source2 = new FileDataSource(allureReportPath + fileSeparator + "allure_report.html");
            messageBodyPart2.setDataHandler(new DataHandler(source2));
            messageBodyPart2.setFileName("allure_report.html");
            multipart.addBodyPart(messageBodyPart2);

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
