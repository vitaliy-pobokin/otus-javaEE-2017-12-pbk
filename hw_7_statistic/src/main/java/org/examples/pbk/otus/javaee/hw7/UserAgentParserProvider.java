package org.examples.pbk.otus.javaee.hw7;

import com.blueconic.browscap.BrowsCapField;
import com.blueconic.browscap.ParseException;
import com.blueconic.browscap.UserAgentParser;
import com.blueconic.browscap.UserAgentService;

import java.io.IOException;
import java.util.Arrays;

public class UserAgentParserProvider {

    private static UserAgentParser instance;

    static {
        try {
            instance = new UserAgentService().loadParser(
                    Arrays.asList(
                            BrowsCapField.BROWSER,
                            BrowsCapField.BROWSER_MAJOR_VERSION,
                            BrowsCapField.PLATFORM,
                            BrowsCapField.DEVICE_TYPE));
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static UserAgentParser getInstance(){
        return instance;
    }
}
