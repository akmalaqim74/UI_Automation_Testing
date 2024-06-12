import org.apache.commons.io.FileUtils;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class main {
    static int skipped,errors,failures,testsRun;
    static String allureReportPath;
    public static void main(String[] args) throws IOException{
        allureHandler();
        String[] mavenCommand = {"cmd.exe", "/c", "mvn", "clean", "test" ,"-Dtest=testSuite"};
        executeCommands(mavenCommand);
        System.out.println("testsRun: " + testsRun);
        System.out.println("failures" + failures);
        System.out.println("errors" + errors);
        System.out.println("skipped: " + skipped);
        String[] allureCommand = { "cmd.exe", "/c", "allure", "generate", "--single-file", "target\\allure-results"};

        executeCommands(allureCommand);
        renameAllureReport();
        sendEmailWithAttachment();
    }
    public static void allureHandler() throws IOException{

        String projectRoot = System.getProperty("user.dir");
        String allureResultsPath = projectRoot + "\\target\\allure-results";
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
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((line = errorReader.readLine()) != null) {
                System.err.println(line);
            }
            while ((line = reader.readLine()) != null) {
                    System.out.println(line);

                    // Parse test results
                    if (line.startsWith("[WARNING] Tests run: ")) {
                        String[] parts = line.split(",");
                        for (String part : parts) {
                            if (part.trim().startsWith("[WARNING] Tests run: ")) {
                                testsRun = Integer.parseInt(part.trim().substring(21).trim());
                            } else if (part.trim().startsWith("Failures: ")) {
                                failures = Integer.parseInt(part.trim().substring(10).trim());
                            } else if (part.trim().startsWith("Errors: ")) {
                                errors = Integer.parseInt(part.trim().substring(8).trim());
                            } else if (part.trim().startsWith("Skipped: ")) {
                                skipped = Integer.parseInt(part.trim().substring(9).trim());
                            }
                        }
                    }
                // Wait for the process to complete
                int exitCode = process.waitFor();
                if (exitCode == 0) {
                    System.out.println("Maven commands executed successfully.");
                } else {
                    System.out.println("Failed to execute Maven commands. Exit code: " + exitCode);
                }
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
    public static void sendEmailWithAttachment() {
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
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("akmalaqim74@gmail.com"));

            // Set Subject: header field
            message.setSubject("Test Report: Allure Report");

            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();

            // Now set the actual message
            messageBodyPart.setText("The test suite has finished executing with errors. Please find the Allure report attached.");

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

            System.out.println("Sent message successfully....");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
