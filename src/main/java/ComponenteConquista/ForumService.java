package ComponenteConquista;

public interface ForumService {

    void addTopic(String user, String topic);
    // - Deve adicionar 5 pontos do tipo "CREATION". Deve adicionar o bagde "I CAN TALK"

    void addComment(String user, String topic, String comment);
    // - Deve adicionar 3 pontos do tipo "PARTICIPATION". Deve adicionar o badge "LET ME ADD"

    void likeTopic(String user, String topic, String topicUser);
    // - Deve adicionar 1 ponto do tipo "CREATION".

    void likeComment(String user, String topic, String comment, String commentUser);
    //- Deve adicionar 1 ponto do tipo "PARTICIPATION".
}
