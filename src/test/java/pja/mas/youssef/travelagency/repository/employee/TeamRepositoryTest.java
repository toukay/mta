package pja.mas.youssef.travelagency.repository.employee;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pja.mas.youssef.travelagency.model.employee.Team;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TeamRepositoryTest {
    @Autowired
    private TeamRepository teamRepository;

    @PersistenceContext
    private EntityManager entityManager;

    Team t1;

    @BeforeEach
    public void initData() {
        t1 = Team.builder()
                .name("Team 1")
                .description("Description 1")
                .build();
    }

    @Test
    public void testTeamRepository(){
        assertNotNull(teamRepository);
    }

    @Test
    public void testFetchAllTeams(){
        Iterable<Team> teams = teamRepository.findAll();
        assertNotNull(teams);
    }

    @Test
    public void testSaveTeam(){
        Team savedTeam = teamRepository.save(t1);
        entityManager.flush();
        long count = teamRepository.count();
        assertEquals(1, count);
    }

    @Test
    public void testFindByName(){
        teamRepository.save(t1);
        entityManager.flush();
        Team team = teamRepository.findByName("Team 1").get(0);
        assertEquals(t1, team);
    }
}