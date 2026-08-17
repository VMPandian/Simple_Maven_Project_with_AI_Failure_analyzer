package com.qa.ai;

public class Main {

    public static void main(String[] args) {

        try {

            FailureAnalyzer analyzer = new FailureAnalyzer();

            String testCaseId = "TC_LOGIN_001";

            String failureMessage = """
                    org.openqa.selenium.NoSuchElementException:
                    Unable to locate element:
                    {"method":"xpath","selector":"//button[@id='login']"}
                    """;

            FailureAnalysis analysis =
                    analyzer.analyze(failureMessage);

            System.out.println("======================================");
            System.out.println("Test Case ID: " + testCaseId);
            System.out.println("======================================");

            System.out.println(
                    "Failure Category: "
                            + analysis.getFailureCategory()
            );

            System.out.println("Top 3 Possible Root Causes:");

for (int i = 0; i < analysis.getPossibleRootCauses().size(); i++) {

    System.out.println(
            (i + 1) + ". "
                    + analysis.getPossibleRootCauses().get(i)
    );
}

            System.out.println("Top 3 Solutions:");

            for (int i = 0; i < analysis.getSolutions().size(); i++) {

                System.out.println(
                        (i + 1) + ". "
                                + analysis.getSolutions().get(i)
                );
            }

        } catch (Exception e) {

            System.err.println("Error occurred:");
            e.printStackTrace();
        }
    }
}