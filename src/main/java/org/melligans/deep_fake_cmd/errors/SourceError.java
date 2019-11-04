package org.melligans.deep_fake_cmd.errors;

public class SourceError extends Error {
    public SourceError(String message) {
        System.out.println(
                "Source Error!" +
                        "\n\t---> "+message
        );
    }
}
