package edu.java.neo4jProject.batch;

import java.util.HashMap;
import java.util.Map;

import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.kernel.impl.batchinsert.BatchInserter;
import org.neo4j.kernel.impl.batchinsert.BatchInserterImpl;

public class BatchDBSetUp {

	private static final int N_NODES = 10000;
	private static final int N_RELATIONS = 1000000;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BatchInserter inserter = new BatchInserterImpl(
				"C:\\Users\\Fede\\Downloads\\neo4j-community-1.8\\data\\graph.db");

		createNodes(inserter);
		createRelationships(inserter);
		inserter.shutdown();

	}

	private static void createRelationships(BatchInserter inserter) {
		int random, random2;

		RelationshipType knows = DynamicRelationshipType.withName("friends");
		Map<String, Object> relationProperties = new HashMap<String, Object>();
		relationProperties.put("__type__",
				"edu.java.neo4jProject.entity.Friendship");

		for (int i = 0; i < N_RELATIONS; i++) {
			random = (int) (Math.random() * ((N_NODES) + 1)) + 1;
			random2 = (int) (Math.random() * ((N_NODES) + 1)) + 1;
			if (random <= N_NODES && random2 <= N_NODES)
				inserter.createRelationship(random, random2, knows,
						relationProperties);
		}
	}

	private static void createNodes(BatchInserter inserter) {
		Map<String, Object> nodeProperties = new HashMap<String, Object>();
		nodeProperties.put("__type__", "edu.java.neo4jProject.entity.User");

		for (int i = 1; i <= N_NODES; i++) {
			nodeProperties.put("username", "" + 1);
			inserter.createNode(nodeProperties);
		}
	}
}
