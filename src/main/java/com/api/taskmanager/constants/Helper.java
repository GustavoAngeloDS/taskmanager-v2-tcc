package com.api.taskmanager.constants;

public final class Helper {
    public static final String EMAIL_SUBJECT = "Convite para o quadro %s";
    public static final String EMAIL_BODY =
            "<html>" +
                "<body>" +
                    "<h1>Você foi convidado a fazer parte do board %s na plataforma TaskManager</h1>" +
                    "<p>Para aceitar o vinculo com o quadro, clique no botão abaixo.</p>" +
                    "<a href=\"%s\"><button>Clique aqui</button></a>" +
                "</body>" +
            "</html>";

    private Helper() {}
}
