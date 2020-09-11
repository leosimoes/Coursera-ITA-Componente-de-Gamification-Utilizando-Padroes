package ComponenteConquista;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.List;

public class TestComponentesAchievements {

    /*
    Os seguintes testes devem ser feitos:
    Chamar o método addTopic() e ver se os achievements foram adicionados da forma correta
    Chamar o método addComment() e ver se os achievements foram adicionados da forma correta
    Chamar o método likeTopic() e ver se os achievements foram adicionados da forma correta
    Chamar o método likeComment() e ver se os achievements foram adicionados da forma correta
    Chamar o método addTopic() duas vezes e ver se os pontos foram somados e se o badge está presente apenas uma vez
    Fazer um teste invocando vários métodos e verificar se o resultado é o esperado.
    Fazer o mock lançar uma exceção para algum método e verificar se os Achievements não foram adicionados.
    Atingir 100 pontos de "CREATION" e verificar se o usuário recebe o badge "INVENTOR"
    Atingir 100 pontos de "PARTICIPATION" e verificar se o usuário recebe o badge "PART OF THE COMMUNITY"
    */

    private ForumServiceGamificationProxy forumServiceGamificationProxy;

    private ForumService forumServiceMock;

    private AchievementStorage achievementStorage;

    private AchievementObserver achievementObserver_Creation;

    private AchievementObserver achievementObserver_Participation;

    private final String USER = "USER";

    @Before
    public void setUp() {
        forumServiceMock = Mockito.mock(ForumService.class);
        forumServiceGamificationProxy = new ForumServiceGamificationProxy(forumServiceMock);

        achievementStorage = new MemoryAchievementStorage();
        AchievementStorageFactory.setAchievementStorage(achievementStorage);

        achievementObserver_Creation = new AchievementObserverCreation();
        achievementObserver_Participation = new AchievementObserverParticipation();
    }

    @After
    public void tearDown() {

    }

    @Test
    public void testAddTopic() {
        // Deve adicionar 5 pontos do tipo "CREATION".
        // Deve adicionar o bagde "I CAN TALK"

        doAnswer((arguments) -> {
            achievementStorage.addAchievement(arguments.getArgument(0), new Points("CREATION", 5));
            achievementStorage.addAchievement(arguments.getArgument(0), new Badge("I CAN TALK"));
            return null;
        }).when(forumServiceMock).addTopic(any(String.class), any(String.class));

        forumServiceGamificationProxy.addTopic(USER, "TOPIC");

        List<Achievement> achievements = achievementStorage.getAchievements(USER);

        assertTrue(!achievements.isEmpty());
        assertEquals(2, achievements.size());
        assertTrue(achievements.contains(new Points("CREATION", 5)));
        assertTrue(achievements.contains(new Badge("I CAN TALK")));

    }

    @Test
    public void testAddComment(){
        // Deve adicionar 3 pontos do tipo "PARTICIPATION".
        // Deve adicionar o badge "LET ME ADD".

        doAnswer((arguments) -> {
            achievementStorage.addAchievement(arguments.getArgument(0), new Points("PARTICIPATION", 3));
            achievementStorage.addAchievement(arguments.getArgument(0), new Badge("LET ME ADD"));
            return null;
        }).when(forumServiceMock).addComment(any(String.class), any(String.class), any(String.class));

        forumServiceGamificationProxy.addComment(USER, "TOPIC", "COMMENT");

        List<Achievement> achievements = achievementStorage.getAchievements(USER);

        assertTrue(!achievements.isEmpty());
        assertEquals(2, achievements.size());
        assertTrue(achievements.contains(new Points("PARTICIPATION", 3)));
        assertTrue(achievements.contains(new Badge("LET ME ADD")));
    }

    @Test
    public void testLikeTopic(){
        // Deve adicionar 1 ponto do tipo "CREATION".

        doAnswer((arguments) -> {
            achievementStorage.addAchievement(arguments.getArgument(0), new Points("CREATION", 1));
            return null;
        }).when(forumServiceMock).likeTopic(any(String.class), any(String.class), any(String.class));

        forumServiceGamificationProxy.likeTopic(USER, "TOPIC", "COMMENT");

        List<Achievement> achievements = achievementStorage.getAchievements(USER);

        assertTrue(!achievements.isEmpty());
        assertEquals(1, achievements.size());
        assertTrue(achievements.contains(new Points("CREATION", 1)));

    }

    @Test
    public void testLikeComment(){
        // Deve adicionar 1 ponto do tipo "PARTICIPATION".

        doAnswer((arguments) -> {
            achievementStorage.addAchievement(arguments.getArgument(0), new Points("PARTICIPATION", 1));
            return null;
        }).when(forumServiceMock).likeComment( any(String.class), any(String.class), any(String.class), any(String.class));

        forumServiceGamificationProxy.likeComment(USER, "TOPIC", "COMMENT", "COMMENT_USER");

        List<Achievement> achievements = achievementStorage.getAchievements(USER);

        assertTrue(!achievements.isEmpty());
        assertEquals(1, achievements.size());
        assertTrue(achievements.contains(new Points("PARTICIPATION", 1)));

    }

    @Test
    public void testAddTopic_2vezes() {
        // Deve adicionar 5 pontos do tipo "CREATION".
        // Deve adicionar o bagde "I CAN TALK"

        doAnswer((arguments) -> {
            achievementStorage.addAchievement(arguments.getArgument(0), new Points("CREATION", 5));
            achievementStorage.addAchievement(arguments.getArgument(0), new Badge("I CAN TALK"));
            return null;
        }).when(forumServiceMock).addTopic(any(String.class), any(String.class));

        forumServiceGamificationProxy.addTopic(USER, "TOPIC");
        forumServiceGamificationProxy.addTopic(USER, "TOPIC");

        List<Achievement> achievements = achievementStorage.getAchievements(USER);

        assertTrue(!achievements.isEmpty());
        assertEquals(2, achievements.size());
        assertTrue(achievements.contains(new Points("CREATION", 10)));
        assertTrue(achievements.contains(new Badge("I CAN TALK")));

    }

    @Test
    public void testObserverCreation() {
        // Quando a quantidade de pontos do tipo "CREATION" chegar a 100,
        // o usuário deve receber o badge "INVENTOR"

        achievementStorage.adicionarObserver(achievementObserver_Creation);
        achievementStorage.addAchievement(USER, new Points("CREATION", 100));

        List<Achievement> achievements = achievementStorage.getAchievements(USER);

        assertTrue(!achievements.isEmpty());
        assertEquals(2, achievements.size());
        assertTrue(achievements.contains(new Points("CREATION", 100)));
        assertTrue(achievements.contains(new Badge("INVENTOR")));
    }

    @Test
    public void testObserverParticipation() {
        // Atingir 100 pontos de "PARTICIPATION" e verificar se o usuário recebe o badge "PART OF THE COMMUNITY"

        achievementStorage.adicionarObserver(achievementObserver_Participation);
        achievementStorage.addAchievement(USER, new Points("PARTICIPATION", 100));

        List<Achievement> achievements = achievementStorage.getAchievements(USER);

        assertTrue(!achievements.isEmpty());
        assertEquals(2, achievements.size());
        assertTrue(achievements.contains(new Points("PARTICIPATION", 100)));
        assertTrue(achievements.contains(new Badge("PART OF THE COMMUNITY")));
    }

    @Test
    public void testMetodosCombinados() {
        // Fazer um teste invocando vários métodos e verificar se o resultado é o esperado.

        achievementStorage.adicionarObserver(achievementObserver_Creation);
        achievementStorage.addAchievement(USER, new Points("CREATION", 100));

        achievementStorage.adicionarObserver(achievementObserver_Participation);
        achievementStorage.addAchievement(USER, new Points("PARTICIPATION", 100));

        List<Achievement> achievements = achievementStorage.getAchievements(USER);

        assertTrue(!achievements.isEmpty());
        assertEquals(4, achievements.size());
        assertTrue(achievements.contains(new Points("CREATION", 100)));
        assertTrue(achievements.contains(new Badge("INVENTOR")));
        assertTrue(achievements.contains(new Points("PARTICIPATION", 100)));
        assertTrue(achievements.contains(new Badge("PART OF THE COMMUNITY")));
    }

    @Test
    public void testExceptionMock() {
        // Fazer o mock lançar uma exceção para algum método e verificar se os Achievements não foram adicionados.

        doThrow(IllegalArgumentException.class).when(forumServiceMock).addTopic(null, null);

        achievementStorage.addAchievement(USER, new Points("PARTICIPATION", 1));

        try {
            forumServiceGamificationProxy.addTopic(null, null);
        } catch (IllegalArgumentException e){
            System.out.println("IllegalArgumentException launched");
        }
        List<Achievement> achievements = achievementStorage.getAchievements(USER);

        assertTrue(!achievements.isEmpty());
        assertEquals(1, achievements.size());
        assertTrue(achievements.contains(new Points("PARTICIPATION", 1)));

    }

}
