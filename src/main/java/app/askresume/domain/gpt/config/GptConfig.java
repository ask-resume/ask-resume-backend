package app.askresume.domain.gpt.config;


import java.time.Duration;

public class GptConfig {

    public final static String MODEL = "gpt-3.5-turbo";
    public final static double TOP_P = 1.0;
    public final static int MAX_TOKEN = 2000;
    public final static double TEMPERATURE = 1.0;
    public final static Duration TIME_OUT = Duration.ofSeconds(300);
}
