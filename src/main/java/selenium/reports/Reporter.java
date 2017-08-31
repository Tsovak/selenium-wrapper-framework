package selenium.reports;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.qatools.allure.annotations.Step;

import java.nio.file.Path;
import java.util.Arrays;

/**
 * Created by Tsovak_Sahakyan.
 */
@UtilityClass
@Slf4j
public class Reporter{


    @Setter
    private static Attachment attachment = new Attachment();
    private static final String REPORT_PREFIX = "IN THE REPORT";

    /**
     * Types of log messages
     */
    public enum Type {
        INFO("STEP"), WARNING("WARNING"), ERROR("ERROR"), ATTACHMENT("ATTACHMENT"), CHECK("CHECK");
        @Getter
        private final String value;

        Type(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return getValue();
        }

    }

    /**
     * Step
     *
     * @param message
     */
    public static void step(String message) {
        step(Type.INFO, message, null, null, null);
    }

    /**
     * such as step
     *
     * @param message
     */
    public static void info(String message) {
        step(Type.INFO, message, null, null, null);
    }

    /**
     * such as step
     *
     * @param message
     */
    public static void info(String message, String desc) {
        step(Type.INFO, message, null, null, desc);
    }

    /**
     * warning
     *
     * @param message
     */
    public static void warn(String message) {
        step(Type.WARNING, message, null, null, null);
    }

    /**
     * warning
     *
     * @param message
     * @param throwable
     */
    public static void warn(String message, Throwable throwable) {
        step(Type.WARNING, message, throwable, null, null);
    }

    /**
     * ERROR. The end of the step
     *
     * @param message
     */
    public static void error(String message) {
        step(Type.ERROR, message, null, null, null);
    }

    /**
     * ERROR. The end of the test
     *
     * @param message
     * @param desc
     */
    public static void error(String message, String desc) {
        step(Type.ERROR, message, null, null, desc);
    }

    /**
     * ERROR. The end of the test
     *
     * @param message
     * @param throwable
     */
    public static void error(String message, Throwable throwable) {
        step(Type.ERROR, message, throwable, null, null);
    }

    /**
     * Log checking. Used inside asserts
     *
     * @param message
     * @param description
     * @param isTrue
     */
    public static void check(String message, String description, boolean isTrue) {
        String finalMessage = Type.CHECK.getValue() + " : " + message;
        if (isTrue) {
            finalMessage += " success";
        } else {
            finalMessage += " fail";
        }
        stepCheck(finalMessage, description, isTrue);
    }

    /**
     * Attach a file as a separate step with an attachment
     *
     * @param name    File name to display in the Report
     * @param content Content
     */
    public static void attachFile(String name, byte[] content) {
        step(Type.ATTACHMENT, "file : " + name, null, () -> attachment.attachFile(name, content), null);
    }

    /**
     * Attach a file as a separate step with an attachment
     *
     * @param file File
     */
    public static void attachFile(Path file) {
        final String message = file.getFileName().toString();

        step(Type.ATTACHMENT, "File : " + message, null, () -> attachment.attachFile(message, file), null);
    }

    /**
     * Attach text as a separate step with an attachment
     *
     * @param text
     */
    public static void attachText(String text) {
        step(Type.ATTACHMENT, "text", null, () -> attachment.attachText("", text), null);
    }

    /**
     * Attach a html page as a separate step with an attachment
     *
     * @param html
     */
    public static void attachHtml(String html) {
        step(Type.ATTACHMENT, "HTML", null, () -> attachment.attachHtml("", html), null);
    }

    /**
     * Screenshot of the browser, if the browser is not running, then the desktop
     */
    public static void screen() {
        step(Type.ATTACHMENT, "screen", null, () -> attachment.attachScreen(""), REPORT_PREFIX);
    }

    /**
     * Screenshot of the browser, if the browser is not running, then the desktop
     *
     * @param message Name of screen
     */
    public static void screen(String message) {
        step(Type.ATTACHMENT, "screen", null, () -> attachment.attachScreen(message), null);

    }

    @Step("{0} : {1}")
    private static void step(Type level, String message, Throwable throwable, Runnable attach, String desc) {
        String finalMessage = REPORT_PREFIX + " - " + level.getValue() + " : " + message;
        switch (level) {
            case INFO:

                attachment.attachScreen("Screenshot of step");

                if (desc != null && !desc.isEmpty()){
                    finalMessage += "\n" + "Step description : " + desc;
                    attachment.attachText("Step description", desc);
                }
                log.info(finalMessage);
                break;
            case WARNING:
                attachment.attachScreen("Screenshot warning");

                if (throwable != null) {
                    attachment.attachText(throwable.getClass().toString(), throwable.toString());
                }
                if (desc != null && !desc.isEmpty()){
                    finalMessage += "\n" + "Screenshot warning : " + desc;
                    attachment.attachText("Screenshot warning", desc);
                }
                log.warn(finalMessage);
                break;
            case ERROR:

                attachment.attachScreen("Screenshot error");

                if (throwable != null) {
                    attachment.attachText(throwable.getClass().toString(), throwable.getLocalizedMessage() +
                                                                           System.lineSeparator() + Arrays
                                                                               .toString(throwable.getStackTrace()));
                }
                if (desc != null && !desc.isEmpty()){
                    finalMessage += "\n" + "Error Description : " + desc;
                    attachment.attachText("Error Description", desc);
                }
                log.error(finalMessage);
                throw new AssertionError(finalMessage);

            case ATTACHMENT:
                log.info(finalMessage);
                if (attach != null) {
                    attach.run();
                }
                break;

            default:
                log.info(finalMessage);
                attachment.attachText("Unprocessed message", finalMessage);
        }


    }

    @Step("{0}")
    private static void stepCheck(String message, String description, boolean isTrue) {
        if (isTrue) {
            log.info(REPORT_PREFIX + " - " + message + " - " + description);
        } else {
            log.error(REPORT_PREFIX + " - " + message + " - " + description);

        }
        if (description != null || !description.isEmpty()) {
            attachment.attachText("Description check", description);
            attachment.attachScreen("Screenshot of check");
        }

    }

    //regions Inner Builder
    public static class Builder {

        private String title;

        private StringBuilder description = new StringBuilder();

        public Builder() {
        }

        public void clear() {
            title = null;
            description = new StringBuilder();
        }

        public void build() {
            String desc = description != null ? description.toString() : "empty";
            if (title != null && !title.isEmpty()) {
                Reporter.info(title, desc);
            } else {
                Reporter.info(desc);
            }
            clear();
        }

        public boolean isEmpty() {
            return description.length() <= 0;
        }

        public Builder withTitle(String message) {
            this.title = message;
            return this;
        }

        public Builder append(String message) {
            this.description.append(message);
            this.description.append(System.lineSeparator());
            return this;
        }


    }
}
