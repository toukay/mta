package pja.mas.youssef.travelagency.repository.employee;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pja.mas.youssef.travelagency.model.employee.Team;
import pja.mas.youssef.travelagency.model.employee.Agent;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TeamRepositoryTest {
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private AgentRepository agentRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private Team team1;
    private Team team2;
    private Agent agent1;
    private Agent agent2;

    @BeforeEach
    public void initData() {
        agent1 = Agent.builder()
                .name("Agent 1")
                .firstName("John")
                .lastName("Doe")
                .branchAddress("Branch 1")
                .specialization(Agent.Specialization.GENERAL)
                .build();
        agent2 = Agent.builder()
                .name("Agent 2")
                .firstName("Jane")
                .lastName("Smith")
                .branchAddress("Branch 2")
                .specialization(Agent.Specialization.CORPORATE)
                .build();
        agentRepository.saveAll(List.of(agent1, agent2));

        team1 = Team.builder()
                .name("Team 1")
                .description("Description 1")
                .agent(agent1)
                .build();
        team2 = Team.builder()
                .name("Team 2")
                .description("Description 2")
                .agent(agent2)
                .build();
    }

    @Test
    public void testTeamRepository() {
        assertNotNull(teamRepository);
    }

    @Test
    public void testFetchAllTeams() {
        teamRepository.saveAll(List.of(team1, team2));
        Iterable<Team> teams = teamRepository.findAll();
        assertNotNull(teams);
        int count = 0;
        for (Team team : teams) {
            count++;
        }
        assertEquals(2, count);
    }

    @Test
    public void testSaveTeam() {
        Team savedTeam = teamRepository.save(team1);
        entityManager.flush();
        Optional<Team> fetchedTeam = teamRepository.findById(savedTeam.getId());
        assertTrue(fetchedTeam.isPresent());
        assertEquals(team1.getName(), fetchedTeam.get().getName());
        assertEquals(team1.getDescription(), fetchedTeam.get().getDescription());
    }

    @Test
    public void testFindByName() {
        teamRepository.save(team1);
        entityManager.flush();
        List<Team> teams = teamRepository.findByName("Team 1");
        assertFalse(teams.isEmpty());
        assertEquals(1, teams.size());
        assertEquals(team1.getName(), teams.get(0).getName());
    }

    @Test
    public void testUpdateTeam() {
        Team savedTeam = teamRepository.save(team1);
        savedTeam.setName("Updated Team");
        savedTeam.setDescription("Updated Description");
        teamRepository.save(savedTeam);
        entityManager.flush();
        entityManager.clear();

        Optional<Team> updatedTeam = teamRepository.findById(savedTeam.getId());
        assertTrue(updatedTeam.isPresent());
        assertEquals("Updated Team", updatedTeam.get().getName());
        assertEquals("Updated Description", updatedTeam.get().getDescription());
    }

    @Test
    public void testDeleteTeam() {
        Team savedTeam = teamRepository.save(team1);
        long initialCount = teamRepository.count();
        teamRepository.delete(savedTeam);
        entityManager.flush();
        long finalCount = teamRepository.count();
        assertEquals(initialCount - 1, finalCount);
        Optional<Team> deletedTeam = teamRepository.findById(savedTeam.getId());
        assertFalse(deletedTeam.isPresent());
    }

    @Test
    public void testAddAgentToTeam() {
        Team savedTeam = teamRepository.save(team1);
        savedTeam.addAgent(agent2);
        teamRepository.save(savedTeam);
        entityManager.flush();
        entityManager.clear();

        Team fetchedTeam = teamRepository.findById(savedTeam.getId()).orElseThrow();
        assertEquals(2, fetchedTeam.getAgents().size());
        assertTrue(fetchedTeam.getAgents().contains(agent1));
        assertTrue(fetchedTeam.getAgents().contains(agent2));
    }

    @Test
    public void testAddManagerToTeam() {
        Team savedTeam = teamRepository.save(team1);
        savedTeam.addManager(agent1);
        teamRepository.save(savedTeam);
        entityManager.flush();
        entityManager.clear();

        Team fetchedTeam = teamRepository.findById(savedTeam.getId()).orElseThrow();
        assertEquals(1, fetchedTeam.getManagers().size());
        assertTrue(fetchedTeam.getManagers().contains(agent1));
    }

    @Test
    public void testRemoveAgentFromTeam() {
        Team savedTeam = teamRepository.save(team1);
        savedTeam.removeAgent(agent1);
        teamRepository.save(savedTeam);
        entityManager.flush();
        entityManager.clear();

        Team fetchedTeam = teamRepository.findById(savedTeam.getId()).orElseThrow();
        assertTrue(fetchedTeam.getAgents().isEmpty());
    }

    @Test
    public void testRemoveManagerFromTeam() {
        Team savedTeam = teamRepository.save(team1);
        savedTeam.addManager(agent1);
        savedTeam.removeManager(agent1);
        teamRepository.save(savedTeam);
        entityManager.flush();
        entityManager.clear();

        Team fetchedTeam = teamRepository.findById(savedTeam.getId()).orElseThrow();
        assertTrue(fetchedTeam.getManagers().isEmpty());
    }
}