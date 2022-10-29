package com.api.taskmanager.constants;

public final class Helper {
    public static final String NEW_INVITATION_EMAIL_SUBJECT = "Convite para o quadro %s";
    public static final String NEW_INVITATION_EMAIL_BODY =
            "<html>" +
                "<body>" +
                    "<h1>Você foi convidado a fazer parte do board %s na plataforma TaskManager</h1>" +
                    "<p>Para aceitar o vinculo com o quadro, clique no botão abaixo.</p>" +
                    "<a href=\"%s\"><button>Clique aqui</button></a>" +
                "</body>" +
            "</html>";

    public static final String PASSWORD_RESET_EMAIL_SUBJECT = "TaskManager - Pedido de alteração de senha";
    public static final String PASSWORD_RESET_EMAIL_BODY =
            "<html>" +
                "<body>" +
                    "<p>Uma alteração de senha foi solicitada para seu usuário. Caso não tenha sido você, nenhuma ação é requerida.</p>" +
                    "<p>Para criar a nova senha, clique no botão abaixo.</p>" +
                    "<a href=\"%s\"><button>Clique aqui</button></a>" +
                    "<p><b>Atenção</b>: Não compartilhe este e-mail ou o Link de acesso com outras pessoas.</p>" +
                "</body>" +
            "</html>";

    private Helper() {}
}
