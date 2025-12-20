package com.savan.bulkRequestProcess.helpers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
public class RestTemplatePostExample {

    @Value("${finfinity.cygnet.pullItr.api.url}")
    private String cygnetPullItrUrl;

    public static void main(String[] args) {
        System.out.println(new RestTemplatePostExample().cygnetPullItrUrl);
    }

    public String postApiResUnirest() {

        System.out.println("cygnetPullItrUrl: "+cygnetPullItrUrl);

        String resp = "";

        // Define the request body (Example: sending a JSON object)
        String jsonBody = """
                {"data":"ew0KICAgICJkYXRhIjogew0KICAgICAgICAidXNlcm5hbWUiOiAiQUhUUEc4Mjc0QSIsDQogICAgICAgICJwYXNzd29yZCI6ICJMdWNrbm93MjAwOSIsDQogICAgICAgICJyZXBvcnRUeXBlIjogIjIiLA0KICAgICAgICAiZlllYXIiOiBbDQogICAgICAgICAgICAiMjAyMC0yMDIxIiwNCiAgICAgICAgICAgICIyMDIxLTIwMjIiLA0KICAgICAgICAgICAgIjIwMjItMjAyMyIsDQogICAgICAgICAgICAiMjAyMy0yMDI0Ig0KICAgICAgICBdDQogICAgfQ0KfQ=="}
                """;
        try {
            HttpResponse<String> response = Unirest.post("https://staging-enrichedapi.cygnetfame.in/v0.1/itr/pullitr")
                    .header("clientid", "iQJKfmjq1+5pOeDTwR+RPXhuGro2nuP8UOinz6E8/w+E+5gOLq8EghaW9E78fTO57GeYFhHemzjobyAPz4kI55asgf8NWJoCRjD+Nx4rRL8HqyWauSO02wGAfJ0EQpVj")
                    .header("client-secret", "0q8fTNYB3gX2ct7yCFkbq4nH1wvj9YJYdh0CjZBjiRKJrDGQTtVlVpF1XuPi/Wd3uLkRnDgjpZXxRVWf3CENmmtj7JZWiECT5hLCYxrLWUSn7uu02jwPQwqF+Gm8HDzkgHyb1AI6J2pItAI6R4MHew3aNwJrC4Fix2KFsgu0X8Yp5m/52VpncA9OsBCq8Wj5ssk1rKORjp9SrkMQWrGS/CycQV0B61BChKBc6qjegSmXhw8wYaktbLExWwRYcXG4")
                    .header("txn", "ENRICHEDTEST_e74c3568b19b4aee669c")
                    .header("ip-usr", "172.16.130.252")
                    .header("IsEncryptionRequired", "false")
                    .header("auth-token", "504fabac6891fd2f72e834c984e661d9bf75a4242dd0c1411dd8c6b4509b1e90")
                    .header("Content-Type", "application/json")
                    .body(jsonBody)
                    .asString();

            resp = response.getBody();
            System.out.println("response: "+resp);

        } catch (Exception e) {
            System.out.println(e);
            log.info("An exception occurred: {}", e.getMessage());
            String errorMessage = e.getMessage();

            Pattern pattern = Pattern.compile("\\{.*\\}");
            Matcher matcher = pattern.matcher(errorMessage);
            if (matcher.find()) {
                resp = matcher.group();
            } else {
                resp = errorMessage;
            }
        }

        return resp;
    }
}
