package ComponenteConquista;

public class ForumServiceGamificationProxy {

    private final ForumService forumService;

    public ForumServiceGamificationProxy(ForumService forumService){
        this.forumService = forumService;
    }

    void addTopic(String user, String topic){
        forumService.addTopic(user, topic);
    }

    void addComment(String user, String topic, String comment){
        forumService.addComment(user, topic, comment);
    }

    void likeTopic(String user, String topic, String topicUser){
        forumService.likeTopic(user, topic, topicUser);
    }

    void likeComment(String user, String topic, String comment, String commentUser){
        forumService.likeComment(user, topic, comment, commentUser);
    }

}
